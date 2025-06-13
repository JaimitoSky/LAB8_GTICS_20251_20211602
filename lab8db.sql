DROP DATABASE IF EXISTS lab8db;

CREATE DATABASE  IF NOT EXISTS `lab8db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `lab8db`;

CREATE TABLE evento (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        match_name VARCHAR(255),
                        event_date DATE,
                        location VARCHAR(100),
                        `condition` VARCHAR(100),
                        max_temp_c DOUBLE,
                        min_temp_c DOUBLE
);

