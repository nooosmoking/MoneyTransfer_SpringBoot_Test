DROP TABLE IF EXISTS users, transfers, phones, emails CASCADE;

SET TIME ZONE 'GMT';

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY NOT NULL,
    login VARCHAR(30) NOT NULL,
    password VARCHAR(1000) NOT NULL,
    balance INTEGER NOT NULL CHECK (balance >= 0),
    full_name VARCHAR(150) NOT NULL,
    birthday DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS phones (
    id SERIAL PRIMARY KEY NOT NULL,
    user_id INTEGER REFERENCES users(id) NOT NULL,
    phone_number VARCHAR(12) NOT NULL
);

CREATE TABLE IF NOT EXISTS emails (
    id SERIAL PRIMARY KEY NOT NULL,
    user_id INTEGER REFERENCES users(id) NOT NULL,
    email VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS payments (
    id SERIAL PRIMARY KEY NOT NULL,
    amount INTEGER NOT NULL CHECK (amount > 0),
    sender_id INTEGER REFERENCES users(id) NOT NULL,
    receiver_id INTEGER REFERENCES users(id) NOT NULL,
    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    );

INSERT INTO users (login, password, balance, full_name, birthday) VALUES ('Ann', '?', 100, 'Чер Анна Андреевна', '2000-03-13'), ('Ivan', 'ii', 100, 'Суров Иван Сергеевич', '1999-04-14'), ('Nikolay', 'nn', 100, 'Алев Николай Алеевич', '2010-10-10');

INSERT INTO phones (user_id, phone_number) VALUES (1, '900'), (2, '+79145588293'), (3, '000000000');

INSERT INTO emails (user_id, email) VALUES (1, 'A@gmail.com'), (2, 'f@r.ru'),(3,'aaaaaaaa@gmail.ru');

INSERT INTO transfers (amount, sender_id, receiver_id) VALUES (100, 2, 1);

