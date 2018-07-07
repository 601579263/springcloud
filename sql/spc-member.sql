/*
Navicat MySQL Data Transfer

Source Server         : 测试
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : spc-member

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-07-01 13:58:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mb_order
-- ----------------------------
DROP TABLE IF EXISTS `mb_order`;
CREATE TABLE `mb_order` (
  `WIDout_trade_no` varchar(255) NOT NULL COMMENT '商户订单号',
  `WIDsubject` varchar(255) DEFAULT NULL COMMENT '订单名称',
  `WIDtotal_amount` double DEFAULT NULL COMMENT '付款金额',
  `WIDbody` varchar(255) DEFAULT NULL COMMENT '商品描述',
  `WIDTQtrade_no` varchar(255) DEFAULT NULL COMMENT '支付宝交易号',
  `WIDTRrefund_amount` double DEFAULT NULL COMMENT '退款金额',
  `WIDTRrefund_reason` varchar(255) DEFAULT NULL COMMENT '退款原因',
  `WIDTRout_request_no` varchar(255) DEFAULT NULL COMMENT '退款请求号',
  PRIMARY KEY (`WIDout_trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mb_order
-- ----------------------------
INSERT INTO `mb_order` VALUES ('201862611819107', '测试', '0.01', '商品描述', '2018062621001004590200932824', '0.01', '退款支付时间:2018-06-26 15:54:35 呵呵', null);
INSERT INTO `mb_order` VALUES ('201862615574328', '哈哈', '12', '商品描述', '2018062621001004590200937049', '12', '退款支付时间:2018-06-26 15:59:37 11', null);
INSERT INTO `mb_order` VALUES ('2018626161113780', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('20186261614829', '测试', '0.01', '商品描述', '2018062621001004590200935901', '0.01', '退款支付时间:2018-06-26 16:15:32 ', null);
INSERT INTO `mb_order` VALUES ('2018626161633343', '测试', '0.01', '商品描述', '2018062621001004590200937050', '0.01', '退款支付时间:2018-06-26 16:17:18 ', null);
INSERT INTO `mb_order` VALUES ('20186261633571', '测试', '0.01', '商品描述', '2018062621001004590200935902', null, null, null);
INSERT INTO `mb_order` VALUES ('201862616394146', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('2018626164259530', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('2018626165012869', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('2018626165343944', '测试', '0.01', '商品描述', '2018062621001004590200937053', null, null, null);
INSERT INTO `mb_order` VALUES ('2018626172921264', '测试', '0.01', '商品描述', '2018062621001004590200935905', null, null, null);
INSERT INTO `mb_order` VALUES ('2018627103627737', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('201862710422674', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('2018627105810115', '测试', '0.01', '商品描述', '2018062721001004590200937065', null, null, null);
INSERT INTO `mb_order` VALUES ('2018627133838476', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('2018627133943441', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('2018627134028344', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('2018627134048274', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('2018627142444553', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('2018628114947495', '测试', '0.01', '商品描述', '2018062821001004590200942163', null, null, null);
INSERT INTO `mb_order` VALUES ('2018630151726409', '测试', '0.01', '商品描述', null, null, null, null);
INSERT INTO `mb_order` VALUES ('201863016310279', '测试', '0.01', '商品描述', null, null, null, null);

-- ----------------------------
-- Table structure for mb_user
-- ----------------------------
DROP TABLE IF EXISTS `mb_user`;
CREATE TABLE `mb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码，加密存储',
  `phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '注册邮箱',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `openid` varchar(255) DEFAULT NULL COMMENT 'qq登陆唯一Id',
  `zfbid` varchar(255) DEFAULT NULL COMMENT '支付用户登陆的账号Id ',
  `sinaid` varchar(255) DEFAULT NULL COMMENT '新浪微博id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of mb_user
-- ----------------------------
INSERT INTO `mb_user` VALUES ('1', 'zxcAS', '7FA8282AD93047A4D6FE6111C93B308A', '', 'zxcz@163.com', '2018-06-16 22:01:49', '2018-06-16 22:01:49', null, null, null);
INSERT INTO `mb_user` VALUES ('69', 'a7aa', '7417CD3383B63EFEEF0955FF6BCBDA27', '42.0', '15284307857@163.com', '2018-06-16 21:54:15', '2018-06-16 21:54:15', null, null, null);
INSERT INTO `mb_user` VALUES ('112', 'aa', 'E10ADC3949BA59ABBE56E057F20F883E', null, '111@qq.com', '2018-06-25 10:12:00', '2018-06-25 10:12:00', '871BC844F3E1C201FBA83AC4E62DE349', null, '6587880793');
INSERT INTO `mb_user` VALUES ('113', 'asdf', 'E10ADC3949BA59ABBE56E057F20F883E', null, '11@qq.com', '2018-06-25 10:12:31', '2018-06-25 10:12:31', '', '2088102176042591', null);
