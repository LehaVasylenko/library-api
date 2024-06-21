package com.example.consumer.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * consumeremail
 * Author: Vasylenko Oleksii
 * Date: 17.06.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDTO {
    String to;
    String subject;
    LoginDTO text;
}
