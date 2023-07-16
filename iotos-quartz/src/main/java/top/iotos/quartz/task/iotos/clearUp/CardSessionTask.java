package top.iotos.quartz.task.iotos.clearUp;


import org.springframework.stereotype.Component;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component("cardSessionTask")
public class CardSessionTask {

    @Resource
    private MQAide mQAide;

    public void clearUp(Integer xDaysAgo)
    {
        String queueName = "task_cardSessionClearUp_queue";
        String routingKey = "task.cardSessionClearUp.queue";
        String exchangeName = "task_exchange";//路由
        Map<String, Object> sendMap = new HashMap<>();
        String  startDate = VeDate.getNextDay(VeDate.getStringDateShort(),"-"+xDaysAgo)+" 00:00:00";
        sendMap.put("startDate",startDate);
        mQAide.send(exchangeName, routingKey, 30, sendMap);
    }




}
