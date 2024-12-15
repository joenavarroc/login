-- Active: 1734201660677@@127.0.0.1@3306@securitydb
drop database if exists securitydb;
create database securitydb;
use securitydb;

SHOW GRANTS FOR 'root'@'localhost';

GRANT ALL PRIVILEGES ON securitydb.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '1234';
FLUSH PRIVILEGES;


SELECT VERSION();

SELECT user, host, plugin FROM mysql.user WHERE user = 'root';
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('1234');
FLUSH PRIVILEGES;


SHOW GRANTS FOR 'root'@'localhost';

SELECT * FROM securitydb.USER;

SELECT host, user FROM mysql.user WHERE user = 'root';


