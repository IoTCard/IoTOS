package top.iotos.web.controller.iotos.im;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.iotos.common.core.domain.entity.SysUser;
import top.iotos.common.utils.iotos.web.IoTOSTools;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.web.core.config.MyBaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 适配 IoTOS-IM 登录校验信息 等操作
 * @author iotos.top
 */
@RestController
@RequestMapping("/iotos/im")
public class IMController extends MyBaseController
{

    @Resource
    private IoTOSTools ioTOSTools;
    @Resource
    private MQAide mQAide;


    @PostMapping(value = "/imLogin")
    public String  download(@RequestBody String pwdStr, HttpServletResponse response,HttpServletRequest request) {
        String ip = getIP();
        if("127.0.0.1".equals(ip) || !ioTOSTools.overclock(ip,"imLogin",20)){
            try {
                HashMap<String, Object> parameter = getParameter(pwdStr);
                if (parameter.get("token") != null) {
                    String token = parameter.get("token").toString();
                    SysUser user = getUser(token);
                    if (user != null && user.getStatus().equals("0") && user.getDelFlag().equals("0")) {//状态 正常 未删除

                        //抄送到 登录通知队列
                        try {
                            String queueName = "admin_imLoginNotify_queue";
                            String routingKey = "admin.imLoginNotify.queue";
                            String exchangeName = "admin_exchange";//路由
                            Map<String, Object> sendMap = new HashMap<>();
                            sendMap.put("user",user);
                            mQAide.send(exchangeName, routingKey, 30, sendMap);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        return RetunnSuccess(user,token,null);
                    }
                } else {
                    logger.info("<br/> pwdStr = {} ip =  {} token 为空 ",pwdStr,ip);
                }
            } catch (Exception e) {
                logger.error("<br/> /iotos/im/imLogin  <br/>  <br/> ip = {} <br/> e = {} <br/>", ip, e.getCause().toString());
            }
        }else {
            logger.error("<br/> /iotos/im/imLogin  <br/>  <br/> ip = {} <br/> e = {} <br/>", ip,"访问超频！！");
        }
        return RetunnError(null);
    }







}