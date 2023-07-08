package com.example.spring_iac_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class HtmlFormController {

    @GetMapping("/signin")
    public String showSignInForm(){
        return "signin";
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup";
    }

}
