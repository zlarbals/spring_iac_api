package com.example.spring_iac_api.service;

import com.example.spring_iac_api.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    public void testSendSignUpGreetingAlarm(){
        String receiverEmail = "zlarbals@gmail.com";
        notificationService.sendSignUpGreetingAlarm(receiverEmail);
    }

}