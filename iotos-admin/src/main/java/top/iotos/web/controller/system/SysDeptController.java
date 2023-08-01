package top.iotos.web.controller.system;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.iotos.common.annotation.Log;
import top.iotos.common.constant.UserConstants;
import top.iotos.web.core.config.MyBaseController;
import top.iotos.common.core.domain.AjaxResult;
import top.iotos.common.core.domain.entity.SysDept;
import top.iotos.common.enums.BusinessType;
import top.iotos.common.utils.StringUtils;
import top.iotos.system.service.ISysDeptService;

/**
 * 企业信息
 * 
 * @author iotos.top
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends MyBaseController
{
    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取企业列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDept dept)
    {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return success(depts);
    }

    /**
     * 查询企业列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId)
    {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        depts.removeIf(d -> d.getDeptId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return success(depts);
    }

    /**
     * 根据企业编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId)
    {
        deptService.checkDeptDataScope(deptId);
        return success(deptService.selectDeptById(deptId));
    }

    /**
     * 新增企业
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "企业管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept)
    {
        if (!deptService.checkDeptNameUnique(dept))
        {
            return error("新增企业'" + dept.getDeptName() + "'失败，企业名称已存在");
        }
        dept.setCreateBy(getUsername());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改企业
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "企业管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept)
    {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (!deptService.checkDeptNameUnique(dept))
        {
            return error("修改企业'" + dept.getDeptName() + "'失败，企业名称已存在");
        }
        else if (dept.getParentId().equals(deptId))
        {
            return error("修改企业'" + dept.getDeptName() + "'失败，上级企业不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0)
        {
            return error("该企业包含未停用的子企业！");
        }
        dept.setUpdateBy(getUsername());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除企业
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "企业管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId)
    {
        if (deptService.hasChildByDeptId(deptId))
        {
            return warn("存在下级企业,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return warn("企业存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }


    @PreAuthorize("@ss.hasPermi('iotos:card:list')")
    @PostMapping(value = "/getDeptName", produces = { "application/json;charset=UTF-8" })
    public String getDeptName()
    {
        try {
            return retunnSuccess(deptService.getDeptName(),null);
        }catch (Exception e){
            logger.error("<br/> /system/dept/getDeptName  <br/><br/> ip = {} <br/> e = {} <br/>",getIP(),e.getCause().toString());
        }
        return retunnError(null);
    }

}
