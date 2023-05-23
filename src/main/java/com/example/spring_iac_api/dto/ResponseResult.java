package com.example.spring_iac_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ResponseResult<T> {

    private HttpStatus httpStatus;

    private T response;

}
