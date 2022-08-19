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
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` varchar(8) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `image_id` bigint DEFAULT NULL,
  `title` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `rule` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `max_num` int DEFAULT NULL,
  `is_open` tinyint(1) DEFAULT NULL,
  `pw` varchar(12) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`room_id`),
  KEY `FK_user_TO_room` (`user_id`),
  KEY `FK_image_TO_room` (`image_id`),
  KEY `FKt8jecef1e0q3iow0vso4hlrpt` (`created_by`),
  KEY `FKdtcyxka76humfbwd3dcr1obpx` (`updated_by`),
  CONSTRAINT `FK_image_TO_room` FOREIGN KEY (`image_id`) REFERENCES `image` (`image_id`),
  CONSTRAINT `FK_user_TO_room` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKdtcyxka76humfbwd3dcr1obpx` FOREIGN KEY (`updated_by`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKt8jecef1e0q3iow0vso4hlrpt` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (2,'FESTIVAL',2,2,'라이어 누구냐!!','거짓말쟁이를 찾는 게임!\n서로간의 매너 지키셔야합니다!',6,0,'1234','2022-08-19 10:02:11','2022-08-19 10:07:01','2022-08-19 10:07:01',2,2),(3,'FESTIVAL',2,3,'Simon Simon 도미닉','힐링 여행 시뮬레이션 WebRTC \"바닷마을 다이어리\"',6,1,'','2022-08-19 10:05:05','2022-08-19 10:05:10','2022-08-19 10:05:10',2,2),(4,'FESTIVAL',3,4,'같이 즐겨요!!!','즐깁시다!!!',6,1,'','2022-08-19 10:05:23','2022-08-19 10:06:07','2022-08-19 10:06:07',3,3),(5,'FESTIVAL',4,5,'바닷마을 파티!','삼겹살을 구워 먹을 예정입니다.',6,1,'','2022-08-19 10:09:12','2022-08-19 10:15:32','2022-08-19 10:15:32',4,4),(6,'FESTIVAL',3,6,'라이어게임 하자!!!','빨리왕',6,1,'','2022-08-19 10:17:31','2022-08-19 10:29:06','2022-08-19 10:29:06',3,3),(7,'FESTIVAL',6,7,'Hello World !','힐링 여행 시뮬레이션 WebRTC \"바닷마을 다이어리\"\n싸닷마을 for us\nus for 싸닷마을\n',6,1,'','2022-08-19 10:18:18','2022-08-19 10:29:33','2022-08-19 10:29:33',6,6),(8,'FESTIVAL',6,8,'바다팀 뒷풀이','우리팀만 들어와',6,0,'1234','2022-08-19 10:31:31','2022-08-19 10:32:57','2022-08-19 10:32:57',6,6),(9,'FESTIVAL',6,9,'바다팀 뒷풀이','우리팀만 들어와 !!',6,0,'1234','2022-08-19 10:33:27','2022-08-19 11:16:15','2022-08-19 11:16:15',6,6),(10,'FESTIVAL',4,10,'바닷마을 축제입니다.','메뉴는 삼겹살 소주입니다.',3,1,'','2022-08-19 11:19:39','2022-08-19 11:21:11','2022-08-19 11:21:11',4,4),(11,'FESTIVAL',4,11,'라이어 게임 고수만!','라이어 게임 고수 들어오세요',4,1,'','2022-08-19 11:40:23','2022-08-19 11:41:24','2022-08-19 11:41:24',4,4);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-19 11:42:35
