CREATE TABLE book(
	id             BIGSERIAL   PRIMARY KEY,
    author         TEXT        NOT NULL,
    price          INTEGER     NOT NULL,
    sellernumber  TEXT   NOT NULL,
    type           TEXT        NOT NULL,
    title          TEXT        NOT NULL
);

INSERT INTO book (author, price, sellernumber, type, title) VALUES 
('Dyaika', 1000, '123456', 'book', 'HamsterPik'),
('DoofHD', 1500, '789012', 'book', 'UhD');

CREATE TABLE client(
	id BIGSERIAL PRIMARY KEY,
    login       TEXT    NOT NULL,
    password    TEXT    NOT NULL,
    name        TEXT    NOT NULL,
    email       TEXT    NOT NULL
);

INSERT INTO client (login, password, name, email) VALUES 
('Dyaika', '123456', 'Sasha', 'abc@abc.com'),
('DoofHD', '789012', 'Edward', 'abd@abd.com');

CREATE TABLE telephone(
    id             BIGSERIAL   PRIMARY KEY,
    vendor         TEXT        NOT NULL,
    battery        INTEGER     NOT NULL,
    sellernumber   TEXT        NOT NULL,
    type           TEXT        NOT NULL,
    price          INTEGER      NOT NULL,
    title          TEXT        NOT NULL
);

INSERT INTO telephone (vendor, price, sellernumber, type, title, battery) VALUES 
('Dyaika', 1000, '123456', 'telephone', 'Samsung', 3000),
('DoofHD', 1500, '789012', 'telephone', 'Huawei', 6000);

CREATE TABLE washing_machine(
    id             BIGSERIAL   PRIMARY KEY,
    vendor         TEXT        NOT NULL,
    volume         INTEGER     NOT NULL,
    sellernumber   TEXT        NOT NULL,
    type           TEXT        NOT NULL,
    price          INTEGER      NOT NULL,
    title          TEXT        NOT NULL
);

INSERT INTO washing_machine (vendor, price, sellernumber, type, title, volume) VALUES 
('Dyaika', 10000, '123456', 'washmachine', 'Samsung', 30),
('DoofHD', 15000, '789012', 'washmachine', 'Huawei', 60);