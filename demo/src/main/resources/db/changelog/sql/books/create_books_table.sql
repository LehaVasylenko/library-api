CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    description TEXT,
    isbn VARCHAR(255) UNIQUE NOT NULL,
    publish_date DATE,
    copies_available INT
);
