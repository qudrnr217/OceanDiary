DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `user_id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `oauth_id`     VARCHAR(50)  DEFAULT NULL,
    `name`         VARCHAR(10)  NOT NULL,
    `email`        VARCHAR(100) NOT NULL,
    `provider`     VARCHAR(30)  NOT NULL,
    `role`         VARCHAR(20)  NOT NULL,
    `last_visited` DATETIME     DEFAULT NULL,
    `birth`        DATETIME     DEFAULT NULL,
    `created_at`   DATETIME     NOT NULL,
    `updated_at`   DATETIME     DEFAULT NULL,
    `deleted_at`   DATETIME     DEFAULT NULL,
    `created_by`   BIGINT       DEFAULT NULL,
    `updated_by`   BIGINT       DEFAULT NULL,
    PRIMARY KEY (`user_id`)
);