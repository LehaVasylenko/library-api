package com.example.demo.services;

import com.example.demo.dto.BorrowRequestDTO;
import com.example.demo.exception.NoSuchBookException;
import com.example.demo.exception.TooManyBooksException;
import com.example.demo.model.Book;
import com.example.demo.model.BusyBook;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BusyBookRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 29.05.2024
 */
@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BorrowService {

    UserRepository userRepository;
    BookRepository bookRepository;
    BusyBookRepository busybookRepository;

    static int MAX_BORROW_LIMIT = 5;
    static  String TOO_MANY_BOOKS_MESSAGE = "You have exceeded the maximum number of books you can borrow. Max limit is " + MAX_BORROW_LIMIT;

    @Async
    @Transactional
    public CompletableFuture<Boolean> borrowBooks(String username, List<BorrowRequestDTO> borrowRequests) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<User> userOpt = null;
            try {
                userOpt = userRepository.findUserByUsername(username).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            if (userOpt.isEmpty()) {
                return false;
            }
            User user = userOpt.get();

            // Check current borrowed books
            List<BusyBook> borrowedBooks = user.getBorrowedBooks();
            int currentBorrowedCount = borrowedBooks.size();
            if (currentBorrowedCount + borrowRequests.size() > MAX_BORROW_LIMIT) {
                throw new TooManyBooksException(TOO_MANY_BOOKS_MESSAGE);
            }

            for (BorrowRequestDTO request : borrowRequests) {
                Optional<Book> bookOpt = bookRepository.findByIsbn(request.getIsbn());
                if (bookOpt.isEmpty()) {
                    throw new NoSuchBookException("No book with ISBN " + request.getIsbn());
                }

                Book book = bookOpt.get();
                if (book.getCopiesAvailable() <= 0) {
                    throw new NoSuchBookException("No available copies of book with ISBN " + request.getIsbn());
                }

                book.setCopiesAvailable(book.getCopiesAvailable() - 1);
                bookRepository.save(book);
                borrowedBooks.add(BusyBook.builder()
                                .user(user)
                                .book(book)
                                .dateStart(LocalDateTime.now())
                                .dateEnd(request.getReturnDate().atStartOfDay())
                        .build());
            }

            user.setBorrowedBooks(borrowedBooks);
            userRepository.save(user);

            return true;
        });
    }

    @Async
    @Transactional
    public CompletableFuture<Boolean> returnBooks(String username, List<String> isbns) {
        return CompletableFuture.supplyAsync(() -> {
            List<Integer> booksId = new ArrayList<>();
            Optional<User> userOpt = null;
            try {
                userOpt = userRepository.findUserByUsername(username).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            if (userOpt.isEmpty()) {
                return null;
            }
            User user = userOpt.get();

            boolean anyReturned = false;
            for (String isbn : isbns) {
                List<BusyBook> borrowedBooks = busybookRepository
                        .findAllBooksByUserId(user.getId());

                for (BusyBook borrowedBook : borrowedBooks) {
                    if (borrowedBook.getBook().getIsbn().equals(isbn)) {
                        Book book = bookRepository.findByIsbn(isbn).get();
                        booksId.add(book.getId());
                        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
                        book.getBusyBooks().remove(borrowedBook);
                        user.getBorrowedBooks().remove(borrowedBook);

                        userRepository.save(user);
                        bookRepository.save(book);
                        busybookRepository.deleteByBookIdAndUserId(book.getId(), user.getId());
                        anyReturned = true;
                    }
                }
            }

            busybookRepository.deleteByBookIdsAndUserId(booksId, user.getId());
            return anyReturned;
        });
    }
}
