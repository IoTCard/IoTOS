set global log_bin_trust_function_creators=TRUE;
-- Function structure for queryChildrenAreaInfo
-- ----------------------------
DROP FUNCTION IF EXISTS `queryChildrenAreaInfo`;
delimiter ;;
CREATE FUNCTION `queryChildrenAreaInfo`(`username_set` char(50) charset utf8mb4 )
 RETURNS varchar(16383) CHARSET utf8mb3
  SQL SECURITY INVOKER
BEGIN

DECLARE sTemp VARCHAR(16383);
DECLARE sTempChd VARCHAR(16383);

SET sTemp="";
SET sTempChd = CAST(username_set AS CHAR);

WHILE sTempChd IS NOT NULL DO
SET sTemp= CONCAT(sTemp,",",sTempChd);
SELECT GROUP_CONCAT(dept_id) INTO sTempChd FROM sys_dept WHERE FIND_IN_SET(parent_id,sTempChd)>0;
END WHILE;
RETURN sTemp;
END
;;
delimiter ;