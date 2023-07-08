package com.example.spring_iac_api.dto;

import com.example.spring_iac_api.domain.Member;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberSyncResponseDto {

    private Long seq;

    private String email;

    private String useYn;

    private LocalDate disabledDate;

    public MemberSyncResponseDto(Member member){
        this.seq = member.getSeq();
        this.email = member.getEmail();
        this.useYn = member.getUseYn().name();
        this.disabledDate = member.getDisabledDate();
    }

}
