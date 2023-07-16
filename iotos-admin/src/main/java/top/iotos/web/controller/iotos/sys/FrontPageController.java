package top.iotos.web.controller.iotos.sys;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.iotos.system.service.iotos.sys.IFrontPageService;
import top.iotos.web.core.config.MyBaseController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 首页查询
 * @author iotos.top
 */
@RestController
@RequestMapping("/iotos/frontPage")
public class FrontPageController extends MyBaseController
{
    @Resource
    private IFrontPageService iFrontPageService;


    @PreAuthorize("@ss.hasPermi('iotos:frontPage:find')")
    @PostMapping(value = "/find", produces = { "application/json;charset=UTF-8" })
    public String find(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameter(pwdStr);
            parameter.put("dept_id",getDeptID());
            return RetunnSuccess(iFrontPageService.find(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/frontPage/find  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }







}