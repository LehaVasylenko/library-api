package com.example.demo.services.inter;

import com.example.demo.dto.BookDTO;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
public interface BookService {

    CompletableFuture<List<BookDTO>> getBooks();
    CompletableFuture<Optional<BookDTO>> getBook(Integer id);
    CompletableFuture<Optional<BookDTO>> getBookByIsbn(String isbn) throws ExecutionException, InterruptedException;
    CompletableFuture<Optional<BookDTO>> getBookByTitle(String title) throws ExecutionException, InterruptedException;
    CompletableFuture<List<BookDTO>> getBookByGenre(String genre) throws ExecutionException, InterruptedException;
    CompletableFuture<List<BookDTO>> getBookByAuthor(String author) throws ExecutionException, InterruptedException;
    CompletableFuture<Boolean> save(BookDTO bookDTO);
    CompletableFuture<Boolean> update(BookDTO bookDTO);
    CompletableFuture<Boolean> delete(Integer id);
    void populateBooksIfTableIsEmpty();
}
