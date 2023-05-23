package com.example.spring_iac_api.controller;

import com.example.spring_iac_api.dto.MemberResponseDto;
import com.example.spring_iac_api.dto.ResponseResult;
import com.example.spring_iac_api.dto.SignUpRequestDto;
import com.example.spring_iac_api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseResult> signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        MemberResponseDto memberResponseDto = memberService.signUp(signUpRequestDto);

        return new ResponseEntity<>(new ResponseResult(HttpStatus.CREATED,memberResponseDto),HttpStatus.CREATED);
    }

}
