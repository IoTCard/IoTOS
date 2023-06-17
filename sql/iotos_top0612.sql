/*
 Navicat Premium Data Transfer

 Source Server         : www.iotos.top
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : 127.0.0.1:3306
 Source Schema         : iotos_top

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 06/06/2023 06:06:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
                              `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                              `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '表名称',
                              `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '表描述',
                              `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
                              `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
                              `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '实体类名称',
                              `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
                              `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成包路径',
                              `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成模块名',
                              `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成业务名',
                              `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成功能名',
                              `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成功能作者',
                              `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
                              `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
                              `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '其它生成选项',
                              `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                              `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                              `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                              `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                              PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
                                     `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                     `table_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '归属表编号',
                                     `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列名称',
                                     `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列描述',
                                     `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列类型',
                                     `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
                                     `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
                                     `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
                                     `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
                                     `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
                                     `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
                                     `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
                                     `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
                                     `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
                                     `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
                                     `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
                                     `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
                                     `sort` int NULL DEFAULT NULL COMMENT '排序',
                                     `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                     `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                     PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for os_business_history
-- ----------------------------
DROP TABLE IF EXISTS `os_business_history`;
CREATE TABLE `os_business_history`  (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `create_time` datetime NOT NULL COMMENT '创建时间',
                                        `create_company_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建企业编号',
                                        `adscription_company_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '归属企业编号',
                                        `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                        `team_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '归属团队编号',
                                        `business_type` smallint NOT NULL COMMENT '业务类型 新增 作废 已转化',
                                        `business_count` int NOT NULL COMMENT '业务操作次数',
                                        `business_no` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务操作编号',
                                        `state_id` smallint NOT NULL COMMENT '状态 ',
                                        `business_raw_data` longblob NOT NULL COMMENT '业务操作 原始数据',
                                        `business_source_type` smallint NOT NULL COMMENT '业务数据 来源类型 表名',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        INDEX `adscription_company_no`(`adscription_company_no`, `tenant_id`, `business_no`, `business_source_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '业务操作-操作历史储存' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_business_history
-- ----------------------------

-- ----------------------------
-- Table structure for os_card
-- ----------------------------
DROP TABLE IF EXISTS `os_card`;
CREATE TABLE `os_card`  (
                            `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
                            `c_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自定义编号',
                            `msisdn` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MSISDN',
                            `iccid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ICCID',
                            `imsi` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IMSI',
                            `imei` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IMEI',
                            `activate_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '激活时间',
                            `dept_id` int NOT NULL COMMENT '所属企业',
                            `channel_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通道编号',
                            `status_show_id` int NULL DEFAULT NULL COMMENT '卡状态',
                            `status_id` int NULL DEFAULT NULL COMMENT '卡状态描述',
                            `c_total` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '卡板总量',
                            `c_used` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '卡板已用流量',
                            `c_left` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '卡板剩余用量',
                            `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `deleted` smallint NOT NULL DEFAULT 0 COMMENT '是否删除',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `iccid`(`iccid`, `dept_id`) USING BTREE,
                            INDEX `msisdn`(`msisdn`) USING BTREE,
                            INDEX `imei`(`imei`) USING BTREE,
                            INDEX `deleted`(`deleted`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '物联卡表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_card
-- ----------------------------
INSERT INTO `os_card` VALUES (1, '18600010001', '1440123123123', '89861231231231231231', NULL, '812345645666661', '2020-06-06 16:16:16', 100, 'chanel-202304081108455161', 4, 4, 100.00, 0.69, 99.31, '2023-01-01 00:00:23', 0);
INSERT INTO `os_card` VALUES (2, '18600010002', '1440123123124', '89861231231231231232', NULL, '812345645666662', '2020-06-06 16:16:16', 100, 'chanel-202304081108455161', 4, 4, 10.00, 0.69, 9.31, '2023-01-01 00:00:23', 0);
INSERT INTO `os_card` VALUES (3, '18600010003', '1440123123125', '89861231231231231233', NULL, '812345645666663', '2020-06-06 16:16:16', 101, 'chanel-202305050233545568', 5, 5, 0.00, 0.00, 0.00, '2023-01-01 00:00:23', 0);
INSERT INTO `os_card` VALUES (4, '18600010004', '1440123123126', '89861231231231231234', NULL, '812345645666664', '2020-06-06 16:16:16', 100, 'chanel-202305050233545568', 4, 4, 0.00, 37.72, -37.72, '2023-01-01 00:00:23', 0);
INSERT INTO `os_card` VALUES (5, '18600010005', '1440123123127', '89861231231231231235', NULL, '812345645666665', '2020-06-06 16:16:16', 101, 'chanel-202304081108455161', 4, 4, 0.00, 4.59, -4.59, '2023-01-01 00:00:23', 0);
INSERT INTO `os_card` VALUES (6, '18600010006', '1440123123128', '89861231231231231236', NULL, '812345645666666', '2020-06-06 16:16:16', 100, 'chanel-202304081108455161', 4, 4, 0.00, 19.58, -19.58, '2023-01-01 00:00:23', 0);

-- ----------------------------
-- Table structure for os_card_api_business
-- ----------------------------
DROP TABLE IF EXISTS `os_card_api_business`;
CREATE TABLE `os_card_api_business`  (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `iccid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ICCID',
                                         `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作业务类型',
                                         `source_data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '源数据',
                                         `change_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '变更值',
                                         `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                                         `state` smallint NOT NULL DEFAULT 0 COMMENT '状态',
                                         `source_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '来源',
                                         `create_time` datetime NOT NULL COMMENT '创建时间',
                                         PRIMARY KEY (`id`) USING BTREE,
                                         INDEX `iccid`(`iccid`) USING BTREE,
                                         INDEX `type`(`type`) USING BTREE,
                                         INDEX `source_type`(`source_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'iotos-物联卡-API业务变更' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_card_api_business
-- ----------------------------

-- ----------------------------
-- Table structure for os_card_info
-- ----------------------------
DROP TABLE IF EXISTS `os_card_info`;
CREATE TABLE `os_card_info`  (
                                 `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
                                 `c_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自定义编号',
                                 `msisdn` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MSISDN',
                                 `iccid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ICCID',
                                 `imsi` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IMSI',
                                 `imei` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IMEI',
                                 `activate_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '激活时间',
                                 `dept_id` int NOT NULL COMMENT '所属企业',
                                 `channel_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通道编号',
                                 `status_show_id` int NULL DEFAULT NULL COMMENT '卡状状态',
                                 `status_id` int NULL DEFAULT NULL COMMENT '卡状状态描述',
                                 `c_total` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '资费总量-和',
                                 `c_used` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '资费已用流量-和',
                                 `c_left` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '资费剩余用量-和',
                                 `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                 `deleted` smallint NOT NULL DEFAULT 0 COMMENT '是否删除',
                                 `first_time_using` datetime NULL DEFAULT NULL COMMENT '首次用量使用时间',
                                 `traffic_sync_time` datetime NULL DEFAULT NULL COMMENT '用量同步时间',
                                 `state_sync_time` datetime NULL DEFAULT NULL COMMENT '状态同步时间',
                                 `w_real_name` smallint NOT NULL DEFAULT 0 COMMENT '是否实名',
                                 `nedd_real_name` smallint NOT NULL DEFAULT 0 COMMENT '是否需要实名',
                                 `type` smallint NULL DEFAULT 0 COMMENT '卡类型',
                                 `network_type` smallint NULL DEFAULT NULL COMMENT '网络类型',
                                 `w_sms` smallint NULL DEFAULT 0 COMMENT '是否支持短信发送',
                                 `w_voice` smallint NULL DEFAULT 0 COMMENT '是否支持语音功能',
                                 `sms_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短信服务号',
                                 `deliver_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货日期',
                                 `storage_date` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '入库日期',
                                 `prodinsteff_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务-生效时间',
                                 `prodinstexp_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务-失效时间',
                                 `delivery_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出库日期',
                                 `w_polling` smallint NOT NULL DEFAULT 1 COMMENT '是否-轮询',
                                 `w_network_break` smallint NOT NULL DEFAULT 0 COMMENT '是否-未订购断网',
                                 `customize_grouping` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义分组',
                                 `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                                 `shutdown_threshold` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '停机阈值',
                                 `package_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资费组编号',
                                 `connection_status` smallint NULL DEFAULT 0 COMMENT '断开网状态',
                                 `w_pool` smallint NOT NULL DEFAULT 0 COMMENT '是否流量池',
                                 `pool_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流量池编号',
                                 `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                 `balance` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '单卡预存余额',
                                 `payment_key` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '单卡支付密码',
                                 `automatic_renewal` smallint NOT NULL DEFAULT 0 COMMENT '是否自动续费',
                                 `u_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用卡人编号',
                                 `supplier` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应商',
                                 `roaming_country` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '漫游国家',
                                 `internet_signal` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网络信号',
                                 `ip_attribution` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip归属地',
                                 `operator` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运营商',
                                 `open_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开卡时间',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `iccid`(`iccid`) USING BTREE,
                                 INDEX `status_show_id`(`status_show_id`) USING BTREE,
                                 INDEX `dept_id`(`dept_id`) USING BTREE,
                                 INDEX `msisdn`(`msisdn`) USING BTREE,
                                 INDEX `imei`(`imei`) USING BTREE,
                                 INDEX `deleted`(`deleted`) USING BTREE,
                                 INDEX `status_id`(`status_id`) USING BTREE,
                                 INDEX `w_polling`(`w_polling`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '卡列表详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_card_info
-- ----------------------------
INSERT INTO `os_card_info` VALUES (1, '18600010001', '1440123123123', '89861231231231231231', NULL, '812345645666661', '2020-06-06 16:16:16', 101, 'chanel-202305050233545568', 4, 4, 100.00, 0.69, 99.31, '2023-01-01 00:00:23', 0, '2023-01-01 00:27:23', '2023-01-01 00:27:23', '2023-01-01 00:27:23', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2023-05-05 14:48:48', 0.00, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-26 06:06:06');
INSERT INTO `os_card_info` VALUES (2, '18600010002', '1440123123124', '89861231231231231232', NULL, '812345645666662', '2020-06-06 16:16:16', 100, 'chanel-202305050233545568', 4, 4, 10.00, 0.69, 9.31, '2023-01-01 00:00:23', 0, '2023-01-01 00:27:23', '2023-01-01 00:27:23', '2023-01-01 00:27:23', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2023-05-05 14:48:48', 0.00, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-26 06:06:06');
INSERT INTO `os_card_info` VALUES (3, '18600010003', '1440123123125', '89861231231231231233', NULL, '812345645666663', '2020-06-06 16:16:16', 100, 'chanel-202304081108455161', 5, 5, 0.00, 0.00, 0.00, '2023-01-01 00:00:23', 0, '2023-01-01 00:27:23', '2023-01-01 00:27:23', '2023-01-01 00:27:23', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2023-05-12 19:27:42', 0.00, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-26 06:06:06');
INSERT INTO `os_card_info` VALUES (4, '18600010004', '1440123123126', '89861231231231231234', NULL, '812345645666664', '2020-06-06 16:16:16', 100, 'chanel-202304081108455161', 4, 4, 0.00, 37.72, -37.72, '2023-01-01 00:00:23', 0, '2023-01-01 00:27:23', '2023-01-01 00:27:23', '2023-01-01 00:27:23', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, 1, 0, NULL, '2023-05-12 20:38:41', 0.00, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-26 06:06:06');
INSERT INTO `os_card_info` VALUES (5, '18600010005', '1440123123127', '89861231231231231235', NULL, '812345645666665', '2020-06-06 16:16:16', 100, 'chanel-202304081108455161', 4, 4, 0.00, 4.59, -4.59, '2023-01-01 00:00:23', 0, '2023-01-01 00:27:23', '2023-01-01 00:27:23', '2023-01-01 00:27:23', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, 1, 0, NULL, '2023-05-12 20:38:41', 0.00, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-26 06:06:06');
INSERT INTO `os_card_info` VALUES (6, '18600010006', '1440123123128', '89861231231231231236', NULL, '812345645666666', '2020-06-06 16:16:16', 101, 'chanel-202304081108455161', 4, 4, 0.00, 19.58, -19.58, '2023-01-01 00:00:23', 0, '2023-01-01 00:27:23', '2023-01-01 00:27:23', '2023-01-01 00:27:23', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2023-05-12 20:38:41', 0.00, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2018-12-26 06:06:06');

-- ----------------------------
-- Table structure for os_card_session
-- ----------------------------
DROP TABLE IF EXISTS `os_card_session`;
CREATE TABLE `os_card_session`  (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `iccid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ICCID',
                                    `template` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板类型',
                                    `create_time` datetime NOT NULL COMMENT '创建时间',
                                    `return_data` longblob NOT NULL COMMENT '返回数据',
                                    `dept_id` int NOT NULL COMMENT '所属企业',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `iccid`(`iccid`, `dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1363 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'iotos-物联卡-会话在线信息记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_card_session
-- ----------------------------

-- ----------------------------
-- Table structure for os_card_user
-- ----------------------------
DROP TABLE IF EXISTS `os_card_user`;
CREATE TABLE `os_card_user`  (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `u_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用卡人编号',
                                 `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
                                 `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
                                 `phone_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
                                 `gender` smallint NOT NULL COMMENT '性别',
                                 `remarks` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                                 `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
                                 `balance` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '用卡人-预存余额',
                                 `payment_key` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用卡人-支付密码',
                                 `head_image` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
                                 `status` smallint NOT NULL COMMENT '状态',
                                 `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                 `dept_id` int NOT NULL COMMENT '所属企业',
                                 `automatic_renewal` bit(1) NOT NULL COMMENT '是否自动续费',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 INDEX `phone_number`(`phone_number`, `tenant_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '物联卡-使用人信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_card_user
-- ----------------------------

-- ----------------------------
-- Table structure for os_channel
-- ----------------------------
DROP TABLE IF EXISTS `os_channel`;
CREATE TABLE `os_channel`  (
                               `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `c_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编号',
                               `template` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板类型',
                               `dept_id` int NOT NULL DEFAULT 0 COMMENT '企业编号',
                               `last_upd_time` datetime NOT NULL COMMENT '最后更新时间',
                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
                               `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
                               `operator_type` smallint NULL DEFAULT NULL COMMENT '运营类型',
                               `deleted` smallint NOT NULL DEFAULT 0 COMMENT '是否删除',
                               `status` smallint NOT NULL COMMENT '状态',
                               `w_polling` smallint NOT NULL DEFAULT 0 COMMENT '是否-轮询',
                               `card_count` int NOT NULL DEFAULT 0 COMMENT '归属卡-总数',
                               `card_used` decimal(18, 2) NOT NULL DEFAULT 0.00 COMMENT '归属卡-总已用流量',
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `c_no`(`c_no`, `dept_id`, `deleted`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '物联卡-通道' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_channel
-- ----------------------------
INSERT INTO `os_channel` VALUES (1, 'chanel-202304081108455161', 'oneLink_ECV5', 103, '2023-06-12 20:56:27', '测试3', '测3', NULL, 0, 0, 1, 0, 0.00);

-- ----------------------------
-- Table structure for os_channel_info
-- ----------------------------
DROP TABLE IF EXISTS `os_channel_info`;
CREATE TABLE `os_channel_info`  (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `template` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板类型',
                                    `dept_id` bigint NOT NULL DEFAULT 0 COMMENT '企业编号',
                                    `last_upd_time` datetime NOT NULL COMMENT '最后更新时间',
                                    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
                                    `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
                                    `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求地址',
                                    `parameter_one` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数一',
                                    `parameter_tow` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数二',
                                    `parameter_three` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数三',
                                    `operator_type` smallint NULL DEFAULT NULL COMMENT '运营类型',
                                    `w_forward` smallint NULL DEFAULT 0 COMMENT '是否转发',
                                    `forward_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '转发地址',
                                    `login_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号登录-地址',
                                    `login_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号登录-账号',
                                    `login_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号登录-密码',
                                    `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                                    `sms_host` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短信-请求地址',
                                    `sms_port` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短信-端口号',
                                    `sms_source_addr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短信-企业代码',
                                    `sms_shared_secret` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短信-密码',
                                    `sms_template` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短信-模板',
                                    `sms_src_terminalId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短信-服务号码',
                                    `w_sync_upstream` smallint NULL DEFAULT NULL COMMENT '是否同步上游卡号',
                                    `sync_change_notification` smallint NULL DEFAULT NULL COMMENT '上游成员变更是是否通知',
                                    `sync_data_type` smallint NULL DEFAULT NULL COMMENT '上游返回的数据-同步类型',
                                    `upstream_card_count` int NULL DEFAULT NULL COMMENT '上游成员卡总数',
                                    `sync_field` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '同步的字段',
                                    `success_rate` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昨日-用量-同步成功率',
                                    `syn_count` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昨日-用量-同步总数',
                                    `success_total` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昨日-用量-同步成功总数',
                                    `fail_total` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昨日-用量-同步失败总数',
                                    `w_setmeal` smallint NOT NULL DEFAULT 0 COMMENT '是否 套餐用量同步 非套餐采用月用量同步模式',
                                    `c_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '归属通道编号',
                                    `statistical_date` date NULL DEFAULT NULL COMMENT '统计截止日期',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `template`(`template`, `c_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '物联卡-通道详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_channel_info
-- ----------------------------
INSERT INTO `os_channel_info` VALUES (3, 'oneLink_ECV5', 103, '2023-06-12 20:56:27', '测试3', '测3', 'https://api.iot.10086.cn/v5/ec', '12222', '123123', '1', NULL, 0, NULL, 'http://card.iotos.top/', '11ffff', '45ggggg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, 0, NULL, '', NULL, NULL, NULL, NULL, 0, 'chanel-202304081108455161', NULL);

-- ----------------------------
-- Table structure for os_front_page
-- ----------------------------
DROP TABLE IF EXISTS `os_front_page`;
CREATE TABLE `os_front_page`  (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `dept_id` int NOT NULL COMMENT '所属企业',
                                  `record_date` date NOT NULL COMMENT '记录日期',
                                  `create_time` datetime NOT NULL COMMENT '创建时间',
                                  `last_upd_time` datetime NOT NULL COMMENT '最后更新时间',
                                  `usage_line` longblob NULL COMMENT '用量增长趋势',
                                  `activate_line` longblob NULL COMMENT '激活增长趋势',
                                  `usage_table` longblob NULL COMMENT '用量排行榜',
                                  `life_cycle_pie` longblob NULL COMMENT '生命周期占比',
                                  `dept_proportion_pie` longblob NULL COMMENT '企业拿卡占比',
                                  `dept_activate_pie` longblob NULL COMMENT '企业激活占比',
                                  `dept_usage_pie` longblob NULL COMMENT '企业用量占比',
                                  `channel_pie` longblob NULL COMMENT '通道卡号占比',
                                  `card_count` int NOT NULL COMMENT '卡总数',
                                  `agent_card_count` int NOT NULL COMMENT '代理卡总数',
                                  `dept_card_count` int NOT NULL COMMENT '直属卡总数',
                                  `dept_count` int NOT NULL COMMENT '企业总数',
                                  `login_ip_count` int NOT NULL COMMENT '本月登录IP总数',
                                  `login_pie` longblob NULL COMMENT '登录次数用户占比',
                                  `login_map` longblob NULL COMMENT '登录用户地图标记',
                                  `card_add_count` int NULL DEFAULT NULL COMMENT '本月新增卡号',
                                  `card_activation_count` int NULL DEFAULT NULL COMMENT '本月激活卡号',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '首页数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_front_page
-- ----------------------------
INSERT INTO `os_front_page` VALUES (33, 100, '2023-06-11', '2023-06-12 22:14:35', '2023-06-12 22:14:35', 0x7B2275736167655F6D6F6E7468223A5B7B227265636F72645F64617465223A22323032322D3132222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D3031222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D3032222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D3033222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D3034222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D3035222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D3036222C22746F74616C5F6461795F75736564223A302E307D5D2C2275736167655F646179223A5B7B227265636F72645F64617465223A22323032332D30362D3035222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D30362D3036222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D30362D3037222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D30362D3038222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D30362D3039222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D30362D3130222C22746F74616C5F6461795F75736564223A302E307D2C7B227265636F72645F64617465223A22323032332D30362D3131222C22746F74616C5F6461795F75736564223A302E307D5D7D, 0x5B7B2267726F757054797065223A22323032322D3132222C22636F756E74223A2230227D2C7B2267726F757054797065223A22323032332D3031222C22636F756E74223A2230227D2C7B2267726F757054797065223A22323032332D3032222C22636F756E74223A2230227D2C7B2267726F757054797065223A22323032332D3033222C22636F756E74223A2230227D2C7B2267726F757054797065223A22323032332D3034222C22636F756E74223A2230227D2C7B2267726F757054797065223A22323032332D3035222C22636F756E74223A2230227D2C7B2267726F757054797065223A22323032332D3036222C22636F756E74223A2230227D5D, 0x7B2275736167655F7461626C655F6D6F6E7468223A5B5D2C2275736167655F7461626C655F646179223A5B5D7D, 0x7B227374617475734C697374223A5B7B2267726F757054797065223A2234222C22636F756E74223A2235227D2C7B2267726F757054797065223A2235222C22636F756E74223A2231227D5D2C2273746174757353686F774C697374223A5B7B2267726F757054797065223A2234222C22636F756E74223A2235227D2C7B2267726F757054797065223A2235222C22636F756E74223A2231227D5D7D, 0x5B7B2267726F757054797065223A3130302C22636F756E74223A347D2C7B2267726F757054797065223A3130312C22636F756E74223A327D5D, 0x5B7B2267726F757054797065223A3130302C22636F756E74223A347D2C7B2267726F757054797065223A3130312C22636F756E74223A327D5D, 0x5B7B2267726F757054797065223A3130302C2273756D223A302E30367D2C7B2267726F757054797065223A3130312C2273756D223A302E30307D5D, 0x5B7B2267726F757054797065223A226368616E656C2D323032333034303831313038343535313631222C22636F756E74223A347D2C7B2267726F757054797065223A226368616E656C2D323032333035303530323333353435353638222C22636F756E74223A327D5D, 6, 2, 4, 4, 0, 0x5B5D, 0x5B5D, 0, 0);

-- ----------------------------
-- Table structure for os_onelink_ecv5_group
-- ----------------------------
DROP TABLE IF EXISTS `os_onelink_ecv5_group`;
CREATE TABLE `os_onelink_ecv5_group`  (
                                          `id` int NOT NULL AUTO_INCREMENT,
                                          `group_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群组 ID',
                                          `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资费名称',
                                          `offering_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资费 ID',
                                          `offering_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资费名称',
                                          `create_time` datetime NOT NULL COMMENT '创建时间',
                                          `last_upd_time` datetime NOT NULL COMMENT '最后更新时间',
                                          `channel_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通道编号',
                                          `up_total_count` int NOT NULL DEFAULT 0 COMMENT '上游群组总数',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'oneLink EcV5 群组记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_onelink_ecv5_group
-- ----------------------------

-- ----------------------------
-- Table structure for os_onelink_ecv5_session
-- ----------------------------
DROP TABLE IF EXISTS `os_onelink_ecv5_session`;
CREATE TABLE `os_onelink_ecv5_session`  (
                                            `id` int NOT NULL AUTO_INCREMENT,
                                            `apn_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'APN ID',
                                            `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '在线状态\r\n00: 离线\r\n01：在线',
                                            `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP 地址（IPV4）\r\nIP v4 地 址 格 式 为 ：\r\nA.B.C.D，A、B、C、D\r\n为 0~255 之间的十进制\r\n数字。\r\n可为空。',
                                            `ipv6_prefix` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IPV6 地址前缀\r\nIPv6 地址前缀格式为：\r\nxxxx:xxxx:xxxx:xxxx ，\r\n其中每个 x 都是十六进\r\n制数（前 64 位地址），\r\n可省略前导 0，如：\r\n2001:0DB8:0000:0023→ \r\n2001:DB8:0:23\r\n可为空。',
                                            `ipv6` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IPv6 地址接口 ID\r\nIPv6 地址接口 ID 格式\r\n为：\r\nxxxx:xxxx:xxxx:xxxx ，\r\n其中每个 x 都是十六进\r\n制数（后 64 位地址），\r\n可省略前导 0，如：\r\n2001:0DB8:0000:0023→ \r\n2001:DB8:0:23\r\n可为空。',
                                            `create_date` datetime NULL DEFAULT NULL COMMENT '会话创建时间',
                                            `rat` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接入方式：\r\n1: 3G\r\n2: 2G\r\n6: 4G\r\n8: NB\r\n10:5G NSA\r\n51:5G SA',
                                            `iccid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ICCID',
                                            `create_time` datetime NOT NULL COMMENT '创建时间',
                                            `s_id` int NOT NULL COMMENT '所属 session id',
                                            `dept_id` int NOT NULL COMMENT '所属企业',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            INDEX `iccid`(`iccid`, `dept_id`) USING BTREE,
                                            INDEX `ip`(`ip`) USING BTREE,
                                            INDEX `rat`(`rat`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1361 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'OneLInk EcV5 在线信息储存' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_onelink_ecv5_session
-- ----------------------------

-- ----------------------------
-- Table structure for os_perform_tasks
-- ----------------------------
DROP TABLE IF EXISTS `os_perform_tasks`;
CREATE TABLE `os_perform_tasks`  (
                                     `id` int NOT NULL AUTO_INCREMENT,
                                     `t_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编号',
                                     `dept_id` int NOT NULL DEFAULT 0 COMMENT '企业编号',
                                     `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '企业名称',
                                     `last_upd_time` datetime NOT NULL COMMENT '最后更新时间',
                                     `starting_time` datetime NOT NULL COMMENT '开始时间',
                                     `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
                                     `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
                                     `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                                     `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
                                     `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户账号',
                                     `expiration_date` date NOT NULL COMMENT '文件失效日期过期删除',
                                     `w_details` smallint NOT NULL COMMENT '是否生成详情',
                                     `ts_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务类别',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `t_no`(`t_no`, `dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 211 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '执行任务 记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_perform_tasks
-- ----------------------------

-- ----------------------------
-- Table structure for os_task_flie
-- ----------------------------
DROP TABLE IF EXISTS `os_task_flie`;
CREATE TABLE `os_task_flie`  (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `t_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务编号',
                                 `dept_id` int NOT NULL DEFAULT 0 COMMENT '企业编号',
                                 `starting_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
                                 `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
                                 `create_time` datetime NOT NULL COMMENT '创建时间',
                                 `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径',
                                 `w_external_link` smallint NOT NULL DEFAULT 0 COMMENT '是否外部链接',
                                 `download_times` int NOT NULL DEFAULT 0 COMMENT '下载次数',
                                 `type` smallint NOT NULL COMMENT '文件类型 上传文件 下载文件',
                                 `show_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名类型',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 247 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '执行任务 文件信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_task_flie
-- ----------------------------

-- ----------------------------
-- Table structure for os_task_flie_download
-- ----------------------------
DROP TABLE IF EXISTS `os_task_flie_download`;
CREATE TABLE `os_task_flie_download`  (
                                          `id` int NOT NULL AUTO_INCREMENT,
                                          `t_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务编号',
                                          `dl_dept_id` int NOT NULL DEFAULT 0 COMMENT '下载企业编号',
                                          `dl_user_id` bigint NULL DEFAULT NULL COMMENT '下载 用户编号',
                                          `create_time` datetime NOT NULL COMMENT '创建时间',
                                          `ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ip地址',
                                          `fid` int NOT NULL COMMENT '文件ID',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '执行任务文件 下载记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_task_flie_download
-- ----------------------------

-- ----------------------------
-- Table structure for os_tasks_details
-- ----------------------------
DROP TABLE IF EXISTS `os_tasks_details`;
CREATE TABLE `os_tasks_details`  (
                                     `id` int NOT NULL AUTO_INCREMENT,
                                     `t_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编号',
                                     `dept_id` int NOT NULL DEFAULT 0 COMMENT '企业编号',
                                     `starting_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
                                     `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
                                     `create_time` datetime NOT NULL COMMENT '创建时间',
                                     `state` smallint NOT NULL COMMENT '状态',
                                     `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                                     `iccid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ICCID',
                                     `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务类型',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '执行任务 详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_tasks_details
-- ----------------------------

-- ----------------------------
-- Table structure for os_upstream_card
-- ----------------------------
DROP TABLE IF EXISTS `os_upstream_card`;
CREATE TABLE `os_upstream_card`  (
                                     `id` int NOT NULL AUTO_INCREMENT,
                                     `msisdn` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MSISDN',
                                     `iccid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ICCID',
                                     `imsi` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IMSI',
                                     `imei` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IMEI',
                                     `group_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '群组 ID',
                                     `activate_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '激活时间',
                                     `open_date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开卡时间',
                                     `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上游备注',
                                     `return_data` longblob NOT NULL COMMENT '返回数据',
                                     `channel_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通道编号',
                                     `create_time` datetime NOT NULL COMMENT '创建时间',
                                     `last_upd_time` datetime NOT NULL COMMENT '最后更新时间',
                                     `inconsistent_iccid` smallint NOT NULL DEFAULT 0 COMMENT '与os_card对比 ICCID不一致',
                                     `w_new` smallint NOT NULL DEFAULT 0 COMMENT '是否为新数据',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `msisdn`(`msisdn`) USING BTREE,
                                     INDEX `channel_id`(`channel_id`, `w_new`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通道同步-上游卡号' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_upstream_card
-- ----------------------------

-- ----------------------------
-- Table structure for os_used_record
-- ----------------------------
DROP TABLE IF EXISTS `os_used_record`;
CREATE TABLE `os_used_record`  (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `iccid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ICCID',
                                   `create_time` datetime NOT NULL COMMENT '创建时间',
                                   `record_date` date NOT NULL COMMENT '记录日期',
                                   `day_used` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '天用量',
                                   `month_used` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '月用量',
                                   `reveal_day_used` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '展示-天用量',
                                   `reveal_month_used` decimal(14, 2) NOT NULL DEFAULT 0.00 COMMENT '展示-月用量',
                                   `dept_id` int NOT NULL COMMENT '所属企业',
                                   `last_upd_time` datetime NOT NULL COMMENT '最后更新时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `iccid`(`iccid`, `record_date`) USING BTREE,
                                   INDEX `dept_id`(`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1319 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用量记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of os_used_record
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
                                       `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                       `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
                                       `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
                                       `blob_data` blob NULL COMMENT '存放持久化Trigger对象',
                                       PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
                                       CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Blob类型的触发器表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
                                   `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                   `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日历名称',
                                   `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
                                   PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日历信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
                                       `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                       `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
                                       `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
                                       `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'cron表达式',
                                       `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '时区',
                                       PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
                                       CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Cron类型的触发器表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
                                        `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                        `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度器实例id',
                                        `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
                                        `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
                                        `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度器实例名',
                                        `fired_time` bigint NOT NULL COMMENT '触发的时间',
                                        `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
                                        `priority` int NOT NULL COMMENT '优先级',
                                        `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态',
                                        `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务名称',
                                        `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务组名',
                                        `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否并发',
                                        `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行',
                                        PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '已触发的触发器表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
                                     `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                     `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
                                     `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务组名',
                                     `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '相关介绍',
                                     `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行任务类名称',
                                     `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否持久化',
                                     `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否并发',
                                     `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否更新数据',
                                     `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否接受恢复执行',
                                     `job_data` blob NULL COMMENT '存放持久化job对象',
                                     PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '任务详细信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
                               `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                               `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '悲观锁名称',
                               PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储的悲观锁信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
                                             `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                             `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
                                             PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '暂停的触发器表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
                                         `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                         `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '实例名称',
                                         `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
                                         `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
                                         PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '调度器状态表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
                                         `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                         `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
                                         `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
                                         `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
                                         `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
                                         `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
                                         PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
                                         CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '简单触发器的信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
                                          `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                          `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
                                          `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
                                          `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
                                          `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
                                          `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
                                          `int_prop_1` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
                                          `int_prop_2` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
                                          `long_prop_1` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
                                          `long_prop_2` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
                                          `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
                                          `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
                                          `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
                                          `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
                                          PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
                                          CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '同步机制的行锁表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
                                  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度名称',
                                  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '触发器的名字',
                                  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '触发器所属组的名字',
                                  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
                                  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
                                  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '相关介绍',
                                  `next_fire_time` bigint NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
                                  `prev_fire_time` bigint NULL DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
                                  `priority` int NULL DEFAULT NULL COMMENT '优先级',
                                  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '触发器状态',
                                  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '触发器的类型',
                                  `start_time` bigint NOT NULL COMMENT '开始时间',
                                  `end_time` bigint NULL DEFAULT NULL COMMENT '结束时间',
                                  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日程表名称',
                                  `misfire_instr` smallint NULL DEFAULT NULL COMMENT '补偿执行的策略',
                                  `job_data` blob NULL COMMENT '存放持久化job对象',
                                  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
                                  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
                                  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '触发器详细信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
                               `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
                               `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数名称',
                               `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键名',
                               `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键值',
                               `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
                               `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                               `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                               `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                               PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2023-04-03 19:50:55', '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2023-04-03 19:50:55', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2023-04-03 19:50:55', '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'true', 'Y', 'admin', '2023-04-03 19:50:55', '', NULL, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2023-04-03 19:50:55', '', NULL, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (6, '用户登录-黑名单列表', 'sys.login.blackIPList', '', 'Y', 'admin', '2023-04-03 19:50:55', '', NULL, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');
INSERT INTO `sys_config` VALUES (7, '首页缓存时间', 'sys.frontPage.time', '15', 'Y', 'admin', '2023-04-03 19:50:55', '', NULL, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
                             `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '企业id',
                             `parent_id` bigint NULL DEFAULT 0 COMMENT '父企业id',
                             `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '祖级列表',
                             `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '企业名称',
                             `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
                             `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
                             `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
                             `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '企业状态（0正常 1停用）',
                             `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
                             `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `dname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简称',
                             PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 201 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '企业表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', 'IoTOS', 0, 'IoTOS', '15888888888', 'card@iotos.top', '0', '0', 'admin', '2023-04-03 19:50:55', 'admin', '2023-04-14 20:18:10', '总平台');
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, 'IoTOS', '15888888888', 'card@iotos.top', '0', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '深圳');
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, 'IoTOS', '15888888888', 'card@iotos.top', '0', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '长沙');
INSERT INTO `sys_dept` VALUES (200, 101, '0,100,101', '深圳子公司', 0, NULL, NULL, NULL, '0', '0', 'iotos.top', '2023-05-28 23:01:33', '', NULL, '圳子');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
                                  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
                                  `dict_sort` int NULL DEFAULT 0 COMMENT '字典排序',
                                  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
                                  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
                                  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
                                  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
                                  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
                                  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
                                  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                                  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                                  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                                  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 288 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (100, 0, '名称', '0', 'iotos_channel_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-03 22:21:18', 'admin', '2023-04-16 12:58:16', NULL);
INSERT INTO `sys_dict_data` VALUES (101, 1, '昵称', '1', 'iotos_channel_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-03 22:21:48', 'admin', '2023-04-16 12:58:21', NULL);
INSERT INTO `sys_dict_data` VALUES (102, 2, '编号', '2', 'iotos_channel_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-03 22:22:01', 'admin', '2023-04-16 12:58:25', NULL);
INSERT INTO `sys_dict_data` VALUES (103, 1, '是', '1', 'iotos_whether', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:24:45', 'admin', '2023-04-08 22:01:44', NULL);
INSERT INTO `sys_dict_data` VALUES (104, 2, '否', '0', 'iotos_whether', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:24:57', 'admin', '2023-04-08 22:01:49', NULL);
INSERT INTO `sys_dict_data` VALUES (105, 0, '不存在时更新', '0', 'up_sync_type', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:35:18', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (106, 1, '覆盖式更新', '1', 'up_sync_type', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:35:27', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (107, 0, '激活时间', 'activate_date', 'channel_syn_field', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:43:42', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (108, 1, 'IMSI', 'imsi', 'channel_syn_field', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:43:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (109, 2, '开卡时间', 'open_date', 'channel_syn_field', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:44:16', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (110, 3, '备注', 'remark', 'channel_syn_field', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:44:30', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (111, 0, '中国移动-ECV5', 'oneLink_ECV5', 'channel_template', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:51:53', 'admin', '2023-04-30 22:49:04', NULL);
INSERT INTO `sys_dict_data` VALUES (112, 1, '中国移动-ECV2', 'oneLink_ECV2', 'channel_template', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:52:23', 'admin', '2023-04-30 22:49:09', NULL);
INSERT INTO `sys_dict_data` VALUES (113, 2, '中国电信-CMP', 'ctwing_CMP', 'channel_template', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:53:33', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (114, 4, '全球电信-DCP', 'global_DCP', 'channel_template', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:54:59', 'admin', '2023-04-05 21:55:40', NULL);
INSERT INTO `sys_dict_data` VALUES (115, 5, '中国电信-CMP5G', 'ctwing_CMP5G', 'channel_template', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:55:26', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (116, 6, '中国联通-CMP', 'unicomt_CMP', 'channel_template', NULL, 'default', 'N', '0', 'admin', '2023-04-05 21:57:51', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (117, 0, '正常', '1', 'iotos_state', NULL, 'default', 'N', '0', 'admin', '2023-04-05 22:17:32', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (118, 0, '停用', '0', 'iotos_state', NULL, 'default', 'N', '0', 'admin', '2023-04-05 22:17:47', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (119, 4, '编号', '0', 'number_type', NULL, 'default', 'N', '0', 'admin', '2023-04-15 15:31:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (120, 1, 'ICCID', '1', 'number_type', NULL, 'default', 'N', '0', 'admin', '2023-04-15 15:32:01', 'admin', '2023-04-15 22:04:44', NULL);
INSERT INTO `sys_dict_data` VALUES (121, 2, 'MSISDN', '2', 'number_type', NULL, 'default', 'N', '0', 'admin', '2023-04-15 15:32:18', 'admin', '2023-04-15 22:04:52', NULL);
INSERT INTO `sys_dict_data` VALUES (122, 3, 'IMSI', '3', 'number_type', NULL, 'default', 'N', '0', 'admin', '2023-04-15 15:32:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (123, 0, '卡号', '0', 'iotos_card_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 11:15:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (124, 1, '编号', '1', 'iotos_card_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 11:15:48', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (125, 2, 'ICCID', '2', 'iotos_card_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 11:15:59', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (126, 3, 'MSISDN', '3', 'iotos_card_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 11:16:27', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (127, 1, '卡列表-更新', 'cardUpdate', 'iotos_task_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 11:54:44', 'admin', '2023-04-20 17:14:55', NULL);
INSERT INTO `sys_dict_data` VALUES (128, 0, '卡列表-导入', 'cardImport', 'iotos_task_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 11:54:58', 'admin', '2023-04-20 17:14:58', NULL);
INSERT INTO `sys_dict_data` VALUES (129, 2, '卡列表-导出', 'cardExport', 'iotos_task_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 11:55:55', 'admin', '2023-04-23 22:59:05', NULL);
INSERT INTO `sys_dict_data` VALUES (130, 0, '编号', '0', 'iotos_task_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 13:00:43', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (131, 1, '任务名', '1', 'iotos_task_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 13:00:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (132, 2, '企业', '2', 'iotos_task_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 13:01:01', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (133, 3, '账号', '3', 'iotos_task_sel_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 13:01:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (134, 1, '卡列表-导入', '_cardImport', 'iotos_task_name', NULL, 'default', 'N', '0', 'admin', '2023-04-16 15:57:39', 'admin', '2023-04-23 23:00:13', NULL);
INSERT INTO `sys_dict_data` VALUES (135, 3, '卡列表-更新', '_cardUpdate', 'iotos_task_name', NULL, 'default', 'N', '0', 'admin', '2023-04-16 15:58:00', 'admin', '2023-04-23 23:00:08', NULL);
INSERT INTO `sys_dict_data` VALUES (136, 0, '上传-源文件', '0', 'task_flie_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 16:01:15', 'admin', '2023-04-16 16:01:52', NULL);
INSERT INTO `sys_dict_data` VALUES (137, 1, '下载-已生成', '1', 'task_flie_type', NULL, 'default', 'N', '0', 'admin', '2023-04-16 16:01:30', 'admin', '2023-04-16 16:02:20', NULL);
INSERT INTO `sys_dict_data` VALUES (138, 2, '模板-更新卡列表', 'updateCard.xls', 'download_template', NULL, 'default', 'N', '0', 'admin', '2023-04-16 16:40:18', 'admin', '2023-04-16 17:04:23', NULL);
INSERT INTO `sys_dict_data` VALUES (139, 1, '模板-导入卡列表', 'importCard.xls', 'download_template', NULL, 'default', 'N', '0', 'admin', '2023-04-16 16:40:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (140, 2, '卡列表-更新-备份', '_cardUpdateBUP', 'iotos_task_name', NULL, 'default', 'N', '0', 'admin', '2023-04-16 18:11:09', 'admin', '2023-04-23 23:00:47', NULL);
INSERT INTO `sys_dict_data` VALUES (141, 4, '卡列表-划卡', '_cardDivid', 'iotos_task_name', NULL, 'default', 'N', '0', 'admin', '2023-04-20 17:10:50', 'admin', '2023-04-23 22:59:59', NULL);
INSERT INTO `sys_dict_data` VALUES (142, 5, '卡列表-划卡-备份', '_cardDividBUP', 'iotos_task_name', NULL, 'default', 'N', '0', 'admin', '2023-04-20 17:11:11', 'admin', '2023-04-23 23:01:04', NULL);
INSERT INTO `sys_dict_data` VALUES (143, 3, '卡列表-划卡', 'cardDivid', 'iotos_task_type', NULL, 'default', 'N', '0', 'admin', '2023-04-20 17:11:46', 'admin', '2023-04-20 17:14:50', NULL);
INSERT INTO `sys_dict_data` VALUES (144, 8, '未知', '0', 'card_status_show_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:33:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (145, 1, '库存', '1', 'card_status_show_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:34:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (146, 2, '可测试', '2', 'card_status_show_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (147, 3, '待激活', '3', 'card_status_show_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:11', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (148, 4, '已激活', '4', 'card_status_show_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:20', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (149, 5, '已停机', '5', 'card_status_show_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:34', 'admin', '2023-04-21 20:35:45', NULL);
INSERT INTO `sys_dict_data` VALUES (150, 6, '预销户', '6', 'card_status_show_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (151, 7, '已销户', '7', 'card_status_show_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:36:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (152, 8, '未知', '0', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:33:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (153, 1, '库存', '1', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:34:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (154, 2, '可测试', '2', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (155, 3, '待激活', '3', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:11', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (156, 4, '已激活', '4', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:20', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (157, 5, '已停机', '5', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:34', 'admin', '2023-04-21 20:35:45', NULL);
INSERT INTO `sys_dict_data` VALUES (158, 6, '预销户', '6', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:35:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (159, 7, '已销户', '7', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:36:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (160, 9, '单项停机', '9', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:39:58', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (161, 10, '预销号', '10', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:40:11', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (162, 11, '过户', '11', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:40:22', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (163, 12, '休眠', '12', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:40:37', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (164, 13, '测试期正常', '13', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:40:59', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (165, 14, '测试期停机', '14', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:41:09', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (166, 15, '停机保号', '15', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:41:41', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (167, 16, '机卡分离停机', '16', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:44:20', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (168, 17, '空套餐', '17', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:44:38', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (169, 18, '运营商管理状态', '18', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:44:50', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (170, 19, '测试去激活', '19', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:45:06', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (171, 20, '拆机', '20', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:45:30', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (172, 21, '销号', '21', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:45:58', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (173, 22, '违章停机', '22', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:47:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (174, 23, '断网', '23', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:48:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (175, 24, '开始', '24', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:48:24', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (176, 25, '已失效', '25', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:48:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (177, 26, '已清除', '26', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:48:48', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (178, 27, '已更换', '27', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:49:02', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (179, 28, '用户注销', '28', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:49:18', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (180, 29, '停断网', '29', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:49:35', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (181, 30, '挂失', '30', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:49:47', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (182, 31, '故障卡', '31', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:49:59', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (183, 32, '未实名停机', '32', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:50:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (184, 33, '超量停机', '33', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:50:30', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (185, 34, '已锁定', '34', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:51:10', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (186, 35, '已过期', '35', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:51:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (187, 36, '已终止', '36', 'card_status_id', NULL, 'default', 'N', '0', 'admin', '2023-04-21 20:51:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (188, 1, '普通贴片MS0 5*6mm', '1', 'card_type', NULL, 'default', 'N', '0', 'admin', '2023-04-21 21:25:38', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (189, 2, '普通SIM卡MS0 5*6mm', '2', 'card_type', NULL, 'default', 'N', '0', 'admin', '2023-04-21 21:25:51', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (190, 3, '工业贴片MS1 5*6mm', '3', 'card_type', NULL, 'default', 'N', '0', 'admin', '2023-04-21 21:26:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (191, 4, '工业插拔', '4', 'card_type', NULL, 'default', 'N', '0', 'admin', '2023-04-21 21:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (192, 1, 'NB', '1', 'card_network_type', NULL, 'default', 'N', '0', 'admin', '2023-04-21 21:27:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (193, 2, '4G SIM', '2', 'card_network_type', NULL, 'default', 'N', '0', 'admin', '2023-04-21 21:27:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (194, 3, '5G SIM', '3', 'card_network_type', NULL, 'default', 'N', '0', 'admin', '2023-04-21 21:27:59', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (195, 4, '2/3/4G', '4', 'card_network_type', NULL, 'default', 'N', '0', 'admin', '2023-04-21 21:28:11', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (196, 0, '未知', '0', 'card_connection_status', NULL, 'default', 'N', '0', 'admin', '2023-04-21 23:12:44', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (197, 1, '正常', '1', 'card_connection_status', NULL, 'default', 'N', '0', 'admin', '2023-04-21 23:12:52', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (198, 2, '断网', '2', 'card_connection_status', NULL, 'default', 'N', '0', 'admin', '2023-04-21 23:13:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (199, 0, '卡列表-导出', '_cardExport', 'iotos_task_name', NULL, 'default', 'N', '0', 'admin', '2023-04-23 22:59:51', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (200, 0, 'cardImport', '7', 'task_flie_expiration_date', NULL, 'default', 'N', '0', 'admin', '2023-04-24 20:22:43', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (201, 1, 'cardUpdate', '14', 'task_flie_expiration_date', NULL, 'default', 'N', '0', 'admin', '2023-04-24 20:23:02', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (202, 2, 'cardExport', '7', 'task_flie_expiration_date', NULL, 'default', 'N', '0', 'admin', '2023-04-24 20:23:11', 'admin', '2023-04-24 20:23:41', NULL);
INSERT INTO `sys_dict_data` VALUES (203, 3, 'cardDivid', '30', 'task_flie_expiration_date', NULL, 'default', 'N', '0', 'admin', '2023-04-24 20:23:32', 'admin', '2023-04-24 20:23:44', NULL);
INSERT INTO `sys_dict_data` VALUES (204, 11, '出库日期', 'delivery_date', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:02:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (205, 0, '激活时间', 'activate_date', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:03:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (206, 1, '发货日期', 'deliver_date', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:04:50', 'admin', '2023-04-26 20:05:00', NULL);
INSERT INTO `sys_dict_data` VALUES (207, 7, '首次使用', 'first_time_using', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:05:25', 'admin', '2023-04-26 20:10:05', NULL);
INSERT INTO `sys_dict_data` VALUES (208, 2, '创建时间', 'create_time', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:05:57', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (209, 3, '用量同步时间', 'traffic_sync_time', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:07:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (210, 9, '入库日期', 'storage_date', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:07:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (211, 5, '生效时间', 'prodinsteff_time', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:08:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (212, 6, '失效时间', 'prodinstexp_time', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:08:26', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (213, 8, '更新时间', 'update_date', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:09:07', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (214, 4, '状态同步时间', 'state_sync_time', 'card_sel_date_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:09:48', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (215, 4, '卡编号', 'c_no', 'card_sel_between_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:43:45', 'admin', '2023-04-26 20:44:59', NULL);
INSERT INTO `sys_dict_data` VALUES (216, 0, 'ICCID', 'iccid', 'card_sel_between_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:45:11', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (217, 1, 'MSISDN', 'msisdn', 'card_sel_between_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:45:26', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (218, 3, 'IMSI', 'imsi', 'card_sel_between_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:45:42', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (219, 2, 'IMEI', 'imei', 'card_sel_between_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 20:45:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (220, 0, '账期用量', 'c_used', 'card_dimension_field', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:08:35', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (221, 1, '账期剩余', 'c_left', 'card_dimension_field', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:08:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (222, 2, '账期总和', 'c_total', 'card_dimension_field', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:09:06', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (223, 3, '预存余额', 'balance', 'card_dimension_field', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:09:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (224, 0, '>=', '0', 'compare_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:17:13', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (225, 1, '>', '1', 'compare_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:17:32', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (226, 2, '=', '2', 'compare_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:17:41', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (227, 3, '<', '3', 'compare_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:17:51', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (228, 4, '<=', '4', 'compare_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:18:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (229, 5, '!=', '5', 'compare_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:18:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (230, 2, '编号', 'c_no', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:21:07', 'admin', '2023-04-26 21:27:48', NULL);
INSERT INTO `sys_dict_data` VALUES (231, 0, 'ICCID', 'iccid', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:21:25', 'admin', '2023-04-26 21:27:42', NULL);
INSERT INTO `sys_dict_data` VALUES (232, 1, 'MSISDN', 'msisdn', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:21:43', 'admin', '2023-04-26 21:27:53', NULL);
INSERT INTO `sys_dict_data` VALUES (233, 4, 'IMSI', 'imsi', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:21:54', 'admin', '2023-04-26 21:27:33', NULL);
INSERT INTO `sys_dict_data` VALUES (234, 3, 'IMEI', 'imei', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:27:22', 'admin', '2023-04-26 21:27:28', NULL);
INSERT INTO `sys_dict_data` VALUES (235, 5, '账期用量', 'c_used', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:29:21', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (236, 6, '账期剩余', 'c_left', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:29:39', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (237, 7, '账期总和', 'c_total', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:29:55', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (238, 8, '预存余额', 'balance', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:30:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (239, 9, '激活时间', 'activate_date', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:40:14', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (240, 10, '首次使用时间', 'first_time_using', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:42:22', 'admin', '2023-04-26 22:18:45', NULL);
INSERT INTO `sys_dict_data` VALUES (241, 11, '用量同步时间', 'traffic_sync_time', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:42:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (242, 12, '状态同步时间', 'state_sync_time', 'card_order_by_type', NULL, 'default', 'N', '0', 'admin', '2023-04-26 21:58:26', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (243, 0, '升序', '0', 'order_by_rule', NULL, 'default', 'N', '0', 'admin', '2023-04-26 22:02:06', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (244, 1, '降序', '1', 'order_by_rule', NULL, 'default', 'N', '0', 'admin', '2023-04-26 22:02:13', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (245, 0, '中国移动-ECV5', 'oneLink_ECV5', 'syn_supported_template', NULL, 'default', 'N', '0', 'admin', '2023-04-30 22:36:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (246, 3, '轮询', 'POLLING', 'sys_job_group', NULL, 'default', 'N', '0', 'admin', '2023-05-01 10:56:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (247, 4, '同步', 'SYNCHRONIZE', 'sys_job_group', NULL, 'default', 'N', '0', 'admin', '2023-05-01 10:57:20', 'admin', '2023-05-01 10:57:25', NULL);
INSERT INTO `sys_dict_data` VALUES (248, 0, '在线', '01', 'onelink_session_status', NULL, 'default', 'N', '0', 'admin', '2023-05-07 13:07:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (249, 1, '离线', '00', 'onelink_session_status', NULL, 'default', 'N', '0', 'admin', '2023-05-07 13:07:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (250, 1, '3G', '1', 'onelink_rat_type', NULL, 'default', 'N', '0', 'admin', '2023-05-07 13:13:14', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (251, 2, '2G', '2', 'onelink_rat_type', NULL, 'default', 'N', '0', 'admin', '2023-05-07 13:13:20', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (252, 3, '4G', '6', 'onelink_rat_type', NULL, 'default', 'N', '0', 'admin', '2023-05-07 13:13:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (253, 4, 'NB', '8', 'onelink_rat_type', NULL, 'default', 'N', '0', 'admin', '2023-05-07 13:13:56', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (254, 5, '5G NSA', '10', 'onelink_rat_type', NULL, 'default', 'N', '0', 'admin', '2023-05-07 13:14:22', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (255, 6, '5G SA', '51', 'onelink_rat_type', NULL, 'default', 'N', '0', 'admin', '2023-05-07 13:14:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (256, 0, '不执行', '0', 'card_openclose', NULL, 'default', 'N', '0', 'admin', '2023-05-10 08:28:31', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (257, 1, '开网', '1', 'card_openclose', NULL, 'default', 'N', '0', 'admin', '2023-05-10 08:28:52', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (258, 2, '断网', '2', 'card_openclose', NULL, 'default', 'N', '0', 'admin', '2023-05-10 08:29:05', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (259, 0, '不执行', '0', 'card_openstop', NULL, 'default', 'N', '0', 'admin', '2023-05-10 08:29:22', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (260, 1, '复机', '1', 'card_openstop', NULL, 'default', 'N', '0', 'admin', '2023-05-10 08:29:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (261, 2, '停机', '2', 'card_openstop', NULL, 'default', 'N', '0', 'admin', '2023-05-10 08:29:53', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (262, 0, '已激活转已停机', '0', 'card_flexiblechange', NULL, 'default', 'N', '0', 'admin', '2023-05-10 09:07:23', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (263, 1, '已停机转已激活', '1', 'card_flexiblechange', NULL, 'default', 'N', '0', 'admin', '2023-05-10 09:07:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (264, 2, '库存转已激活', '2', 'card_flexiblechange', NULL, 'default', 'N', '0', 'admin', '2023-05-10 09:07:35', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (265, 3, '可测试转库存', '3', 'card_flexiblechange', NULL, 'default', 'N', '0', 'admin', '2023-05-10 09:07:43', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (266, 4, '可测试转待激活', '4', 'card_flexiblechange', NULL, 'default', 'N', '0', 'admin', '2023-05-10 09:07:50', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (267, 5, '可测试转已激活', '5', 'card_flexiblechange', NULL, 'default', 'N', '0', 'admin', '2023-05-10 09:07:59', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (268, 6, '待激活转已激活', '6', 'card_flexiblechange', NULL, 'default', 'N', '0', 'admin', '2023-05-10 09:08:06', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (269, 8, '待激活转库存', '8', 'card_flexiblechange', NULL, 'default', 'N', '0', 'admin', '2023-05-10 09:08:30', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (270, 9, '库存转待激活', '9', 'card_flexiblechange', NULL, 'default', 'N', '0', 'admin', '2023-05-10 09:08:41', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (271, 3, '模板-ICCID', 'importICCID.xls', 'download_template', NULL, 'default', 'N', '0', 'admin', '2023-05-10 11:32:25', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (272, 1, '开机', 'startUp', 'tasks_details_type', NULL, 'default', 'N', '0', 'admin', '2023-05-10 21:05:06', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (273, 2, '停机', 'shutdown', 'tasks_details_type', NULL, 'default', 'N', '0', 'admin', '2023-05-10 21:05:20', 'admin', '2023-05-10 21:05:25', NULL);
INSERT INTO `sys_dict_data` VALUES (274, 3, '开网', 'openNet', 'tasks_details_type', NULL, 'default', 'N', '0', 'admin', '2023-05-10 21:05:37', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (275, 4, '断网', 'disconnected', 'tasks_details_type', NULL, 'default', 'N', '0', 'admin', '2023-05-10 21:05:46', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (276, 5, '网络重置', 'networkReset', 'tasks_details_type', NULL, 'default', 'N', '0', 'admin', '2023-05-10 21:05:59', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (277, 6, '灵活变更', 'flexibleChange', 'tasks_details_type', NULL, 'default', 'N', '0', 'admin', '2023-05-10 21:06:21', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (278, 0, '执行中', '0', 'result_status', NULL, 'default', 'N', '0', 'admin', '2023-05-11 22:28:11', 'admin', '2023-05-11 22:30:07', NULL);
INSERT INTO `sys_dict_data` VALUES (279, 1, '成功', '1', 'result_status', NULL, 'default', 'N', '0', 'admin', '2023-05-11 22:28:18', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (280, 2, '失败', '2', 'result_status', NULL, 'default', 'N', '0', 'admin', '2023-05-11 22:30:18', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (281, 0, '批量业务办理', 'batch_processing', 'api_source_type', NULL, 'default', 'N', '0', 'admin', '2023-05-12 14:59:11', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (282, 1, '轮询执行', 'polling_execution', 'api_source_type', NULL, 'default', 'N', '0', 'admin', '2023-05-12 14:59:44', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (283, 5, '卡列表-业务办理', 'cardBusinessHandling', 'iotos_task_type', NULL, 'default', 'N', '0', 'admin', '2023-05-12 23:57:26', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (284, 6, '卡列表-业务办理', '_cardBusinessHandling', 'iotos_task_name', NULL, 'default', 'N', '0', 'admin', '2023-05-12 23:58:51', 'admin', '2023-05-14 11:50:47', NULL);
INSERT INTO `sys_dict_data` VALUES (285, 5, '月初执行', 'MONTH', 'sys_job_group', NULL, 'default', 'N', '0', 'admin', '2023-05-13 10:40:41', 'admin', '2023-05-13 10:41:09', NULL);
INSERT INTO `sys_dict_data` VALUES (286, 6, '执行任务-详情-导出', 'tasksDetailsExport', 'iotos_task_type', NULL, 'default', 'N', '0', 'admin', '2023-05-14 11:48:40', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (287, 7, '执行任务详情', '_tasksDetailsExport', 'iotos_task_name', NULL, 'default', 'N', '0', 'admin', '2023-05-14 11:50:00', 'admin', '2023-05-14 11:50:51', NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
                                  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
                                  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典名称',
                                  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
                                  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
                                  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                                  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                                  PRIMARY KEY (`dict_id`) USING BTREE,
                                  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (100, '通道查询条件', 'iotos_channel_sel_type', '0', 'admin', '2023-04-03 22:16:28', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (101, '自定义是否', 'iotos_whether', '0', 'admin', '2023-04-05 20:58:34', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (102, '通道上游数据新增方式', 'up_sync_type', '0', 'admin', '2023-04-05 21:31:33', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (103, '通道上游同步字段', 'channel_syn_field', '0', 'admin', '2023-04-05 21:42:57', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (104, '通道模板', 'channel_template', '0', 'admin', '2023-04-05 21:49:55', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (105, '自定义状态', 'iotos_state', '0', 'admin', '2023-04-05 22:17:17', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (106, '卡号类型', 'number_type', '0', 'admin', '2023-04-15 13:41:22', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (107, '卡列表查询条件', 'iotos_card_sel_type', '0', 'admin', '2023-04-16 11:14:16', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (108, '执行任务类别', 'iotos_task_type', '0', 'admin', '2023-04-16 11:54:18', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (109, '执行任务查询条件', 'iotos_task_sel_type', '0', 'admin', '2023-04-16 12:56:54', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (110, '任务文件名', 'iotos_task_name', '0', 'admin', '2023-04-16 15:56:41', '', NULL, '执行任务文件名称解析');
INSERT INTO `sys_dict_type` VALUES (111, '文件来源类型', 'task_flie_type', '0', 'admin', '2023-04-16 16:00:50', '', NULL, '上传文件 or 下载文件');
INSERT INTO `sys_dict_type` VALUES (112, '可下载模板', 'download_template', '0', 'admin', '2023-04-16 16:39:05', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (113, '卡状态', 'card_status_show_id', '0', 'admin', '2023-04-21 20:32:46', 'admin', '2023-04-21 20:36:47', NULL);
INSERT INTO `sys_dict_type` VALUES (114, '卡状态描述', 'card_status_id', '0', 'admin', '2023-04-21 20:37:03', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (115, '卡类型', 'card_type', '0', 'admin', '2023-04-21 21:25:14', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (116, '卡网络类型', 'card_network_type', '0', 'admin', '2023-04-21 21:26:41', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (117, '断开网状态', 'card_connection_status', '0', 'admin', '2023-04-21 23:12:29', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (118, '执行任务储存天数', 'task_flie_expiration_date', '0', 'admin', '2023-04-24 20:21:37', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (119, '卡列表-时间类型', 'card_sel_date_type', '0', 'admin', '2023-04-26 19:57:35', 'admin', '2023-04-26 20:43:03', NULL);
INSERT INTO `sys_dict_type` VALUES (120, '卡列表-起止条件', 'card_sel_between_type', '0', 'admin', '2023-04-26 20:42:54', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (121, '卡列表-维度类型', 'card_dimension_field', '0', 'admin', '2023-04-26 21:06:57', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (122, '比较条件', 'compare_type', '0', 'admin', '2023-04-26 21:17:01', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (123, '卡列表-排序条件', 'card_order_by_type', '0', 'admin', '2023-04-26 21:20:57', 'admin', '2023-04-26 22:01:28', NULL);
INSERT INTO `sys_dict_type` VALUES (124, '排序类型', 'order_by_rule', '0', 'admin', '2023-04-26 22:01:53', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (125, '支持上游同步模板', 'syn_supported_template', '0', 'admin', '2023-04-30 22:35:57', '', NULL, '支持上游同步卡列表通道模板');
INSERT INTO `sys_dict_type` VALUES (126, 'oneLink在线状态', 'onelink_session_status', '0', 'admin', '2023-05-07 13:06:47', '', NULL, 'oneLink在线状态');
INSERT INTO `sys_dict_type` VALUES (127, 'onelink_rat接入方式', 'onelink_rat_type', '0', 'admin', '2023-05-07 13:12:59', '', NULL, 'onelink_rat接入方式');
INSERT INTO `sys_dict_type` VALUES (128, '断开网操作', 'card_openclose', '0', 'admin', '2023-05-10 08:21:14', 'admin', '2023-05-10 08:27:30', '断开网操作');
INSERT INTO `sys_dict_type` VALUES (129, '停复机操作', 'card_openstop', '0', 'admin', '2023-05-10 08:27:59', '', NULL, '停复机操作');
INSERT INTO `sys_dict_type` VALUES (130, '灵活变更', 'card_flexiblechange', '0', 'admin', '2023-05-10 09:06:46', '', NULL, '灵活变更 目前主要针对于OneLInk EcV5');
INSERT INTO `sys_dict_type` VALUES (131, '执行任务详情类型', 'tasks_details_type', '0', 'admin', '2023-05-10 21:04:28', '', NULL, '执行任务详情类型');
INSERT INTO `sys_dict_type` VALUES (132, '通用结果状态', 'result_status', '0', 'admin', '2023-05-11 22:27:47', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (133, 'API业务变更来源', 'api_source_type', '0', 'admin', '2023-05-12 14:58:14', 'admin', '2023-05-13 15:02:51', NULL);

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
                            `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
                            `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '任务名称',
                            `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
                            `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标字符串',
                            `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
                            `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
                            `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
                            `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
                            `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                            `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                            `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注信息',
                            PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2023-04-03 19:50:55', '', NULL, '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2023-04-03 19:50:55', '', NULL, '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2023-04-03 19:50:55', '', NULL, '');
INSERT INTO `sys_job` VALUES (100, 'RabbitMQ初始化', 'SYSTEM', 'initMQ.initMQConfig', '0/20 * * * * ?', '3', '1', '1', 'admin', '2023-04-13 13:56:11', '', NULL, '');
INSERT INTO `sys_job` VALUES (101, '清理执行任务', 'SYSTEM', 'performTask.clearUp', '0 30 0 * * ?', '1', '1', '0', 'admin', '2023-04-25 15:04:11', 'admin', '2023-04-30 15:27:58', '');
INSERT INTO `sys_job` VALUES (102, '同步上游卡列表', 'SYNCHRONIZE', 'synUpstreamCardTask.send', '0 45 0 * * ?', '1', '1', '0', 'admin', '2023-05-01 10:58:09', '', '2023-05-01 22:13:55', '');
INSERT INTO `sys_job` VALUES (103, '同步上游卡列表总数核验', 'SYNCHRONIZE', 'synUpstreamCardTask.verificationTotal', '0 0/30 * * * ?', '1', '1', '0', 'admin', '2023-05-01 21:44:54', 'admin', '2023-05-02 16:21:34', '');
INSERT INTO `sys_job` VALUES (104, '同步上游卡列表对比差异', 'SYNCHRONIZE', 'synUpstreamCardTask.contrastDifference', '0 0/5 * * * ?', '1', '1', '0', 'admin', '2023-05-02 11:35:23', 'admin', '2023-05-02 16:21:52', '');
INSERT INTO `sys_job` VALUES (105, '同步上游卡号导入卡列表', 'SYNCHRONIZE', 'synUpstreamCardTask.newDataImport', '0 30 9 * * ?', '1', '1', '0', 'admin', '2023-05-02 13:13:03', '', '2023-05-02 13:13:11', '');
INSERT INTO `sys_job` VALUES (106, '同步上游卡号数据修复', 'SYNCHRONIZE', 'synUpstreamCardTask.synDataRecovery', '0 0/1 * * * ?', '1', '1', '0', 'admin', '2023-05-02 16:11:55', 'admin', '2023-05-02 16:20:30', '');
INSERT INTO `sys_job` VALUES (107, '初始化月用量记录', 'MONTH', 'initDBTask.monthlyMigration(-1)', '0 0 1 1 * ? *', '1', '1', '0', 'admin', '2023-05-05 16:16:20', 'admin', '2023-05-13 10:41:02', '');
INSERT INTO `sys_job` VALUES (108, '轮询执行', 'POLLING', 'pollingStartTask.run', '0 10 0 * * ?', '1', '1', '0', 'admin', '2023-05-09 08:10:42', '', '2023-05-09 08:10:50', '');
INSERT INTO `sys_job` VALUES (109, '轮询补偿-用量-已激活/已停机', 'POLLING', 'pollingStartTask.compensation(\'4,5\',\'1\',300,\'synFlow\')', '0 0/1 * * * ?', '1', '1', '0', 'admin', '2023-05-14 17:15:47', 'admin', '2023-05-14 20:55:52', '');
INSERT INTO `sys_job` VALUES (110, '初始化补偿 记录时间', 'POLLING', 'pollingStartTask.initTime', '0 10 0/1 * * ?', '1', '1', '0', 'admin', '2023-05-14 17:54:54', '', '2023-05-14 18:15:49', '');
INSERT INTO `sys_job` VALUES (111, '轮询补偿-状态-已激活/已停机', 'POLLING', 'pollingStartTask.compensation(\'4,5\',\'1\',300,\'synStatus\')', '0 0/1 * * * ?', '1', '1', '0', 'admin', '2023-05-14 20:42:45', 'admin', '2023-05-14 20:48:59', '');
INSERT INTO `sys_job` VALUES (112, '轮询补偿-状态-未知/库存/可测试/待激活', 'POLLING', 'pollingStartTask.compensation(\'0,1,2,3\',\'1\',300,\'synStatus\')', '0 10 0/1 * * ?', '1', '1', '0', 'admin', '2023-05-14 20:43:58', 'admin', '2023-05-14 20:55:50', '');
INSERT INTO `sys_job` VALUES (113, '总部首页数据生成', 'SYSTEM', 'frontPageTask.generate(100)', '0 5 0 * * ?', '1', '1', '0', 'admin', '2023-05-22 20:52:04', '', '2023-05-22 20:52:08', '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
                                `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
                                `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
                                `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务组名',
                                `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标字符串',
                                `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日志信息',
                                `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
                                `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '异常信息',
                                `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12397 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
                                   `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
                                   `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户账号',
                                   `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录IP地址',
                                   `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录地点',
                                   `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器类型',
                                   `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作系统',
                                   `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
                                   `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '提示消息',
                                   `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
                                   PRIMARY KEY (`info_id`) USING BTREE,
                                   INDEX `idx_sys_logininfor_s`(`status`) USING BTREE,
                                   INDEX `idx_sys_logininfor_lt`(`login_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 344 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                             `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
                             `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
                             `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
                             `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
                             `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
                             `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
                             `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
                             `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
                             `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
                             `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
                             `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
                             `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
                             `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
                             `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
                             `tcode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国际化-对应code',
                             PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2038 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 10, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2023-04-03 19:50:55', 'admin', '2023-04-03 20:33:17', '系统管理目录', 'System_Management');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 1, 10, 'monitor', NULL, '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2023-04-03 19:50:55', 'admin', '2023-04-03 20:27:35', '系统监控目录', 'System_monitoring');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 1, 11, 'tool', NULL, '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2023-04-03 19:50:55', 'admin', '2023-04-03 20:27:48', '系统工具目录', 'System_Tool');
INSERT INTO `sys_menu` VALUES (4, '关于IoTOS', 0, 4, 'IoTOS', 'IoTOS', '', 1, 0, 'C', '0', '0', '', 'international', 'admin', '2023-04-03 19:50:55', 'admin', '2023-06-11 15:31:38', '若依官网地址', 'IoTOS_official_website');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 2037, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2023-04-03 19:50:55', 'admin', '2023-06-11 13:57:57', '用户管理菜单', 'User_Management');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 2037, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2023-04-03 19:50:55', 'admin', '2023-06-11 13:58:05', '角色管理菜单', 'role_management');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2023-04-03 19:50:55', '', NULL, '菜单管理菜单', 'menu_management');
INSERT INTO `sys_menu` VALUES (103, '企业管理', 2037, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2023-04-03 19:50:55', 'admin', '2023-06-11 13:58:13', '企业管理菜单', 'department_management');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 2037, 5, 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2023-04-03 19:50:55', 'admin', '2023-06-11 13:58:23', '岗位管理菜单', 'job_management');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2023-04-03 19:50:55', '', NULL, '字典管理菜单', 'dictionary_management');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2023-04-03 19:50:55', '', NULL, '参数设置菜单', 'parameter_settings');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2023-04-03 19:50:55', '', NULL, '通知公告菜单', 'announcement');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2023-04-03 19:50:55', '', NULL, '日志管理菜单', 'log_management');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2023-04-03 19:50:55', '', NULL, '在线用户菜单', 'online_user');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2023-04-03 19:50:55', '', NULL, '定时任务菜单', 'timed_task');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2023-04-03 19:50:55', '', NULL, '数据监控菜单', 'data_monitoring');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2023-04-03 19:50:55', '', NULL, '服务监控菜单', 'service_monitoring');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2023-04-03 19:50:55', '', NULL, '缓存监控菜单', 'cache_monitoring');
INSERT INTO `sys_menu` VALUES (114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', '2023-04-03 19:50:55', '', NULL, '缓存列表菜单', 'cache_list');
INSERT INTO `sys_menu` VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2023-04-03 19:50:55', '', NULL, '表单构建菜单', 'form_building');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2023-04-03 19:50:55', '', NULL, '代码生成菜单', 'code_generation');
INSERT INTO `sys_menu` VALUES (117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2023-04-03 19:50:55', '', NULL, '系统接口菜单', 'system_interface');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2023-04-03 19:50:55', '', NULL, '操作日志菜单', 'operation_log');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2023-04-03 19:50:55', '', NULL, '登录日志菜单', 'login_log');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'user_query');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'new_user');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'user_modification');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'user_delete');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'user_export');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'user_import');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'reset_Password');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'role_query');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'new_role');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'role_modification');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'role_deletion');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'character_export');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'menu_query');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'New_menu');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'menu_modification');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'menu_delete');
INSERT INTO `sys_menu` VALUES (1016, '企业查询', 103, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Department_inquiry');
INSERT INTO `sys_menu` VALUES (1017, '企业新增', 103, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'new_department');
INSERT INTO `sys_menu` VALUES (1018, '企业修改', 103, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'department_modification');
INSERT INTO `sys_menu` VALUES (1019, '企业删除', 103, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'department_delete');
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'job_search');
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'new_jobs');
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'job_modification');
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'post_delete');
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'post_export');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'dictionary_lookup');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'dictionary_added');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'dictionary_modification');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'dictionary_deletion');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Dictionary_export');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'parameter_query');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'New_parameters');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'parameter_modification');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'parameter_deletion');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Parameter_export');
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Announcement_query');
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Announcement_added');
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Announcement_modification');
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Announcement_delete');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Operation_query');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'operation_delete');
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'log_export');
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'login_query');
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Login_to_delete');
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'log_export');
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'account_unlock');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'online_search');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'Forced_withdrawal_in_batches');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'single_forced_exit');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'task_query');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'new_task');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'task_modification');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'task_delete');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'status_modification');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 6, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'task_export');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 116, 1, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'generate_query');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 116, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'generate_modification');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 116, 3, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'generate_delete');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 116, 4, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'import_code');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 116, 5, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'preview_code');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 116, 6, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2023-04-03 19:50:55', '', NULL, '', 'generate_code');
INSERT INTO `sys_menu` VALUES (2000, '连接', 0, 1, 'connect', NULL, NULL, 1, 0, 'M', '0', '0', '', 'cascader', 'admin', '2023-04-03 20:33:08', 'admin', '2023-04-03 20:34:15', '', 'connect');
INSERT INTO `sys_menu` VALUES (2001, '流量卡', 2000, 1, 'card', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'button', 'admin', '2023-04-03 20:34:29', '', NULL, '', 'traffic_card');
INSERT INTO `sys_menu` VALUES (2002, '通道', 2001, 1, 'channel', 'iotos/connect/channel/index', NULL, 1, 0, 'C', '0', '0', 'iotos:channel:list', '#', 'admin', '2023-04-03 20:35:58', 'admin', '2023-04-03 20:46:31', '', 'aisle');
INSERT INTO `sys_menu` VALUES (2003, '卡列表', 2001, 1, 'card', 'iotos/connect/card/index', NULL, 1, 0, 'C', '0', '0', 'iotos:card:list', '#', 'admin', '2023-04-03 20:36:26', 'admin', '2023-04-27 21:27:23', '', 'card_list');
INSERT INTO `sys_menu` VALUES (2004, '销号列表', 2001, 3, 'cancelAccount', 'iotos/connect/card/cancelAccount', '', 1, 0, 'C', '0', '0', 'iotos:card:list', '#', 'admin', '2023-04-03 20:37:34', 'admin', '2023-04-27 21:54:41', '', 'List_of_pin_numbers');
INSERT INTO `sys_menu` VALUES (2005, '计费组', 2001, 4, 'bill', NULL, NULL, 1, 0, 'M', '0', '1', '', '#', 'admin', '2023-04-03 20:38:02', 'admin', '2023-05-28 20:27:02', '', 'billing_group');
INSERT INTO `sys_menu` VALUES (2006, '充值管理', 2001, 5, 'recharge', NULL, NULL, 1, 0, 'C', '0', '1', '', '#', 'admin', '2023-04-03 20:40:25', 'admin', '2023-05-28 20:27:14', '', 'Recharge_management');
INSERT INTO `sys_menu` VALUES (2007, '详情', 2002, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:channel:find', '#', 'admin', '2023-04-13 14:09:41', '', NULL, '', 'common.details');
INSERT INTO `sys_menu` VALUES (2008, '新增', 2002, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:channel:add', '#', 'admin', '2023-04-13 14:10:36', '', NULL, '', 'common.save');
INSERT INTO `sys_menu` VALUES (2009, '编辑', 2002, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:channel:edit', '#', 'admin', '2023-04-13 14:11:10', '', NULL, '', 'common.edie');
INSERT INTO `sys_menu` VALUES (2010, '删除', 2002, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:channel:del', '#', 'admin', '2023-04-13 14:11:41', '', NULL, '', 'common.delete');
INSERT INTO `sys_menu` VALUES (2011, '详情', 2003, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:find', '#', 'admin', '2023-04-13 14:09:41', '', NULL, '', 'common.details');
INSERT INTO `sys_menu` VALUES (2012, '新增', 2003, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:add', '#', 'admin', '2023-04-13 14:10:36', '', NULL, '', 'common.save');
INSERT INTO `sys_menu` VALUES (2013, '编辑', 2003, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:edit', '#', 'admin', '2023-04-13 14:11:10', '', NULL, '', 'common.edie');
INSERT INTO `sys_menu` VALUES (2014, '删除', 2003, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:del', '#', 'admin', '2023-04-13 14:11:41', '', NULL, '', 'common.delete');
INSERT INTO `sys_menu` VALUES (2015, '执行任务', 0, 2, 'tasks', 'iotos/performTasks/index', NULL, 1, 0, 'C', '0', '0', 'iotos:performTasks:list', 'download', 'admin', '2023-04-16 11:28:24', 'admin', '2023-04-16 14:07:12', '', 'performTasks');
INSERT INTO `sys_menu` VALUES (2016, '总部标识', 0, 99, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:sys:headquarters', '#', 'admin', '2023-04-16 12:40:23', '', NULL, '', 'headquarters');
INSERT INTO `sys_menu` VALUES (2018, '任务文件下载', 2015, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:performTasks:download', '#', 'admin', '2023-04-16 15:04:45', '', NULL, '', 'downloadFile');
INSERT INTO `sys_menu` VALUES (2019, '划卡', 2003, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:divideCard', '#', 'admin', '2023-04-19 21:41:56', '', NULL, '', 'divideCard');
INSERT INTO `sys_menu` VALUES (2020, '查看文件下载记录', 2015, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:performTasks:flieDownload', '#', 'admin', '2023-04-25 22:29:43', '', NULL, '', 'flieDownload');
INSERT INTO `sys_menu` VALUES (2021, '所属通道查询', 2003, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:queryChannel', '#', 'admin', '2023-04-26 22:07:44', 'admin', '2023-04-26 22:07:59', '', 'queryChannel');
INSERT INTO `sys_menu` VALUES (2022, '查询卡列表分组', 2003, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:getGrouping', '#', 'admin', '2023-04-26 22:44:57', '', NULL, '', 'getGrouping');
INSERT INTO `sys_menu` VALUES (2023, '一键同步', 2003, 8, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:synInfo', '#', 'admin', '2023-05-05 22:14:16', '', NULL, '', 'synInfo');
INSERT INTO `sys_menu` VALUES (2024, '同步用量', 2003, 9, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:synFlow', '#', 'admin', '2023-05-05 22:15:25', '', NULL, '', 'synFlow');
INSERT INTO `sys_menu` VALUES (2025, '同步状态', 2003, 10, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:synStatus', '#', 'admin', '2023-05-05 22:15:54', '', NULL, '', 'synStatus');
INSERT INTO `sys_menu` VALUES (2026, '同步实名状态', 2003, 11, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:synRealName', '#', 'admin', '2023-05-12 20:44:17', '', NULL, '', 'synRealName');
INSERT INTO `sys_menu` VALUES (2027, '同步在线信息', 2003, 12, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:synSession', '#', 'admin', '2023-05-12 20:44:43', '', NULL, '', 'synSession');
INSERT INTO `sys_menu` VALUES (2028, '同步激活时间', 2003, 13, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:synActivateDate', '#', 'admin', '2023-05-12 20:45:02', '', NULL, '', 'synActivateDate');
INSERT INTO `sys_menu` VALUES (2029, '同步IMEI', 2003, 14, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:synImei', '#', 'admin', '2023-05-12 20:45:19', '', NULL, '', 'synImei');
INSERT INTO `sys_menu` VALUES (2030, 'API业务办理', 2003, 15, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:businessHandling', '#', 'admin', '2023-05-12 20:45:41', '', NULL, '', 'businessHandling');
INSERT INTO `sys_menu` VALUES (2031, '查询用量记录', 2003, 16, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:usedRecord:list', '#', 'admin', '2023-05-13 11:13:00', 'admin', '2023-05-13 11:13:17', '', 'usedRecord');
INSERT INTO `sys_menu` VALUES (2032, '查询API业务办理记录', 2003, 17, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:getApiBusinessList', '#', 'admin', '2023-05-13 13:09:04', '', NULL, '', 'getApiBusinessList');
INSERT INTO `sys_menu` VALUES (2033, '查询执行任务详情', 2015, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:performTasks:tasksDetailsList', '#', 'admin', '2023-05-13 20:17:58', '', NULL, '', 'tasksDetailsList');
INSERT INTO `sys_menu` VALUES (2034, '查询执行任务详情导出', 2015, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:performTasks:tasksDetailsExport', '#', 'admin', '2023-05-13 20:20:32', '', NULL, '', 'tasksDetailsExport');
INSERT INTO `sys_menu` VALUES (2035, '首页', 0, 0, 'index', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:frontPage:find', '#', 'admin', '2023-05-28 21:15:44', 'admin', '2023-05-28 21:59:57', '', 'index');
INSERT INTO `sys_menu` VALUES (2037, '账号中心', 0, 3, 'accountCenter', NULL, NULL, 1, 0, 'M', '0', '0', '', 'dept', 'admin', '2023-06-11 13:57:46', 'admin', '2023-06-11 15:25:13', '', 'accountCenter');
INSERT INTO `sys_menu` VALUES (2039, '导出', 2003, 18, '', NULL, NULL, 1, 0, 'F', '0', '0', 'iotos:card:export', '#', 'admin', '2023-06-12 21:15:52', '', NULL, '', NULL);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
                               `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
                               `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
                               `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
                               `notice_content` longblob NULL COMMENT '公告内容',
                               `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
                               `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                               `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                               `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                               PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
                                 `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
                                 `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '模块标题',
                                 `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
                                 `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法名称',
                                 `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方式',
                                 `operator_type` int NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
                                 `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人员',
                                 `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '企业名称',
                                 `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求URL',
                                 `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '主机地址',
                                 `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作地点',
                                 `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求参数',
                                 `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '返回参数',
                                 `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
                                 `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '错误消息',
                                 `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
                                 `cost_time` bigint NULL DEFAULT 0 COMMENT '消耗时间',
                                 PRIMARY KEY (`oper_id`) USING BTREE,
                                 INDEX `idx_sys_oper_log_bt`(`business_type`) USING BTREE,
                                 INDEX `idx_sys_oper_log_s`(`status`) USING BTREE,
                                 INDEX `idx_sys_oper_log_ot`(`oper_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1161 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
                             `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
                             `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
                             `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位名称',
                             `post_sort` int NOT NULL COMMENT '显示顺序',
                             `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态（0正常 1停用）',
                             `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                             PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2023-04-03 19:50:55', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2023-04-03 19:50:55', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2023-04-03 19:50:55', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2023-04-03 19:50:55', '', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                             `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
                             `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
                             `role_sort` int NOT NULL COMMENT '显示顺序',
                             `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本企业数据权限 4：本企业及以下数据权限）',
                             `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
                             `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '企业树选择项是否关联显示',
                             `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
                             `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
                             `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                             PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2023-04-03 19:50:55', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '集团用户', 'common', 2, '4', 0, 0, '0', '0', 'admin', '2023-04-03 19:50:55', 'admin', '2023-06-12 21:34:09', '普通角色');
INSERT INTO `sys_role` VALUES (3, '演示账号', 'demoAccount', 3, '1', 0, 0, '0', '0', 'admin', '2023-06-12 20:54:22', 'admin', '2023-06-12 21:33:59', NULL);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
                                  `role_id` bigint NOT NULL COMMENT '角色ID',
                                  `dept_id` bigint NOT NULL COMMENT '企业ID',
                                  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和企业关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
                                  `role_id` bigint NOT NULL COMMENT '角色ID',
                                  `menu_id` bigint NOT NULL COMMENT '菜单ID',
                                  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (2, 2001);
INSERT INTO `sys_role_menu` VALUES (2, 2003);
INSERT INTO `sys_role_menu` VALUES (2, 2011);
INSERT INTO `sys_role_menu` VALUES (2, 2015);
INSERT INTO `sys_role_menu` VALUES (2, 2018);
INSERT INTO `sys_role_menu` VALUES (2, 2019);
INSERT INTO `sys_role_menu` VALUES (2, 2035);
INSERT INTO `sys_role_menu` VALUES (2, 2036);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 4);
INSERT INTO `sys_role_menu` VALUES (3, 100);
INSERT INTO `sys_role_menu` VALUES (3, 101);
INSERT INTO `sys_role_menu` VALUES (3, 102);
INSERT INTO `sys_role_menu` VALUES (3, 103);
INSERT INTO `sys_role_menu` VALUES (3, 104);
INSERT INTO `sys_role_menu` VALUES (3, 105);
INSERT INTO `sys_role_menu` VALUES (3, 106);
INSERT INTO `sys_role_menu` VALUES (3, 107);
INSERT INTO `sys_role_menu` VALUES (3, 110);
INSERT INTO `sys_role_menu` VALUES (3, 1000);
INSERT INTO `sys_role_menu` VALUES (3, 1007);
INSERT INTO `sys_role_menu` VALUES (3, 1012);
INSERT INTO `sys_role_menu` VALUES (3, 1016);
INSERT INTO `sys_role_menu` VALUES (3, 1020);
INSERT INTO `sys_role_menu` VALUES (3, 1025);
INSERT INTO `sys_role_menu` VALUES (3, 1030);
INSERT INTO `sys_role_menu` VALUES (3, 1035);
INSERT INTO `sys_role_menu` VALUES (3, 1049);
INSERT INTO `sys_role_menu` VALUES (3, 2000);
INSERT INTO `sys_role_menu` VALUES (3, 2001);
INSERT INTO `sys_role_menu` VALUES (3, 2002);
INSERT INTO `sys_role_menu` VALUES (3, 2003);
INSERT INTO `sys_role_menu` VALUES (3, 2004);
INSERT INTO `sys_role_menu` VALUES (3, 2005);
INSERT INTO `sys_role_menu` VALUES (3, 2006);
INSERT INTO `sys_role_menu` VALUES (3, 2007);
INSERT INTO `sys_role_menu` VALUES (3, 2011);
INSERT INTO `sys_role_menu` VALUES (3, 2012);
INSERT INTO `sys_role_menu` VALUES (3, 2013);
INSERT INTO `sys_role_menu` VALUES (3, 2014);
INSERT INTO `sys_role_menu` VALUES (3, 2015);
INSERT INTO `sys_role_menu` VALUES (3, 2016);
INSERT INTO `sys_role_menu` VALUES (3, 2018);
INSERT INTO `sys_role_menu` VALUES (3, 2019);
INSERT INTO `sys_role_menu` VALUES (3, 2020);
INSERT INTO `sys_role_menu` VALUES (3, 2021);
INSERT INTO `sys_role_menu` VALUES (3, 2022);
INSERT INTO `sys_role_menu` VALUES (3, 2023);
INSERT INTO `sys_role_menu` VALUES (3, 2024);
INSERT INTO `sys_role_menu` VALUES (3, 2025);
INSERT INTO `sys_role_menu` VALUES (3, 2026);
INSERT INTO `sys_role_menu` VALUES (3, 2027);
INSERT INTO `sys_role_menu` VALUES (3, 2028);
INSERT INTO `sys_role_menu` VALUES (3, 2029);
INSERT INTO `sys_role_menu` VALUES (3, 2030);
INSERT INTO `sys_role_menu` VALUES (3, 2031);
INSERT INTO `sys_role_menu` VALUES (3, 2032);
INSERT INTO `sys_role_menu` VALUES (3, 2033);
INSERT INTO `sys_role_menu` VALUES (3, 2034);
INSERT INTO `sys_role_menu` VALUES (3, 2035);
INSERT INTO `sys_role_menu` VALUES (3, 2037);
INSERT INTO `sys_role_menu` VALUES (3, 2039);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                             `dept_id` bigint NULL DEFAULT NULL COMMENT '企业ID',
                             `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
                             `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
                             `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
                             `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户邮箱',
                             `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '手机号码',
                             `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
                             `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像地址',
                             `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码',
                             `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
                             `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
                             `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后登录IP',
                             `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
                             `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                             `language_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问语言编码',
                             PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 100, 'admin', 'IoTOS管理员', '00', '', '15888888888', '1', '', '$10$rF2yx1YAUyQ046W/u/DJKeb/yC3GSTOZoLaN3lwOT06XMNjYAPBOy', '0', '0', '127.0.0.1', '2023-06-12 20:43:02', 'admin', '2023-04-03 19:50:55', '', '2023-06-12 20:43:01', '管理员', 'zh-CN');
INSERT INTO `sys_user` VALUES (2, 100, 'iotos', '企业一', '00', 'card@iotos.top', '15666666666', '1', '', '$2a$10$GT0DABkdxsmcO/ObS.xXh.7HQIUEGie9ruXoicuyklbOrjd9ZWJHW', '0', '0', '127.0.0.1', '2023-06-12 21:54:02', 'admin', '2023-04-03 19:50:55', 'admin', '2023-06-12 21:54:01', '测试员', 'en');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
                                  `user_id` bigint NOT NULL COMMENT '用户ID',
                                  `post_id` bigint NOT NULL COMMENT '岗位ID',
                                  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                  `user_id` bigint NOT NULL COMMENT '用户ID',
                                  `role_id` bigint NOT NULL COMMENT '角色ID',
                                  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 3);
INSERT INTO `sys_user_role` VALUES (100, 2);

SET FOREIGN_KEY_CHECKS = 1;
