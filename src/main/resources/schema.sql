DROP TABLE IF EXISTS users, transfers, phones, emails CASCADE;

SET TIME ZONE 'GMT';

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY NOT NULL,
    login VARCHAR(30) NOT NULL,
    password VARCHAR(1000) NOT NULL,
    balance NUMERIC(15,2) DEFAULT 0.00 CHECK (balance >= 0)
);

CREATE TABLE IF NOT EXISTS user_phones (
                                           id SERIAL PRIMARY KEY NOT NULL,
                                           user_id INTEGER REFERENCES users(id) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    UNIQUE(user_id, phone_number)
    );

CREATE TABLE IF NOT EXISTS user_emails (
                                           id SERIAL PRIMARY KEY NOT NULL,
                                           user_id INTEGER REFERENCES users(id) NOT NULL,
    email VARCHAR(255) NOT NULL,
    UNIQUE(user_id, email)
    );

CREATE TABLE IF NOT EXISTS transfers (
    id SERIAL PRIMARY KEY NOT NULL,
    amount NUMERIC(15,2) NOT NULL CHECK (amount > 0),
    sender_id INTEGER REFERENCES users(id) NOT NULL,
    receiver_id INTEGER REFERENCES users(id) NOT NULL,
    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    );
