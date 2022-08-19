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
-- Table structure for table `participant`
--

DROP TABLE IF EXISTS `participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participant` (
  `participant_id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `name` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `enter_date` datetime DEFAULT NULL,
  `exit_date` datetime DEFAULT NULL,
  PRIMARY KEY (`participant_id`),
  KEY `FK_user_TO_participant` (`user_id`),
  KEY `FK6urix8695eogaruawu46907yj` (`room_id`),
  CONSTRAINT `FK6urix8695eogaruawu46907yj` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`),
  CONSTRAINT `FK_room_TO_participant` FOREIGN KEY (`user_id`) REFERENCES `room` (`room_id`),
  CONSTRAINT `FK_user_TO_participant` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participant`
--

LOCK TABLES `participant` WRITE;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
INSERT INTO `participant` VALUES (2,2,2,'강병국','2022-08-19 10:02:11','2022-08-19 10:07:01'),(7,3,2,'강병국','2022-08-19 10:05:05','2022-08-19 10:05:10'),(8,2,2,'강병국','2022-08-19 10:05:18','2022-08-19 10:07:01'),(9,4,3,'황재완','2022-08-19 10:05:23','2022-08-19 10:06:07'),(10,2,4,'류형주','2022-08-19 10:05:24','2022-08-19 10:07:01'),(12,2,3,'황재완','2022-08-19 10:06:14','2022-08-19 10:07:01'),(16,5,4,'류형주','2022-08-19 10:09:12','2022-08-19 10:15:32'),(17,5,NULL,'재완잉','2022-08-19 10:09:21','2022-08-19 10:15:29'),(18,5,NULL,'남궁종훈','2022-08-19 10:09:24','2022-08-19 10:15:32'),(19,5,2,'강병국','2022-08-19 10:09:32','2022-08-19 10:15:31'),(22,5,5,'이지연','2022-08-19 10:09:53','2022-08-19 10:12:19'),(25,5,5,'이지연','2022-08-19 10:12:42','2022-08-19 10:14:19'),(26,6,3,'황재완','2022-08-19 10:17:31','2022-08-19 10:29:06'),(27,6,4,'류형주','2022-08-19 10:17:37','2022-08-19 10:29:00'),(28,7,6,'곽종훈','2022-08-19 10:18:18','2022-08-19 10:29:33'),(29,6,2,'강병국','2022-08-19 10:19:32','2022-08-19 10:29:06'),(30,6,NULL,'제갈종훈','2022-08-19 10:19:40','2022-08-19 10:29:06'),(33,6,7,'지연','2022-08-19 10:24:41','2022-08-19 10:29:03'),(35,6,NULL,'민하은','2022-08-19 10:27:26','2022-08-19 10:29:02'),(36,8,6,'곽종훈','2022-08-19 10:31:31','2022-08-19 10:32:57'),(38,8,NULL,' 병구깅','2022-08-19 10:32:13','2022-08-19 10:32:29'),(39,9,6,'곽종훈','2022-08-19 10:33:27','2022-08-19 11:16:15'),(40,9,NULL,'재왕이에용','2022-08-19 10:33:45','2022-08-19 11:07:41'),(41,9,2,'강병국','2022-08-19 10:34:02','2022-08-19 11:16:15'),(43,9,5,'이지연','2022-08-19 10:34:50','2022-08-19 11:03:38'),(44,9,NULL,'민하은','2022-08-19 10:36:04','2022-08-19 11:04:16'),(45,9,NULL,'류형주','2022-08-19 10:38:54','2022-08-19 10:47:26'),(46,9,NULL,'류형주','2022-08-19 10:49:12','2022-08-19 10:49:20'),(47,9,NULL,'류형주','2022-08-19 10:50:16','2022-08-19 11:16:15'),(48,9,5,'이지연','2022-08-19 11:03:42','2022-08-19 11:03:53'),(49,10,4,'류형주','2022-08-19 11:19:39','2022-08-19 11:21:11'),(50,11,4,'류형주','2022-08-19 11:40:23','2022-08-19 11:41:24');
/*!40000 ALTER TABLE `participant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-19 11:42:36
