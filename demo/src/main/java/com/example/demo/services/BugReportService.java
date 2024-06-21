package com.example.demo.services;

import com.example.demo.dto.bugreport.BugReportDTO;
import com.example.demo.dto.bugreport.BugReportRequest;
import com.example.demo.dto.bugreport.BugReportResponse;
import com.example.demo.mapper.BugReportMapper;
import com.example.demo.model.User;
import com.example.demo.repository.BugReportRepository;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 31.05.2024
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BugReportService {
    UserRepository userRepository;
    BugReportRepository bugReportRepository;

    @Async
    public CompletableFuture<?> takeReport (UserDetails userDetails, BugReportRequest report) throws ExecutionException, InterruptedException {
        CompletableFuture<Optional<User>> futureUser = userRepository.findUserByUsername(userDetails.getUsername());
        Optional<User> optUser = futureUser.get();
        User user = null;

        if (optUser.isEmpty()) throw new IllegalAccessError("Who are you?!");
        else {
            user = optUser.get();
        }
        BugReportDTO bugReportDTO = BugReportDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .subject(report.getSubject())
                .reportLink(report.getReportLink())
                .build();

        bugReportRepository.save(BugReportMapper.INSTANCE.toModel(bugReportDTO));

        return CompletableFuture.completedFuture(BugReportResponse.builder()
                .reporter(user.getUsername())
                .subject(report.getSubject())
                .date(new Date(System.currentTimeMillis()))
                .build());
    }

}
