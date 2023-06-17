package com.example.spring_iac_api.service;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.domain.UseStatusYn;
import com.example.spring_iac_api.dto.MemberResponseDto;
import com.example.spring_iac_api.dto.MemberRequestDto;
import com.example.spring_iac_api.exception.MemberDuplicateException;
import com.example.spring_iac_api.repository.MemberRepository;
import com.example.spring_iac_api.util.JwtTokenProvider;
import com.example.spring_iac_api.util.PromisedReturnMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public MemberResponseDto signUp(MemberRequestDto memberRequestDto) {

        validateDuplicateMember(memberRequestDto.getEmail());

        Member member = Member.builder()
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .useYn(UseStatusYn.Y)
                .build();

        Member newMember = memberRepository.save(member);

        return new MemberResponseDto(newMember);
    }

    private void validateDuplicateMember(String email) {

        if (memberRepository.existsMemberByEmail(email)){
            throw new MemberDuplicateException(PromisedReturnMessage.DUPLICATE_EMAIL);
        }

    }

    public MemberResponseDto signIn(MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findMemberByEmail(memberRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(PromisedReturnMessage.FAIL_LOGIN));

        if(!passwordEncoder.matches(memberRequestDto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException(PromisedReturnMessage.FAIL_LOGIN);
        }

        String accessToken= jwtTokenProvider.generateAccessToken(member.getEmail());
        String refreshToken= jwtTokenProvider.generateRefreshToken(member.getEmail());

        return new MemberResponseDto(member,accessToken,refreshToken);
    }

    public MemberResponseDto refreshToken(String accessToken, String refreshToken) {
        boolean isAccessTokenValid = jwtTokenProvider.validateAccessToken(accessToken);
        boolean isRefreshTokenValid = jwtTokenProvider.validateRefreshToken(refreshToken);


        String memberEmail = jwtTokenProvider.getMemberEmail(accessToken, refreshToken);
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(memberEmail);
        if(ObjectUtils.isEmpty(memberEmail) || optionalMember.isEmpty()){
            return new MemberResponseDto();
        }

        Member member = optionalMember.get();

        if(isAccessTokenValid && isRefreshTokenValid){
          return new MemberResponseDto(member,accessToken,refreshToken);
        } else if(!isAccessTokenValid && isRefreshTokenValid){
            String newAccessToken = jwtTokenProvider.generateAccessToken(memberEmail);
            return new MemberResponseDto(member,newAccessToken,refreshToken);
        }else{
            return new MemberResponseDto();
        }
    }
}
