package com.example.consumer.health;

import com.example.consumer.telegram.TelegramProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * consumer
 * Author: Vasylenko Oleksii
 * Date: 21.06.2024
 */
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HealthConfig {

    KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    public KafkaHealthIndicator kafkaHealthIndicator() {
        return new KafkaHealthIndicator(kafkaTemplate);
    }

    @Bean
    public TelegramHealthIndicator telegramHealthIndicator() {
        return new TelegramHealthIndicator(new RestTemplate());
    }
}
