package com.example.demo.dto.user;

import com.example.demo.model.BusyBook;
import com.example.demo.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    Integer id;
    String username;
    String email;
    String password;
    String passEnc;
    @Enumerated(EnumType.STRING)
    Role role;
    Date registrationDate;
    List<BusyBook> borrowedBooks;
}
