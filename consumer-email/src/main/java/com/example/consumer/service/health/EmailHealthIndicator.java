package com.example.consumer.service.health;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

/**
 * consumeremail
 * Author: Vasylenko Oleksii
 * Date: 20.06.2024
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailHealthIndicator implements HealthIndicator {

    final JavaMailSender javaMailSender;

    @Value("${email.health.receiver}")
    String EMAIL;

    @Async
    @Override
    public Health health() {
        try {
            log.info(EMAIL);
            MimeMessageHelper helper = new MimeMessageHelper(javaMailSender.createMimeMessage());
            helper.setTo(EMAIL);
            helper.setSubject("Health Check");
            helper.setText("Health check email");

            javaMailSender.send(helper.getMimeMessage());
            return Health.up().withDetail("message", "Email sender is healthy").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("message", "Email sender is down").build();
        }
    }
}
