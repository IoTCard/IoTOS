package top.iotos.consumer.task.clearUp;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.mapper.mysql.card.synData.UpstreamCardMapper;
import top.iotos.common.mapper.mysql.card.synData.oneLink.Ecv5GroupMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 上游已经同步过来的卡号清理 消费者
 */
@Slf4j
@Component
public class UpstreamCardTaskMQ {

    @Resource
    private UpstreamCardMapper upstreamCardMapper;
    @Resource
    private Ecv5GroupMapper ecv5GroupMapper;


    @RabbitHandler
    @RabbitListener(queues = "task_upstreamCardClearUp_queue")
    public void upstreamCardClearUp(String msg) {
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
            log.error(">>错误 - 上游已经同步过来的卡号清理 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(Map<String, Object> map){

        //直接删除表格数据
        int uCount = upstreamCardMapper.deleteAll(map);//直接清除
        int v5DCount = ecv5GroupMapper.deleteAll(map);//直接清除
        log.info("upstreamCardClearUp   uCount {} | v5DCount {}",uCount,v5DCount);
    }


}
