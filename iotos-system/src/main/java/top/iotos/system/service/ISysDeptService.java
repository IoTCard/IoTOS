package top.iotos.system.service;

import java.util.List;
import java.util.Map;

import top.iotos.common.core.domain.TreeSelect;
import top.iotos.common.core.domain.entity.SysDept;

/**
 * 企业管理 服务层
 * 
 * @author iotos.top
 */
public interface ISysDeptService
{
    /**
     * 查询企业管理数据
     * 
     * @param dept 企业信息
     * @return 企业信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 查询企业树结构信息
     * 
     * @param dept 企业信息
     * @return 企业树信息集合
     */
    public List<TreeSelect> selectDeptTreeList(SysDept dept);

    /**
     * 构建前端所需要树结构
     * 
     * @param depts 企业列表
     * @return 树结构列表
     */
    public List<SysDept> buildDeptTree(List<SysDept> depts);

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param depts 企业列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 根据角色ID查询企业树信息
     * 
     * @param roleId 角色ID
     * @return 选中企业列表
     */
    public List<Long> selectDeptListByRoleId(Long roleId);

    /**
     * 根据企业ID查询信息
     * 
     * @param deptId 企业ID
     * @return 企业信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 根据ID查询所有子企业（正常状态）
     * 
     * @param deptId 企业ID
     * @return 子企业数
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在企业子节点
     * 
     * @param deptId 企业ID
     * @return 结果
     */
    public boolean hasChildByDeptId(Long deptId);

    /**
     * 查询企业是否存在用户
     * 
     * @param deptId 企业ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * 校验企业名称是否唯一
     * 
     * @param dept 企业信息
     * @return 结果
     */
    public boolean checkDeptNameUnique(SysDept dept);

    /**
     * 校验企业是否有数据权限
     * 
     * @param deptId 企业id
     */
    public void checkDeptDataScope(Long deptId);

    /**
     * 新增保存企业信息
     * 
     * @param dept 企业信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改保存企业信息
     * 
     * @param dept 企业信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 删除企业管理信息
     * 
     * @param deptId 企业ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    public List<Map<String,Object>> getDeptName();

}
