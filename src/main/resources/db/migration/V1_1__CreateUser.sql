CREATE TABLE sh_user
(
id int NOT NULL AUTO_INCREMENT,
username varchar(255),
email varchar(255),
place varchar(255),
country varchar(255),
password varchar(255),
salt varchar(255),
PRIMARY KEY (id)
)