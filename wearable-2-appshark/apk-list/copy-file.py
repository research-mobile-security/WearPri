import os
import shutil
import pandas as pd

# Đường dẫn file CSV
csv_path = r"C:\Users\ASUS\anaconda3-project-code\wearable-2-appshark\apk-list\wearable-project.wearable-app.csv"

# Thư mục chứa APK gốc
source_dir = r"C:\Users\ASUS\anaconda3-project-code\wearable-standard-app\apk-final"

# Thư mục đích
dest_dir = r"F:\wearable-2-appshark\wearable-app"

# Đảm bảo thư mục đích tồn tại
os.makedirs(dest_dir, exist_ok=True)

# Đọc file CSV
df = pd.read_csv(csv_path)

# Duyệt từng giá trị trong cột "_id"
for app_id in df["_id"]:
    apk_name = f"{app_id}.apk"  # thêm extension .apk
    source_path = os.path.join(source_dir, apk_name)
    dest_path = os.path.join(dest_dir, apk_name)

    if os.path.exists(source_path):
        shutil.copy2(source_path, dest_path)
        print(f"Copied: {apk_name}")
    else:
        print(f"Not found: {apk_name}")
