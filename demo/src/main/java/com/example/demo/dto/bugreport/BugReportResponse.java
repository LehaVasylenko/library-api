package com.example.demo.dto.bugreport;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 03.06.2024
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BugReportResponse {
    String subject;
    String reporter;
    Date date;
}
