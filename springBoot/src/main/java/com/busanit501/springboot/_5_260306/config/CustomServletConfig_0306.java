package com.busanit501.springboot._5_260306.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc   //스프링부트에서 자동 mvc설정을 해주지만 정적자원을 직접 제어할때 사용
public class CustomServletConfig_0306 implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
// 웹브라우저에서, http://localhost:8080/js/test.js 연결 시도.
// 프로젝트 폴더의 /static/js/ 연결 시켜주는 기능.
        //추후 js와 css를 분리할때 읽어올수 있도록 세팅
        registry.addResourceHandler("/js/**")   //resources/static/js폴더 인식
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/css/**")  //resources/static/css폴더 인식
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}