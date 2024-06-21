package com.example.demo.dto;

import com.example.demo.dto.user.LoginDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * consumeremail
 * Author: Vasylenko Oleksii
 * Date: 17.06.2024
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDTO {
    String to;
    String subject;
    LoginDTO text;

    @Override
    public String toString() {
        return "{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
