CREATE TABLE orders (
    id INT NOT NULL AUTO_INCREMENT,
    customer_id int NOT NULL,
    store_id int NOT NULL,
    total DECIMAL(19, 2) NOT NULL,
    status VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (store_id) REFERENCES store (id)
);
