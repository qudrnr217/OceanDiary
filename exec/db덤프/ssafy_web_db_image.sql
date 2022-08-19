-- MySQL dump 10.13  Distrib 8.0.24, for macos11 (x86_64)
--
-- Host: i7a406.p.ssafy.io    Database: ssafy_web_db
-- ------------------------------------------------------
-- Server version	8.0.30-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `image_id` bigint NOT NULL AUTO_INCREMENT,
  `extension` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `origin_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `size` bigint NOT NULL,
  `width` int NOT NULL,
  `height` int NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FK20mnr8xpp1mgo57mlwbp1bwhq` (`created_by`),
  KEY `FKfoi40f0me2n7w2x8nfedgby49` (`updated_by`),
  CONSTRAINT `FK20mnr8xpp1mgo57mlwbp1bwhq` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKfoi40f0me2n7w2x8nfedgby49` FOREIGN KEY (`updated_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (2,'png','a970622f-911c-41ac-8eb2-cba547c9a7e0.png','unknown.png','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/a970622f-911c-41ac-8eb2-cba547c9a7e0.png',505889,779,472,'2022-08-19 10:02:11','2022-08-19 10:02:11',NULL,2,2),(3,'jpg','425511c2-d2b0-46cb-8c9a-4cdf92e6b8ab.jpg','helloworld.jpg','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/425511c2-d2b0-46cb-8c9a-4cdf92e6b8ab.jpg',1006349,1318,805,'2022-08-19 10:05:05','2022-08-19 10:05:05',NULL,2,2),(4,'png','72b64513-c1a6-4163-88a9-cca70de9186d.png','KakaoTalk_Photo_2022-07-29-16-56-20.png','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/72b64513-c1a6-4163-88a9-cca70de9186d.png',486670,1000,659,'2022-08-19 10:05:23','2022-08-19 10:05:23',NULL,3,3),(5,'png','82ddb7bc-9b64-417d-a209-892ee8c7b0e7.png','BG.png','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/82ddb7bc-9b64-417d-a209-892ee8c7b0e7.png',290854,497,325,'2022-08-19 10:09:12','2022-08-19 10:09:12',NULL,4,4),(6,'png','bb95d8ba-1066-4217-988c-034da176bdf4.png','KakaoTalk_Photo_2022-07-29-16-56-20.png','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/bb95d8ba-1066-4217-988c-034da176bdf4.png',486670,1000,659,'2022-08-19 10:17:31','2022-08-19 10:17:31',NULL,3,3),(7,'jpg','6e7aa34f-457e-488f-a37a-929959bf121a.jpg','haedal.jpg','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/6e7aa34f-457e-488f-a37a-929959bf121a.jpg',69336,450,411,'2022-08-19 10:18:18','2022-08-19 10:18:18',NULL,6,6),(8,'jpg','23475c07-a3ee-4577-97b3-f97da69ce987.jpg','haedal.jpg','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/23475c07-a3ee-4577-97b3-f97da69ce987.jpg',69336,450,411,'2022-08-19 10:31:31','2022-08-19 10:31:31',NULL,6,6),(9,'jpg','95984a5f-3a24-464c-b4c2-bebc46a7d7eb.jpg','haedal.jpg','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/95984a5f-3a24-464c-b4c2-bebc46a7d7eb.jpg',69336,450,411,'2022-08-19 10:33:27','2022-08-19 10:33:27',NULL,6,6),(10,'png','1c002c28-2bc5-43f8-8602-4ea03305d32c.png','BG.png','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/1c002c28-2bc5-43f8-8602-4ea03305d32c.png',290854,497,325,'2022-08-19 11:19:39','2022-08-19 11:19:39',NULL,4,4),(11,'png','0220fa99-d4ff-4fcb-a87c-87decd748112.png','BG.png','https://s3.ap-northeast-2.amazonaws.com/oceandiary.s3.file/0220fa99-d4ff-4fcb-a87c-87decd748112.png',290854,497,325,'2022-08-19 11:40:23','2022-08-19 11:40:23',NULL,4,4);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-19 11:42:34
