package com.example.spring_iac_api.controller;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.dto.MemberSyncResponseDto;
import com.example.spring_iac_api.dto.ResponseResult;
import com.example.spring_iac_api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sync")
@RequiredArgsConstructor
public class SyncController {

    private final MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity<ResponseResult> syncMember(HttpServletRequest request){
        String serviceName = (String) request.getAttribute("service");

        List<MemberSyncResponseDto> result = memberService.getAllMemberByServiceName(serviceName);
        return new ResponseEntity<>(new ResponseResult(HttpStatus.OK,result),HttpStatus.OK);
    }

}
