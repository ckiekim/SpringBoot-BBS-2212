SET SESSION FOREIGN_KEY_CHECKS=0;	# 체크 해제

/* Drop Tables */

DROP TABLE IF EXISTS anniversary;
DROP TABLE IF EXISTS likeTable;
DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS profile;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS users;


/* Create Tables */

CREATE TABLE anniversary
(
	aid int NOT NULL,
	aname varchar(20) NOT NULL,
	adate char(8) NOT NULL,
	isHoliday int DEFAULT 0,
	PRIMARY KEY (aid)
);

CREATE TABLE board
(
	bid int NOT NULL AUTO_INCREMENT,
	uid varchar(20) NOT NULL,
	title varchar(128) NOT NULL,
	content varchar(4096),
	modTime datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
	viewCount int DEFAULT 0 NOT NULL,
	replyCount int DEFAULT 0 NOT NULL,
	isDeleted int DEFAULT 0 NOT NULL,
	files varchar(400),
	likeCount int DEFAULT 0 NOT NULL,
	PRIMARY KEY (bid)
);

CREATE TABLE likeTable
(
	lid int NOT NULL AUTO_INCREMENT,
	bid int NOT NULL,
	uid varchar(20) NOT NULL,
	likeTime datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
	PRIMARY KEY (lid)
);

CREATE TABLE profile
(
	uid varchar(20) NOT NULL,
	github varchar(20),
	instagram varchar(20),
	twitter varchar(20),
	homepage varchar(80),
	blog varchar(80),
	addr varchar(20),
	image mediumblob,
	size int,
	filename varchar(40),
	PRIMARY KEY (uid)
);

CREATE TABLE reply
(
	rid int NOT NULL AUTO_INCREMENT,
	content varchar(128) NOT NULL,
	regDate datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
	isMine int DEFAULT 0 NOT NULL,
	uid varchar(20) NOT NULL,
	bid int NOT NULL,
	PRIMARY KEY (rid)
);

CREATE TABLE schedule
(
	sid int NOT NULL AUTO_INCREMENT,
	uid varchar(20) NOT NULL,
	sdate char(8) NOT NULL,
	title varchar(40) NOT NULL,
	place varchar(40),
	startTime datetime,
	endTime datetime,
	isImportant int DEFAULT 0,
	memo varchar(100),
	PRIMARY KEY (sid)
);

CREATE TABLE users
(
	uid varchar(20) NOT NULL,
	pwd char(60) NOT NULL,
	uname varchar(20) NOT NULL,
	email varchar(32),
	regDate date DEFAULT NOW(), SYSDATE(),
	isDeleted int DEFAULT 0 NOT NULL,
	PRIMARY KEY (uid)
);


/* Create Foreign Keys */

ALTER TABLE likeTable
	ADD FOREIGN KEY (bid)
	REFERENCES board (bid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE reply
	ADD FOREIGN KEY (bid)
	REFERENCES board (bid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE board
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE likeTable
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE reply
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE schedule
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

SET SESSION FOREIGN_KEY_CHECKS=1;	# 체크 설정
