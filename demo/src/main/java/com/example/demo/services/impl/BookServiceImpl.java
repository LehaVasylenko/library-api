package com.example.demo.services.impl;

import com.example.demo.dto.BookDTO;
import com.example.demo.mapper.BookMapper;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.services.inter.BookService;
import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    @Async
    @Override
    public CompletableFuture<List<BookDTO>> getBooks() {
        var books = IterableUtils.toList(bookRepository.findAll());
        return CompletableFuture.completedFuture(books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .toList());
    }

    @Async
    @Override
    public CompletableFuture<Optional<BookDTO>> getBook(Integer id) {
        var book = bookRepository.findById(id);
        return CompletableFuture.completedFuture(book.map(BookMapper.INSTANCE::toDto));
    }

    @Async
    @Override
    public CompletableFuture<Optional<BookDTO>> getBookByIsbn(String isbn) throws ExecutionException, InterruptedException {
        var book = bookRepository.findByIsbn(isbn);
        return CompletableFuture.completedFuture(book.map(BookMapper.INSTANCE::toDto));
    }

    @Async
    @Override
    public CompletableFuture<Optional<BookDTO>> getBookByTitle(String title) throws ExecutionException, InterruptedException {
        var book = bookRepository.findByTitle(title);
        return CompletableFuture.completedFuture(book.map(BookMapper.INSTANCE::toDto));
    }

    @Async
    @Override
    public CompletableFuture<List<BookDTO>> getBookByGenre(String genre) throws ExecutionException, InterruptedException {
        var books = IterableUtils.toList(bookRepository.findAllByGenre(genre));
        return CompletableFuture.completedFuture(books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .toList());
    }

    @Async
    @Override
    public CompletableFuture<List<BookDTO>> getBookByAuthor(String author) throws ExecutionException, InterruptedException {
        var books = IterableUtils.toList(bookRepository.findAllByAuthor(author));
        return CompletableFuture.completedFuture(books.stream()
                .map(BookMapper.INSTANCE::toDto)
                .toList());
    }

    @Async
    @Override
    public CompletableFuture<Boolean> save(BookDTO bookDTO) {
        bookRepository.save(BookMapper.INSTANCE.toModel(bookDTO));
        return CompletableFuture.completedFuture(true);
    }

    @Async
    @Override
    public CompletableFuture<Boolean> update(BookDTO bookDTO) {
        if (bookRepository.findById(bookDTO.getId()).isPresent()) {
            bookRepository.save(BookMapper.INSTANCE.toModel(bookDTO));
            return CompletableFuture.completedFuture(true);
        }
        return CompletableFuture.completedFuture(false);
    }

    @Async
    @Override
    public CompletableFuture<Boolean> delete(Integer id) {
        bookRepository.deleteById(id);
        return CompletableFuture.completedFuture(true);
    }

    @Async
    public void populateBooksIfTableIsEmpty() {
        if (bookRepository.count() == 0) {
            Faker faker = new Faker();
            Set<String> titles = new HashSet<>();
            Set<String> isbns = new HashSet<>();

            for (int i = 0; i < 50; i++) {
                String title = faker.book().title();
                String isbn = faker.code().isbn13();

                // Ensure unique title and ISBN
                if (titles.contains(title) || isbns.contains(isbn)) {
                    i--;
                    continue;
                }

                titles.add(title);
                isbns.add(isbn);

                Book book = Book.builder()
                        .title(title)
                        .author(faker.book().author())
                        .genre(faker.book().genre())
                        .description(faker.lorem().sentence())
                        .isbn(isbn)
                        .publishDate(faker.date().past(10000, java.util.concurrent.TimeUnit.DAYS))
                        .copiesAvailable(faker.number().numberBetween(1, 10))
                        .build();

                bookRepository.save(book);
            }
        }
    }
}
