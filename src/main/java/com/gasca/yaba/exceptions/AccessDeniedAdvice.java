package com.gasca.yaba.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class AccessDeniedAdvice {
        
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    Map<String, Object> badRequestHanlder(AccessDeniedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Forbidden");
        body.put("message", "You can't perform this action on this resource");
        
        return body;
    }
}
