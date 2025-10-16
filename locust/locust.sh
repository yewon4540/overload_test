#!/bin/bash
if [ -f .env ]; then
    export $(grep -v '^#' .env | xargs)
else
    echo "[!] .env 파일이 없습니다. 기본값으로 실행합니다."
    HOST=${HOST:-http://localhost:5001}
    USERS=${USERS:-20}
    SPAWN_RATE=${SPAWN_RATE:-5}
    DURATION=${DURATION:-1m}
    OUTPUT_DIR=${OUTPUT_DIR:-result}
fi

TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
RESULT_HTML="${OUTPUT_DIR}/locust_report_${TIMESTAMP}.html"
RESULT_CSV="${OUTPUT_DIR}/locust_result_${TIMESTAMP}.csv"

mkdir -p $OUTPUT_DIR

echo "[*] Locust 부하테스트 시작..."
locust -f scenario/locustfile.py --headless --host=$HOST -u 20 -r 5 -t 1m \
    --csv=${OUTPUT_DIR}/locust_result_${TIMESTAMP} > /dev/null 2>&1

# HTML 리포트 생성 (CSV → HTML)
python3 - <<'EOF'
import pandas as pd, matplotlib.pyplot as plt, seaborn as sns, os
import glob

result_files = sorted(glob.glob("result/locust_result_*_stats.csv"))
if not result_files:
    exit()

df = pd.read_csv(result_files[-1])
summary = df[["Name", "Requests/s", "Median Response Time", "Failure Count"]]
print(summary)

plt.figure(figsize=(8, 5))
sns.barplot(data=summary, x="Name", y="Requests/s")
plt.xticks(rotation=45)
plt.title("Requests per second by Endpoint")
plt.tight_layout()
plt.savefig(os.path.join("result", "locust_graph.png"))
EOF

echo "[✔] 결과 저장 위치: ${RESULT_CSV}, ${RESULT_HTML}"
echo "[✔] 그래프: result/locust_graph.png"
