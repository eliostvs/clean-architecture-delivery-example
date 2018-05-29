CREATE TABLE store (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(200) NOT NULL,
    cousine_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cousine_id) REFERENCES cousine (id)
);

SET @COUSINE_ID = SELECT id FROM cousine WHERE name = 'Chinese';

INSERT INTO store (name, address, cousine_id)
VALUES ('Hai Shang', '2991 Pembina Hwy, Winnipeg, Manitoba R3T 2H5, Canada', @COUSINE_ID);
