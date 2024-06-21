package com.example.consumer.service.health;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;

/**
 * consumeremail
 * Author: Vasylenko Oleksii
 * Date: 20.06.2024
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaHealthIndicator implements HealthIndicator {

    KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    @Override
    public Health health() {
        try {
            kafkaTemplate.send("test-topic", "test-key", "test-message").get();
            return Health.up().withDetail("message", "Kafka connection is healthy").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("message", "Kafka connection is down").build();
        }
    }
}
