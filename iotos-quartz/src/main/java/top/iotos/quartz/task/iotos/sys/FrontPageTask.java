package top.iotos.quartz.task.iotos.sys;


import org.springframework.stereotype.Component;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 初始化 关于 数据库的表
 */

@Component("frontPageTask")
public class FrontPageTask {

    @Resource
    private MQAide mQAide;



    public void generate(Integer dept_id)
    {
        String queueName = "task_frontPageGenerate_queue";
        String routingKey = "task.frontPageGenerate.queue";
        String exchangeName = "task_exchange";//路由

        String record_date =  VeDate.getBeforeAfterDate(VeDate.getStringDateShort(),-1);//昨天
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("dept_id",dept_id);
        sendMap.put("record_date",record_date);
        mQAide.send(exchangeName, routingKey, 30, sendMap);
    }




}
