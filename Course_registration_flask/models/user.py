class User:
    def __init__(self, username, password):
        self.username = username
        self.password = password

    def is_valid(self):
        # 실제 환경에서는 DB 검증, 지금은 하드코딩 예시
        return self.username == 'admin' and self.password == '1234'
