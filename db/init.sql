create table item
(
	id             BIGSERIAL   PRIMARY KEY,
    price          INTEGER     NOT NULL,
    sellernumber  TEXT   NOT NULL,
    type           TEXT        NOT NULL,
    title          TEXT        NOT NULL
);

INSERT INTO item (price, sellernumber, type, title) VALUES 
(1000, '123456', 'book', 'Normalise'),
(1500, '789012', 'book', 'Dont care'),
(100000, '123456', 'telephone', 'Samsung 2'),
(15000, '789012', 'telephone', 'Huawei 5'),
(10000, '123456', 'washmachine', 'moidodyr'),
(15000, '789012', 'washmachine', 'vanessh');

CREATE TABLE book(
	id             BIGINT   PRIMARY KEY,
    author         TEXT        NOT NULL,
    foreign key (id) references item (id)
);

INSERT INTO book (id, author) VALUES 
(1, 'HTML programmer'),
(2, 'Senior Tomato');

CREATE TABLE telephone(
	id             BIGINT   PRIMARY KEY,
    vendor         TEXT        NOT NULL,
    battery        INTEGER     NOT NULL,
    foreign key (id) references item (id)
);

INSERT INTO telephone (id, vendor, battery) VALUES 
(3, 'Samsung', 3600),
(4, 'Huawei', 6666);

CREATE TABLE washing_machine(
	id             BIGINT   PRIMARY KEY,
    vendor         TEXT        NOT NULL,
    volume         INTEGER     NOT NULL,
    foreign key (id) references item (id)
);

INSERT INTO washing_machine (id, vendor, volume) VALUES 
(5, 'Samsung', 15),
(6, 'Huawei', 22);

CREATE TABLE client(
	id 			BIGSERIAL PRIMARY KEY,
    login       TEXT    NOT NULL,
    password    TEXT    NOT NULL,
    name        TEXT    NOT NULL,
    email       TEXT    NOT NULL,
	role		TEXT	NOT NULL
);

INSERT INTO client (login, password, name, email, role) VALUES 
('Dyaika', '1234', 'Sasha', 'abc@abc.com', 'ADMIN'),
('DoofHD', '5678', 'Edward', 'abd@abd.com', 'CLIENT');

CREATE TABLE stock(
	id             BIGINT   PRIMARY KEY,
    amount         INTEGER  NOT NULL,
    foreign key (id) references item (id)
);

INSERT INTO stock (id, amount) VALUES 
(1, 10),
(2, 11),
(3, 3),
(4, 2),
(5, 8),
(6, 0);

CREATE TABLE cart_item(
    clientid 	BIGINT,
    itemid 		BIGINT,
    amount 		INTEGER,
    PRIMARY KEY (clientid, itemid),
    FOREIGN KEY (clientid) REFERENCES client(id),
    FOREIGN KEY (itemid) REFERENCES item(id)
);