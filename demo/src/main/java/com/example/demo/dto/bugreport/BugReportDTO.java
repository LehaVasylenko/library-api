package com.example.demo.dto.bugreport;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 31.05.2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BugReportDTO {
    Integer id;
    String username;
    String email;
    String subject;
    String reportLink;
}
