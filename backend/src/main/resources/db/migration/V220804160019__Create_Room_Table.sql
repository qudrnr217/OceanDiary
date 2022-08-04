DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`
(
    `room_id`        BIGINT          NOT NULL AUTO_INCREMENT,
    `category_id`    VARCHAR(8)      NOT NULL,
    `user_id`        BIGINT          NOT NULL,
    `image_id`       BIGINT          NOT NULL,
    `title`          VARCHAR(100)    NOT NULL,
    `rule`           VARCHAR(500)    NULL,
    `max_num`        INT(3)          NULL,
    `is_open`        BOOLEAN         NULL,
    `pw`             VARCHAR(12)     NULL,
    `created_at`     DATETIME        NOT NULL,
    `updated_at`     DATETIME        DEFAULT NULL,
    `deleted_at`     DATETIME        DEFAULT NULL,
    `created_by`     BIGINT          DEFAULT NULL,
    `updated_by`     BIGINT          DEFAULT NULL,
    PRIMARY KEY (`room_id`)
);