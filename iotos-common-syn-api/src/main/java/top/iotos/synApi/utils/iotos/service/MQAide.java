package top.iotos.synApi.utils.iotos.service;


import com.alibaba.fastjson2.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import top.iotos.synApi.utils.iotos.web.AesEncryptUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ 辅助类
 */

@Component
public class MQAide {



    @Resource
    private RabbitTemplate rabbitTemplate;

    public boolean send(String exchangeName,String routingKey,int eMin,Map<String, Object> sendMap){
        boolean bool = false;
        try {
            String pwdStr = AesEncryptUtil.encrypt(JSON.toJSONString(sendMap));//加密
            rabbitTemplate.convertAndSend(exchangeName, routingKey, pwdStr, message -> {
                message.getMessageProperties().setExpiration("" + (eMin * 1000 * 60));// 设置消息过期时间 X 分钟 过期
                return message;
            });
            bool = true;
        } catch (Exception e) {
            System.out.println("MQAide.send" + e.getMessage());
        }
        return bool;
    }


    /**
     * 解密
     * @param pwdStr
     * @return
     */
    public static HashMap<String, Object> getParameter(String pwdStr){
        HashMap<String, Object> parameter = null;
        if (pwdStr != null) {
            pwdStr = pwdStr.replace("%2F", "/");//转义 /
            pwdStr = pwdStr.replace(" ", "+");//转义 [ ]  》 +
        }
        try {
            pwdStr = AesEncryptUtil.desEncrypt(pwdStr);
        }catch (Exception e){
            System.err.println("MQAide getParameter 异常 pwdStr "+pwdStr+"  <br/>  e:{} "+e.getMessage());
        }
        parameter = JSON.parseObject(pwdStr);
        return parameter;
    }


}
