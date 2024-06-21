package com.example.demo.dto;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 31.05.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusyBookDTO {
    Integer id;
    User user;
    Book book;
    LocalDateTime dateStart;
    LocalDateTime dateEnd;
}
