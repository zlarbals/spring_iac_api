package com.example.spring_iac_api.exception;

public class MemberDuplicateException extends RuntimeException{

    public MemberDuplicateException(String message){
        super(message);
    }

}
