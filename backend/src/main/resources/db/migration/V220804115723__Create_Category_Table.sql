DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `category_id`  VARCHAR(8)  NOT NULL,
    `name`         VARCHAR(10) NOT NULL,
    PRIMARY KEY (`category_id`)
);