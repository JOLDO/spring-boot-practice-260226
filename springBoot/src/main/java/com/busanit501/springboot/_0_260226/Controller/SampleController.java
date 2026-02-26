package com.busanit501.springboot._0_260226.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class SampleController {

    @GetMapping("/_0_260226/hello")
    public void hello(Model model) {
        log.info("출력 hello 확인...");
        model.addAttribute("msg", "헬로우 월드!!! Hello World!!!");
    }
}
