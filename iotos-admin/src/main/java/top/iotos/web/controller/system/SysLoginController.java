package top.iotos.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.iotos.common.constant.Constants;
import top.iotos.common.core.domain.AjaxResult;
import top.iotos.common.core.domain.entity.SysMenu;
import top.iotos.common.core.domain.entity.SysUser;
import top.iotos.common.core.domain.model.LoginBody;
import top.iotos.common.utils.SecurityUtils;
import top.iotos.framework.web.service.SysLoginService;
import top.iotos.framework.web.service.SysPermissionService;
import top.iotos.system.service.ISysMenuService;
import top.iotos.web.core.config.MyBaseController;

/**
 * 登录验证
 * 
 * @author iotos.top
 */
@RestController
public class SysLoginController extends MyBaseController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     * @param pwdStr 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody String pwdStr)
    {
        HashMap<String, Object> parameter = getParameter(pwdStr);
        LoginBody loginBody = new LoginBody(parameter);
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        Map<String,Object> rMap = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
            loginBody.getUuid());
        ajax.put(Constants.TOKEN, rMap.get("token").toString());
        ajax.put("lgCode", rMap.get("lgCode").toString());
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
