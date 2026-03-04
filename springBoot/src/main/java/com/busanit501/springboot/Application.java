package com.busanit501.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  //베이스 엔티티를 적용
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
