ALTER TABLE busy_books ADD CONSTRAINT fk_busy_books_book_id FOREIGN KEY (book_id) REFERENCES books(book_id);
