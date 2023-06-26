package com.example.spring_iac_api.advice;

import com.example.spring_iac_api.dto.ErrorResult;
import com.example.spring_iac_api.exception.AuthKeyInvalidException;
import com.example.spring_iac_api.exception.MemberDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class, MemberDuplicateException.class})
    public ResponseEntity<ErrorResult> illegalExceptionHandle(Exception e){
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST,e.getMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthKeyInvalidException.class})
    public ResponseEntity<ErrorResult> unauthorizedExceptionHandle(Exception e){
        ErrorResult errorResult = new ErrorResult(HttpStatus.UNAUTHORIZED, e.getMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.UNAUTHORIZED);
    }

}
