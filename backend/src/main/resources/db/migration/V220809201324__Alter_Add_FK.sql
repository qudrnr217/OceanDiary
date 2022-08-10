ALTER TABLE `room` ADD CONSTRAINT `FK_user_TO_room` FOREIGN KEY (
                                                                 `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

ALTER TABLE `room` ADD CONSTRAINT `FK_image_TO_room` FOREIGN KEY (
                                                                  `image_id`
    )
    REFERENCES `image` (
                        `image_id`
        );

ALTER TABLE `participant` ADD CONSTRAINT `FK_room_TO_participant` FOREIGN KEY (
                                                                               `user_id`
    )
    REFERENCES `room` (
                       `room_id`
        );

ALTER TABLE `participant` ADD CONSTRAINT `FK_user_TO_participant` FOREIGN KEY (
                                                                               `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

ALTER TABLE `stamp` ADD CONSTRAINT `FK_user_TO_stamp` FOREIGN KEY (
                                                                               `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );