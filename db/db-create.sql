--------
-- Drop all tables
--------

DROP SCHEMA IF EXISTS public CASCADE;

CREATE SCHEMA public;

--------
-- Creating tables
--------

CREATE TABLE users (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    login VARCHAR(15) UNIQUE NOT NULL,
--                     password VARCHAR(150), -- hashed pwd in SHA-256
                    password VARCHAR(100) NOT NULL, -- hashed pwd in SHA-256
                    name VARCHAR NOT NULL,
                    surname VARCHAR NOT NULL,
                    phone_number VARCHAR(15) UNIQUE NOT NULL,
                    email VARCHAR(30) UNIQUE NOT NULL,
                    role INT NOT NULL); -- customer(1) or manager(2)

CREATE TABLE rooms (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    room_number INT UNIQUE NOT NULL ,
                    floor INT NOT NULL,
                    capacity INT NOT NULL,
                    beds_types VARCHAR(8), -- S - single, D - double
                    class INT NOT NULL); -- standard(1), deluxe(2), suite(3)

CREATE TABLE rooms_images (room_id INT REFERENCES rooms(id) ON DELETE CASCADE,
                    img_path VARCHAR(10) NOT NULL,
                    UNIQUE (room_id, img_path));

CREATE TABLE reservations (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, -- already payed up
                    user_id INT REFERENCES users(id) ON DELETE CASCADE,
                    room_id INT REFERENCES rooms(id) ON DELETE CASCADE,
                    date_from DATE NOT NULL,
                    date_to DATE NOT NULL,
                    guests_amount INT);

CREATE TABLE requests (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                    user_id INT REFERENCES users(id) ON DELETE CASCADE,
                    room_id INT REFERENCES rooms(id),
                    date_from DATE NOT NULL,
                    date_to DATE NOT NULL,
                    date_of_customer_acceptance DATE,
                    adults_amount INT,
                    children_amount INT,
                    status INT NOT NULL, -- waiting for: manager response(1), customer accept(2), payment(3), canceled(4)
                    price DOUBLE PRECISION);

CREATE TABLE users_requests(user_id INT REFERENCES users(id) ON DELETE CASCADE,
                    request_id INT REFERENCES requests(id) ON DELETE CASCADE,
                    UNIQUE (user_id, request_id));

--------
-- Inserting default values
--------

INSERT INTO users  -- temporary values for dev process --
    (login, password, name, surname, phone_number, email, role)
VALUES
    ('admin', 'a141aeaa52255df9:8e05661b11d6bdad94b2f50a1fdef8fe4ba19b8f405c6f2fa751e132c15d7bcd',
     'Admin', 'Adminov', '+111111111111', 'hotel_admin@gmail.com', 3),
    ('manager', '824bcb1c0446d078:6ec937e670eea75bc7dd2d8d30b41807690d4955b6c902f1dc7e46e24d25670e',
     'Manager', 'Managerov', '+222222222222', 'hotel_manager@gmail.com', 2),
    ('user', 'ca1f23e567162130:c4987aef350c32d8720c8d97fe215cd9a2e228d9c016a61647c9dbd1abb24b03',
     'User', 'Userov', '+333333333333', 'hotel_user@gmail.com', 1);

INSERT INTO rooms  -- temporary values for dev process --
    (room_number, floor, capacity, beds_types, class)
VALUES
    (1,           1,     1,        '1S',       1),
    (2,           1,     2,        '1D',       2),
    (3,           1,     3,        '1D, 1S',   3),
    (4,           2,     4,        '2D',       3),
    (5,           2,     2,        '2S',       1);

INSERT INTO reservations  -- temporary values for dev process --
    (user_id, room_id, date_from,    date_to,      guests_amount)
VALUES
    (3,       2,       '2021-11-16', '2021-11-20', 2);

INSERT INTO requests  -- temporary values for dev process --
    (user_id, room_id, date_from,    date_to,       date_of_customer_acceptance,      adults_amount, children_amount, status, price)
VALUES
    (3,       3,       '2021-11-20', '2021-11-21',  '2021-11-17',                     1,             1,               1,       900),
    (3,       4,       '2021-11-21', '2021-11-22',  '2021-11-15',                     1,             1,               3,       900),
    (3,       5,       '2021-11-21', '2021-11-22',  '2021-11-14',                     1,             1,               3,       900);