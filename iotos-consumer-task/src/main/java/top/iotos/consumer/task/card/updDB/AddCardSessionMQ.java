package top.iotos.consumer.task.card.updDB;


import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.synApi.mapper.mysql.card.CardSessionMapper;
import top.iotos.synApi.mapper.mysql.card.OneLinkEcV5SessionMapper;
import top.iotos.synApi.utils.iotos.common.GetMapUtil;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 增加卡号会话信息 消费者
 */
@Slf4j
@Component
public class AddCardSessionMQ {

    @Resource
    private CardSessionMapper cardSessionMapper;
    @Resource
    private OneLinkEcV5SessionMapper oneLinkEcV5SessionMapper;

    @RabbitHandler
    @RabbitListener(queues = "polling_addSimSession_queue",containerFactory = "customContainerFactory")
    public void updCard(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_polling_addSimSession_queue",containerFactory = "customContainerFactory")
    public void dlxUpdCard(String msg) {
        execute(msg);
    }


    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            execution(map);
        } catch (Exception e) {
            log.error(">>错误 - 修改卡号信息 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(Map<String, Object> map){
        if(map.get("addMap")!=null){
            Map<String, Object> addMap = (Map<String, Object>) map.get("addMap");
            String rStr = addMap.get("return_data").toString();
            String template = addMap.get("template").toString();

            Map<String, Object> return_data = null;
            if(rStr!=null){
                return_data = JSON.parseObject(rStr);
            }

            try {
               int addCount = cardSessionMapper.save(addMap);
                int addDetailsCount = 0;
                if(addCount>0){
                   return_data.put("s_id",addMap.get("id"));
                    return_data.put("iccid",addMap.get("iccid"));
                    return_data.put("dept_id",addMap.get("dept_id"));
                   if(return_data.get("ip")!=null){
                       if(template.equalsIgnoreCase("oneLink_ECV5")){
                           return_data = GetMapUtil.getMap(return_data,"apn_id",null);
                           return_data = GetMapUtil.getMap(return_data,"status",null);
                           return_data = GetMapUtil.getMap(return_data,"ip",null);
                           return_data = GetMapUtil.getMap(return_data,"ipv6_prefix",null);
                           return_data = GetMapUtil.getMap(return_data,"ipv6",null);
                           return_data = GetMapUtil.getMap(return_data,"create_date",null);
                           return_data = GetMapUtil.getMap(return_data,"rat",null);

                           addDetailsCount = oneLinkEcV5SessionMapper.save(return_data);
                       }
                   }
               }
               log.info("polling_addSimSession_queue addCount = {} ， addDetailsCount = {}",addCount,addDetailsCount);
            }catch (Exception e){
                log.error("polling_addSimSession_queue 异常 {}",e.getMessage());
            }
        }
    }


}
