package top.iotos.web.controller.iotos.connect;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.iotos.system.service.iotos.card.IUsedRecordService;
import top.iotos.web.core.config.MyBaseController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 用量记录
 * @author iotos.top
 */
@RestController
@RequestMapping("/iotos/usedRecord")
public class UsedRecordController extends MyBaseController
{
    @Resource
    private IUsedRecordService iUsedRecordService;


    @PreAuthorize("@ss.hasPermi('iotos:usedRecord:list')")
    @PostMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
    public String list(@RequestBody String pwdStr)
    {
        try {
            HashMap<String, Object> parameter =  getParameterAddPermissions(pwdStr);
            return retunnSuccess(iUsedRecordService.getList(parameter),null);
        }catch (Exception e){
            logger.error("<br/> /iotos/usedRecord/list  <br/> pwdStr = {} <br/> ip = {} <br/> e = {} <br/>",pwdStr,getIP(),e.getCause().toString());
        }
        return retunnError(null);
    }







}