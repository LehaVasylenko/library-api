package com.example.consumer.repository;

import com.example.consumer.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * consumer
 * Author: Vasylenko Oleksii
 * Date: 15.06.2024
 */
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
}
