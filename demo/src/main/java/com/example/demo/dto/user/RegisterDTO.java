package com.example.demo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "Data Transfer Object for Registration")
public class RegisterDTO {

    @NotEmpty(message = "Username can't be empty!")
    @NotNull(message = "Who are you, a warrior? Achilles, son of Peleus?")
    @Schema(description = "Username", example = "SeniorQA")
    String username;

    @NotEmpty(message = "Email cannot be empty!")
    @NotNull(message = "Where is email, Lebovsky?!")
    @Email(message = "Email should be valid! With @ and so else")
    @Schema(description = "Email", example = "example.email@host.dlt")
    String email;
}
