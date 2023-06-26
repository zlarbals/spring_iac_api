package com.example.spring_iac_api.service;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.dto.MemberRequestDto;
import com.example.spring_iac_api.dto.MemberResponseDto;
import com.example.spring_iac_api.exception.MemberDuplicateException;
import com.example.spring_iac_api.exception.TokenValidationException;
import com.example.spring_iac_api.repository.MemberRepository;
import com.example.spring_iac_api.util.jwt.JwtTokenProvider;
import com.example.spring_iac_api.util.time.TestTimeProvider;
import com.example.spring_iac_api.util.time.TimeProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final int REFRESH_TOKEN_EXPIRATION_MINUTES = 60;

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

    @DisplayName("로그인 테스트 - 정상입력")
    @Test
    public void testMemberSignIn(){
        //case
        String signUpEmail = "1234@gmail.com";
        String signUpPassword = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail,signUpPassword));

        String signInEmail = "1234@gmail.com";
        String signInPassword = "12345678";

        //when
        MemberResponseDto memberResponseDto = memberService.signIn(new MemberRequestDto(signInEmail, signInPassword));

        //then
        assertEquals(memberResponseDto.getEmail(),signInEmail);
    }

    @DisplayName("로그인 테스트 - 회원가입 안한 사용자")
    @Test
    public void testMemberSignInWithNotSignUpUser(){
        //case
        String signUpEmail = "1234@gmail.com";
        String signUpPassword = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail,signUpPassword));

        String signInEmail = "1234no@gmail.com";
        String signInPassword = "12345678";

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> memberService.signIn(new MemberRequestDto(signInEmail, signInPassword)));
    }

    @DisplayName("로그인 테스트 - 잘못된 패스워드")
    @Test
    public void testMemberSignInWithWrongPassword(){
        //case
        String signUpEmail = "1234@gmail.com";
        String signUpPassword = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail,signUpPassword));

        String signInEmail = "1234@gmail.com";
        String signInPassword = "123456789";

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> memberService.signIn(new MemberRequestDto(signInEmail, signInPassword)));
    }

    @DisplayName("토큰 리프레시 테스트 - email,RefreshToken 정상")
    @Test
    public void testTokenRefresh(){
        //case
        String signUpEmail = "1234@gmail.com";
        String signUpPassword = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail,signUpPassword));
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String refreshToken = jwtTokenProvider.generateRefreshToken(signUpEmail);

        //when
        MemberResponseDto result = memberService.refreshToken(signUpEmail, refreshToken);

        //then
        assertEquals(signUpEmail,result.getEmail());
        assertNotNull(result.getAccessToken());
        assertEquals(refreshToken,result.getRefreshToken());
    }

    @DisplayName("토큰 리프레시 테스트 - email 정상, RT 만료")
    @Test
    public void testTokenRefreshWithValidEmailAndExpiredRefreshToken(){
        //case
        String signUpEmail = "1234@gmail.com";
        String signUpPassword = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail,signUpPassword));

        TimeProvider timeProvider = new TestTimeProvider(REFRESH_TOKEN_EXPIRATION_MINUTES);
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(timeProvider);
        String refreshToken = jwtTokenProvider.generateRefreshToken(signUpEmail);

        //when
        //then
        assertThrows(TokenValidationException.class, () -> memberService.refreshToken(signUpEmail,refreshToken));
    }

    @DisplayName("토큰 리프레시 테스트 - payload와 다른 email , RT 정상")
    @Test
    public void testTokenRefreshWithInvalidEmailAndValidRt(){
        //case
        String signUpEmail = "1234@gmail.com";
        String signUpPassword = "12345678";
        String invalidEmail = "12345@gmail.com";
        memberService.signUp(new MemberRequestDto(signUpEmail,signUpPassword));

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String refreshToken = jwtTokenProvider.generateRefreshToken(signUpEmail);

        //when
        //then
        assertThrows(TokenValidationException.class, () -> memberService.refreshToken(invalidEmail,refreshToken));
    }

}