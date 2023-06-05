package com.example.spring_iac_api.service;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.dto.MemberRequestDto;
import com.example.spring_iac_api.exception.MemberDuplicateException;
import com.example.spring_iac_api.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 테스트 - 정상입력")
    @Test
    public void testMemberSignUp(){

        //case
        String email = "1234@gmail.com";
        String password = "12345678";
        MemberRequestDto memberRequestDto = new MemberRequestDto(email,password);

        //when
        memberService.signUp(memberRequestDto);

        //then
        assertTrue(memberRepository.existsMemberByEmail(email));
    }

    @DisplayName("회원가입 테스트 - 이메일 중복 입력")
    @Test
    public void testMemberSignUpWithDuplicateEmail(){

        //case
        String email = "1234@gmail.com";
        String password = "12345678";
        MemberRequestDto memberRequestDto = new MemberRequestDto(email,password);
        memberService.signUp(memberRequestDto);

        //when
        //then
        assertThrows(MemberDuplicateException.class, () -> memberService.signUp(memberRequestDto));
    }

    @DisplayName("회원가입 테스트 - 패스워드 암호화 확인")
    @Test
    public void testMemberSignUpEncryptionPassword(){
        //case
        String email = "1234@gmail.com";
        String password = "12345678";
        MemberRequestDto memberRequestDto = new MemberRequestDto(email,password);

        //when
        memberService.signUp(memberRequestDto);

        //then
        Member member = memberRepository.findMemberByEmail(email).get();
        assertNotEquals(password,member.getPassword());
        assertTrue(passwordEncoder.matches(password,member.getPassword()));
    }



}