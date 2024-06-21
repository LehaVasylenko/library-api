CREATE TABLE busy_books (
    busy_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    date_start DATETIME,
    date_end DATETIME
);