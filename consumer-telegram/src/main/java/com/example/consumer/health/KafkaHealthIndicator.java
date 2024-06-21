package com.example.consumer.health;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * consumer
 * Author: Vasylenko Oleksii
 * Date: 21.06.2024
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class KafkaHealthIndicator implements HealthIndicator {

    KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Health health() {
        try {
            kafkaTemplate.send("health-check-topic", "health-check-key", "health-check-message").get();
            return Health.up().withDetail("message", "Kafka connection is healthy").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("message", "Kafka connection is down").build();
        }
    }
}
