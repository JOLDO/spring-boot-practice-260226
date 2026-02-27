package com.busanit501.springboot._1_260227.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SampleRestController_0227 {
    @GetMapping("/_1_260227/helloRest")
    public String[] helloRest() {
        log.info("출력 REST API 확인");
        return new String[] {"ABC", "DEF", "GHI"};
        //화면 없음
    }
}
