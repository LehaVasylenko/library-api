package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 31.05.2024
 */
@Data
@Builder
@Entity
@Table(name = "bug_reports")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BugReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    Integer id;

    @Column(name = "user_name")
    String username;
    String email;
    String subject;

    @Column(name = "report_link")
    String reportLink;
}
