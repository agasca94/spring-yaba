package com.gasca.yaba.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceNotFoundAdvice {
    
    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, String> resoruceNotFoundHandler(ResourceNotFoundException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return body;
    }

}
