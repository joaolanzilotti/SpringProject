CREATE TABLE IF NOT EXISTS `persons`
(
    `birthday` date                                                          NULL DEFAULT NULL,
    `id`       bigint                                                        NOT NULL AUTO_INCREMENT,
    `email`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);