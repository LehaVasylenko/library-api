package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "author")
    String author;

    @Column(name = "genre")
    String genre;

    @Column(name = "description")
    String description;

    @Column(name = "isbn", unique = true, nullable = false)
    String isbn;

    @Column(name = "publish_date")
    Date publishDate;

    @Column(name = "copies_available")
    Integer copiesAvailable;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<BusyBook> busyBooks;
}
