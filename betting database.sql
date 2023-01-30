/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 8.0.30 : Database - betting_app
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`betting_app` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `betting_app`;

/*Table structure for table `bet` */

DROP TABLE IF EXISTS `bet`;

CREATE TABLE `bet` (
  `betID` bigint NOT NULL AUTO_INCREMENT,
  `ticketID` bigint NOT NULL,
  `betOdds` double DEFAULT NULL,
  `state` varchar(11) DEFAULT 'unprocessed',
  `gameID` bigint DEFAULT NULL,
  `typeID` bigint DEFAULT NULL,
  PRIMARY KEY (`betID`,`ticketID`),
  KEY `ticketID` (`ticketID`),
  KEY `bet_ibfk_2` (`typeID`),
  KEY `match` (`gameID`,`typeID`),
  CONSTRAINT `bet_ibfk_4` FOREIGN KEY (`ticketID`) REFERENCES `ticket` (`ticketID`),
  CONSTRAINT `bet_ibfk_5` FOREIGN KEY (`gameID`, `typeID`) REFERENCES `odds` (`gameID`, `typeID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bet` */

insert  into `bet`(`betID`,`ticketID`,`betOdds`,`state`,`gameID`,`typeID`) values 
(18,19,1.6,'X',18,2),
(19,20,1.4,'✓',18,1),
(20,21,1.6,'X',18,2),
(21,22,1.9,'✓',20,2),
(22,22,1.3,'✓',21,8),
(23,23,1.8,'processed',22,3),
(24,23,1.2,'processed',23,10),
(25,24,1.8,'unprocessed',25,2),
(26,25,1.8,'canceled',25,2),
(27,26,1.87,'processed',28,2),
(28,27,1.87,'processed',28,2),
(29,28,1.87,'processed',28,2),
(30,29,1.99,'processed',28,3),
(31,30,1.4,'processed',29,2),
(32,31,1.3,'canceled',29,1),
(33,32,1.4,'canceled',29,2);

/*Table structure for table `bettype` */

DROP TABLE IF EXISTS `bettype`;

CREATE TABLE `bettype` (
  `typeID` bigint NOT NULL AUTO_INCREMENT,
  `typeName` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`typeID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bettype` */

insert  into `bettype`(`typeID`,`typeName`) values 
(1,'1'),
(2,'X'),
(3,'2'),
(4,'1X'),
(5,'X2'),
(6,'12'),
(7,'3+'),
(8,'2-4'),
(9,'0-2'),
(10,'GG'),
(11,'NG');

/*Table structure for table `game` */

DROP TABLE IF EXISTS `game`;

CREATE TABLE `game` (
  `gameID` bigint NOT NULL AUTO_INCREMENT,
  `dateOfPlay` datetime DEFAULT NULL,
  `home` bigint DEFAULT NULL,
  `homeGoals` int DEFAULT NULL,
  `away` bigint DEFAULT NULL,
  `awayGoals` int DEFAULT NULL,
  `state` varchar(2) DEFAULT 'NS',
  PRIMARY KEY (`gameID`),
  KEY `home` (`home`),
  KEY `away` (`away`),
  CONSTRAINT `game_ibfk_1` FOREIGN KEY (`home`) REFERENCES `team` (`teamID`),
  CONSTRAINT `game_ibfk_2` FOREIGN KEY (`away`) REFERENCES `team` (`teamID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `game` */

insert  into `game`(`gameID`,`dateOfPlay`,`home`,`homeGoals`,`away`,`awayGoals`,`state`) values 
(18,'2022-11-22 20:00:00',2,3,1,1,'FT'),
(19,'2022-11-17 19:17:00',7,1,8,2,'FT'),
(20,'2023-01-23 14:30:00',4,1,1,1,'FT'),
(21,'2023-01-23 14:30:00',7,0,8,3,'FT'),
(22,'2023-01-23 14:45:00',2,0,9,0,'IP'),
(23,'2023-01-23 14:45:00',23,0,24,0,'IP'),
(24,'2023-01-23 20:25:00',2,0,19,0,'IP'),
(25,'1000-01-30 20:00:00',7,0,1,0,'IP'),
(28,'2023-01-30 20:00:00',6,0,23,0,'NS'),
(29,'2023-02-20 20:00:00',9,0,21,0,'NS');

/*Table structure for table `odds` */

DROP TABLE IF EXISTS `odds`;

CREATE TABLE `odds` (
  `gameID` bigint NOT NULL,
  `typeID` bigint NOT NULL,
  `odds` double(5,2) DEFAULT NULL,
  PRIMARY KEY (`typeID`,`gameID`),
  KEY `betID` (`gameID`),
  CONSTRAINT `odds_ibfk_4` FOREIGN KEY (`typeID`) REFERENCES `bettype` (`typeID`),
  CONSTRAINT `odds_ibfk_5` FOREIGN KEY (`gameID`) REFERENCES `game` (`gameID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `odds` */

insert  into `odds`(`gameID`,`typeID`,`odds`) values 
(18,1,1.40),
(19,1,1.40),
(20,1,1.20),
(21,1,1.76),
(22,1,1.40),
(23,1,1.80),
(24,1,1.20),
(28,1,1.30),
(29,1,1.30),
(18,2,1.60),
(19,2,1.60),
(20,2,1.90),
(21,2,1.50),
(22,2,1.60),
(23,2,1.50),
(24,2,1.70),
(25,2,1.80),
(28,2,1.87),
(29,2,1.40),
(19,3,1.70),
(20,3,2.00),
(21,3,1.80),
(22,3,1.80),
(23,3,1.70),
(24,3,2.00),
(25,3,1.90),
(28,3,1.99),
(29,3,1.80),
(20,4,1.10),
(21,4,1.43),
(20,5,1.50),
(21,5,1.45),
(20,6,1.20),
(21,6,1.34),
(20,7,1.80),
(21,7,1.60),
(20,8,1.50),
(21,8,1.30),
(21,9,2.00),
(23,10,1.20),
(23,11,1.60);

/*Table structure for table `team` */

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `teamID` bigint NOT NULL AUTO_INCREMENT,
  `teamName` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`teamID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `team` */

insert  into `team`(`teamID`,`teamName`) values 
(1,'West Ham'),
(2,'Arsenal'),
(4,'Chelsea'),
(5,'Everton'),
(6,'Liverpul'),
(7,'Manchester City'),
(8,'Manchester United'),
(9,'Tottenham'),
(10,'Leeds'),
(11,'Crystal Palace'),
(13,'Newcastle'),
(14,'Aston Villa'),
(15,'Leicester'),
(16,'Nottingham Forest'),
(17,'Wolves'),
(19,'Bretford'),
(20,'Brighton'),
(21,'Southampton'),
(23,'Bournemouth'),
(24,'Fulham');

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `ticketID` bigint NOT NULL AUTO_INCREMENT,
  `wager` decimal(7,0) DEFAULT NULL,
  `combinedOdds` double(7,2) DEFAULT NULL,
  `potentialWin` decimal(7,2) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `state` varchar(11) DEFAULT 'unprocessed',
  `playedByUser` bigint DEFAULT NULL,
  PRIMARY KEY (`ticketID`),
  KEY `user` (`playedByUser`),
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`playedByUser`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ticket` */

insert  into `ticket`(`ticketID`,`wager`,`combinedOdds`,`potentialWin`,`date`,`state`,`playedByUser`) values 
(19,200,1.60,320.00,'2022-11-17 18:54:38','X',1),
(20,200,1.40,280.00,'2022-11-17 19:04:34','✓',1),
(21,200,1.60,320.00,'2022-11-17 19:05:23','X',1),
(22,2000,2.47,4940.00,'2023-01-23 14:21:13','✓',1),
(23,200,2.16,432.00,'2023-01-23 14:40:10','processed',1),
(24,1212,1.80,2181.60,'2023-01-28 13:08:18','canceled',1),
(25,202,1.80,363.60,'2023-01-28 13:13:03','canceled',1),
(26,1231,1.87,2301.97,'2023-01-28 19:28:50','processed',1),
(27,200,1.87,374.00,'2023-01-28 19:37:10','processed',1),
(28,200,1.87,374.00,'2023-01-28 19:51:47','processed',1),
(29,200,1.99,398.00,'2023-01-28 20:19:56','processed',1),
(30,200,1.40,280.00,'2023-01-28 20:25:55','processed',1),
(31,200,1.30,260.00,'2023-01-28 20:31:36','canceled',1),
(32,200,1.40,280.00,'2023-01-28 20:35:59','canceled',1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userID` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `surname` varchar(30) DEFAULT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`userID`,`name`,`surname`,`username`,`password`,`role`) values 
(1,'Nikol','Jankovic','a','a','ADMIN'),
(2,'Ni','K','u','u','CLIENT'),
(3,'Nikola','Jankovic','janko','admin','ADMIN');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
