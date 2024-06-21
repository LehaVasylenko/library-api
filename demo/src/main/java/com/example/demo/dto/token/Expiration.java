package com.example.demo.dto.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.temporal.ChronoUnit;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Expiration {
    INSTANCE;

    int amount;
    ChronoUnit unit;

    Expiration(){
        this.amount = 1;
        this.unit = ChronoUnit.HOURS;
    }

    public long getExpirationMillis() {
        return unit.getDuration().toMillis() * amount;
    }
}
