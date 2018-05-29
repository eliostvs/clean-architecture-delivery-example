CREATE TABLE cousine (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    UNIQUE (name),
    PRIMARY KEY (id)
);

INSERT INTO cousine (name) VALUES ('Chinese');
INSERT INTO cousine (name) VALUES ('Pizza');
INSERT INTO cousine (name) VALUES ('Sushi');
INSERT INTO cousine (name) VALUES ('Vietnamese');
