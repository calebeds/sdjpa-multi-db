DROP TABLE IF EXISTS credit_card;

CREATE TABLE credit_card (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cvv VARCHAR(20),
    expiration_date VARCHAR(7),
    PRIMARY KEY (id)
);