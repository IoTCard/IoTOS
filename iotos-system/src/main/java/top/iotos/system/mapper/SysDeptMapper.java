package top.iotos.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import top.iotos.common.core.domain.entity.SysDept;

/**
 * 企业管理 数据层
 * 
 * @author iotos.top
 */
public interface SysDeptMapper
{
    /**
     * 查询企业管理数据
     * 
     * @param dept 企业信息
     * @return 企业信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 根据角色ID查询企业树信息
     * 
     * @param roleId 角色ID
     * @param deptCheckStrictly 企业树选择项是否关联显示
     * @return 选中企业列表
     */
    public List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * 根据企业ID查询信息
     * 
     * @param deptId 企业ID
     * @return 企业信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 根据ID查询所有子企业
     * 
     * @param deptId 企业ID
     * @return 企业列表
     */
    public List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 根据ID查询所有子企业（正常状态）
     * 
     * @param deptId 企业ID
     * @return 子企业数
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在子节点
     * 
     * @param deptId 企业ID
     * @return 结果
     */
    public int hasChildByDeptId(Long deptId);

    /**
     * 查询企业是否存在用户
     * 
     * @param deptId 企业ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 校验企业名称是否唯一
     * 
     * @param deptName 企业名称
     * @param parentId 父企业ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 新增企业信息
     * 
     * @param dept 企业信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改企业信息
     * 
     * @param dept 企业信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 修改所在企业正常状态
     * 
     * @param deptIds 企业ID组
     */
    public void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 修改子元素关系
     * 
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 删除企业管理信息
     * 
     * @param deptId 企业ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);
    public List<Map<String,Object>> getDeptName();

}
