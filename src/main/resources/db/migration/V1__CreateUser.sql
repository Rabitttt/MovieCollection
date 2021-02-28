CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY,
    username VARCHAR,
    password VARCHAR,
    email VARCHAR,
    gender VARCHAR,
    joinDate DATE default CURRENT_DATE
);
