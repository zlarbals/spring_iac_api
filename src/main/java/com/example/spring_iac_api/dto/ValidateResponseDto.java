package com.example.spring_iac_api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateResponseDto {

    private String redirectUrl;

    public void generateRedirectUrlWithAuthToken(String redirectUrl, String authToken) {
        this.redirectUrl = redirectUrl + "?token=" + authToken;
    }
}
