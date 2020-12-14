CREATE DATABASE nalaz;
ALTER DATABASE nalaz CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'nalazDB' IDENTIFIED BY 'nalazDBpw';
GRANT ALL ON `nalaz`.* TO 'nalazDB'@'%' IDENTIFIED BY 'nalazDBpw';
GRANT ALL ON `nalaz`.* TO 'nalazDB'@'localhost' IDENTIFIED BY 'nalazDBpw';
FLUSH PRIVILEGES;