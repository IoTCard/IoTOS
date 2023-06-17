package top.iotos.system.mapper;

import java.util.List;
import top.iotos.system.domain.SysRoleDept;

/**
 * 角色与企业关联表 数据层
 * 
 * @author iotos.top
 */
public interface SysRoleDeptMapper
{
    /**
     * 通过角色ID删除角色和企业关联
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleDeptByRoleId(Long roleId);

    /**
     * 批量删除角色企业关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleDept(Long[] ids);

    /**
     * 查询企业使用数量
     * 
     * @param deptId 企业ID
     * @return 结果
     */
    public int selectCountRoleDeptByDeptId(Long deptId);

    /**
     * 批量新增角色企业信息
     * 
     * @param roleDeptList 角色企业列表
     * @return 结果
     */
    public int batchRoleDept(List<SysRoleDept> roleDeptList);
}
