import subprocess
from pathlib import Path
import pandas as pd
import time
import sys

# === Fixed paths ===
ROOT = Path(r"C:\Users\ASUS\anaconda3-project-code\wearable-2-appshark")
CSV_PATH = ROOT / r"apk-list\wearable-project.wearable-app.csv"

JAR_PATH = ROOT / r'build\libs\AppShark-0.1.2-all.jar'
CONFIG_DIR = ROOT / r'config\config-non-standalone'

# Log folder
LOG_DIR = Path(r"F:\wearable-2-appshark\logs\run-non-standalone")
LOG_DIR.mkdir(parents=True, exist_ok=True)

# === Run a single app ===
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

            # Stream output to both console and log file
            proc = subprocess.Popen(
                cmd,
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                text=True,
                cwd=str(ROOT)   # run from project root so relative paths resolve correctly
            )

            for line in proc.stdout:
                lf.write(line); lf.flush()
                print(line, end="")

            proc.wait()
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

# === Main: read CSV, filter pending apps, run sequentially, update CSV ===
def main():
    # Read CSV
    df = pd.read_csv(CSV_PATH)

    # Ensure 'status' column exists (insert next to '_id')
    if "status" not in df.columns:
        df.insert(df.columns.get_loc("_id") + 1, "status", "")

    # Normalize 'status' to string for empty check
    df["status"] = df["status"].astype(str)

    # Pending means empty string or "nan"
    mask_pending = (df["status"].str.strip() == "") | (df["status"].str.lower().eq("nan"))
    pending_ids = (
        df.loc[mask_pending, "_id"]
        .dropna()
        .astype(str)
        .str.strip()
        .tolist()
    )
    # print(len(pending_ids))
    # print(pending_ids)
    if not pending_ids:
        print("No pending apps (status is empty). Nothing to run.")
        return

    print(f"Will run {len(pending_ids)} pending apps...\n")

    try:
        for i, app_id in enumerate(pending_ids, 1):
            print(f"\n=== [{i}/{len(pending_ids)}] Run: {app_id} ===")
            status = run_one(app_id)

            # Update status for this _id
            df.loc[df["_id"] == app_id, "status"] = status

            # Persist progress after each app
            df.to_csv(CSV_PATH, index=False, encoding="utf-8")

            print(f"=== Done: {app_id} -> {status} ===\n")
    except KeyboardInterrupt:
        print("\nInterrupted by user. Saving progress...", file=sys.stderr)
        df.to_csv(CSV_PATH, index=False, encoding="utf-8")
        raise
    except Exception as e:
        print(f"\nUnexpected error: {e}. Saving progress...", file=sys.stderr)
        df.to_csv(CSV_PATH, index=False, encoding="utf-8")
        raise

    print("All pending apps completed.")

if __name__ == "__main__":
    main()
