DROP DATABASE lib;

create DATABASE lib;

use lib;

CREATE TABLE
    `category` (
                   `category_id` int NOT NULL AUTO_INCREMENT,
                   `name` varchar(250) NOT NULL,
                   PRIMARY KEY (`category_id`)
) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

CREATE TABLE
    `author` (
                 `author_id` int NOT NULL AUTO_INCREMENT,
                 `name` varchar(250) NOT NULL,
                 PRIMARY KEY (`author_id`)
) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

CREATE TABLE
    `book` (
               `id` int NOT NULL AUTO_INCREMENT,
               `book_id` VARCHAR(250) NOT NULL,
               `name` varchar(250) NOT NULL,
               `category_id` int NOT NULL,
               `author_id` int DEFAULT NULL,
               `amount` int NOT NULL,
               `create_day` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
               `is_delete` bit(1) default b '0',
               PRIMARY KEY (`id`),
               KEY `category_id` (`category_id`),
               KEY `author_id` (`author_id`),
               CONSTRAINT `book_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
               CONSTRAINT `book_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`)
) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

CREATE TABLE
    `customer` (
                   `customer_id` int NOT NULL AUTO_INCREMENT,
                   `name` varchar(250) NOT NULL,
                   `email` varchar(250) NOT Null,
                   `phone_number` varchar(11) Not null,
                   `identity_card` varchar(15) NOT NULL,
                   `is_delete` bit(1) default b '0',
                   PRIMARY KEY (`customer_id`)
) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

CREATE TABLE
    `borrow` (
                 `borrow_id` int NOT NULL AUTO_INCREMENT,
                 `customer_id` int NOT NULL,
                 `id` int NOT NULL,
                 `start_day` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                 `end_day` timestamp NULL DEFAULT NULL,
                 `status` bit(1) DEFAULT b '0',
                 PRIMARY KEY (`borrow_id`),
                 KEY `customer_id` (`customer_id`),
                 KEY `book_id` (`id`),
                 CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
                 CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`id`) REFERENCES `book` (`id`)
) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

CREATE TABLE
    `user` (
               `user_id` int NOT NULL AUTO_INCREMENT,
               `username` varchar(250) NOT NULL,
               `password` varchar(250) NOT NULL,
               PRIMARY KEY (`user_id`)
) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

INSERT INTO category (name)
VALUES ('Category 1'), ('Category 2'), ('Category 3'), ('Category 4'), ('Category 5');

INSERT INTO author (name)
VALUES ('Author 1'), ('Author 2'), ('Author 3'), ('Author 4'), ('Author 5');

INSERT INTO
    book (
    book_id,
    name,
    category_id,
    author_id,
    amount
)
VALUES ('BOO1', 'Book 1', 1, 1, 10), ('BOO2', 'Book 2', 2, 2, 8), ('BOO3', 'Book 3', 3, 3, 15), ('B004', 'Book 4', 1, 4, 12), ('BOO5', 'Book 5', 2, 5, 7);

INSERT INTO
    customer (
    name,
    email,
    phone_number,
    identity_card
)
VALUES (
           'Customer 1',
           'customer1@example.com',
           '1234567891',
           'ID1111'
       ), (
           'Customer 2',
           'customer2@example.com',
           '1234567892',
           'ID2222'
       ), (
           'Customer 3',
           'customer3@example.com',
           '1234567893',
           'ID3333'
       ), (
           'Customer 4',
           'customer4@example.com',
           '1234567894',
           'ID4444'
       ), (
           'Customer 5',
           'customer5@example.com',
           '1234567895',
           'ID5555'
       );

INSERT INTO
    borrow (
    customer_id,
    id,
    start_day,
    end_day
)
VALUES (
           1,
           1,
           '2023-11-01',
           '2023-11-10'
       ), (
           2,
           2,
           '2023-11-02',
           '2023-11-11'
       ), (
           3,
           3,
           '2023-11-03',
           '2023-11-12'
       ), (
           4,
           4,
           '2023-11-04',
           '2023-11-13'
       ), (
           5,
           5,
           '2023-11-05',
           '2023-11-14'
       );

insert into user VALUES(1,'1','1');

select * from author;

INSERT INTO `author` (`name`)
VALUES ("Author 2") ON DUPLICATE KEY
UPDATE `name` = `name`;

Select author_id from author where author_id = 'Author 1';

SELECT * from author WHERE name = 'author 1'

select * from book;

select name from book where author_id = 1 and category_id =1;

select * from category;

SELECT COUNT(*) FROM author WHERE name = '1'

update book set is_delete = 0;