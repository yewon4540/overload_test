# jMeter 부하테스트 시나리오

jMeter 기반 부하테스트 시나리오입니다.  
기본 포트는 `5001`이며, `/login`, `/course`, `/course/<id>` 요청을 순차적으로 테스트합니다.

## 폴더 구조
```
jmeter/
├── dataset/
│   └── users.csv
├── result/
└── scenario/
    └── course_test_plan.jmx
```

## 실행 방법
```bash
cd jmeter
jmeter -n -t scenario/course_test_plan.jmx -l result/results.csv -e -o result/report
```
- `-n`: Non-GUI 모드
- `-t`: 테스트 플랜 지정
- `-l`: raw 결과(csv) 저장
- `-e -o`: HTML 리포트 자동 생성 (`result/report/index.html`)

## 구성 요소 요약

| 구성 요소 | 설명 |
| --- | --- |
| Thread Group | 10명 동시 사용자, 2회 반복 |
| CSV Data Set | `../dataset/users.csv` 로부터 사용자 계정 로드 |
| HTTP Defaults | 기본 URL = `http://localhost:5001` |
| Login 요청 | `POST /login` |
| Course 목록 | `GET /course` |
| Course 상세 페이지 | `/course/1` ~ `/course/4` 자동 반복 |
| Summary Report | `../result/results.csv` 저장 |
| HTML Report 생성 시 | CLI 실행 시 `-e -o ../result/report` 옵션 추가 |

## 결과 확인
- HTML 리포트: `result/report/index.html`
- 지표: 평균/중앙 응답시간, 처리량(Throughput), 성공률/실패률, P90/P95 등
