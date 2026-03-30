import subprocess
from pathlib import Path
import pandas as pd
import time

# === Đường dẫn cố định ===
ROOT = Path(r"C:\Users\ASUS\anaconda3-project-code\wearable-2-appshark")
CSV_PATH = ROOT / r"apk-list\wearable-project.wearable-standalone.csv"

JAR_PATH = ROOT / r'build\libs\AppShark-0.1.2-all.jar'
CONFIG_DIR = ROOT / r'config\config-standalone'

# Thư mục log
LOG_DIR = ROOT / r"logs\run-standalone"
LOG_DIR.mkdir(parents=True, exist_ok=True)

# App muốn test
TEST_APP_ID = "app.groupcal.www"
apk_file_name = f"{TEST_APP_ID}.apk"
config_path = CONFIG_DIR / f"config-{apk_file_name}.json5"

# Đọc file CSV
df = pd.read_csv(CSV_PATH)

# Nếu chưa có cột status thì thêm
if "status" not in df.columns:
    df.insert(df.columns.get_loc("_id") + 1, "status", "")

def run_one(app_id: str) -> str:
    apk_file_name = f"{app_id}.apk"
    config_path = CONFIG_DIR / f"config-{apk_file_name}.json5"

    if not config_path.exists():
        return "CONFIG_NOT_FOUND"

    cmd = [
        "java", "-jar",
        str(JAR_PATH),
        str(config_path)
    ]

    log_file = LOG_DIR / f"{app_id.replace('.', '_')}.log"
    start = time.time()
    try:
        with open(log_file, "w", encoding="utf-8", errors="ignore") as lf:
            lf.write(f"CMD: {' '.join(cmd)}\n")
            lf.write(f"START: {time.strftime('%Y-%m-%d %H:%M:%S')}\n\n")
            proc = subprocess.run(cmd, stdout=lf, stderr=lf, text=True, check=False)
            dur = time.time() - start
            lf.write(f"\nEND: {time.strftime('%Y-%m-%d %H:%M:%S')}\n")
            lf.write(f"DURATION_SEC: {dur:.1f}\n")

        if proc.returncode == 0:
            return "OK"
        else:
            return f"FAIL({proc.returncode})"
    except FileNotFoundError:
        return "JAVA_OR_JAR_NOT_FOUND"
    except Exception as e:
        return f"ERROR({type(e).__name__})"

# Chạy cho 1 app
status = run_one(TEST_APP_ID)
df.loc[df["_id"] == TEST_APP_ID, "status"] = status
df.to_csv(CSV_PATH, index=False, encoding="utf-8")

print(f"{TEST_APP_ID}: {status}")
