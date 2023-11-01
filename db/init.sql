CREATE TABLE book(
	id             BIGSERIAL   PRIMARY KEY,
    author         TEXT        NOT NULL,
    price          INTEGER     NOT NULL,
    sellernumber  TEXT   NOT NULL,
    type           TEXT        NOT NULL,
    title          TEXT        NOT NULL
);

INSERT INTO book (author, price, sellernumber, type, title) VALUES 
('Dyaika', 1000, '123456', '1', 'HamsterPik'),
('DoofHD', 1500, '789012', '1', 'UhD');

CREATE TABLE client(
    login       TEXT    PRIMARY KEY,
    password    TEXT    NOT NULL,
    name        TEXT    NOT NULL,
    email       TEXT    NOT NULL
);

CREATE TABLE telephone(
    id             BIGSERIAL   PRIMARY KEY,
    vendor         TEXT        NOT NULL,
    life           INTEGER     NOT NULL,
    vendor_number   TEXT        NOT NULL,
    type           TEXT        NOT NULL,
    price          INTEGER     NOT NULL,
    name           TEXT        NOT NULL
);

CREATE TABLE washing_machine(
    id             BIGSERIAL   PRIMARY KEY,
    vendor         TEXT        NOT NULL,
    volume         INTEGER     NOT NULL,
    vendor_number   TEXT        NOT NULL,
    type           TEXT        NOT NULL,
    price          INTEGER     NOT NULL,
    name           TEXT        NOT NULL
);