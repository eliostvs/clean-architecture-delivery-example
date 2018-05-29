CREATE TABLE store (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(300) NOT NULL,
    cousine_id INT NOT NULL,
    UNIQUE(name),
    PRIMARY KEY (id),
    FOREIGN KEY (cousine_id) REFERENCES cousine (id)
);

SET @COUSINE_ID = SELECT id FROM cousine WHERE name = 'Chinese';

INSERT INTO store (name, address, cousine_id)
VALUES ('Hai Shang', '2991 Pembina Hwy, Winnipeg, Manitoba R3T 2H5, Canada', @COUSINE_ID);

SET @COUSINE_ID = SELECT id FROM cousine WHERE name = 'Pizza';

INSERT INTO store (name, address, cousine_id)
VALUES ('Za Pizza Bistro', 'E-1220 St Mary s Rd, Winnipeg, Manitoba R2M 3V6, Canada', @COUSINE_ID);
