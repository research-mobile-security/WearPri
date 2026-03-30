import pandas as pd
from pathlib import Path
import json

# === Paths gốc ===
ROOT = Path(r"C:\Users\ASUS\anaconda3-project-code\wearable-2-appshark")
CSV_PATH = ROOT / r"apk-list\wearable-project.wearable-app.csv"

#APK_STANDALONE_DIR = ROOT / "app-standalone"
APK_DIR = Path(r"F:\wearable-2-appshark\wearable-app")
OUT_ROOT = Path(r"F:\wearable-2-appshark\appshark-output\app-non-standalone")
CONFIG_ROOT = ROOT / r"config\config-non-standalone"
RULE_PATH = ROOT / r"config\rules-2"

# Đảm bảo các thư mục gốc tồn tại
OUT_ROOT.mkdir(parents=True, exist_ok=True)
CONFIG_ROOT.mkdir(parents=True, exist_ok=True)

# 1) Đọc CSV, lấy cột _id và thêm .apk
df = pd.read_csv(CSV_PATH)
ids = (
    df["_id"]
    .dropna()
    .astype(str)
    .str.strip()
)

for app_id in ids:
    apk_file_name = f"{app_id}.apk"

    # 2) Tạo thư mục out: appshark-output/app-non-standalone/{APK file name}
    out_dir = OUT_ROOT / apk_file_name
    out_dir.mkdir(parents=True, exist_ok=True)

    # 3) Tạo nội dung file config dựa trên APK file name
    payload = {
        "apkPath": (APK_DIR / apk_file_name).as_posix(),
        "out": out_dir.as_posix(),
        "rulePath": RULE_PATH.as_posix(),
        "logLevel": 1,
        "javaSource": True
    }

    # 4) Lưu file: config/config-non-standalone/config-{apk_file_name}.json5
    config_path = CONFIG_ROOT / f"config-{apk_file_name}.json5"
    with open(config_path, "w", encoding="utf-8") as f:
        json.dump(payload, f, ensure_ascii=False, indent=2)

    print(f"Created: {config_path}")
