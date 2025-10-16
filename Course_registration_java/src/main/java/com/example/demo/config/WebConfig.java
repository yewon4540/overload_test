package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginInterceptor())
            .addPathPatterns("/**"); // 전역 적용(허용 경로는 인터셉터 내부에서 필터링)
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    // thymeleaf 기본 설정 사용 (커스텀 필요 시 추가)
  }
}
