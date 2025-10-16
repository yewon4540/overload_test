package com.example.demo.controller;

import com.example.demo.model.Course;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller; // ★ 반드시 이 import가 있어야 합니다.
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

  // 루트 → 로그인으로 리다이렉트
  @GetMapping("/")
  public String home() {
    return "redirect:/login";
  }

  // 로그인 뷰
  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }

  // 로그인 처리 (테스트 계정: user/1234)
  @PostMapping("/login")
  public String login(@RequestParam String username,
                      @RequestParam String password,
                      HttpSession session,
                      Model model) {
    if ("user".equals(username) && "1234".equals(password)) {
      session.setAttribute("user", username);
      return "redirect:/course";
    }
    model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
    return "login";
  }

  // 로그아웃
  @PostMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }

  // 수강신청 메인
  @GetMapping("/course")
  public String course(Model model) {
    List<Course> courses = List.of(
        new Course(1, "파이썬 기초", "데이터 타입, 제어문, 함수 중심 실습 위주 강의"),
        new Course(2, "웹 개발 입문", "HTTP, HTML/CSS/JS, 간단한 서버구성"),
        new Course(3, "데이터베이스", "관계형 모델, SQL, 인덱스/실행계획 기초"),
        new Course(4, "클라우드 개론", "AWS 핵심 서비스와 비용/운영 베스트프랙티스")
    );
    model.addAttribute("courses", courses);
    return "course";
  }

  // 각 코스 상세
  @GetMapping("/course/{id}")
  public String courseDetail(@PathVariable int id, Model model) {
    model.addAttribute("id", id);
    switch (id) {
      case 1 -> {
        model.addAttribute("title", "파이썬 기초");
        model.addAttribute("summary", "자료형/제어문/함수 중심의 실습형 커리큘럼");
        model.addAttribute("bullets", List.of(
            "파이썬 문법 핵심 요약",
            "코딩 도장 실습 과제",
            "간단한 CLI 프로젝트 완성"
        ));
        return "course1";
      }
      case 2 -> {
        model.addAttribute("title", "웹 개발 입문");
        model.addAttribute("summary", "프론트/백엔드 기초와 HTTP 흐름 이해");
        model.addAttribute("bullets", List.of(
            "HTML/CSS 레이아웃",
            "Fetch API & 폼 처리",
            "미니 블로그 서버"
        ));
        return "course2";
      }
      case 3 -> {
        model.addAttribute("title", "데이터베이스");
        model.addAttribute("summary", "SQL과 인덱스 개념, 정규화/트랜잭션");
        model.addAttribute("bullets", List.of(
            "CRUD & JOIN 집중",
            "실행 계획 읽기",
            "ORM 기초"
        ));
        return "course3";
      }
      case 4 -> {
        model.addAttribute("title", "클라우드 개론");
        model.addAttribute("summary", "AWS 버튼-투-클릭 데모와 비용 최적화");
        model.addAttribute("bullets", List.of(
            "VPC/EC2/S3/RDS 체험",
            "모니터링/알림",
            "보안/비용 체크리스트"
        ));
        return "course4";
      }
      default -> {
        return "redirect:/course";
      }
    }
  }
}
