package com.example.consumer.health;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * consumer
 * Author: Vasylenko Oleksii
 * Date: 21.06.2024
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class TelegramHealthIndicator implements HealthIndicator {

    final RestTemplate restTemplate;

    @Value("${telegram-bot.token}")
    String token;

    @Override
    public Health health() {
        String telegramApiUrl = "https://api.telegram.org/bot" + token + "/getMe";
        try {
            String response = restTemplate.getForObject(telegramApiUrl, String.class);
            if (response != null && response.contains("\"ok\":true")) {
                return Health.up().withDetail("message", "Telegram API is healthy").build();
            } else {
                return Health.down().withDetail("message", "Telegram API is not responding correctly").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("message", "Telegram API is down").build();
        }
    }
}
