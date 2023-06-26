package com.example.spring_iac_api.dto;

import com.example.spring_iac_api.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String email;

    private LocalDate createdDate;

    private String accessToken;

    private String refreshToken;

    public MemberResponseDto(Member member){
        this.email = member.getEmail();
        this.createdDate = member.getCreatedDate();
    }

    public MemberResponseDto(Member member, String accessToken, String refreshToken){
        this.email = member.getEmail();
        this.createdDate = member.getCreatedDate();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
