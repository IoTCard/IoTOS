package top.iotos.quartz.task.iotos.card.channe.statistics;


import org.springframework.stereotype.Component;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//通道 统计任务
@Component("statisticsTask")
public class StatisticsTask {

    @Resource
    private MQAide mQAide;



    public void cardCount(){
        String exchangeName = "task_exchange";
        String queueName  = "task_cardCount_queue";
        String routingKey = "task.cardCount.queue";
        Map<String, Object> sendMap = new HashMap<>();
        mQAide.send(exchangeName, routingKey, 30, sendMap);
    }

    public void sumUsed(){
        String exchangeName = "task_exchange";
        String queueName  = "task_cardSum_queue";
        String routingKey = "task.cardSum.queue";
        Map<String, Object> sendMap = new HashMap<>();
        mQAide.send(exchangeName, routingKey, 30, sendMap);
    }




}
