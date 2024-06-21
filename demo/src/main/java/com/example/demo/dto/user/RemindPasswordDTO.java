package com.example.demo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 03.06.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Data Transfer Object to Remind a Password")
public class RemindPasswordDTO {
    @NotEmpty(message = "Email cannot be empty!")
    @NotNull(message = "Where is email, Lebovsky?!")
    @Email(message = "Email should be valid! With @ and so else")
    @Schema(description = "Email", example = "example.email@host.dlt")
    String email;
}
