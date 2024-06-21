CREATE TABLE bug_reports (
    report_id SERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    email VARCHAR(255),
    subject VARCHAR(255),
    report_link TEXT
);
