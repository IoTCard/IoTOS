package top.iotos.synApi.utils.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供发送 同步时常用 队列发送
 */
@Component
public class MQSyncSend {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MQAide mQAide;

    /**
     * 发送 到用量计算同步队列 下一步逻辑处理
     * @param cardMap
     * @param used
     */
    public void sendUsedResultProcessing(Map<String, Object> cardMap, double used,String executionDate,String starting_time){
        try {
            String exchangeName = "polling_exchange";//路由
            String queueName = "polling_usedResultProcessing_queue";
            String routingKey = "polling.usedResultProcessing.queue";
            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("cardMap",cardMap);
            sendMap.put("used",used);
            sendMap.put("executionDate",executionDate);
            sendMap.put("starting_time",starting_time);
            mQAide.send(exchangeName, routingKey, 30, sendMap);
        }catch (Exception e){
            logger.error("seedUsedResultProcessing send 异常 {}",e.getMessage());
        }
    }


    /**
     * 批量 发送修改卡号信息队列
     * @param uMap
     */
    public void sendUpdCardBatch(Map<String, Object> uMap){
        for(String key:uMap.keySet()){
            if(uMap.get(key)!=null){
                sendUpdCard((Map<String, Object>) uMap.get(key));
            }
        }
    }





    /**
     * 发送修改卡号信息队列
     * @param updMap
     */
    public void sendUpdCard(Map<String, Object> updMap){
        try {
            String exchangeName = "polling_exchange";//路由
            String queueName = "polling_updCard_queue";
            String routingKey = "polling.updCard.queue";
            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("updMap",updMap);
            mQAide.send(exchangeName, routingKey, 30, sendMap);
        }catch (Exception e){
            logger.error("seedUpdCard send 异常 {}",e.getMessage());
        }
    }

    public void sendAddSimSession(Map<String, Object> addMap){
        try {
            String exchangeName = "polling_exchange";//路由
            String queueName = "polling_addSimSession_queue";
            String routingKey = "polling.addSimSession.queue";
            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("addMap",addMap);
            mQAide.send(exchangeName, routingKey, 30, sendMap);
        }catch (Exception e){
            logger.error("seedUpdCard send 异常 {}",e.getMessage());
        }
    }


}
