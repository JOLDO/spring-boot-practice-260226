package com.busanit501.springboot._5_260306.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig_0306 {

    @Bean
    public OpenAPI openAPI() {  //스웨거 사용하기 위한 빈 주입
        return new OpenAPI()
                .info(new Info()    //import io.swagger.v3.oas.models.info.Info;
                        .title("레스트 API 테스트")
                        .description("Rest 활용해서 댓글도 구현해보기")
                        .version("1.0.0")
                );
    }

}