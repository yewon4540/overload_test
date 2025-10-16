import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns, os, glob

result_files = sorted(glob.glob("result/*_stats.csv"))
if not result_files:
    print("[!] 결과 파일을 찾을 수 없습니다.")
    exit()

latest_file = result_files[-1]
print(f"[*] 분석 중: {latest_file}")

df = pd.read_csv(latest_file)
summary = df[["Name", "Requests/s", "Median Response Time", "Failure Count"]]
print("\n[요약 결과]")
print(summary)

plt.figure(figsize=(8, 5))
sns.barplot(data=summary, x="Name", y="Requests/s")
plt.xticks(rotation=45)
plt.title("Requests per second by Endpoint")
plt.tight_layout()

output_path = os.path.join("result", "locust_graph.png")
plt.savefig(output_path)
print(f"[✔] 그래프 저장 완료: {output_path}")
