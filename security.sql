/*
Navicat MySQL Data Transfer

Source Server         : sunyue
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2021-02-02 21:51:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test_user
-- ----------------------------
DROP TABLE IF EXISTS `test_user`;
CREATE TABLE `test_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `authority` varchar(100) DEFAULT NULL,
  `created` date DEFAULT NULL,
  `updated` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test_user
-- ----------------------------
INSERT INTO `test_user` VALUES ('3', 'user', '$2a$10$Skjo8i3cSopkOtVsvfX5I.eOPCOFm2B/CD4t0VjUDXfTZk6aSAvia', 'ROLE_USER', '2021-01-12', '2021-01-12');
INSERT INTO `test_user` VALUES ('4', 'admin', '$2a$10$3rzQ1Pn.Onx9N/Dy6a5O8.TZfB/kqo/Z1UOj9udQl4ne0AZDaxn4O', 'admin', '2021-01-12', '2021-01-12');
