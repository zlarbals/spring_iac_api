package com.example.spring_iac_api.service;

import com.example.spring_iac_api.domain.Member;
import com.example.spring_iac_api.util.alarm.NotificationMessage;
import com.example.spring_iac_api.util.alarm.Notifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final Map<String, Notifier> notifierMap;

    private final TemplateEngine templateEngine;

    public void sendSignUpGreetingAlarm(String receiverEmail){
        Notifier emailNotifier = notifierMap.get("email");
        NotificationMessage notificationMessage = getGreetingNotificationMessage(receiverEmail);
        emailNotifier.sendNotification(notificationMessage);
    }

    private NotificationMessage getGreetingNotificationMessage(String receiverEmail) {
        Context context = new Context();
        String message = templateEngine.process("mail/greeting", context);

        NotificationMessage notificationMessage = NotificationMessage.builder()
                .to(receiverEmail)
                .subject("통합 계정 회원가입을 환영합니다!")
                .message(message)
                .build();

        return notificationMessage;
    }

}
