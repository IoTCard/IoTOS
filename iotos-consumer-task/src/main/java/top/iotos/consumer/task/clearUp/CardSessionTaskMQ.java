package top.iotos.consumer.task.clearUp;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.synApi.mapper.mysql.card.CardSessionMapper;
import top.iotos.synApi.mapper.mysql.card.OneLinkEcV5SessionMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 卡号会话信息清理 消费者
 */
@Slf4j
@Component
public class CardSessionTaskMQ {

    @Resource
    private CardSessionMapper cardSessionMapper;
    @Resource
    private OneLinkEcV5SessionMapper oneLinkEcV5SessionMapper;


    @RabbitHandler
    @RabbitListener(queues = "task_cardSessionClearUp_queue")
    public void cardSessionClearUp(String msg) {
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
            log.error(">>错误 - 卡号会话信息清理 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(Map<String, Object> map){

        //获取快到期 任务
        int dCount = cardSessionMapper.deleteReserveDayOne(map);//主表直接清除
        int v5DCount = oneLinkEcV5SessionMapper.deleteReserveDayOne(map);//主表直接清除

        log.info("deleteSession   dCount {} | v5DCount {}",dCount,v5DCount);
    }


}
