package com.example.spring_iac_api.dto;

import com.example.spring_iac_api.domain.Member;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberResponseDto {

    private String email;

    private LocalDate createdDate;

    public MemberResponseDto(Member member){
        this.email = member.getEmail();
        this.createdDate = member.getCreatedDate();
    }

}
