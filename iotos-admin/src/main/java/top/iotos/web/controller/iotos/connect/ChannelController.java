package top.iotos.web.controller.iotos.connect;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.iotos.common.annotation.Log;
import top.iotos.common.enums.BusinessType;
import top.iotos.system.service.iotos.connect.IChannelService;
import top.iotos.web.core.config.MyBaseController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 通道
 * @author iotos.top
 */
@RestController
@RequestMapping("/iotos/channel")
public class ChannelController extends MyBaseController
{
    @Resource
    private IChannelService iChannelSercice;


    @PreAuthorize("@ss.hasPermi('iotos:channel:list')")
    @PostMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
    public String list(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameter(pwdStr);
            return RetunnSuccess(iChannelSercice.getList(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/channel/list  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }




    @PreAuthorize("@ss.hasPermi('iotos:channel:find')")
    @PostMapping(value = "/find", produces = { "application/json;charset=utf-8" })
    public String find(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameter(pwdStr);
            return RetunnSuccess(iChannelSercice.find(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/channel/find  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }




    @Log(title = "通道", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('iotos:channel:add')")
    @PostMapping(value = "/save", produces = { "application/json;charset=utf-8" })
    public String save(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameter(pwdStr);
            parameter.put("dept_id",getDeptId());
            return RetunnIfStr(iChannelSercice.save(parameter),null,null,null);
        }catch (Exception e){
            logger.error("<br/> /iotos/channel/save  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }




    @Log(title = "通道", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('iotos:channel:edit')")
    @PostMapping(value = "/edit", produces = { "application/json;charset=utf-8" })
    public String edit(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameter(pwdStr);
            parameter.put("dept_id",getDeptId());
            return RetunnIfStr(iChannelSercice.update(parameter),null,null,null);
        }catch (Exception e){
            logger.error("<br/> /iotos/channel/edit  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }

   
    @Log(title = "通道", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('iotos:channel:del')")
    @PostMapping(value = "/delete", produces = { "application/json;charset=utf-8" })
    public String delete(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameter(pwdStr);
            parameter.put("dept_id",getDeptId());
            return RetunnIfStr(iChannelSercice.delete(parameter),null,null,null);
        }catch (Exception e){
            logger.error("<br/> /iotos/channel/delRoute  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 获取通道简要信息
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:card:list')")
    @PostMapping(value = "/getNameOpen", produces = { "application/json;charset=utf-8" })
    public String getNameOpen(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameter(pwdStr);
            boolean headquarters = getDeptID().equals("100")?true:false;
            return RetunnSuccess(iChannelSercice.getNameOpen(parameter,headquarters),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/channel/getNameOpen  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }


    /**
     * 内部获取通道简要信息
     * @param pwdStr
     * @return
     */
    @PreAuthorize("@ss.hasPermi('iotos:sys:headquarters')")
    @PostMapping(value = "/getName", produces = { "application/json;charset=utf-8" })
    public String getName(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameter(pwdStr);
            return RetunnSuccess(iChannelSercice.getName(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/channel/getName  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return RetunnError(null);
    }


}