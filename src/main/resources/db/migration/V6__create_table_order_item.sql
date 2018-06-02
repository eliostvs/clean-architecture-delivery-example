CREATE TABLE order_item (
    id INT NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    product_id int not null,
    quantity int NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    total DECIMAL(19, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
