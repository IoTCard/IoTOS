package top.iotos.consumer.task.card.updDB;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.synApi.utils.iotos.common.DbFieldArr;
import top.iotos.synApi.utils.iotos.common.ListCompare;
import top.iotos.synApi.utils.iotos.service.CardUpdate;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 修改卡号信息 消费者
 */
@Slf4j
@Component
public class UpdCardMQ {


    @Resource
    private CardUpdate cardUpdate;


    @RabbitHandler
    @RabbitListener(queues = "polling_updCard_queue",containerFactory = "customContainerFactory")
    public void updCard(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_polling_updCard_queue",containerFactory = "customContainerFactory")
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
        if(map.get("updMap")!=null){
            Map<String, Object> updMap = (Map<String, Object>) map.get("updMap");
            String iccid = updMap.get("iccid").toString();
            updMap.put("batchType","1");//iccid
            updMap.put("bValue",iccid);
            updMap.remove("iccid");
            try {
               int uCCount = cardUpdate.cardUpdate(updMap);
               int uCInfoCount = cardUpdate.cardInfoUpdate(updMap);
               log.info("{} polling_updCard_queue uCCount = {} ， uCInfoCount = {}",iccid,uCCount,uCInfoCount);
            }catch (Exception e){
                log.error("polling_updCard_queue 异常 {}",e.getMessage());
            }
        }
    }


}
