package com.example.spring_iac_api.util.alarm;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationMessage {

    private String to;

    private String subject;

    private String message;

}
