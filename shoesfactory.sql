-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        5.7.28-log - MySQL Community Server (GPL)
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- shoesfactory 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `shoesfactory` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `shoesfactory`;

-- 테이블 shoesfactory.auth_mail 구조 내보내기
CREATE TABLE IF NOT EXISTS `auth_mail` (
  `mem_id` varchar(50) NOT NULL,
  `mail` varchar(100) NOT NULL,
  `auth_key` varchar(50) NOT NULL,
  PRIMARY KEY (`mem_id`),
  CONSTRAINT `auth_mail_ibfk_1` FOREIGN KEY (`mem_id`) REFERENCES `member` (`mem_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.brand 구조 내보내기
CREATE TABLE IF NOT EXISTS `brand` (
  `brand_idx` int(11) NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(20) NOT NULL,
  PRIMARY KEY (`brand_idx`),
  UNIQUE KEY `brand_name` (`brand_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.cart 구조 내보내기
CREATE TABLE IF NOT EXISTS `cart` (
  `cart_idx` int(11) NOT NULL AUTO_INCREMENT,
  `cart_member` varchar(40) NOT NULL,
  `cart_product` int(11) NOT NULL,
  `cart_size` int(11) NOT NULL,
  `cart_count` int(11) NOT NULL,
  PRIMARY KEY (`cart_idx`),
  KEY `cart_ibfk_1` (`cart_member`),
  KEY `cart_ibfk_2` (`cart_product`,`cart_size`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`cart_member`) REFERENCES `member` (`mem_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`cart_product`, `cart_size`) REFERENCES `stock` (`product_idx`, `size`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=277 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.category 구조 내보내기
CREATE TABLE IF NOT EXISTS `category` (
  `category_idx` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) NOT NULL,
  PRIMARY KEY (`category_idx`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.display 구조 내보내기
CREATE TABLE IF NOT EXISTS `display` (
  `display_idx` int(11) NOT NULL AUTO_INCREMENT,
  `display_product` int(11) NOT NULL,
  `display_order` int(11) NOT NULL,
  `display_md` int(11) NOT NULL,
  PRIMARY KEY (`display_idx`),
  KEY `FK_display_maindisplay` (`display_md`),
  KEY `FK_display_product` (`display_product`),
  CONSTRAINT `FK_display_maindisplay` FOREIGN KEY (`display_md`) REFERENCES `maindisplay` (`md_idx`),
  CONSTRAINT `FK_display_product` FOREIGN KEY (`display_product`) REFERENCES `product` (`product_idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.maindisplay 구조 내보내기
CREATE TABLE IF NOT EXISTS `maindisplay` (
  `md_name` varchar(30) NOT NULL,
  `md_idx` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`md_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.member 구조 내보내기
CREATE TABLE IF NOT EXISTS `member` (
  `mem_idx` int(11) NOT NULL AUTO_INCREMENT,
  `mem_name` varchar(20) NOT NULL,
  `mem_birth` date NOT NULL,
  `mem_id` varchar(40) NOT NULL,
  `mem_pw` varchar(60) NOT NULL,
  `mem_phone` varchar(30) NOT NULL,
  `mem_addr1` varchar(50) NOT NULL,
  `mem_addr2` varchar(30) NOT NULL,
  `mem_regdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `mem_role` int(11) DEFAULT '2',
  `mem_total` int(11) DEFAULT '0',
  `mem_postcode` varchar(20) DEFAULT NULL,
  `mem_mail` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`mem_id`),
  UNIQUE KEY `mem_phone` (`mem_phone`),
  UNIQUE KEY `mem_idx` (`mem_idx`),
  KEY `FK_member_role` (`mem_role`)
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.notice 구조 내보내기
CREATE TABLE IF NOT EXISTS `notice` (
  `notice_type` varchar(30) NOT NULL,
  `notice_idx` int(11) NOT NULL AUTO_INCREMENT,
  `notice_id` varchar(40) NOT NULL,
  `notice_title` varchar(100) DEFAULT NULL,
  `notice_contents` varchar(2000) NOT NULL,
  `notice_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notice_idx`),
  KEY `FK_notice_member` (`notice_id`),
  KEY `FK_notice_noticetype` (`notice_type`),
  CONSTRAINT `FK_notice_member` FOREIGN KEY (`notice_id`) REFERENCES `member` (`mem_id`),
  CONSTRAINT `FK_notice_noticetype` FOREIGN KEY (`notice_type`) REFERENCES `noticetype` (`typename`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.noticetype 구조 내보내기
CREATE TABLE IF NOT EXISTS `noticetype` (
  `typename` varchar(30) NOT NULL,
  PRIMARY KEY (`typename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.order_ 구조 내보내기
CREATE TABLE IF NOT EXISTS `order_` (
  `order_idx` int(11) NOT NULL AUTO_INCREMENT,
  `mem_id` varchar(40) NOT NULL,
  `mem_name` varchar(40) NOT NULL,
  `order_postcode` varchar(40) NOT NULL,
  `order_addr1` varchar(50) NOT NULL,
  `order_addr2` varchar(50) NOT NULL,
  `order_phone` varchar(50) NOT NULL,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `product_idx` int(11) NOT NULL,
  `size` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `pay` int(11) NOT NULL,
  `order_state` varchar(50) NOT NULL DEFAULT '배송준비중',
  `order_code` varchar(30) NOT NULL,
  `merchant_uid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`order_code`),
  UNIQUE KEY `order_idx` (`order_idx`),
  KEY `FK_order__product` (`product_idx`),
  KEY `mem_id` (`mem_id`),
  CONSTRAINT `FK_order__product` FOREIGN KEY (`product_idx`) REFERENCES `product` (`product_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.product 구조 내보내기
CREATE TABLE IF NOT EXISTS `product` (
  `product_idx` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_price` int(11) NOT NULL,
  `product_contents` varchar(2000) NOT NULL,
  `product_image` varchar(100) NOT NULL,
  `product_isdisplay` int(11) NOT NULL,
  `product_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `product_salecount` int(11) DEFAULT '0',
  `product_rating` float DEFAULT '0',
  `product_brand` varchar(50) DEFAULT NULL,
  `product_category` varchar(50) DEFAULT NULL,
  `product_reviewcount` int(11) DEFAULT '0',
  PRIMARY KEY (`product_idx`),
  KEY `FK_product_brand` (`product_brand`),
  KEY `FK_product_category` (`product_category`),
  CONSTRAINT `FK_product_brand` FOREIGN KEY (`product_brand`) REFERENCES `brand` (`brand_name`),
  CONSTRAINT `FK_product_category` FOREIGN KEY (`product_category`) REFERENCES `category` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=366 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.qna 구조 내보내기
CREATE TABLE IF NOT EXISTS `qna` (
  `qna_idx` int(11) NOT NULL AUTO_INCREMENT,
  `qna_type` varchar(30) DEFAULT NULL,
  `qna_title` varchar(100) DEFAULT NULL,
  `qna_question` varchar(1000) DEFAULT NULL,
  `qna_answer` varchar(1000) DEFAULT NULL,
  `qna_secret` int(11) NOT NULL,
  `qna_member` varchar(40) NOT NULL,
  `qna_product` int(11) NOT NULL,
  `qna_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`qna_idx`),
  KEY `fk1` (`qna_member`),
  KEY `fk2` (`qna_product`),
  CONSTRAINT `fk1` FOREIGN KEY (`qna_member`) REFERENCES `member` (`mem_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk2` FOREIGN KEY (`qna_product`) REFERENCES `product` (`product_idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.review 구조 내보내기
CREATE TABLE IF NOT EXISTS `review` (
  `review_product` int(11) DEFAULT NULL,
  `review_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `review_contents` varchar(1500) DEFAULT NULL,
  `review_rating` int(11) DEFAULT NULL,
  `review_member` varchar(50) DEFAULT NULL,
  `review_ordercode` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`review_ordercode`),
  KEY `FK_review_product` (`review_product`),
  KEY `FK_review_member` (`review_member`),
  CONSTRAINT `FK_review_member` FOREIGN KEY (`review_member`) REFERENCES `member` (`mem_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_review_order_` FOREIGN KEY (`review_ordercode`) REFERENCES `order_` (`order_code`),
  CONSTRAINT `FK_review_product` FOREIGN KEY (`review_product`) REFERENCES `product` (`product_idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.role 구조 내보내기
CREATE TABLE IF NOT EXISTS `role` (
  `role_idx` int(11) DEFAULT NULL,
  `role_name` varchar(20) NOT NULL,
  `role_price` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 shoesfactory.stock 구조 내보내기
CREATE TABLE IF NOT EXISTS `stock` (
  `product_idx` int(11) NOT NULL,
  `size` int(11) NOT NULL,
  `COUNT` int(11) NOT NULL,
  PRIMARY KEY (`product_idx`,`size`),
  CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`product_idx`) REFERENCES `product` (`product_idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
