# v1.0.0 版本升级到 v1.2.1 数据库需要增加文件

# [os_sys_notice_viewable]-[2023-07-08]-[iotos]-[新增表]

CREATE TABLE `os_sys_notice_viewable` (
                                          `id` int NOT NULL AUTO_INCREMENT,
                                          `notice_id` int NOT NULL COMMENT '公告ID',
                                          `dept_id` bigint DEFAULT NULL COMMENT '部门',
                                          `user_id` bigint DEFAULT NULL COMMENT '用户ID',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统通知公告可查看权限配置';

# [sys_notice]-[2023-07-08]-[iotos]-[新增字段]

alter table sys_notice add `start_time` datetime DEFAULT NULL COMMENT '开始时间';
alter table sys_notice add `end_time` datetime DEFAULT NULL COMMENT '结束时间';
alter table sys_notice add `terminal_type` smallint DEFAULT NULL COMMENT '终端通知类型';
alter table sys_notice add `method_informe` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '通知方式 dept user all';
alter table sys_notice add `total` int DEFAULT NULL COMMENT '通知总数';

