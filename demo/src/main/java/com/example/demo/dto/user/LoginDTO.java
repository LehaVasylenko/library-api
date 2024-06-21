package com.example.demo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Data Transfer Object for Login")
public class LoginDTO {

    @NotEmpty(message = "Username can't be empty!")
    @NotNull(message = "Don't you forget a username?")
    @Schema(description = "Username, received in email", example = "SeniorQA")
    String username;

    @NotEmpty(message = "Password can't be empty!")
    @NotNull(message = "Where is a password, Lebovsky?!")
    @Schema(description = "Password, received in email", example = "92308rugibrgneop3i2yrtg3")
    String password;

    public String toMail() {
        return "{\n" +
                "\t\"username\": \"" + username + "\",\n" +
                "\t\"password\": \"" + password + "\"\n" +
                "}";
    }
}
