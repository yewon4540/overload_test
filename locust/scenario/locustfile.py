from locust import HttpUser, task, between
import csv, itertools, os

# 사용자 목록 로드
def load_users():
    dataset_path = os.path.join(os.path.dirname(__file__), "../dataset/users.csv")
    with open(dataset_path, newline='') as f:
        reader = csv.DictReader(f)
        return itertools.cycle(list(reader))

users = load_users()

class CourseUser(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        user = next(users)
        self.username = user['username']
        self.password = user['password']
        self.login()

    def login(self):
        response = self.client.post(
            "/login",
            data={"username": self.username, "password": self.password},
            allow_redirects=True
        )
        if response.status_code != 200:
            print(f"[FAIL] 로그인 실패: {self.username}")
        else:
            print(f"[OK] 로그인 성공: {self.username}")

    @task(1)
    def view_courses(self):
        self.client.get("/course")

    @task(2)
    def view_course_details(self):
        for i in range(1, 5):
            self.client.get(f"/course/{i}")

    @task(1)
    def logout(self):
        self.client.get("/logout")
