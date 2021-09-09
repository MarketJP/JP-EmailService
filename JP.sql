/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.1.41:3306
 Source Schema         : gy_message

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 09/09/2021 17:00:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gy_email_config
-- ----------------------------
DROP TABLE IF EXISTS `gy_email_config`;
CREATE TABLE `gy_email_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识ID',
  `system_name` varchar(100) DEFAULT NULL COMMENT '系统编码',
  `username` varchar(100) DEFAULT NULL COMMENT '发送帐户',
  `default_encoding` varchar(100) DEFAULT NULL COMMENT '发送编码',
  `password` varchar(150) DEFAULT NULL COMMENT '发送邮箱密码',
  `host` varchar(255) DEFAULT NULL COMMENT '邮件协议smtp',
  `port` varchar(20) DEFAULT NULL COMMENT '邮件端口',
  `from_name` varchar(255) DEFAULT NULL COMMENT '发送人名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='邮件发送记录';

-- ----------------------------
-- Table structure for gy_email_record
-- ----------------------------
DROP TABLE IF EXISTS `gy_email_record`;
CREATE TABLE `gy_email_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识ID',
  `system_name` varchar(20) DEFAULT NULL COMMENT '系统简称',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `mail` text COMMENT '邮箱',
  `result` int(2) DEFAULT NULL COMMENT '发送结果(0成功,非0失败)',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `modifier` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `file_json` text,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1733 DEFAULT CHARSET=utf8 COMMENT='邮件发送记录';

-- ----------------------------
-- Table structure for gy_notify_hw_identification
-- ----------------------------
DROP TABLE IF EXISTS `gy_notify_hw_identification`;
CREATE TABLE `gy_notify_hw_identification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识ID',
  `system_name` varchar(20) NOT NULL COMMENT '系统简称',
  `divice_name` varchar(20) NOT NULL COMMENT '设备（手机类型）简称',
  `association_identifier` varchar(32) DEFAULT NULL COMMENT '关联的标识',
  `ident_code` varchar(200) NOT NULL COMMENT '唯一的识别码',
  `is_validate` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否已经失效,0有效，1失效',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推送设备标识表（标识各业务系统与被推送手机的对应关系）';

-- ----------------------------
-- Table structure for gy_notify_record
-- ----------------------------
DROP TABLE IF EXISTS `gy_notify_record`;
CREATE TABLE `gy_notify_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识ID',
  `system_name` varchar(20) DEFAULT NULL COMMENT '系统简称',
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `extras` varchar(1024) DEFAULT NULL COMMENT '推送标签',
  `signature` text COMMENT '推送标识',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `modifier` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `push_type` varchar(50) DEFAULT NULL COMMENT '推送平台类型，1极光，2华为，3小米',
  `push_status` int(11) DEFAULT '0' COMMENT 'push状态，默认成功，0成功，1失败',
  `fail_reason` varchar(255) DEFAULT NULL COMMENT '失败原因',
  `to_system_name` varchar(256) DEFAULT NULL COMMENT '目标App系统简称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='App推送记录';

-- ----------------------------
-- Table structure for gy_sms_record
-- ----------------------------
DROP TABLE IF EXISTS `gy_sms_record`;
CREATE TABLE `gy_sms_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识ID',
  `system_name` varchar(50) DEFAULT NULL COMMENT '系统简称',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `template_param` varchar(200) DEFAULT NULL COMMENT '短信入参数',
  `template_code` varchar(200) DEFAULT NULL COMMENT '内容',
  `result` int(2) DEFAULT NULL COMMENT '发送结果(0成功,非0失败)',
  `message` varchar(200) DEFAULT NULL COMMENT '结果消息',
  `smsid` varchar(50) DEFAULT NULL COMMENT '短信ID(短信平台返回的ID)',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `modifier` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `extras` varchar(256) DEFAULT NULL COMMENT '额外字段，存放自定义信息',
  `fail_reason` varchar(255) DEFAULT NULL COMMENT '失败原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='短信推送记录';

-- ----------------------------
-- Table structure for gy_sms_sign
-- ----------------------------
DROP TABLE IF EXISTS `gy_sms_sign`;
CREATE TABLE `gy_sms_sign` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识ID',
  `sign_name` varchar(32) NOT NULL COMMENT '短信签名',
  `system_name` varchar(20) NOT NULL COMMENT '所属系统,common代表所有系统共用',
  `platform` varchar(20) DEFAULT NULL COMMENT '所属平台，如common代表所有平台共用,app,web,wechat(微信公众号)',
  `is_common` int(11) DEFAULT '1' COMMENT '是否公用，1是，0否',
  `remark` varchar(50) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='短信签名表';

-- ----------------------------
-- Table structure for gy_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `gy_sms_template`;
CREATE TABLE `gy_sms_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识ID',
  `description` varchar(64) NOT NULL COMMENT '短信模版描述',
  `template_code` varchar(64) NOT NULL COMMENT '短信模版code',
  `template_content` varchar(512) NOT NULL COMMENT '短信模版内容',
  `symbol` varchar(32) DEFAULT NULL COMMENT '唯一查找标识',
  `is_common` int(11) DEFAULT '1' COMMENT '是否公用，1是，2否',
  `remark` varchar(50) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `symbol` (`symbol`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='短信模版表';

SET FOREIGN_KEY_CHECKS = 1;
