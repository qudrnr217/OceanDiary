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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `oauth_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `provider` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `last_visited` datetime DEFAULT NULL,
  `birth` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `refresh_token` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKdltbr5t0nljpuuo4isxgslt82` (`created_by`),
  KEY `FK2a54xhceitopkkw1hlo3tkv3i` (`updated_by`),
  CONSTRAINT `FK2a54xhceitopkkw1hlo3tkv3i` FOREIGN KEY (`updated_by`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKdltbr5t0nljpuuo4isxgslt82` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2378022250','민하은','gkdms6575@kakao.com','KAKAO','USER',NULL,'1998-11-15 00:00:00','2022-08-19 09:58:46','2022-08-19 09:58:46',NULL,NULL,NULL,NULL),(2,'BoFNkY4xgA9w5PW8Q_iikkoVySmeidk44whHAPHIgMQ','강병국','qudrnr217@naver.com','NAVER','USER','2022-08-19 11:16:15','1997-03-17 00:00:00','2022-08-19 09:59:07','2022-08-19 11:16:15',NULL,NULL,6,NULL),(3,'livrxYuCTpTvEV0AsJPzb4kuDnLYUK8mpmsRW3UUfS4','황재완','jaewan9074@naver.com','NAVER','USER','2022-08-19 10:29:06','1995-10-02 00:00:00','2022-08-19 09:59:45','2022-08-19 10:29:06',NULL,NULL,3,NULL),(4,'dqiU8S7_v_zon6PApuVsSZ8eeT7mnRBz6ZoNIMVII7c','류형주','96fbgudwn@naver.com','NAVER','USER','2022-08-19 11:41:24','1996-07-11 00:00:00','2022-08-19 10:03:19','2022-08-19 11:41:24',NULL,NULL,4,NULL),(5,'2394448524','이지연','jy.agnes.lee@gmail.com','KAKAO','USER','2022-08-19 11:03:53','1998-06-21 00:00:00','2022-08-19 10:09:26','2022-08-19 11:03:53',NULL,NULL,5,NULL),(6,'HdoSqkEH_Yw0QuNx7sSEAsFi4hfHx2BeUBKXU8gJGdM','곽종훈','yi_jonghoon@naver.com','NAVER','USER','2022-08-19 11:16:15','1995-12-07 00:00:00','2022-08-19 10:17:15','2022-08-19 11:16:15',NULL,NULL,6,NULL),(7,'UCYisqrPsQJO0M0u8NRAvuiAI32JZYR7930oYzc5il0','지연','21yeonny@naver.com','NAVER','USER','2022-08-19 10:29:03','1998-06-21 00:00:00','2022-08-19 10:24:22','2022-08-19 10:29:03',NULL,NULL,7,NULL),(8,'yTxL-ZYdefIQX6EHhUGFOw0n2pVDR0UOPkUCjWuuXBU','민하은','gkdms6575@naver.com','NAVER','USER',NULL,'1998-11-15 00:00:00','2022-08-19 10:24:37','2022-08-19 10:24:37',NULL,NULL,NULL,NULL),(9,'2393639027','황재완','jaewanhwang@kakao.com','KAKAO','USER',NULL,'1996-10-02 00:00:00','2022-08-19 10:31:56','2022-08-19 10:31:56',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
