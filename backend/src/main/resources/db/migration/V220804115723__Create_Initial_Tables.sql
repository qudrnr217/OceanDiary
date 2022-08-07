DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `category_id` VARCHAR(8)  NOT NULL,
    `name`        VARCHAR(10) NOT NULL,
    PRIMARY KEY (`category_id`)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `user_id`       BIGINT       NOT NULL AUTO_INCREMENT,
    `oauth_id`      VARCHAR(50)  DEFAULT NULL,
    `name`          VARCHAR(10)  NOT NULL,
    `email`         VARCHAR(100) NOT NULL,
    `provider`      VARCHAR(30)  NOT NULL,
    `role`          VARCHAR(20)  NOT NULL,
    `last_visited`  DATETIME     DEFAULT NULL,
    `birth`         DATETIME     DEFAULT NULL,
    `created_at`    DATETIME     NOT NULL,
    `updated_at`    DATETIME     DEFAULT NULL,
    `deleted_at`    DATETIME     DEFAULT NULL,
    `created_by`    BIGINT       DEFAULT NULL,
    `updated_by`    BIGINT       DEFAULT NULL,
    `refresh_token` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`user_id`)
);


DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`
(
    `image_id`    BIGINT       NOT NULL AUTO_INCREMENT,
    `extension`   VARCHAR(255) NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `origin_name` VARCHAR(255) NOT NULL,
    `url`         VARCHAR(255) NULL,
    `size`        BIGINT       NOT NULL,
    `width`       INT          NOT NULL,
    `height`      INT          NOT NULL,
    `created_at`  DATETIME     NOT NULL,
    `updated_at`  DATETIME DEFAULT NULL,
    `deleted_at`  DATETIME DEFAULT NULL,
    `created_by`  BIGINT   DEFAULT NULL,
    `updated_by`  BIGINT   DEFAULT NULL,
    PRIMARY KEY (`image_id`)
);

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`
(
    `room_id`     BIGINT       NOT NULL AUTO_INCREMENT,
    `category_id` VARCHAR(8)   NULL,
    `user_id`     BIGINT       NULL,
    `image_id`    BIGINT       NULL,
    `title`       VARCHAR(100) NOT NULL,
    `rule`        VARCHAR(500) NULL,
    `max_num`     INT(3)       NULL,
    `is_open`     BOOLEAN      NULL,
    `pw`          VARCHAR(12)  NULL,
    `created_at`  DATETIME     NOT NULL,
    `updated_at`  DATETIME DEFAULT NULL,
    `deleted_at`  DATETIME DEFAULT NULL,
    `created_by`  BIGINT   DEFAULT NULL,
    `updated_by`  BIGINT   DEFAULT NULL,
    PRIMARY KEY (`room_id`),
    FOREIGN KEY (`user_id`) REFERENCES user (`user_id`),
    FOREIGN KEY (`image_id`) REFERENCES image (`image_id`)
);


DROP TABLE IF EXISTS `participant`;
CREATE TABLE `participant`
(
    `participant_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `room_id`        BIGINT      NULL,
    `user_id`        BIGINT      NULL,
    `name`           VARCHAR(10) NULL,
    `enter_date`     DATETIME    NULL,
    `exit_date`      DATETIME    NULL,
    PRIMARY KEY (`participant_id`),
    FOREIGN KEY (`room_id`) REFERENCES room (`room_id`),
    FOREIGN KEY (`user_id`) REFERENCES user (`user_id`)
);


DROP TABLE IF EXISTS `stamp`;
CREATE TABLE `stamp`
(
    `stamp_id`    BIGINT     NOT NULL AUTO_INCREMENT,
    `user_id`     BIGINT     NULL,
    `category_id` VARCHAR(8) NULL,
    `enter_time`  DATETIME   NULL DEFAULT now(),
    `exit_time`   DATETIME   NULL DEFAULT now(),
    `total_time`  TIME       NULL,
    PRIMARY KEY (`stamp_id`)
);



DROP TABLE IF EXISTS `dropout`;
CREATE TABLE `dropout`
(
    `dropout_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `room_id`    BIGINT      NULL,
    `user_id`    BIGINT      NULL,
    `name`       VARCHAR(10) NULL,
    `created_at` DATETIME    NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    `created_by` BIGINT   DEFAULT NULL,
    `updated_by` BIGINT   DEFAULT NULL,
    PRIMARY KEY (`dropout_id`)
);