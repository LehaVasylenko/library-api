ALTER TABLE busy_books ADD CONSTRAINT fk_busy_books_user_id FOREIGN KEY (user_id) REFERENCES users(user_id);
