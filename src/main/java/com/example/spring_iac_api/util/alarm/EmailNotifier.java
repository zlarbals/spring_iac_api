package com.example.spring_iac_api.util.alarm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Slf4j
@Profile("local")
@Component(value = "email")
public class EmailNotifier implements Notifier {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendNotification(NotificationMessage message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,false,"UTF-8");
            mimeMessageHelper.setTo(message.getTo());
            mimeMessageHelper.setFrom("zlarbals@naver.com");
            mimeMessageHelper.setSubject(message.getSubject());
            mimeMessageHelper.setText(message.getMessage(),true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("failed to send email to {}",message.getTo());
        }
    }
}
