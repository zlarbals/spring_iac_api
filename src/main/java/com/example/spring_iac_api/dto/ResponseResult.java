package com.example.spring_iac_api.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseResult<T> extends StatusResult{

    private T response;

    public ResponseResult(HttpStatus status,T response){
        super(status);
        this.response = response;
    }

}
