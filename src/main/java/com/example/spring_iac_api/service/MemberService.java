package com.example.spring_iac_api.service;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.domain.UseStatusYn;
import com.example.spring_iac_api.dto.MemberResponseDto;
import com.example.spring_iac_api.dto.MemberRequestDto;
import com.example.spring_iac_api.dto.MemberSyncResponseDto;
import com.example.spring_iac_api.exception.MemberDuplicateException;
import com.example.spring_iac_api.exception.TokenValidationException;
import com.example.spring_iac_api.repository.MemberRepository;
import com.example.spring_iac_api.util.jwt.JwtTokenProvider;
import com.example.spring_iac_api.util.PromisedReturnMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

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

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String accessToken= jwtTokenProvider.generateAccessToken(member.getEmail());
        String refreshToken= jwtTokenProvider.generateRefreshToken(member.getEmail());

        return new MemberResponseDto(member,accessToken,refreshToken);
    }

    public MemberResponseDto refreshToken(String email, String refreshToken) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        boolean isRefreshTokenValid = jwtTokenProvider.validateRefreshToken(refreshToken);
        if(!isRefreshTokenValid){
            throw new TokenValidationException(PromisedReturnMessage.TOKEN_NOT_VALIDATION);
        }

        String memberEmail = jwtTokenProvider.getMemberEmail(email, refreshToken);
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(memberEmail);
        if(ObjectUtils.isEmpty(memberEmail) || optionalMember.isEmpty()){
            throw new TokenValidationException(PromisedReturnMessage.TOKEN_NOT_VALIDATION);
        }

        Member member = optionalMember.get();
        String newAccessToken = jwtTokenProvider.generateAccessToken(memberEmail);
        return new MemberResponseDto(member, newAccessToken,refreshToken);
    }

    public List<MemberSyncResponseDto> getAllMemberByServiceName(String serviceName) {
        List<Member> memberListByServiceName = memberRepository.findMembersByMembershipList(serviceName);

        List<MemberSyncResponseDto> memberSyncResponseDtoList = new ArrayList<>();
        memberListByServiceName.forEach(member -> {
            MemberSyncResponseDto memberSyncResponseDto = new MemberSyncResponseDto(member);
            memberSyncResponseDtoList.add(memberSyncResponseDto);
        });


        return memberSyncResponseDtoList;
    }
}
