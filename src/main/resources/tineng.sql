/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50711
Source Host           : 127.0.0.1:3306
Source Database       : tineng

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2018-05-16 14:46:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_customer
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime DEFAULT NULL COMMENT '更改时间',
  `nickname` varchar(16) NOT NULL COMMENT '呢称',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `username` varchar(16) NOT NULL COMMENT '登录名',
  `gender` tinyint(6) NOT NULL DEFAULT '1' COMMENT '性别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_customer
-- ----------------------------
INSERT INTO `t_customer` VALUES ('1', '2017-12-31 00:32:16', '2018-05-15 20:51:49', '会员111', '342401bf3afbe8b5f7c742f99b4759bf4a21a933', 'customer', '1');
INSERT INTO `t_customer` VALUES ('5', '2018-05-15 18:01:49', '2018-05-15 18:01:49', '女', '6acb1ee793518f9f3d4157ee2f1524dea7d04989', 'nnnnnn', '0');
INSERT INTO `t_customer` VALUES ('6', '2018-05-15 21:38:07', '2018-05-15 21:38:07', 'CCCC', '8134300c66116e9eaa570ea22f73e7109b5e4cfe', 'cccccc', '0');
INSERT INTO `t_customer` VALUES ('7', '2018-05-16 13:33:27', '2018-05-16 13:33:27', '员工666', '4f507da4062bdc1b768f64280124520a2e07cfc0', 'g666666', '1');
INSERT INTO `t_customer` VALUES ('8', '2018-05-16 14:03:11', '2018-05-16 14:03:11', '666666666666', '6822343ddb23ee4598205f90a6234eb44af2821d', 'n666666', '0');
INSERT INTO `t_customer` VALUES ('9', '2018-05-16 14:34:50', '2018-05-16 14:34:50', 'www', 'ef3d530e82d2e7ef553c6612c6427db7d92b8145', 'wwwwww', '1');

-- ----------------------------
-- Table structure for t_physical
-- ----------------------------
DROP TABLE IF EXISTS `t_physical`;
CREATE TABLE `t_physical` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL COMMENT '会员账号',
  `gender` tinyint(6) NOT NULL COMMENT '性别：1男，0女',
  `good_monring` float DEFAULT NULL COMMENT '体前屈',
  `height` float DEFAULT NULL COMMENT '身高',
  `jump` float DEFAULT NULL COMMENT '跳远',
  `meter1000` float DEFAULT NULL COMMENT '1000米跑耗时秒数',
  `meter50` float DEFAULT NULL COMMENT '50米跑耗时秒数',
  `meter800` float DEFAULT NULL COMMENT '800米跑耗时秒数',
  `nickname` varchar(16) NOT NULL COMMENT '呢称',
  `pull_up` int(11) DEFAULT NULL COMMENT '引体向上',
  `sit_ups` int(11) DEFAULT NULL COMMENT '仰卧起坐',
  `vital_capacity` float DEFAULT NULL COMMENT '肺活量',
  `weight` float DEFAULT NULL COMMENT '体重',
  `score` float DEFAULT NULL COMMENT '体能总得分',
  `pass` bit(1) DEFAULT b'0' COMMENT '是否通过测试',
  `created_time` datetime NOT NULL,
  `modified_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rrcq4vrx0uinwpf73otkf6nfs` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_physical
-- ----------------------------
INSERT INTO `t_physical` VALUES ('2', 'customer', '1', '16', '171.33', '255.55', '211.221', '6.8', null, '会员111', '17', null, '5555.55', '63.336', '91.1289', '', '2018-05-15 21:18:35', '2018-05-16 14:39:38');
INSERT INTO `t_physical` VALUES ('5', 'n666666', '0', '22', '156', '159', null, '8.4', '256', '666666666666', null, '44', '3333', '43', '68.7158', '\0', '2018-05-16 14:06:10', '2018-05-16 14:12:06');
INSERT INTO `t_physical` VALUES ('6', 'wwwwww', '1', '8', '155', '288', '300', '7.8', null, 'www', '17', null, '2888', '60', '63.3383', '\0', '2018-05-16 14:36:51', '2018-05-16 14:38:54');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime DEFAULT NULL COMMENT '更改时间',
  `nickname` varchar(16) NOT NULL COMMENT '呢称',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `username` varchar(16) NOT NULL COMMENT '登录名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '2017-12-31 00:32:16', '2018-05-15 17:27:39', '蔡大哥大晒', '342401bf3afbe8b5f7c742f99b4759bf4a21a933', 'cherish');
INSERT INTO `t_user` VALUES ('2', '2018-05-15 17:28:28', '2018-05-15 17:28:28', '管理员', '46cc50571a16c00fb376518299fc19059fddb399', 'adminadmin');
