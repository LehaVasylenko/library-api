package com.example.demo.services.factory;

import com.example.demo.dto.EmailDTO;
import com.example.demo.dto.user.LoginDTO;
import com.example.demo.dto.user.RegisterDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.services.KafkaProducerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFactory {
    static Logger log = LoggerFactory.getLogger(UserFactory.class);
    PasswordEncoder passwordEncoder;
    KafkaProducerService kafkaProducerService;

    @Async
    public CompletableFuture<User> getUser(RegisterDTO request) throws ExecutionException, InterruptedException {
        CompletableFuture<String> password = createPassword(request);
        CompletableFuture<LocalDateTime> date = getDate();
        String pass = password.get();
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .pass(passwordEncoder.encode(pass))
                .passEnc(pass)//
                .role(Role.USER)
                .registrationDate(date.get())
                .build();
        log.info("{}: {}", user.getUsername(), pass);
        sendMail(user, pass);
        return CompletableFuture.completedFuture(user);
    }

    public CompletableFuture<User> getAdmin(RegisterDTO request) throws ExecutionException, InterruptedException {
        CompletableFuture<String> password = createPassword(request);
        CompletableFuture<LocalDateTime> date = getDate();
        String pass = password.get();
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .pass(passwordEncoder.encode(pass))
                .passEnc(pass)//
                .role(Role.ADMIN)
                .registrationDate(date.get())
                .build();
        log.info("{}: {}", user.getUsername(), pass);
        sendMail(user, pass);
        return CompletableFuture.completedFuture(user);
    }

    @Async
    private CompletableFuture<String> createPassword(RegisterDTO request) {
        SecureRandom random = new SecureRandom((request.getUsername() + request.getEmail()).getBytes());
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return CompletableFuture.completedFuture(Base64.getEncoder().encodeToString(bytes));
    }

    private CompletableFuture<LocalDateTime> getDate() {
        return CompletableFuture.completedFuture(LocalDateTime.now());
    }

    private CompletableFuture<Void> sendMail(User user, String password) {
        var message = LoginDTO.builder()
                        .username(user.getUsername())
                        .password(password)
                        .build();
        kafkaProducerService.sendEmail(EmailDTO.builder()
                        .to(user.getEmail())
                        .subject("Password")
                        .text(message)
                        .build());
        return CompletableFuture.completedFuture(null);
    }
}
