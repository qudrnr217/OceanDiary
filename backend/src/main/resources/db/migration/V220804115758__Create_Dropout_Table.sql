DROP TABLE IF EXISTS `dropout`;
CREATE TABLE `dropout`
(
    `dropout_id`    BIGINT          NOT NULL AUTO_INCREMENT,
    `room_id`       BIGINT          NOT NULL,
    `user_id`       BIGINT          NULL,
    `name`          VARCHAR(10)     NULL,
    `created_at`    DATETIME        NOT NULL,
    `updated_at`    DATETIME        DEFAULT NULL,
    `deleted_at`    DATETIME        DEFAULT NULL,
    PRIMARY KEY (`dropout_id`)
);