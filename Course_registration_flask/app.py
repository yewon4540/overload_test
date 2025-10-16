from flask import Flask, render_template, request, redirect, url_for, session, flash
from models.user import User
from models.course import courses

app = Flask(__name__)
app.secret_key = 'supersecretkey'  # 세션용 키 (임시)

@app.route('/', methods=['GET', 'POST'])
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']

        user = User(username, password)
        if user.is_valid():
            session['logged_in'] = True
            session['username'] = username
            return redirect(url_for('course'))
        else:
            flash('로그인 실패! 아이디/비밀번호를 확인하세요.')
            return render_template('login.html')
    return render_template('login.html')


@app.route('/course')
def course():
    if not session.get('logged_in'):
        return redirect(url_for('login'))
    return render_template('course.html', courses=courses)


@app.route('/course/<int:course_id>')
def course_detail(course_id):
    if not session.get('logged_in'):
        return redirect(url_for('login'))

    course = next((c for c in courses if c['id'] == course_id), None)
    if not course:
        return "존재하지 않는 강의입니다.", 404
    return render_template(f'course{course_id}.html', course=course)


@app.route('/logout')
def logout():
    session.clear()
    return redirect(url_for('login'))


if __name__ == '__main__':
    app.run(debug=True, port=5001)
