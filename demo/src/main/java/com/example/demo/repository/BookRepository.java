package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAllByGenre(String genre);
    List<Book> findAllByAuthor(String author);
}
