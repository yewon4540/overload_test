# Locust 부하테스트 시나리오

Locust 기반 부하테스트 시나리오입니다.  
기본 포트는 `5001`이며, `/login`, `/course`, `/course/<id>` 요청을 순차적으로 테스트합니다.

## 폴더 구조
```
locust/
├── dataset/
│   └── users.csv
├── result/
├── scenario/
│   └── locustfile.py
└── locust.sh
```

## 실행 방법

### 스크립트로 실행
```bash
bash locust.sh
```

### 직접 실행(헤드리스 예시)
```bash
cd locust
locust -f scenario/locustfile.py --headless --host=http://localhost:5001 -u 20 -r 5 -t 1m --csv=result/locust_result
```
- `-u`: 동시 사용자 수
- `-r`: 초당 스폰 속도
- `-t`: 테스트 지속 시간
- `--csv`: 결과 CSV 프리픽스

### UI 모드
```bash
bash UI_mode_locust.sh
```
localhost:8089로 접속해서 확인

## 구성 요소 요약

| 구성 요소 | 설명 |
| --- | --- |
| Thread Group (Users) | 10명 동시 사용자, 2회 반복 *(locust.sh는 20명, 1분 예시)* |
| CSV Data Set | `../dataset/users.csv` 로부터 사용자 계정 로드 |
| HTTP Defaults | 기본 URL = `http://localhost:5001` |
| Login 요청 | `POST /login` |
| Course 목록 | `GET /course` |
| Course 상세 페이지 | `/course/1` ~ `/course/4` 자동 반복 |
| Summary Report | `result/locust_result_*.csv` 저장 |
| HTML/그래프 | `--html report.html`로 HTML 생성 가능, 스크립트는 PNG 그래프(`result/locust_graph.png`) 생성 |

## 결과 확인
- CSV: `result/locust_result_*_stats.csv`
- 그래프(PNG, 스크립트 생성): `result/locust_graph.png`
- (선택) Web UI: `locust -f scenario/locustfile.py --host=http://localhost:5001` 후 `http://localhost:8089`
