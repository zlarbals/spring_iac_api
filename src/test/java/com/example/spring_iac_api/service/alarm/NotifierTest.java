package com.example.spring_iac_api.service.alarm;

import com.example.spring_iac_api.util.alarm.NotificationMessage;
import com.example.spring_iac_api.util.alarm.Notifier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
@ActiveProfiles("local")
class NotifierTest {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private Notifier notifier;


    @Test
    public void sendEmailTest(){
        Context context = new Context();
        String message = templateEngine.process("mail/test", context);

        NotificationMessage emailMessage = NotificationMessage.builder()
                .to("zlarbals@gmail.com")
                .subject("test")
                .message(message)
                .build();

        notifier.sendNotification(emailMessage);
    }

}