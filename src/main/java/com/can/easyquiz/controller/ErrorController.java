package com.can.easyquiz.controller;

import com.can.easyquiz.enums.SystemCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ErrorController extends BasicErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @GetMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>(2);
        error.put("code", SystemCode.InnerError.getCode());
        error.put("message", SystemCode.InnerError.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // 更符合错误处理的语义
    }
}