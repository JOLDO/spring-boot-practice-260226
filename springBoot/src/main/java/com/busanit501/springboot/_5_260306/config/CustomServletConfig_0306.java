package com.busanit501.springboot._5_260306.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CustomServletConfig_0306 implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
// 웹브라우저에서, http://localhost:8080/js/test.js 연결 시도.
// 프로젝트 폴더의 /static/js/ 연결 시켜주는 기능.
        registry.addResourceHandler("/js/**")   //resources/static/js폴더 인식
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/css/**")  //resources/static/css폴더 인식
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}