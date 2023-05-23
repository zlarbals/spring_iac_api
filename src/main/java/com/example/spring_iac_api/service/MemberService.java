package com.example.spring_iac_api.service;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.domain.UseStatus;
import com.example.spring_iac_api.dto.MemberResponseDto;
import com.example.spring_iac_api.dto.SignUpRequestDto;
import com.example.spring_iac_api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        Member member = Member.builder()
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .useYn(UseStatus.Y)
                .build();

        //TODO 패스워드 암호화

        Member newMember = memberRepository.save(member);

        return new MemberResponseDto(newMember);
    }
}
