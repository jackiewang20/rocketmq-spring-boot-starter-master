/*
Navicat MySQL Data Transfer

Source Server         : localhost192.168.120.62
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : shopping_order

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2019-01-25 20:16:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `channel`
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_no` varchar(255) DEFAULT NULL,
  `channel_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES ('1', '001', '天猫');
INSERT INTO `channel` VALUES ('2', '002', '京东');
INSERT INTO `channel` VALUES ('3', '003', '考拉');

-- ----------------------------
-- Table structure for `country`
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `country_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `country_code` varchar(255) DEFAULT NULL COMMENT '代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='国家信息';

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for `integral`
-- ----------------------------
DROP TABLE IF EXISTS `integral`;
CREATE TABLE `integral` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT '0' COMMENT '0服装类，1数码产品类',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of integral
-- ----------------------------
INSERT INTO `integral` VALUES ('1', '121', '0', '1');
INSERT INTO `integral` VALUES ('2', '100', '12', '2');

-- ----------------------------
-- Table structure for `inventory`
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL COMMENT '库存数量',
  `channel_no` varchar(11) DEFAULT NULL COMMENT '渠道编号',
  `product_id` int(11) DEFAULT NULL COMMENT '商品编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inventory
-- ----------------------------
INSERT INTO `inventory` VALUES ('1', '100', 'no001', '1');
INSERT INTO `inventory` VALUES ('2', '98', 'no002', '2');

-- ----------------------------
-- Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `order_code` varchar(64) DEFAULT NULL COMMENT '订单编码（作为外键使用）',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单号',
  `shop_id` int(11) DEFAULT NULL,
  `order_status` int(11) DEFAULT NULL,
  `order_amount` decimal(15,4) DEFAULT NULL COMMENT '订单金额',
  `create_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_index_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1', 'code-00001', 'no-00001', '1', '1', '1999.0000', '2019-01-23 17:29:01', '1');
INSERT INTO `order` VALUES ('27', null, 'orderNo-gen-003', '1', '1', '1999.0000', '2019-01-25 20:06:17', '1');

-- ----------------------------
-- Table structure for `order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL,
  `product_id` bigint(64) NOT NULL COMMENT '产品id',
  `product_name` varchar(128) NOT NULL DEFAULT '' COMMENT '商品名称（商品可能修改或删除,所以这里要记录）',
  `product_price` decimal(15,4) NOT NULL,
  `product_mode_desc` varchar(256) DEFAULT NULL COMMENT '商品型号信息 (记录详细商品型号，如颜色、规格、包装等)',
  `product_mode_params` varchar(800) DEFAULT NULL COMMENT '商品型号参数 (JSON格式，记录单位编号、颜色编号、规格编号等)',
  `number` int(11) NOT NULL COMMENT '商品数量',
  `subtotal` decimal(10,0) NOT NULL COMMENT '小计金额',
  `remark` varchar(128) DEFAULT NULL COMMENT '客户商品备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1', '1', '2', '小米', '1999.0000', 'blue,250g', null, '1', '1999', null);
INSERT INTO `order_detail` VALUES ('23', '27', '2', '小米', '1999.0000', 'blue,250g', 'tx商品类型参数', '1', '1999', '备注');

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `product_code` varchar(64) DEFAULT NULL COMMENT '唯一约束，作为外键使用',
  `product_no` varchar(64) DEFAULT NULL COMMENT '商品编号（共客户查询）',
  `product_name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `picture_list` varchar(255) DEFAULT NULL,
  `specification` varchar(255) DEFAULT NULL,
  `shop_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_index_product_no` (`product_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', null, 'p-0001', 'iphone x', '9999', null, 'red,200g', '1');
INSERT INTO `product` VALUES ('2', null, 'p-0002', '小米', '1999', null, 'blue,250g', '1');

-- ----------------------------
-- Table structure for `product_inventory`
-- ----------------------------
DROP TABLE IF EXISTS `product_inventory`;
CREATE TABLE `product_inventory` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` bigint(10) unsigned DEFAULT NULL,
  `inventory_id` bigint(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_inventory
-- ----------------------------
INSERT INTO `product_inventory` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for `shop`
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `good_comment_rate` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('1', '淘宝数码之家', '5', '99.99');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `countrycode` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'jackie', '28', '1', '2019-01-23 17:14:48', '备注');
INSERT INTO `user` VALUES ('2', 'tom', '22', '2', '2019-01-24 17:15:23', null);
