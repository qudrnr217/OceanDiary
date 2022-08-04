DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`
(
    `image_id`      BIGINT          NOT NULL AUTO_INCREMENT,
    `extension`     VARCHAR(255)    NOT NULL,
    `name`          VARCHAR(255)    NOT NULL,
    `origin_name`   VARCHAR(255)    NOT NULL,
    `url`           VARCHAR(255)    NULL,
    `size`          BIGINT          NOT NULL,
    `width`         INT             NOT NULL,
    `height`        INT             NOT NULL,
    `created_at`    DATETIME        NOT NULL,
    `updated_at`    DATETIME        DEFAULT NULL,
    `deleted_at`    DATETIME        DEFAULT NULL,
    `created_by`    BIGINT          DEFAULT NULL,
    `updated_by`    BIGINT          DEFAULT NULL,
    PRIMARY KEY (`image_id`)
);