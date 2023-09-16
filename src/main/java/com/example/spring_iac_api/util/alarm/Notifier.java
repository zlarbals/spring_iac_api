package com.example.spring_iac_api.util.alarm;

import org.springframework.stereotype.Component;

@Component
public interface Notifier {

    void sendNotification(NotificationMessage message);

}
