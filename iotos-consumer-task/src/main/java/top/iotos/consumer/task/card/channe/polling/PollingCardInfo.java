package top.iotos.consumer.task.card.channe.polling;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.sync.ApiProcessor;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class PollingCardInfo {

    @Resource
    private ApiProcessor apiProcessor;
    @Resource
    private RedisCache redisCache;



    @RabbitHandler
    @RabbitListener(queues = "polling_synInfo_queue",containerFactory = "customContainerFactory")
    public void pollingStart(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_polling_synInfo_queue",containerFactory = "customContainerFactory")
    public void dlxPollingStart(String msg) {
        execute(msg);
    }


    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            if(map!=null){
                execution(map);
            }
        } catch (Exception e) {
            log.error(">>错误 - 轮询卡信息 消费者:{}<<", e.getMessage());
        }
    }

    public void execution(Map<String, Object> map){
        String iccid = map.get("iccid").toString();
        Map<String, Object> channel = (Map<String, Object>) map.get("channel");
        apiProcessor.synInfo(iccid,channel);


    }



    //========================[轮询补偿执行 队列 ↓ ]===


    @RabbitHandler
    @RabbitListener(queues = "polling_compensationExecute_queue",containerFactory = "customContainerFactory")
    public void compensationExecute(String msg) {
        executeCompensation(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_polling_compensationExecute_queue",containerFactory = "customContainerFactory")
    public void dlxCompensationExecute(String msg) {
        executeCompensation(msg);
    }


    private void executeCompensation(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            if(map!=null){
                String iccid = map.get("iccid").toString();
                String functionName = map.get("functionName").toString();
                String prefix = "polling_compensationExecute_queue";
                //执行前判断 redis 是否存在 执行数据 存在时 不执行
                String key_val = prefix+":"+ iccid+":"+functionName;
                Object  wExecute = redisCache.getCacheObject(key_val);
                if(wExecute==null){
                    redisCache.setCacheObject(key_val, msg, 3, TimeUnit.SECONDS);//3 秒缓存 避免 重复消费
                    executionCompensation(map);
                }

            }
        } catch (Exception e) {
            log.error(">>错误 - 轮询补偿执行队列 消费者:{}<<", e.getMessage());
        }
    }

    public void executionCompensation(Map<String, Object> map){
        String iccid = map.get("iccid").toString();
        String functionName = map.get("functionName").toString();
        Map<String, Object> channel = (Map<String, Object>) map.get("channel");
        if(functionName.equalsIgnoreCase("synFlow")){
            apiProcessor.synFlow(iccid,channel);
        } else if (functionName.equalsIgnoreCase("synStatus")) {
            apiProcessor.synStatus(iccid,channel);
        }
    }
    

}
