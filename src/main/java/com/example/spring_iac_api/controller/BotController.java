package com.example.spring_iac_api.controller;

import com.example.spring_iac_api.dto.MemberRequestDto;
import com.example.spring_iac_api.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/bot")
public class BotController {

    private final MemberService memberService;

    @GetMapping("/form/signin")
    public String botShowSignInForm(@RequestParam String botUserId, @RequestParam String botId, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("BotId",botId);
        redirectAttributes.addFlashAttribute("BotUserId",botUserId);

        return "redirect:/form/signin";
    }

    @PostMapping("/signin")
    public String botSignIn(@RequestBody MemberRequestDto memberRequestDto){
        memberService.botSignIn(memberRequestDto);

        return "redirect:/form/signinconfirm";
    }

}
