package com.example.demo.services;

import com.example.demo.dto.EmailDTO;
import com.example.demo.dto.auth.AuthResponse;
import com.example.demo.dto.token.Expiration;
import com.example.demo.dto.user.LoginDTO;
import com.example.demo.dto.user.RemindPasswordDTO;
import com.example.demo.dto.user.RemindResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginService {
    static final Logger log = LoggerFactory.getLogger(LoginService.class);
    final UserRepository userRepository;
    final JWTService jwtService;
    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;
    final KafkaProducerService kafkaProducerService;

    private User user;

    @Async
    @Transactional
    public CompletableFuture<AuthResponse> login(LoginDTO request) throws ExecutionException, InterruptedException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        if (authenticateUser(request).get()) {
            var jwt = jwtService.generateToken(user);
            return CompletableFuture.completedFuture(AuthResponse.builder()
                    .token(jwt)
                    .expired(LocalDateTime.now().plus(Expiration.INSTANCE.getAmount(), Expiration.INSTANCE.getUnit()))
                    .build());
        } else
            return CompletableFuture.completedFuture(null);
    }

    @Async
    @Transactional
    public CompletableFuture<Boolean> authenticateUser(LoginDTO request) throws ExecutionException, InterruptedException {
        user = userRepository.findUserByUsername(request.getUsername()).get().orElseThrow();
        log.info(user.toString());//, passwordEncoder.encode(request.getPassword())
        if (user != null) {
            return CompletableFuture.completedFuture(passwordEncoder.matches(request.getPassword(), user.getPassword())); // Compare passwords
        }
        return CompletableFuture.completedFuture(false);
    }

    @Async
    @Transactional
    public CompletableFuture<?> remindPassword(RemindPasswordDTO remindPasswordDTO) throws ExecutionException, InterruptedException, NoSuchElementException {
        user = userRepository.findUserByEmail(remindPasswordDTO.getEmail()).get().orElseThrow();
        kafkaProducerService.sendEmail(EmailDTO.builder()
                        .to(remindPasswordDTO.getEmail())
                        .subject("Password reminder")
                        .text(LoginDTO
                                .builder()
                                .username(user.getUsername())
                                .password(user.getPassEnc())
                                .build())
                        .build());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return CompletableFuture.completedFuture(RemindResponse.builder()
                .sendTo(user.getEmail())
                .dateSend(LocalDateTime.now().format(formatter))
                .build());
    }

}
