-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: localhost    Database: apo_production
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.16.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `exchange`
--

DROP TABLE IF EXISTS `exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(1020) DEFAULT NULL,
  `TransID` varchar(255) DEFAULT NULL,
  `exchange` varchar(255) DEFAULT NULL,
  `froms` varchar(255) DEFAULT NULL,
  `tos` varchar(255) DEFAULT NULL,
  `hash` varchar(1020) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dates` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `btc_address` varchar(1020) DEFAULT NULL,
  `btc_secret` varchar(1020) DEFAULT NULL,
  `eth_address` varchar(1020) DEFAULT NULL,
  `eth_secret` varchar(1020) DEFAULT NULL,
  `xrp_address` varchar(1020) DEFAULT NULL,
  `xrp_secret` varchar(1020) DEFAULT NULL,
  `bch_address` varchar(1020) DEFAULT NULL,
  `bch_secret` varchar(1020) DEFAULT NULL,
  `etc_address` varchar(1020) DEFAULT NULL,
  `etc_secret` varchar(1020) DEFAULT NULL,
  `ltc_address` varchar(1020) DEFAULT NULL,
  `ltc_secret` varchar(1020) DEFAULT NULL,
  `zec_address` varchar(1020) DEFAULT NULL,
  `zec_secret` varchar(1020) DEFAULT NULL,
  `word` varchar(1020) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(1020) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `idtype` varchar(255) DEFAULT NULL,
  `idno` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `status` varchar(10) DEFAULT '0',
  `password_hash` varchar(250) DEFAULT NULL,
  `salt` varchar(250) DEFAULT NULL,
  `hash` varchar(250) DEFAULT NULL,
  `hashes` varchar(250) DEFAULT NULL,
  `otpcode` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2728 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10  2:20:19
