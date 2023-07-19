CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8mb4;

USE `test`;

CREATE TABLE `user`
(
    `id`  int         NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `show_id` int NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='테스트';

INSERT INTO `user` (user_id, show_id) VALUES (1, 2),(1, 3),(2, 4);