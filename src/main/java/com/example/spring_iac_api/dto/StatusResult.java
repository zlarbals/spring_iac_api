package com.example.spring_iac_api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class StatusResult {

    private HttpStatus httpStatus;

}
