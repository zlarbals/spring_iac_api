package com.example.spring_iac_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberRequestDto extends BotRequestDto{

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;

    @Size(min = 8, max = 20)
    private String password;

    public MemberRequestDto(String email, String password){
        this.email = email;
        this.password = password;
    }

    public MemberRequestDto(String email, String password, String botId, String botUserId){
        super(botId, botUserId);
        this.email = email;
        this.password = password;
    }
}
