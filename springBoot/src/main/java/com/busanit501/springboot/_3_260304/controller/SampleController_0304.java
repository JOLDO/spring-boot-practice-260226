package com.busanit501.springboot._3_260304.controller;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
public class SampleController_0304 {

    @GetMapping("/_3_260304/ex/ex3")
    public void ex3(Model model) {
        log.info("/ex/ex3 확인.....");
        List<String> strList = Arrays.asList("111", "BBB", "CCC");
        model.addAttribute("strList", strList);
    }
}
