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
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/refresh")
    public ResponseEntity<ResponseResult> refreshToken(@RequestHeader("Authorization") String bearerRefreshToken, @RequestParam("email") String email){
        String refreshToken = extractTokenFromHeader(bearerRefreshToken);
        MemberResponseDto memberResponseDto = memberService.refreshToken(email, refreshToken);

        return new ResponseEntity<>(new ResponseResult(HttpStatus.OK,memberResponseDto), HttpStatus.OK);
    }

    private String extractTokenFromHeader(String bearerRefreshToken) {
        if(!ObjectUtils.isEmpty(bearerRefreshToken)){
            String[] stringList = bearerRefreshToken.split(" ");

            if(stringList.length == 2 && stringList[0].equals("Bearer")){
                return stringList[1];
            }
        }

        return null;
    }

}
