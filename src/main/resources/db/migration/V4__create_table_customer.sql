CREATE TABLE customer (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    password VARCHAR(100) NOT NULL,
    UNIQUE (email),
    PRIMARY KEY (id)
);