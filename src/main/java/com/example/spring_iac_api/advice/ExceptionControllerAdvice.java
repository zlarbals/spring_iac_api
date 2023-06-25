package com.example.spring_iac_api.advice;

import com.example.spring_iac_api.dto.ErrorResult;
import com.example.spring_iac_api.exception.MemberDuplicateException;
import com.example.spring_iac_api.exception.TokenValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class, MemberDuplicateException.class, TokenValidationException.class})
    public ResponseEntity<ErrorResult> illegalExceptionHandle(Exception e){
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST,e.getMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }

}
