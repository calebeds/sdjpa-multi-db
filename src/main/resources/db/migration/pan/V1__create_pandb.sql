DROP TABLE IF EXISTS credit_card_pan;

CREATE TABLE credit_card_pan(
    id BIGINT NOT NULL auto_increment,
    credit_card_number VARCHAR(30),
    PRIMARY KEY (id)
);