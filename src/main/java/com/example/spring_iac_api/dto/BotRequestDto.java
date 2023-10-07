package com.example.spring_iac_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BotRequestDto {

    private String botId;

    private String botUserId;

}
