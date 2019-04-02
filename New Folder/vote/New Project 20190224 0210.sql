-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.27


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema muvoter
--

CREATE DATABASE IF NOT EXISTS muvoter;
USE muvoter;

--
-- Temporary table structure for view `results`
--
DROP TABLE IF EXISTS `results`;
DROP VIEW IF EXISTS `results`;
CREATE TABLE `results` (
  `votes` bigint(21),
  `candidateid` int(10) unsigned,
  `year` int(10) unsigned,
  `position` int(10) unsigned
);

--
-- Definition of table `admins`
--

DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(1000) DEFAULT NULL,
  `groupID` int(10) unsigned DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`adminId`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admins`
--

/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` (`adminId`,`firstName`,`lastName`,`phone`,`username`,`password`,`groupID`,`email`) VALUES 
 (9,'Joseph','Kimani','0723160064','joseph','1234',1,'jnepmark@gmail.com'),
 (11,'Test','test','0728140544','test','1234',0,'test@mu.ac.ke'),
 (12,'joe','ndungu','8765020','joe','12345',0,'kjskjdj@gmail.com');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;


--
-- Definition of table `audit`
--

DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit` (
  `idaudit` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `actionperformed` varchar(200) NOT NULL,
  `dateperformed` datetime NOT NULL,
  `user` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idaudit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `audit`
--

/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;


--
-- Definition of table `blockchain`
--

DROP TABLE IF EXISTS `blockchain`;
CREATE TABLE `blockchain` (
  `idblockchain` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `currenthash` varchar(200) NOT NULL,
  `originaldata` varchar(200) NOT NULL,
  `timeStamp` datetime NOT NULL,
  `previoushash` varchar(300) NOT NULL,
  PRIMARY KEY (`idblockchain`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `blockchain`
--

/*!40000 ALTER TABLE `blockchain` DISABLE KEYS */;
/*!40000 ALTER TABLE `blockchain` ENABLE KEYS */;


--
-- Definition of table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
CREATE TABLE `candidate` (
  `idcandidate` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `positionid` int(10) unsigned NOT NULL,
  `voterid` int(10) unsigned NOT NULL,
  `status` int(10) unsigned NOT NULL,
  `staffid` int(11) DEFAULT NULL,
  `creationdate` datetime NOT NULL,
  `policies` varchar(400) NOT NULL,
  `alias` varchar(45) NOT NULL,
  `photo` varchar(100) NOT NULL,
  PRIMARY KEY (`idcandidate`),
  KEY `FK_candidate_1` (`positionid`),
  KEY `FK_candidate_2` (`voterid`),
  KEY `FK_candidate_3` (`status`),
  KEY `FK_candidate_4` (`staffid`),
  CONSTRAINT `FK_candidate_1` FOREIGN KEY (`positionid`) REFERENCES `position` (`idposition`),
  CONSTRAINT `FK_candidate_2` FOREIGN KEY (`voterid`) REFERENCES `voter` (`idvoter`),
  CONSTRAINT `FK_candidate_3` FOREIGN KEY (`status`) REFERENCES `status` (`idstatus`),
  CONSTRAINT `FK_candidate_4` FOREIGN KEY (`staffid`) REFERENCES `admins` (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `candidate`
--

/*!40000 ALTER TABLE `candidate` DISABLE KEYS */;
INSERT INTO `candidate` (`idcandidate`,`positionid`,`voterid`,`status`,`staffid`,`creationdate`,`policies`,`alias`,`photo`) VALUES 
 (1,1,1,1,9,'2017-03-16 18:33:54','will advicate for more hostels to be built, will increase accessibility of WIFI, will make sure moi university is noticed within kenya','chairman mandevu','C:/photos/jnep.jpg'),
 (2,2,2,1,9,'2017-03-16 18:47:57','will advicate for more hostels to be built, will increase accessibility of WIFI, will make sure moi university is noticed within kenya','chairman mandevu','C:/photos/jnep.jpg'),
 (3,1,3,1,9,'2017-03-16 21:36:55','will advicate for more hostels to be built, will increase accessibility of WIFI, will make sure moi university is noticed within kenya','chairman mandevu','C:/photos/jnep.jpg'),
 (4,2,4,1,9,'2017-03-16 21:37:11','will advicate for more hostels to be built, will increase accessibility of WIFI, will make sure moi university is noticed within kenya','chairman mandevu','C:/photos/jnep.jpg'),
 (5,2,7,1,9,'2017-03-16 21:49:37','will advicate for more hostels to be built, will increase accessibility of WIFI, will make sure moi university is noticed within kenya','chairman mandevu','C:/photos/jnep.jpg'),
 (6,1,8,1,9,'2017-03-16 21:50:16','Make sure that there is a fence all over Moi University to stop theft and insecurity','KIbich22','C:/photos/jnep.jpg');
/*!40000 ALTER TABLE `candidate` ENABLE KEYS */;


--
-- Definition of table `comments`
--

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `idcomments` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `comment` varchar(400) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idcomments`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comments`
--

/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` (`idcomments`,`name`,`comment`,`email`,`phone`) VALUES 
 (1,'Amon Kiptoo Sabul','THERE WAS HIGH RIGGING ON THE SIDE OF THE CHAIRMAN','chepserron@gmail.com',728140544),
 (2,'Amon Kiptoo Sabul','THERE WAS HIGH RIGGING ON THE SIDE OF THE vice chairman too','chepserron@gmail.com',728140544);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;


--
-- Definition of table `department`
--

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `iddepartment` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `roles` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`iddepartment`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department`
--

/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`iddepartment`,`name`,`roles`) VALUES 
 (1,'ICT','these are ICT administrators');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;


--
-- Definition of table `groups`
--

DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `idgroups` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  `description` varchar(200) NOT NULL,
  `staffid` int(11) unsigned NOT NULL,
  `creationdate` datetime NOT NULL,
  PRIMARY KEY (`idgroups`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groups`
--

/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;


--
-- Definition of table `position`
--

DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `idposition` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(200) NOT NULL,
  `staffid` int(11) DEFAULT NULL,
  `creationdate` datetime NOT NULL,
  `academicyear` int(10) unsigned NOT NULL,
  `roles` varchar(400) NOT NULL,
  `deptid` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idposition`),
  KEY `FK_position_1` (`staffid`),
  KEY `FK_position_2` (`deptid`),
  CONSTRAINT `FK_position_1` FOREIGN KEY (`staffid`) REFERENCES `admins` (`adminId`),
  CONSTRAINT `FK_position_2` FOREIGN KEY (`deptid`) REFERENCES `department` (`iddepartment`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `position`
--

/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` (`idposition`,`name`,`description`,`staffid`,`creationdate`,`academicyear`,`roles`,`deptid`) VALUES 
 (1,'chairman','the position belongs to a chairman who is the overall leader of voters',NULL,'2017-03-16 00:00:00',2017,'lead voters, represent voters in university activities, answerable to the administration in issues concerning voters',1),
 (2,'vice chair person','the position belongs to the vice chairman who is a direct representative of the chairman',NULL,'2017-03-16 00:00:00',2017,'lead the other voters in their daily university affairs',1);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;


--
-- Definition of table `quotes`
--

DROP TABLE IF EXISTS `quotes`;
CREATE TABLE `quotes` (
  `idquotes` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `quoter` varchar(45) DEFAULT NULL,
  `quoteDescription` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`idquotes`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quotes`
--

/*!40000 ALTER TABLE `quotes` DISABLE KEYS */;
INSERT INTO `quotes` (`idquotes`,`quoter`,`quoteDescription`) VALUES 
 (7,'Sabul','the system is quite intuitive and easy to use ');
/*!40000 ALTER TABLE `quotes` ENABLE KEYS */;


--
-- Definition of table `result`
--

DROP TABLE IF EXISTS `result`;
CREATE TABLE `result` (
  `idresults` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `candidateid` int(10) unsigned NOT NULL,
  `result` int(11) NOT NULL,
  `voterid` int(10) unsigned NOT NULL,
  `creationate` datetime NOT NULL,
  `status` int(10) unsigned NOT NULL,
  `registrationyear` int(10) unsigned NOT NULL,
  `positionid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idresults`),
  KEY `FK_result_1` (`candidateid`),
  KEY `FK_result_2` (`voterid`),
  KEY `FK_result_3` (`status`),
  KEY `FK_result_4` (`positionid`),
  CONSTRAINT `FK_result_1` FOREIGN KEY (`candidateid`) REFERENCES `candidate` (`idcandidate`),
  CONSTRAINT `FK_result_2` FOREIGN KEY (`voterid`) REFERENCES `voter` (`idvoter`),
  CONSTRAINT `FK_result_3` FOREIGN KEY (`status`) REFERENCES `status` (`idstatus`),
  CONSTRAINT `FK_result_4` FOREIGN KEY (`positionid`) REFERENCES `position` (`idposition`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `result`
--

/*!40000 ALTER TABLE `result` DISABLE KEYS */;
INSERT INTO `result` (`idresults`,`candidateid`,`result`,`voterid`,`creationate`,`status`,`registrationyear`,`positionid`) VALUES 
 (1,1,1,1,'2017-03-16 19:48:52',1,117,1),
 (2,2,1,1,'2017-03-16 19:49:02',1,117,2),
 (3,2,1,1,'2017-03-16 20:19:19',1,117,2),
 (4,2,1,1,'2017-03-16 20:19:22',1,117,2),
 (5,2,1,1,'2017-03-16 20:19:24',1,117,2),
 (6,2,1,1,'2017-03-16 20:19:25',1,117,2),
 (7,2,1,1,'2017-03-16 20:19:26',1,117,2),
 (8,2,1,1,'2017-03-16 20:19:27',1,117,2),
 (9,2,1,1,'2017-03-16 20:19:27',1,117,2),
 (10,2,1,1,'2017-03-16 20:19:28',1,117,2),
 (11,2,1,1,'2017-03-16 20:19:29',1,117,2),
 (12,1,1,1,'2017-03-16 20:19:30',1,117,1),
 (13,1,1,1,'2017-03-16 20:23:29',1,117,1),
 (14,1,1,1,'2017-03-16 20:23:30',1,117,1),
 (15,2,1,1,'2017-03-16 20:23:32',1,117,2),
 (16,2,1,1,'2017-03-16 20:23:32',1,117,2),
 (17,1,1,1,'2017-03-16 20:23:34',1,117,1),
 (18,1,1,1,'2017-03-16 20:23:35',1,117,1),
 (19,2,1,1,'2017-03-16 20:23:35',1,117,2),
 (20,2,1,1,'2017-03-16 20:23:36',1,117,2),
 (21,2,1,1,'2017-03-16 20:23:58',1,117,2),
 (22,2,1,1,'2017-03-16 20:23:58',1,117,2),
 (23,2,1,1,'2017-03-16 20:23:59',1,117,2),
 (24,2,1,1,'2017-03-16 20:24:00',1,117,2),
 (25,2,1,1,'2017-03-16 20:24:00',1,117,2),
 (26,2,1,1,'2017-03-16 20:34:33',1,117,2),
 (27,2,1,1,'2017-03-16 20:34:34',1,117,2),
 (28,2,1,1,'2017-03-16 20:34:35',1,117,2),
 (29,2,1,1,'2017-03-16 20:34:36',1,117,2),
 (30,2,1,1,'2017-03-16 20:34:37',1,117,2),
 (31,2,1,1,'2017-03-16 20:34:37',1,117,2),
 (32,2,1,1,'2017-03-16 20:34:38',1,117,2),
 (33,2,1,1,'2017-03-16 20:34:38',1,117,2),
 (34,2,1,1,'2017-03-16 20:34:38',1,117,2),
 (35,2,1,1,'2017-03-16 20:34:39',1,117,2),
 (36,2,1,1,'2017-03-16 20:34:39',1,117,2),
 (37,2,1,1,'2017-03-16 20:34:40',1,117,2),
 (38,2,1,1,'2017-03-16 20:34:41',1,117,2),
 (39,2,1,1,'2017-03-16 20:34:41',1,117,2),
 (40,2,1,1,'2017-03-16 20:34:42',1,117,2),
 (41,2,1,1,'2017-03-16 20:34:42',1,117,2),
 (42,2,1,1,'2017-03-16 20:34:43',1,117,2),
 (43,2,1,1,'2017-03-16 20:34:43',1,117,2),
 (44,2,1,1,'2017-03-16 20:34:44',1,117,2),
 (45,2,1,1,'2017-03-16 20:34:44',1,117,2),
 (46,2,1,1,'2017-03-16 20:34:45',1,117,2),
 (47,2,1,1,'2017-03-16 20:34:46',1,117,2),
 (48,2,1,1,'2017-03-16 20:34:46',1,117,2),
 (49,2,1,1,'2017-03-16 20:34:47',1,117,2),
 (50,2,1,1,'2017-03-16 20:35:25',1,117,2),
 (51,2,1,1,'2017-03-16 20:35:26',1,117,2),
 (52,2,1,1,'2017-03-16 20:35:26',1,117,2),
 (53,2,1,1,'2017-03-16 20:35:27',1,117,2),
 (54,2,1,1,'2017-03-16 20:35:27',1,117,2),
 (55,2,1,1,'2017-03-16 20:35:28',1,117,2),
 (56,2,1,1,'2017-03-16 20:35:29',1,117,2),
 (57,2,1,1,'2017-03-16 20:35:29',1,117,2),
 (58,2,1,1,'2017-03-16 20:35:30',1,117,2),
 (59,2,1,1,'2017-03-16 20:35:31',1,117,2),
 (60,2,1,1,'2017-03-16 20:35:31',1,117,2),
 (61,2,1,1,'2017-03-16 20:35:32',1,117,2),
 (62,2,1,1,'2017-03-16 20:35:46',1,117,2),
 (63,2,1,1,'2017-03-16 20:35:49',1,117,2),
 (64,2,1,1,'2017-03-16 20:35:49',1,117,2),
 (65,2,1,1,'2017-03-16 20:35:50',1,117,2),
 (66,2,1,1,'2017-03-16 20:35:51',1,117,2),
 (67,2,1,1,'2017-03-16 20:35:51',1,117,2),
 (68,2,1,1,'2017-03-16 20:35:51',1,117,2),
 (69,2,1,1,'2017-03-16 20:35:52',1,117,2),
 (70,2,1,1,'2017-03-16 20:35:52',1,117,2),
 (71,2,1,1,'2017-03-16 20:35:53',1,117,2),
 (72,2,1,1,'2017-03-16 20:35:53',1,117,2),
 (73,2,1,1,'2017-03-16 20:35:53',1,117,2),
 (74,2,1,1,'2017-03-16 20:35:54',1,117,2),
 (75,2,1,1,'2017-03-16 20:35:54',1,117,2),
 (76,2,1,1,'2017-03-16 20:35:55',1,117,2),
 (77,2,1,1,'2017-03-16 20:35:55',1,117,2),
 (78,2,1,1,'2017-03-16 20:35:56',1,117,2),
 (79,2,1,1,'2017-03-16 20:35:56',1,117,2),
 (80,2,1,1,'2017-03-16 20:35:56',1,117,2),
 (81,2,1,1,'2017-03-16 20:35:57',1,117,2),
 (82,2,1,1,'2017-03-16 20:35:57',1,117,2),
 (83,2,1,1,'2017-03-16 20:35:58',1,117,2),
 (84,2,1,1,'2017-03-16 20:35:58',1,117,2),
 (85,2,1,1,'2017-03-16 20:35:59',1,117,2),
 (86,2,1,1,'2017-03-16 20:35:59',1,117,2),
 (87,2,1,1,'2017-03-16 20:36:00',1,117,2),
 (88,2,1,1,'2017-03-16 20:35:29',1,117,2),
 (89,2,1,1,'2017-03-16 20:35:30',1,117,2),
 (90,2,1,1,'2017-03-16 20:19:29',1,117,2),
 (91,2,1,1,'2017-03-16 20:35:54',1,117,2),
 (92,2,1,1,'2017-03-16 20:35:56',1,117,2),
 (93,2,1,1,'2017-03-16 20:34:45',1,117,2),
 (94,2,1,1,'2017-03-16 20:34:43',1,117,2),
 (95,2,1,1,'2017-03-16 20:34:42',1,117,2),
 (96,2,1,1,'2017-03-16 20:19:26',1,117,2),
 (97,2,1,1,'2017-03-16 20:36:00',1,117,2),
 (98,2,1,1,'2017-03-16 20:34:44',1,117,2),
 (99,2,1,1,'2017-03-16 20:34:41',1,117,2),
 (100,2,1,1,'2017-03-16 20:35:56',1,117,2),
 (101,2,1,1,'2017-03-16 20:34:46',1,117,2),
 (102,2,1,1,'2017-03-16 20:34:47',1,117,2),
 (103,2,1,1,'2017-03-16 20:34:38',1,117,2),
 (104,2,1,1,'2017-03-16 20:19:22',1,117,2),
 (105,2,1,1,'2017-03-16 20:35:26',1,117,2),
 (106,2,1,1,'2017-03-16 20:23:35',1,117,2),
 (107,2,1,1,'2017-03-16 20:35:32',1,117,2),
 (108,2,1,1,'2017-03-16 20:23:36',1,117,2),
 (109,2,1,1,'2017-03-16 20:35:49',1,117,2),
 (110,2,1,1,'2017-03-16 20:35:52',1,117,2),
 (111,2,1,1,'2017-03-16 20:35:56',1,117,2),
 (112,2,1,1,'2017-03-16 20:34:35',1,117,2),
 (113,2,1,1,'2017-03-16 20:23:59',1,117,2),
 (114,2,1,1,'2017-03-16 20:35:54',1,117,2),
 (115,2,1,1,'2017-03-16 20:34:42',1,117,2),
 (116,2,1,1,'2017-03-16 20:34:39',1,117,2),
 (117,2,1,1,'2017-03-16 20:35:31',1,117,2),
 (118,2,1,1,'2017-03-16 20:35:50',1,117,2),
 (119,2,1,1,'2017-03-16 20:35:59',1,117,2),
 (120,2,1,1,'2017-03-16 20:35:55',1,117,2),
 (121,2,1,1,'2017-03-16 20:35:57',1,117,2),
 (122,2,1,1,'2017-03-16 20:34:44',1,117,2),
 (123,2,1,1,'2017-03-16 20:19:27',1,117,2),
 (124,2,1,1,'2017-03-16 20:19:25',1,117,2),
 (125,2,1,1,'2017-03-16 20:35:58',1,117,2),
 (126,2,1,1,'2017-03-16 20:34:36',1,117,2),
 (127,2,1,1,'2017-03-16 20:34:41',1,117,2),
 (128,2,1,1,'2017-03-16 20:24:00',1,117,2),
 (129,2,1,1,'2017-03-16 20:35:26',1,117,2),
 (130,2,1,1,'2017-03-16 20:34:33',1,117,2),
 (131,2,1,1,'2017-03-16 20:35:27',1,117,2),
 (132,2,1,1,'2017-03-16 20:35:59',1,117,2),
 (133,2,1,1,'2017-03-16 20:35:55',1,117,2),
 (134,2,1,1,'2017-03-16 20:35:58',1,117,2),
 (135,2,1,1,'2017-03-16 20:35:57',1,117,2),
 (136,2,1,1,'2017-03-16 20:35:51',1,117,2),
 (137,2,1,1,'2017-03-16 20:23:32',1,117,2),
 (138,2,1,1,'2017-03-16 20:23:58',1,117,2),
 (139,2,1,1,'2017-03-16 20:35:53',1,117,2),
 (140,2,1,1,'2017-03-16 20:24:00',1,117,2),
 (141,2,1,1,'2017-03-16 20:34:38',1,117,2),
 (142,2,1,1,'2017-03-16 20:35:29',1,117,2),
 (143,2,1,1,'2017-03-16 20:34:43',1,117,2),
 (144,2,1,1,'2017-03-16 20:35:53',1,117,2),
 (145,2,1,1,'2017-03-16 20:35:25',1,117,2),
 (146,2,1,1,'2017-03-16 20:23:58',1,117,2),
 (147,2,1,1,'2017-03-16 20:23:32',1,117,2),
 (148,2,1,1,'2017-03-16 19:49:02',1,117,2),
 (149,2,1,1,'2017-03-16 20:35:46',1,117,2),
 (150,2,1,1,'2017-03-16 20:19:27',1,117,2),
 (151,2,1,1,'2017-03-16 20:34:37',1,117,2),
 (152,2,1,1,'2017-03-16 20:35:53',1,117,2),
 (153,2,1,1,'2017-03-16 20:34:46',1,117,2),
 (154,2,1,1,'2017-03-16 20:34:34',1,117,2),
 (155,2,1,1,'2017-03-16 20:19:24',1,117,2),
 (156,2,1,1,'2017-03-16 20:35:49',1,117,2),
 (157,2,1,1,'2017-03-16 20:35:28',1,117,2),
 (158,2,1,1,'2017-03-16 20:34:40',1,117,2),
 (159,2,1,1,'2017-03-16 20:34:38',1,117,2),
 (160,2,1,1,'2017-03-16 20:19:19',1,117,2),
 (161,2,1,1,'2017-03-16 20:19:28',1,117,2),
 (162,2,1,1,'2017-03-16 20:35:31',1,117,2),
 (163,2,1,1,'2017-03-16 20:35:52',1,117,2),
 (164,2,1,1,'2017-03-16 20:34:37',1,117,2),
 (165,2,1,1,'2017-03-16 20:35:27',1,117,2),
 (166,2,1,1,'2017-03-16 20:35:51',1,117,2),
 (167,2,1,1,'2017-03-16 20:34:39',1,117,2),
 (168,2,1,1,'2017-03-16 20:35:51',1,117,2),
 (169,1,1,1,'2017-03-16 20:23:34',1,117,1),
 (170,1,1,1,'2017-03-16 20:23:35',1,117,1),
 (171,1,1,1,'2017-03-16 20:23:30',1,117,1),
 (172,1,1,1,'2017-03-16 20:23:29',1,117,1),
 (173,1,1,1,'2017-03-16 20:19:30',1,117,1),
 (174,1,1,1,'2017-03-16 19:48:52',1,117,1),
 (175,4,1,1,'2017-03-16 21:37:37',1,117,2),
 (176,4,1,1,'2017-03-16 21:37:39',1,117,2),
 (177,4,1,1,'2017-03-16 21:37:40',1,117,2),
 (178,4,1,1,'2017-03-16 21:37:42',1,117,2),
 (179,3,1,1,'2017-03-16 21:37:44',1,117,1),
 (180,3,1,1,'2017-03-16 21:37:45',1,117,1),
 (181,3,1,1,'2017-03-16 21:37:59',1,117,1),
 (182,3,1,1,'2017-03-16 21:38:00',1,117,1),
 (183,3,1,1,'2017-03-16 21:38:02',1,117,1),
 (184,3,1,1,'2017-03-16 21:38:04',1,117,1),
 (185,3,1,1,'2017-03-16 21:38:19',1,117,1),
 (186,3,1,1,'2017-03-16 21:38:22',1,117,1),
 (187,3,1,1,'2017-03-16 21:38:23',1,117,1),
 (188,4,1,1,'2017-03-16 21:37:37',1,117,2),
 (189,4,1,1,'2017-03-16 21:37:42',1,117,2),
 (190,4,1,1,'2017-03-16 21:37:39',1,117,2),
 (191,4,1,1,'2017-03-16 21:37:40',1,117,2),
 (192,6,1,8,'2017-03-16 21:52:58',1,117,1),
 (193,6,1,8,'2017-03-16 21:53:00',1,117,1),
 (194,6,1,8,'2017-03-16 21:53:01',1,117,1),
 (195,6,1,8,'2017-03-16 21:53:03',1,117,1),
 (196,6,1,8,'2017-03-16 21:53:04',1,117,1),
 (197,5,1,8,'2017-03-16 21:53:06',1,117,2),
 (198,5,1,8,'2017-03-16 21:53:09',1,117,2),
 (199,5,1,8,'2017-03-16 21:53:11',1,117,2),
 (200,5,1,8,'2017-03-16 21:53:12',1,117,2),
 (201,4,1,8,'2017-03-16 21:53:14',1,117,2),
 (202,2,1,8,'2017-03-16 21:53:18',1,117,2),
 (203,4,1,8,'2017-03-16 21:53:19',1,117,2),
 (204,3,1,8,'2017-03-16 21:53:21',1,117,1),
 (205,1,1,8,'2017-03-16 21:53:25',1,117,1),
 (206,2,1,8,'2017-03-16 21:53:27',1,117,2),
 (207,4,1,8,'2017-03-16 21:53:29',1,117,2),
 (208,6,1,8,'2017-03-16 21:53:47',1,117,1),
 (209,6,1,8,'2017-03-16 21:53:48',1,117,1),
 (210,6,1,8,'2017-03-16 21:53:50',1,117,1),
 (211,5,1,8,'2017-03-16 21:53:52',1,117,2),
 (212,5,1,8,'2017-03-16 22:08:23',1,117,2),
 (213,5,1,8,'2017-03-16 22:08:25',1,117,2),
 (214,5,1,8,'2017-03-16 22:08:26',1,117,2),
 (215,5,1,8,'2017-03-16 22:08:28',1,117,2),
 (216,5,1,8,'2017-03-16 22:08:29',1,117,2),
 (217,5,1,8,'2017-03-16 22:08:31',1,117,2),
 (218,5,1,8,'2017-03-16 22:08:32',1,117,2),
 (219,5,1,8,'2017-03-16 22:08:33',1,117,2),
 (220,5,1,8,'2017-03-16 22:08:35',1,117,2),
 (221,5,1,8,'2017-03-16 22:08:37',1,117,2),
 (222,5,1,8,'2017-03-16 22:08:39',1,117,2),
 (223,5,1,8,'2017-03-16 22:08:40',1,117,2),
 (224,5,1,8,'2017-03-16 22:08:42',1,117,2),
 (225,5,1,8,'2017-03-16 22:08:43',1,117,2),
 (226,5,1,8,'2017-03-16 22:08:45',1,117,2),
 (227,5,1,8,'2017-03-16 22:08:46',1,117,2),
 (228,5,1,8,'2017-03-16 22:08:48',1,117,2),
 (229,5,1,8,'2017-03-16 22:08:49',1,117,2),
 (230,5,1,8,'2017-03-16 22:08:51',1,117,2),
 (231,5,1,8,'2017-03-16 22:09:10',1,117,2),
 (232,5,1,8,'2017-03-16 22:09:12',1,117,2),
 (233,5,1,8,'2017-03-16 22:09:13',1,117,2),
 (234,5,1,8,'2017-03-16 22:09:15',1,117,2),
 (235,5,1,8,'2017-03-16 22:09:16',1,117,2),
 (236,5,1,8,'2017-03-16 22:09:18',1,117,2),
 (237,5,1,8,'2017-03-16 22:09:19',1,117,2),
 (238,5,1,8,'2017-03-16 22:09:21',1,117,2),
 (239,5,1,8,'2017-03-16 22:09:24',1,117,2),
 (240,5,1,8,'2017-03-16 22:09:25',1,117,2),
 (241,5,1,8,'2017-03-16 22:09:27',1,117,2),
 (242,5,1,8,'2017-03-16 22:09:30',1,117,2),
 (243,5,1,8,'2017-03-16 22:09:31',1,117,2),
 (244,5,1,8,'2017-03-16 22:09:32',1,117,2),
 (245,5,1,8,'2017-03-16 22:09:34',1,117,2),
 (246,5,1,8,'2017-03-16 22:09:35',1,117,2),
 (247,5,1,8,'2017-03-16 22:09:36',1,117,2),
 (248,5,1,8,'2017-03-16 22:09:38',1,117,2),
 (249,5,1,8,'2017-03-16 22:09:41',1,117,2),
 (250,2,1,8,'2017-03-17 07:27:05',1,117,2),
 (251,4,1,8,'2017-03-17 07:27:09',1,117,2),
 (252,6,1,8,'2017-03-17 07:27:11',1,117,1),
 (253,1,1,9,'2017-03-17 06:30:07',1,117,1),
 (254,1,1,9,'2017-03-17 12:16:55',1,117,1);
/*!40000 ALTER TABLE `result` ENABLE KEYS */;


--
-- Definition of table `status`
--

DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `idstatus` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `staffid` int(11) NOT NULL,
  `creationdate` datetime NOT NULL,
  PRIMARY KEY (`idstatus`),
  KEY `FK_status_1` (`staffid`),
  CONSTRAINT `FK_status_1` FOREIGN KEY (`staffid`) REFERENCES `admins` (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status`
--

/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` (`idstatus`,`name`,`description`,`staffid`,`creationdate`) VALUES 
 (1,'Active','the stuent is active and continuing',9,'2017-03-16 00:00:00'),
 (2,'inactive','the voter has either been dicsontinued or is not allowed to vie for any position',9,'2017-03-16 00:00:00');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;


--
-- Definition of table `voter`
--

DROP TABLE IF EXISTS `voter`;
CREATE TABLE `voter` (
  `idvoter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(200) NOT NULL,
  `regnum` varchar(45) NOT NULL,
  `dob` datetime NOT NULL,
  `nationalid` varchar(45) NOT NULL,
  `department` int(10) unsigned NOT NULL,
  `yearsvoted` int(10) unsigned DEFAULT NULL,
  `status` int(10) unsigned NOT NULL,
  `staffid` int(11) NOT NULL,
  `creationDate` datetime NOT NULL,
  `secondname` varchar(45) NOT NULL,
  `thirdname` varchar(45) NOT NULL,
  `registrationyear` int(10) unsigned DEFAULT NULL,
  `emailadd` varchar(100) NOT NULL,
  `phone` int(10) unsigned NOT NULL,
  `gender` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`idvoter`),
  KEY `FK_voter_1` (`staffid`),
  KEY `FK_voter_2` (`status`),
  KEY `FK_voter_3` (`department`),
  CONSTRAINT `FK_voter_1` FOREIGN KEY (`staffid`) REFERENCES `admins` (`adminId`),
  CONSTRAINT `FK_voter_2` FOREIGN KEY (`status`) REFERENCES `status` (`idstatus`),
  CONSTRAINT `FK_voter_3` FOREIGN KEY (`department`) REFERENCES `department` (`iddepartment`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `voter`
--

/*!40000 ALTER TABLE `voter` DISABLE KEYS */;
INSERT INTO `voter` (`idvoter`,`firstname`,`regnum`,`dob`,`nationalid`,`department`,`yearsvoted`,`status`,`staffid`,`creationDate`,`secondname`,`thirdname`,`registrationyear`,`emailadd`,`phone`,`gender`,`password`) VALUES 
 (1,'Joseph','24403355','2017-03-16 00:00:00','24655940',1,0,1,9,'2017-03-16 00:00:00','Kimani','Joseph',4,'jnepmark@gail.com',728140544,'male','2222'),
 (2,'Philip','24403355','2017-03-16 00:00:00','24655940',1,0,1,9,'2017-03-16 00:00:00','Kibiru','Nyamongo',4,'jnepmark@gmail.com',728140544,'male','4321'),
 (3,'Test2','24403355','2017-03-16 00:00:00','24654546',1,0,1,9,'2017-03-16 00:00:00','Test2','Test2',6,'test2@gmail.com',728140544,'male','test2'),
 (4,'Test4','24403355','2017-03-16 00:00:00','24635353',1,0,1,9,'2017-03-16 00:00:00','Test4','Test4',2017,'test4@gmail.com',728140544,'male','test3'),
 (7,'Test6','24403355','2017-03-16 00:00:00','24635353',1,0,1,9,'2017-03-16 00:00:00','Test6','Test6',2017,'test6@gmail.com',728140544,'male','test3'),
 (8,'Elias','24403355','2017-03-16 00:00:00','24655940',1,0,1,9,'2017-03-16 00:00:00','Kibiru','Nyamongo',4,'jnepmark@gmail.com',728140544,'male','1234'),
 (9,'Test1','24403355','2017-03-16 00:00:00','24655940',1,0,1,9,'2017-03-16 00:00:00','Test1','Test1',4,'jnepmark@gmail.com',728140544,'male','3333');
/*!40000 ALTER TABLE `voter` ENABLE KEYS */;


--
-- Definition of view `results`
--

DROP TABLE IF EXISTS `results`;
DROP VIEW IF EXISTS `results`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `results` AS select count(0) AS `votes`,`result`.`candidateid` AS `candidateid`,`result`.`registrationyear` AS `year`,`result`.`positionid` AS `position` from `result` group by `result`.`candidateid`,`result`.`registrationyear`,`result`.`positionid`;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
