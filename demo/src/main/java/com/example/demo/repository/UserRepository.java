package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Async
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.borrowedBooks WHERE u.username = :username")
    CompletableFuture<Optional<User>> findUserByUsername(String username);

    @Async
    //@Query("SELECT u FROM User u LEFT JOIN FETCH u.borrowedBooks WHERE u.email = :email")
    CompletableFuture<Optional<User>> findUserByEmail(String email);

    @Async
    CompletableFuture<Optional<User>> findUserByUsernameAndPass(String username, String pass);
}
