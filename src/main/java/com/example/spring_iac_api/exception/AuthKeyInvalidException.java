package com.example.spring_iac_api.exception;

public class AuthKeyInvalidException extends RuntimeException{

    public AuthKeyInvalidException(String message){
        super(message);
    }

}
