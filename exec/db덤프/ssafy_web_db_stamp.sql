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
-- Table structure for table `stamp`
--

DROP TABLE IF EXISTS `stamp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stamp` (
  `stamp_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `category` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `enter_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `exit_time` datetime DEFAULT NULL,
  `total_time` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`stamp_id`),
  KEY `FK_user_TO_stamp` (`user_id`),
  KEY `FKmc8kpnwtc4js7lhwiln9if6mc` (`created_by`),
  KEY `FKem7k1lg9jtpb0f3hf9xgqsrii` (`updated_by`),
  CONSTRAINT `FK_user_TO_stamp` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKem7k1lg9jtpb0f3hf9xgqsrii` FOREIGN KEY (`updated_by`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKmc8kpnwtc4js7lhwiln9if6mc` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stamp`
--

LOCK TABLES `stamp` WRITE;
/*!40000 ALTER TABLE `stamp` DISABLE KEYS */;
INSERT INTO `stamp` VALUES (1,2,'FESTIVAL','2022-08-19 10:05:05','2022-08-19 10:05:10','00:00:05','2022-08-19 10:05:10','2022-08-19 10:05:10',NULL,2,2),(2,3,'FESTIVAL','2022-08-19 10:05:23','2022-08-19 10:06:07','00:00:44','2022-08-19 10:06:07','2022-08-19 10:06:07',NULL,3,3),(3,4,'OCEAN','2022-08-19 01:05:24','2022-08-19 12:07:01','11:01:36','2022-08-19 10:07:01','2022-08-19 12:07:01',NULL,2,2),(4,3,'FESTIVAL','2022-08-19 10:06:14','2022-08-19 10:07:01','00:00:46','2022-08-19 10:07:01','2022-08-19 10:07:01',NULL,2,2),(5,2,'FESTIVAL','2022-08-19 10:02:11','2022-08-19 10:07:01','00:04:49','2022-08-19 10:07:01','2022-08-19 10:07:01',NULL,2,2),(6,2,'FESTIVAL','2022-08-19 10:05:18','2022-08-19 10:07:01','00:01:42','2022-08-19 10:07:01','2022-08-19 10:07:01',NULL,2,2),(7,5,'FESTIVAL','2022-08-19 10:09:53','2022-08-19 10:12:19','00:02:26','2022-08-19 10:12:19','2022-08-19 10:12:19',NULL,5,5),(8,5,'FESTIVAL','2022-08-19 10:12:42','2022-08-19 10:14:19','00:01:37','2022-08-19 10:14:19','2022-08-19 10:14:19',NULL,5,5),(9,2,'FESTIVAL','2022-08-19 10:09:32','2022-08-19 10:15:31','00:05:59','2022-08-19 10:15:31','2022-08-19 10:15:31',NULL,2,2),(10,4,'CAFE','2022-08-19 03:09:12','2022-08-19 10:15:32','07:06:20','2022-08-19 10:15:32','2022-08-19 12:15:32',NULL,4,4),(11,4,'LIBRARY','2022-08-19 06:17:37','2022-08-19 10:29:00','04:11:22','2022-08-19 10:29:00','2022-08-19 12:29:00',NULL,4,4),(12,7,'FESTIVAL','2022-08-19 10:24:41','2022-08-19 10:29:03','00:04:22','2022-08-19 10:29:03','2022-08-19 10:29:03',NULL,7,7),(13,3,'FESTIVAL','2022-08-19 10:17:31','2022-08-19 10:29:06','00:11:34','2022-08-19 10:29:06','2022-08-19 10:29:06',NULL,3,3),(14,2,'FESTIVAL','2022-08-19 10:19:32','2022-08-19 10:29:06','00:09:33','2022-08-19 10:29:06','2022-08-19 10:29:06',NULL,3,3),(15,6,'FESTIVAL','2022-08-19 10:18:18','2022-08-19 10:29:33','00:11:14','2022-08-19 10:29:33','2022-08-19 10:29:33',NULL,6,6),(16,6,'FESTIVAL','2022-08-19 10:31:31','2022-08-19 10:32:57','00:01:25','2022-08-19 10:32:57','2022-08-19 10:32:57',NULL,6,6),(17,5,'FESTIVAL','2022-08-19 10:34:50','2022-08-19 11:03:38','00:28:48','2022-08-19 11:03:38','2022-08-19 11:03:38',NULL,5,5),(18,5,'FESTIVAL','2022-08-19 11:03:42','2022-08-19 11:03:53','00:00:10','2022-08-19 11:03:53','2022-08-19 11:03:53',NULL,5,5),(19,6,'FESTIVAL','2022-08-19 10:33:27','2022-08-19 11:16:15','00:42:47','2022-08-19 11:16:15','2022-08-19 11:16:15',NULL,6,6),(20,2,'FESTIVAL','2022-08-19 10:34:02','2022-08-19 11:16:15','00:42:12','2022-08-19 11:16:15','2022-08-19 11:16:15',NULL,6,6),(21,4,'FESTIVAL','2022-08-19 01:19:39','2022-08-19 11:21:11','10:01:31','2022-08-19 11:21:11','2022-08-19 12:21:11',NULL,4,4),(22,4,'CAFE','2022-08-19 01:34:02','2022-08-19 11:16:15','10:42:12','2022-08-19 11:16:15','2022-08-19 12:16:15',NULL,4,4),(23,4,'CAFE','2022-08-19 01:33:27','2022-08-19 11:16:15','10:42:47','2022-08-19 11:16:15','2022-08-19 11:16:15',NULL,4,4),(24,4,'FESTIVAL','2022-08-19 11:40:23','2022-08-19 11:41:24','00:01:00','2022-08-19 11:41:24','2022-08-19 11:41:24',NULL,4,4);
/*!40000 ALTER TABLE `stamp` ENABLE KEYS */;
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
