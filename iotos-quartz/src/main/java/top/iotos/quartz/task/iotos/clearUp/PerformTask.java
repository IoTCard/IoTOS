package top.iotos.quartz.task.iotos.clearUp;


import org.springframework.stereotype.Component;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component("performTask")
public class PerformTask {

    @Resource
    private MQAide mQAide;

    public void clearUp()
    {
        String queueName = "task_tasksClearUp_queue";
        String routingKey = "task.tasksClearUp.queue";
        String exchangeName = "task_exchange";//路由
        Map<String, Object> sendMap = new HashMap<>();
        mQAide.send(exchangeName, routingKey, 30, sendMap);
    }




}
