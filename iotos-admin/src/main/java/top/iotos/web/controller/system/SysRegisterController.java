package top.iotos.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.iotos.web.core.config.MyBaseController;
import top.iotos.common.core.domain.AjaxResult;
import top.iotos.common.core.domain.model.RegisterBody;
import top.iotos.common.utils.StringUtils;
import top.iotos.framework.web.service.SysRegisterService;
import top.iotos.system.service.ISysConfigService;

/**
 * 注册验证
 * 
 * @author iotos.top
 */
@RestController
public class SysRegisterController extends MyBaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        return error("当前系统没有开启注册功能！");
       /* String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);*/
    }
}
