package com.busanit501.springboot._5_260306.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig_0306 {

    @Bean
    public ModelMapper getMapper_0306() {   //model mapper를 빈으로 주입(여러개가 있어서 사용할땐 @Qualifier("getMapper_0306")로 지정해서 주입해줌)
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setFieldMatchingEnabled(true)  //필드 직접 매핑 허용
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)  //private까지 접근 가능
            .setMatchingStrategy(MatchingStrategies.STRICT);    //필드명 정확히 일치해야 매핑
        return modelMapper;
    }
}
