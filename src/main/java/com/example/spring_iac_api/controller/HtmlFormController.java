package com.example.spring_iac_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class HtmlFormController {

    @GetMapping("/signin")
    public String showSignInForm(@ModelAttribute("botUserId") String botUserId, @ModelAttribute("botId") String botId, Model model){
        if(!ObjectUtils.isEmpty(botUserId) && !ObjectUtils.isEmpty(botId)){
            model.addAttribute("botUserId",botUserId);
            model.addAttribute("botId",botId);
        }

        return "signin";
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup";
    }

    @GetMapping("/signinconfirm")
    public String showSignInConfirm(){
        return "signinconfirm";
    }

}
