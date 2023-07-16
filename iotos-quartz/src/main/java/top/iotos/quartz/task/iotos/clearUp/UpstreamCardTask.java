package top.iotos.quartz.task.iotos.clearUp;


import org.springframework.stereotype.Component;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 清理 上游已经同步过来的卡号（确保上游卡号变动时更新新的卡号或已销户卡号不再同步）
 * [该定时任务不建议少于 3天触发一次，同步卡号需要时间避免未同步完数据就已经删除。]
 */
@Component("upstreamCardTask")
public class UpstreamCardTask {

    @Resource
    private MQAide mQAide;

    public void clearUp()
    {
        String queueName = "task_upstreamCardClearUp_queue";
        String routingKey = "task.upstreamCardClearUp.queue";
        String exchangeName = "task_exchange";//路由
        Map<String, Object> sendMap = new HashMap<>();
        mQAide.send(exchangeName, routingKey, 30, sendMap);
    }




}
