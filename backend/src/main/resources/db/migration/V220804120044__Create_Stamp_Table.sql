DROP TABLE IF EXISTS `stamp`;
CREATE TABLE `stamp`
(
    `stamp_id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `user_id`           BIGINT      NOT NULL,
    `category_id`       VARCHAR(8)  NOT NULL,
    `enter_time`        DATETIME    NULL    DEFAULT now(),
    `exit_time`         DATETIME    NULL    DEFAULT now(),
    `total_time`        TIME        NULL,
    PRIMARY KEY (`stamp_id`)
);