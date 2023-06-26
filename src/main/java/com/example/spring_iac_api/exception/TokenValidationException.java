package com.example.spring_iac_api.exception;

public class TokenValidationException extends RuntimeException{

    public TokenValidationException(String message){
        super(message);
    }

}
