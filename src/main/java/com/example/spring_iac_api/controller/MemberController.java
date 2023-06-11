package com.example.spring_iac_api.controller;

import com.example.spring_iac_api.dto.MemberResponseDto;
import com.example.spring_iac_api.dto.ResponseResult;
import com.example.spring_iac_api.dto.MemberRequestDto;
import com.example.spring_iac_api.service.MemberService;
import com.example.spring_iac_api.util.PromisedReturnMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseResult> signUp(@Validated @RequestBody MemberRequestDto memberRequestDto, Errors errors){

        if(errors.hasErrors()){
            log.error("sign up error : {}",errors);
            throw new IllegalArgumentException(PromisedReturnMessage.WRONG_FORMAT);
        }

        MemberResponseDto memberResponseDto = memberService.signUp(memberRequestDto);

        return new ResponseEntity<>(new ResponseResult(HttpStatus.CREATED,memberResponseDto),HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseResult> signIn(@RequestBody MemberRequestDto memberRequestDto){
        MemberResponseDto memberResponseDto = memberService.signIn(memberRequestDto);

        return new ResponseEntity<>(new ResponseResult(HttpStatus.OK,memberResponseDto), HttpStatus.OK);
    }

}
