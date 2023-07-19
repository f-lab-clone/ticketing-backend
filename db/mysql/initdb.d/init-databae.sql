CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8mb4;

USE `test`;

CREATE TABLE `test`
(
    `id`  int         NOT NULL AUTO_INCREMENT,
    `user_id` varchar(30) NOT NULL,
    `show_id` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='테스트';

INSERT INTO `test` (user_id, show_id) VALUES (1, 2),(1, 3),(2, 4);