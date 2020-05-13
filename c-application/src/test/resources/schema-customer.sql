DROP ALL OBJECTS;

DROP SCHEMA IF EXISTS customer;
CREATE SCHEMA IF NOT EXISTS customer;

USE customer;

CREATE TABLE user
(
    username              VARCHAR(45)  NOT NULL,
    password              VARCHAR(255) NOT NULL,
    first_name            VARCHAR(45)  NOT NULL,
    last_name             VARCHAR(45)  NOT NULL,
    email                 VARCHAR(255) NOT NULL,
    phone                 VARCHAR(13)  NOT NULL,
    address               VARCHAR(500) NOT NULL,
    enabled               TINYINT      NOT NULL DEFAULT 1,
    last_shopping_card_id BIGINT,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles
(
    id       INT(11)     NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    role     VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uni_username_role (role, username),
    CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES user (username)
);