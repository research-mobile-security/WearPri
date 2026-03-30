from pymongo import MongoClient, ASCENDING
import os
import javalang
from typing import List
import shutil
import json
# Load environment
from dotenv import load_dotenv
# OpenAPI model
import openai
from langchain_core.prompts import ChatPromptTemplate
from langchain.prompts import PromptTemplate
from langchain_openai import ChatOpenAI
from langchain.chat_models import init_chat_model
from langchain_community.vectorstores import FAISS
from langchain_community.document_loaders import TextLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.text_splitter import Language
from langchain_openai import OpenAIEmbeddings  
from langchain.chains import RetrievalQA
from langchain.vectorstores.base import VectorStoreRetriever
# Embedding model
from langchain.document_loaders import TextLoader
from langchain_text_splitters import (
    Language,
    RecursiveCharacterTextSplitter,
)
from langchain.vectorstores import FAISS
from langchain_core.language_models import BaseLanguageModel
from langchain.schema import Document
from langchain.chains.retrieval import create_retrieval_chain
from langchain.chains.combine_documents import create_stuff_documents_chain
from langchain.prompts import ChatPromptTemplate
from langchain_core.runnables import RunnableLambda 
from typing import List, Dict, Tuple
import re
import json
from typing import Literal
import tiktoken

vectorDB_directory = r"C:\Users\ASUS\anaconda3-project-code\wearable-2-rag-for-sdk\vectorDB_from_JAVA"
code_block_root_directory = r"E:\wearable-2-dataset-code-extraction"
# server
mongoDB_uri = 'mongodb://localhost:27017'
mongoDB_database = 'wearable-project-2' 
mongoDB_collection = 'evaluate-responsible-entity'
# Setup model
# Load environment variables
load_dotenv()

# Retrieve API key
api_key = os.getenv("OPENAI_API_KEY")
# Ensure the API key is correctly set
if not api_key:
    raise ValueError("OPENAI_API_KEY is not set in the environment variables")

# Initialize the ChatOpenAI model
llm = ChatOpenAI(
    model="gpt-4o-mini",
    temperature=0,
    openai_api_key=api_key,  # Ensure you explicitly pass the API key
    base_url="https://api.openai.com/v1",
)

def get_all_subdirectories_under_one_level(root_dir: str):
    """
    Get all subdirectories directly under the given root directory (one level only),
    excluding any directory named 'original' (case-insensitive).
    Return a list of full paths.
    """
    subdirs = []
    for name in os.listdir(root_dir):
        full_path = os.path.join(root_dir, name)
        if os.path.isdir(full_path) and name.lower() != "original":
            subdirs.append(full_path)
    return subdirs
def get_files_with_prefix_and_ext(
    root_dir: str,
    exclude_prefixes: List[str] = None,
    extension: str = ""
):
    """
    Lấy tất cả file trong thư mục (1 cấp).
    Loại bỏ những file có prefix nằm trong danh sách exclude_prefixes.
    Chỉ giữ lại file có phần mở rộng extension (nếu có).
    
    Args:
        root_dir (str): Đường dẫn thư mục gốc.
        exclude_prefixes (List[str]): Danh sách prefix cần loại bỏ (ví dụ ["clean-", "formated-"]).
        extension (str): Đuôi file cần giữ lại (ví dụ ".java").

    Returns:
        List[str]: Danh sách đường dẫn đầy đủ của các file phù hợp.
    """
    if exclude_prefixes is None:
        exclude_prefixes = []

    matched_files = []
    for name in os.listdir(root_dir):
        full_path = os.path.join(root_dir, name)
        if os.path.isfile(full_path):
            # Kiểm tra không bắt đầu bằng bất kỳ prefix nào trong exclude_prefixes
            if not any(name.startswith(p) for p in exclude_prefixes):
                # Kiểm tra extension nếu có
                if extension == "" or name.endswith(extension):
                    matched_files.append(full_path)
    return matched_files
def get_app_package_name(path: str) -> str:
    """
    Return the last directory name from a full path.
    Example:
        E:\\wearable-2-dataset-code-extraction\\adarshurs.android.vlcmobileremote
        -> 'adarshurs.android.vlcmobileremote'
    """
    return os.path.basename(os.path.normpath(path))
def connect_mongodb(uri, db_name, collection_name):
    # Connect to MongoDB 
    client = MongoClient(uri)
    
    # Select the database and collection
    db = client[db_name]
    collection = db[collection_name]
    
    return collection
def ensure_result_indexes(results_col):
    """
    Tạo index để truy vấn nhanh và tránh trùng:
    - app_package_name
    - (app_package_name, file_path) unique → cùng file không insert 2 lần
    """
    results_col.create_index([("app_package_name", ASCENDING)])
    results_col.create_index(
        [("app_package_name", ASCENDING), ("file_path", ASCENDING)],
        unique=True
    )

def to_save_doc(app_package_name: str, tps_name, result_dict: dict) -> dict:
    """
    Chuyển kết quả từ classify_single_java_file_with_rag
    thành document đơn giản để lưu MongoDB.
    """
    file_path = result_dict.get("file_path", "")
    label = result_dict.get("label", "")
    source = ""

    matches = result_dict.get("matches", [])
    if matches and isinstance(matches, list):
        meta = matches[0].get("metadata", {})
        source = meta.get("source", "")

    return {
        "app_package_name": app_package_name,
        "file_path": file_path,
        "label": label,
        "source": source,
        "tps_name":tps_name
    }

def upsert_result(results_col, save_doc: dict):
    """
    Upsert theo (app_package_name, file_path).
    Nếu đã có → cập nhật label/source.
    Nếu chưa có → insert mới (MongoDB tự tạo _id).
    """
    results_col.update_one(
        {
            "app_package_name": save_doc["app_package_name"],
            "tps_name": save_doc["tps_name"],
            "file_path": save_doc["file_path"]
        },
        {"$set": {
            "label": save_doc["label"],
            "source": save_doc["source"]
        }},
        upsert=True
    )
# ====== Load FAISS vectorDB đã có ======
def load_vectorstore(faiss_index_path: str) -> FAISS:
    embeddings = OpenAIEmbeddings()
    vectorstore = FAISS.load_local(
        folder_path=faiss_index_path,
        embeddings=embeddings,
        allow_dangerous_deserialization=True
    )
    return vectorstore

# ====== Static retriever (cho RAG chain) ======
class StaticRetriever:
    def __init__(self, documents: List[Document]):
        self.documents = documents

    def get_relevant_documents(self, query: str) -> List[Document]:
        return self.documents

# ====== Prompt + chain để phân loại implemented / non-implemented ======
def classify_with_rag(llm: ChatOpenAI, code_text: str, retrieved_docs: List[Document]) -> str:
    retriever = StaticRetriever(retrieved_docs)
    runnable_retriever = RunnableLambda(lambda x: retriever.get_relevant_documents(x["input"]))

    prompt = ChatPromptTemplate.from_template("""
You are an expert in Android privacy compliance.
Based on the given CONTEXT (opt-out implementation examples) and the SOURCE CODE below,
determine whether this code implements an opt-out mechanism for collecting user sensitive data.

Answer strictly with the following format:
Label: implemented | non-implemented
Reason: <one short sentence>

CONTEXT:
{context}

SOURCE CODE:
{input}
""")

    combine_docs_chain = create_stuff_documents_chain(llm, prompt)
    rag_chain = create_retrieval_chain(
        retriever=runnable_retriever,
        combine_docs_chain=combine_docs_chain
    )
    result = rag_chain.invoke({"input": code_text})
    return result["answer"]

def parse_label_reason(text: str) -> Tuple[str, str]:
    """
    Parse 'Label: ...' and 'Reason: ...' từ output LLM.
    Trả về (label, reason). Nếu không parse được sẽ fallback hợp lý.
    """
    label_match = re.search(r"Label:\s*(implemented|non-implemented)", text, re.IGNORECASE)
    reason_match = re.search(r"Reason:\s*(.+)", text, re.IGNORECASE | re.DOTALL)

    label = (label_match.group(1).lower() if label_match else "unknown").strip()
    reason = (reason_match.group(1).strip() if reason_match else "").strip()
    return label, reason

# ====== HÀM CHÍNH: phân loại 1 file Java + trả về matches từ FAISS ======
def classify_single_java_file_with_rag(
    file_path: str,
    faiss_index_path: str,
    top_k: int = 3,
    model_name: str = "gpt-4o-mini",
    return_format: Literal["dict", "json"] = "dict",  # <-- NEW
    max_token_limit: int = 128000,  # ≈ giới hạn thực tế cho GPT-4o-mini
) -> Dict | str:
    """
    - Đọc 1 file Java
    - Retrieve top_k documents từ FAISS (vector DB đã embed các ví dụ opt-out)
    - Chạy RAG để phân loại: implemented | non-implemented
    - Trả về: label, reason, và danh sách matches (doc content + metadata + score)
    - return_format: 'dict' (mặc định) hoặc 'json' (string JSON)
    """
    if not os.path.isfile(file_path) or not file_path.endswith(".java"):
        raise ValueError(f"Invalid Java file: {file_path}")
    # Giới hạn token của model (gpt-4o-mini = 128k tokens)
    max_token_limit = 128000  

    # Hàm tính token
    def count_tokens(text: str) -> int:
        try:
            enc = tiktoken.encoding_for_model(model_name)
        except Exception:
            enc = tiktoken.get_encoding("cl100k_base")
        return len(enc.encode(text))
    # 1) Load vectorstore + LLM
    vectorstore = load_vectorstore(faiss_index_path)
    llm = ChatOpenAI(model=model_name, temperature=0)

    # 2) Đọc code nguồn
    with open(file_path, "r", encoding="utf-8", errors="ignore") as f:
        code_text = f.read().strip()
    # 3) Đếm token thực tế
    token_count = count_tokens(code_text)
    if token_count > max_token_limit:
        print(f"⚠️ Skipped file (too large): {file_path} ({token_count} tokens > {max_token_limit})")
        return {
            "file_path": file_path,
            "label": "skipped",
            "reason": f"File too large ({token_count} tokens, limit={max_token_limit})",
            "matches": []
        }

    # 4) Retrieve top_k document (lấy cả score để trả về cho bạn)
    hits = vectorstore.similarity_search_with_score(code_text, k=top_k)
    retrieved_docs = [doc for (doc, score) in hits]

    # 5) Phân loại với RAG
    llm_output = classify_with_rag(llm, code_text, retrieved_docs)
    label, reason = parse_label_reason(llm_output)

    # 6) Chuẩn hoá kết quả matches (content + metadata + score)
    matches = []
    for (doc, score) in hits:
        matches.append({
            "score": float(score),
            "content": doc.page_content,
            "metadata": doc.metadata
        })

    result_obj = {
        "file_path": file_path,
        "label": label,                      # "implemented" | "non-implemented" | "unknown"
        "reason": reason,
        "matches": matches                   # top-k retrieved from FAISS (để biết giống data nào)
    }

    # NEW: cho phép trả về JSON string nếu cần
    if return_format == "json":
        return json.dumps(result_obj, ensure_ascii=False, indent=2)

    return result_obj
# MAIN
level_1_code_directory_arr = get_all_subdirectories_under_one_level(code_block_root_directory)
mongodb_connection = connect_mongodb(mongoDB_uri, mongoDB_database, mongoDB_collection)
ensure_result_indexes(mongodb_connection)
for a in range(295,len(level_1_code_directory_arr)):
    level_1_code_directory_path = level_1_code_directory_arr[a]
    app_package_name = get_app_package_name(level_1_code_directory_path)
    print("App package name: ",app_package_name)
    print("=================================== Loop-"+str(a)+"==="+app_package_name+"===================================")
    print("Level-1 path: ",level_1_code_directory_path)
    level_2_code_directory_arr = get_all_subdirectories_under_one_level(level_1_code_directory_path)
    # print("Level-2 array: ", level_2_code_directory_arr)
    for b in range(len(level_2_code_directory_arr)):
        level_2_code_directory_path = level_2_code_directory_arr[b]
        tps_name = get_app_package_name(level_2_code_directory_path)
        print("+++++++++++ TPS name: ",tps_name)
        # print("--Level-2 path: ",level_2_code_directory_path)
        level_3_code_directory_arr = get_all_subdirectories_under_one_level(level_2_code_directory_path)
        # print("Level-3 array: ", level_3_code_directory_arr)
        for c in range(len(level_3_code_directory_arr)):
            level_3_code_directory_path = level_3_code_directory_arr[c]
            # print("---Level-3 path: ",level_3_code_directory_path)
            # file_path_arr = get_files_with_prefix_and_ext(level_3_code_directory_path, "", ".java")
            file_path_arr = get_files_with_prefix_and_ext(level_3_code_directory_path,["clean-", "formated-"],".java")
            # print("file_path_arr: ",file_path_arr)
            for d in range(len(file_path_arr)):
                java_file_path = file_path_arr[d]
                print("-------------------------------------------------")
                print("++++++++File path: ",java_file_path)
                result_dict = classify_single_java_file_with_rag(file_path=java_file_path,faiss_index_path=vectorDB_directory,top_k=1)
                #result_json = classify_single_java_file_with_rag(file_path=java_file_path,faiss_index_path=vectorDB_directory,top_k=1,return_format="json")
                # In kết quả
                # print(result_dict)
                save_doc = to_save_doc(app_package_name, tps_name, result_dict)
                # print(save_doc)
                upsert_result(mongodb_connection, save_doc)