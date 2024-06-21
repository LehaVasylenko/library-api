package com.example.demo.health;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 20.06.2024
 */
@RequiredArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaHealthIndicator implements HealthIndicator {
    KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    @Override
    public Health health() {
        try {
            // Replace with an actual test message and topic relevant to your setup
            kafkaTemplate.send("test-topic", "test-message").get();
            return Health.up().withDetail("message", "Kafka connection is healthy").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("message", "Kafka connection is down").build();
        }
    }
}
