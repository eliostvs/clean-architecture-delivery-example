CREATE TABLE product (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(300) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    store_id INT NOT NULL,
    UNIQUE (name, store_id),
    PRIMARY KEY (id),
    FOREIGN KEY (store_id) REFERENCES store (id)
);

SET @STORE_ID = SELECT id FROM store WHERE name = 'Hai Shang';

INSERT INTO product (name, description, price, store_id)
VALUES ('Shrimp Tempura', 'Fresh shrimp battered and deep fried until golden brown', 10.95, @STORE_ID);
