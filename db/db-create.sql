--------
-- Drop all tables
--------

DROP SCHEMA IF EXISTS public CASCADE;

CREATE SCHEMA public;

--------
-- Creating tables
--------

CREATE TABLE users (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    login VARCHAR(15) UNIQUE,
                    password VARCHAR(40), -- TODO store hashed pwd
                    phone_number VARCHAR(15) UNIQUE,
                    email VARCHAR(30) UNIQUE,
                    role INT NOT NULL); -- customer(1), manager(2) or admin(3) TODO maybe do in separate tab

CREATE TABLE rooms (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    room_number INT UNIQUE,
                    floor INT NOT NULL,
                    capacity INT NOT NULL,
                    beds_types VARCHAR(8), -- S - single, D - double
                    class INT NOT NULL); -- standard(1), deluxe(2), suite(3)

CREATE TABLE rooms_images (room_id INT REFERENCES rooms(id) ON DELETE CASCADE,
                    img_path VARCHAR(10),
                    UNIQUE (room_id, img_path));

CREATE TABLE reservations (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, -- already payed up
                    user_id INT REFERENCES users(id) ON DELETE CASCADE,
                    room_id INT REFERENCES rooms(id) ON DELETE CASCADE,
                    date_from DATE NOT NULL,
                    date_to DATE NOT NULL,
                    guests_amount INT);

CREATE TABLE requests (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    user_id INT REFERENCES users(id) ON DELETE CASCADE,
                    room_id INT REFERENCES rooms(id) NULL,
                    date_from DATE NOT NULL,
                    date_to DATE NOT NULL,
                    guests_amount INT NOT NULL,
                    status INT NOT NULL, -- waiting for: manager response(1), customer accept(2), payment(3)
                    price FLOAT);

CREATE TABLE users_requests(user_id INT REFERENCES users(id) ON DELETE CASCADE,
                    request_id INT REFERENCES requests(id) ON DELETE CASCADE,
                    UNIQUE (user_id, request_id));

--------
-- Inserting default values
--------

-- INSERT INTO users  -- temporary values for dev process --
--     (login,     password,  phone_number,    email,                     role)
-- VALUES
--     ('admin',   'admin',   '+111111111111', 'hotel_admin@gmail.com',   3),
--     ('manager', 'manager', '+222222222222', 'hotel_manager@gmail.com', 2),
--     ('user',    'user',    '+333333333333', 'hotel_user@gmail.com',    1);
--
-- INSERT INTO rooms  -- temporary values for dev process --
--     (room_number, floor, capacity, beds_types, class)
-- VALUES
--     (1,           1,     1,        '1S',       1),
--     (2,           1,     2,        '1D',       2),
--     (3,           1,     3,        '1D, 1S',   3);