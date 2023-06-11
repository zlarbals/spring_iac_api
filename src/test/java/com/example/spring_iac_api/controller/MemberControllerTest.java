package com.example.spring_iac_api.controller;

import com.example.spring_iac_api.dto.MemberRequestDto;
import com.example.spring_iac_api.service.MemberService;
import com.example.spring_iac_api.util.PromisedReturnMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원가입 테스트 - 정상 회원가입")
    @Test
    public void testMemberSignUp() throws Exception {
        //case
        String email = "1234@gmail.com";
        String password = "12345678";
        MemberRequestDto memberRequestDto = new MemberRequestDto(email,password);
        String memberRequestDtoToJson = objectMapper.writeValueAsString(memberRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(memberRequestDtoToJson))
                .andDo(print());

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.email").value(email))
                .andExpect(jsonPath("$.response.accessToken").isEmpty())
                .andExpect(jsonPath("$.response.refreshToken").isEmpty());
    }

    @DisplayName("회원가입 테스트 - dto 이메일 유효성 검사")
    @Test
    public void testMemberSignUpWithDtoEmailValidTest() throws Exception {

        //case
        String email = "1234";
        String password = "12345678";
        MemberRequestDto memberRequestDto = new MemberRequestDto(email,password);
        String memberRequestDtoToJson = objectMapper.writeValueAsString(memberRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(memberRequestDtoToJson))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @DisplayName("회원가입 테스트 - dto 패스워드 유효성 검사")
    @Test
    public void testMemberSignUpWithDtoPasswordValidTest() throws Exception {

        //case
        String email = "1234@gmail.com";
        String password = "123456";
        MemberRequestDto memberRequestDto = new MemberRequestDto(email,password);
        String memberRequestDtoToJson = objectMapper.writeValueAsString(memberRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(memberRequestDtoToJson))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @DisplayName("로그인 테스트 - 정상 로그인")
    @Test
    public void testMemberSignIn() throws Exception {
        //case
        String signUpEmail = "1234@gmail.com";
        String signUpPassword = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail,signUpPassword));

        String signInEmail = "1234@gmail.com";
        String signInPassword = "12345678";
        MemberRequestDto memberRequestDto = new MemberRequestDto(signInEmail,signInPassword);
        String memberRequestDtoToJson = objectMapper.writeValueAsString(memberRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/member/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(memberRequestDtoToJson))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.response.email").value(signInEmail))
                .andExpect(jsonPath("$.response.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.response.refreshToken").isNotEmpty());
    }

    @DisplayName("로그인 테스트 - 회원가입 안한 사용자")
    @Test
    public void testMemberSignInWithNotSignUpUser() throws Exception {
        //case
        String signUpEmail = "1234@gmail.com";
        String signUpPassword = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail,signUpPassword));

        String signInEmail = "12345@gmail.com";
        String signInPassword = "12345678";
        MemberRequestDto memberRequestDto = new MemberRequestDto(signInEmail,signInPassword);
        String memberRequestDtoToJson = objectMapper.writeValueAsString(memberRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/member/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(memberRequestDtoToJson))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(PromisedReturnMessage.FAIL_LOGIN));
    }

    @DisplayName("로그인 테스트 - 잘못된 패스워드")
    @Test
    public void testMemberSignInWithWrongPassword() throws Exception {
        //case
        String signUpEmail = "1234@gmail.com";
        String signUpPassword = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail,signUpPassword));

        String signInEmail = "1234@gmail.com";
        String signInPassword = "123456789";
        MemberRequestDto memberRequestDto = new MemberRequestDto(signInEmail,signInPassword);
        String memberRequestDtoToJson = objectMapper.writeValueAsString(memberRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/member/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(memberRequestDtoToJson))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(PromisedReturnMessage.FAIL_LOGIN));
    }
}