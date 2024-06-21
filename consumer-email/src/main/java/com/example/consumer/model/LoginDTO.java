package com.example.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String username;
    private String password;

    public String toMail() {
        return "{\n" +
                "\t\"username\": \"" + username + "\",\n" +
                "\t\"password\": \"" + password + "\"\n" +
                "}";
    }
}
