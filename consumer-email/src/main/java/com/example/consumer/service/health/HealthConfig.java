package com.example.consumer.service.health;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * consumeremail
 * Author: Vasylenko Oleksii
 * Date: 20.06.2024
 */
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HealthConfig {

    KafkaTemplate<String, Object> kafkaTemplate;
    JavaMailSender javaMailSender;

    @Bean
    public KafkaHealthIndicator kafkaHealthIndicator() {
        return new KafkaHealthIndicator(kafkaTemplate);
    }

    @Bean
    public EmailHealthIndicator emailHealthIndicator() {
        return new EmailHealthIndicator(javaMailSender);
    }
}
