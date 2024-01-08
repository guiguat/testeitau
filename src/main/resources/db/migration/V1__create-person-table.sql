CREATE TABLE person(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    age INT NOT NULL,
    country VARCHAR(2) NOT NULL,
    CONSTRAINT unique_names UNIQUE (first_name, last_name)
);