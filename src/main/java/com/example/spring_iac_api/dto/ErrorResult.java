package com.example.spring_iac_api.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResult extends StatusResult{

    private String errorMessage;

    public ErrorResult(HttpStatus httpStatus, String errorMessage){
        super(httpStatus);
        this.errorMessage = errorMessage;
    }

}
