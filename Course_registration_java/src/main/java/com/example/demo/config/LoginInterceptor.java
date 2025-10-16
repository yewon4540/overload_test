package com.example.demo.config;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String uri = request.getRequestURI();

    // 허용 경로: 로그인 화면/액션, 루트, 정적, 헬스체크 등
    if (uri.equals("/") || uri.startsWith("/login") || uri.startsWith("/actuator")
        || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/images")) {
      return true;
    }

    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("user") != null) {
      return true; // 로그인됨
    }

    response.sendRedirect("/login");
    return false;
  }
}
