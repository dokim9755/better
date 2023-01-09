DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `board`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `user_pwd` varchar(20) NOT NULL,
  `user_nick` varchar(30) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid_no`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `user_nick` (`user_nick`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `board` (
  `board_no` bigint NOT NULL AUTO_INCREMENT,
  `uid_no` int NOT NULL,
  `board_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `board_writer` varchar(30) NOT NULL,
  `board_title` varchar(100) NOT NULL,
  `board_content` varchar(2000) NOT NULL,
  `board_cnt` int NOT NULL DEFAULT '0',
  `board_cmnts` int NOT NULL DEFAULT '0',
  `board_like` int NOT NULL DEFAULT '0',
  `board_update` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`board_no`),
  KEY `uid_no` (`uid_no`),
  CONSTRAINT `board_ibfk_1` FOREIGN KEY (`uid_no`) REFERENCES `user` (`uid_no`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comment` (
  `cmnt_no` int NOT NULL AUTO_INCREMENT,
  `uid_no` int NOT NULL,
  `board_no` bigint NOT NULL,
  `cmnt_writer` varchar(30) NOT NULL,
  `cmnt_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cmnt_update` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cmnt_content` varchar(1000) NOT NULL,
  `cmnt_like` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`cmnt_no`),
  KEY `FK_usertbl_cmnttbl` (`uid_no`),
  KEY `FK_boardtbl_cmnttbl` (`board_no`),
  CONSTRAINT `FK_boardtbl_cmnttbl` FOREIGN KEY (`board_no`) REFERENCES `board` (`board_no`),
  CONSTRAINT `FK_usertbl_cmnttbl` FOREIGN KEY (`uid_no`) REFERENCES `user` (`uid_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;