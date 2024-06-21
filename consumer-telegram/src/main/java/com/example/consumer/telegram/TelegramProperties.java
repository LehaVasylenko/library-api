package com.example.consumer.telegram;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * consumer
 * Author: Vasylenko Oleksii
 * Date: 15.06.2024
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "telegram-bot")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramProperties {
    String username;
    String token;
}
