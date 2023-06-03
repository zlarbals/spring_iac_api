package com.example.spring_iac_api.service;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.domain.UseStatus;
import com.example.spring_iac_api.dto.MemberResponseDto;
import com.example.spring_iac_api.dto.SignUpRequestDto;
import com.example.spring_iac_api.exception.MemberDuplicateException;
import com.example.spring_iac_api.repository.MemberRepository;
import com.example.spring_iac_api.util.PromisedReturnMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        validateDuplicateMember(signUpRequestDto.getEmail());

        Member member = Member.builder()
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .useYn(UseStatus.Y)
                .build();

        Member newMember = memberRepository.save(member);

        return new MemberResponseDto(newMember);
    }

    private void validateDuplicateMember(String email) {

        if (memberRepository.existsMemberByEmail(email)){
            throw new MemberDuplicateException(PromisedReturnMessage.DUPLICATE_EMAIL);
        }

    }
}
