create table USER
(
	ID bigint AUTO_INCREAMENT PRIMARY KEY NOT NULL ,
	ACCOUNT_ID varchar(100),
	NAME varchar(50),
	TOKEN varchar(36),
	GMT_CREATE bigint,
	GMT_MODIFIED bigint
);