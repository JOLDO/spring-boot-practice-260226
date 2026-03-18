package com.busanit501.springboot._5_260306.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice   // AOP 기반 전역 예외 처리 (컨트롤러에서 발생한 예외를 일괄 가로채서 처리)
//예를 들어 restcontroller에서 BindException이 발생(throw)해주면 handleBindException메서드에서 가로채서 처리)
@Log4j2
public class CustomRestAdvice_0306 {
    @ExceptionHandler(BindException.class)  //유효성체크관련 에러
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String,String>> handleBindException(BindException e) {
        log.error("CustomRestAdvice에서, 에러를 일괄 처리중...e : "+ e);

        Map<String, String> errorMap = new HashMap<>();

        if(e.hasErrors()) {
            BindingResult bindingResult = e.getBindingResult();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(NoSuchElementException.class)  //유효성체크관련 에러
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String,String>> handleNoSuchElementException(NoSuchElementException e) {
        log.error("CustomRestAdvice에서, 에러를 일괄 처리중...e : "+ e);
        log.error("데이터를 찾을 수 없습니다. : "+ e.getMessage());
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("message", "해당 데이터가 존재하지 않습니다.");
        errorMap.getOrDefault("details", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }
}
