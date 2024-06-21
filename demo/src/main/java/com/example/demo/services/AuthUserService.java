package com.example.demo.services;

import com.example.demo.dto.user.RegisterDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.factory.UserFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthUserService {
    UserRepository userRepository;
    UserFactory userFactory;

    @Async
    public CompletableFuture<Boolean> registerUser(RegisterDTO request) throws ExecutionException, InterruptedException {
        Optional<User> userByEmail = userRepository.findUserByEmail(request.getEmail()).get();
        if (userByEmail.isEmpty()) {
            CompletableFuture<User> user = userFactory.getUser(request);
            userRepository.save(user.get());
            return CompletableFuture.completedFuture(true);
        } else if (userByEmail.get().getRole() != Role.USER) {
            CompletableFuture<User> user = userFactory.getUser(request);
            userRepository.save(user.get());
            return CompletableFuture.completedFuture(true);
        } else return CompletableFuture.completedFuture(false);
    }

    @Async
    public CompletableFuture<Boolean> registerAdmin(RegisterDTO request) throws ExecutionException, InterruptedException {
        Optional<User> userByEmail = userRepository.findUserByEmail(request.getEmail()).get();
        if (userByEmail.isEmpty()) {
            CompletableFuture<User> user = userFactory.getAdmin(request);
            userRepository.save(user.get());
            return CompletableFuture.completedFuture(true);
        } else if (userByEmail.get().getRole() != Role.ADMIN) {
            CompletableFuture<User> user = userFactory.getAdmin(request);
            userRepository.save(user.get());
            return CompletableFuture.completedFuture(true);
        } else return CompletableFuture.completedFuture(false);
    }

}
