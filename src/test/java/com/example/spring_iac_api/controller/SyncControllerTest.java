package com.example.spring_iac_api.controller;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.domain.MemberMembershipLink;
import com.example.spring_iac_api.domain.Membership;
import com.example.spring_iac_api.dto.MemberRequestDto;
import com.example.spring_iac_api.repository.MemberMembershipLinkRepository;
import com.example.spring_iac_api.repository.MemberRepository;
import com.example.spring_iac_api.repository.MembershipRepository;
import com.example.spring_iac_api.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("local")
class SyncControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MemberMembershipLinkRepository memberMembershipLinkRepository;



    @DisplayName("회원 동기화 테스트")
    @Test
    public void testSyncMember() throws Exception{
        //case
        //Member 추가
        String signUpEmail1 = "12@gmail.com";
        String signUpPassword1 = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail1,signUpPassword1));
        Member member1 = memberRepository.findMemberByEmail(signUpEmail1).get();

        String signUpEmail2 = "34@gmail.com";
        String signUpPassword2 = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail2,signUpPassword2));
        Member member2 = memberRepository.findMemberByEmail(signUpEmail2).get();

        //Membership 추가
        String serviceName1 = "test1";
        Membership membership1 = new Membership(serviceName1,"test1");
        Membership savedMembership1 = membershipRepository.save(membership1);

        String serviceName2 = "test2";
        Membership membership2 = new Membership(serviceName2,"test2");
        Membership savedMembership2 = membershipRepository.save(membership2);

        //MemberMembershipLink 추가
        MemberMembershipLink memberMembershipLink1 = new MemberMembershipLink(null,member1,savedMembership1);
        memberMembershipLinkRepository.save(memberMembershipLink1);

        MemberMembershipLink memberMembershipLink2 = new MemberMembershipLink(null,member2,savedMembership2);
        memberMembershipLinkRepository.save(memberMembershipLink2);

        //when
        ResultActions resultActions = mockMvc.perform(get("/sync/member")
                        .header("AuthKey", "test1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response",hasSize(1)));


    }

    @DisplayName("회원 동기화 테스트 - 헤더에 없는 서비스 이름 입력")
    @Test
    public void testSyncMemberWithInvalidServiceNameInHeader() throws Exception{
        //case
        //Member 추가
        String signUpEmail1 = "12@gmail.com";
        String signUpPassword1 = "12345678";
        memberService.signUp(new MemberRequestDto(signUpEmail1,signUpPassword1));
        Member member1 = memberRepository.findMemberByEmail(signUpEmail1).get();

        //Membership 추가
        String serviceName1 = "test1";
        Membership membership1 = new Membership(serviceName1,"test1");
        Membership savedMembership1 = membershipRepository.save(membership1);

        //MemberMembershipLink 추가
        MemberMembershipLink memberMembershipLink1 = new MemberMembershipLink(null,member1,savedMembership1);
        memberMembershipLinkRepository.save(memberMembershipLink1);

        //when
        ResultActions resultActions = mockMvc.perform(get("/sync/member")
                        .header("AuthKey", "test2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());

        //then
        resultActions.andExpect(status().isUnauthorized());

    }

}