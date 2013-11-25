CREATE SCHEMA `sp` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE users (
	username   VARCHAR(255) NOT NULL PRIMARY KEY,
	full_name  VARCHAR(255) NOT NULL,
	hash       VARCHAR(255) NOT NULL,
    user_group VARCHAR(255) NOT NULL );

CREATE TABLE protocols (
	id          INT          AUTO_INCREMENT PRIMARY KEY,
	ware        VARCHAR(255) NOT NULL,
	price       INT          NOT NULL,
    amount_owed INT          NOT NULL,
	date        BIGINT       NOT NULL,
	buyer       VARCHAR(255) NOT NULL,
	debtor      VARCHAR(255) NOT NULL,
	FOREIGN KEY(buyer)  REFERENCES users(username),
	FOREIGN KEY(debtor) REFERENCES users(username) );
