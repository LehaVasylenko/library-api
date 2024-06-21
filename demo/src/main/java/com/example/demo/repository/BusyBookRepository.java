package com.example.demo.repository;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 29.05.2024
 */
import com.example.demo.model.BusyBook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusyBookRepository extends JpaRepository<BusyBook, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM BusyBook b WHERE b.book.id = :id")
    void deleteByBookId(Integer id);

    @Query("SELECT b FROM BusyBook b WHERE b.user.id = :id")
    List<BusyBook> findAllBooksByUserId(Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM BusyBook b WHERE b.book.id = :bookId AND b.user.id = :userId")
    void deleteByBookIdAndUserId(@Param("bookId") Integer bookId, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM BusyBook b WHERE b.book.id IN :bookIds AND b.user.id = :userId")
    void deleteByBookIdsAndUserId(@Param("bookIds") List<Integer> bookIds, @Param("userId") Integer userId);
}
