DROP TABLE IF EXISTS `participant`;
CREATE TABLE `participant`
(
    `participant_id`    BIGINT          NOT NULL AUTO_INCREMENT,
    `room_id`           BIGINT          NOT NULL,
    `user_id`           BIGINT          NULL,
    `name`              VARCHAR(10)     NULL,
    `enter_date`        DATETIME        NULL,
    `exit_date`         DATETIME        NULL,
    PRIMARY KEY (`participant_id`)
);