package com.example.spring_iac_api.dto;

import com.example.spring_iac_api.domain.Member;

import java.time.LocalDate;

public class MemberResponseDto {

    private String email;

    private LocalDate createdDate;

    public MemberResponseDto(Member member){
        this.email = member.getEmail();
        this.createdDate = member.getCreatedDate();
    }

}
