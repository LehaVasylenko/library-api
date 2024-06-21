package com.example.demo.dto.user;

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
public class RemindResponse {
    String sendTo;
    String dateSend;
}
