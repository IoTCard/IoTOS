package top.iotos.consumer.task.card.channe.statistics;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.utils.iotos.web.IoTOSTools;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.synApi.mapper.mysql.channel.ChannelMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计通道数据 消费者
 */
@Slf4j
@Component
public class StatisticsMQ {


    @Resource
    private CardMapper cardMapper;
    @Resource
    private ChannelMapper channelMapper;


    @RabbitHandler
    @RabbitListener(queues = "task_cardCount_queue")
    public void cardCount(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_task_cardCount_queue")
    public void dlxCardCount(String msg) {
        execute(msg);
    }


    @RabbitHandler
    @RabbitListener(queues = "task_cardSum_queue")
    public void cardSum(String msg) {
        executeSum(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_task_cardSum_queue")
    public void dlxCardSum(String msg) {
        executeSum(msg);
    }



    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            execution(map);
        } catch (Exception e) {
            log.error(">>错误 - 统计通道卡后数量 消费者:{}<<", e.getMessage());
        }
    }

    private void executeSum(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            executionSum(map);
        } catch (Exception e) {
            log.error(">>错误 - 统计通道卡后数量 消费者:{}<<", e.getMessage());
        }
    }

    public void execution(Map<String, Object> map){
        List<Map<String, Object>> list = cardMapper.getChannelCount(map);
        if(IoTOSTools.isNull(list)){
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> obj = list.get(i);
                Integer card_count = obj.get("count")!=null?Integer.parseInt(obj.get("count").toString()):0;
                Map<String, Object> updMap = new HashMap<>();
                updMap.put("c_no",obj.get("channel_id"));
                updMap.put("card_count",card_count);
                channelMapper.update(updMap);
            }
        }
    }


    public void executionSum(Map<String, Object> map){
        List<Map<String, Object>> list = cardMapper.getChannelSum(map);
        if(IoTOSTools.isNull(list)){
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> obj = list.get(i);
                double card_used = obj.get("sumUsed")!=null?Double.parseDouble(obj.get("sumUsed").toString()):0.00;
                double card_left = obj.get("sumLeft")!=null?Double.parseDouble(obj.get("sumLeft").toString()):0.00;
                double card_total = obj.get("sumTotal")!=null?Double.parseDouble(obj.get("sumTotal").toString()):0.00;
                Map<String, Object> updMap = new HashMap<>();
                updMap.put("c_no",obj.get("channel_id"));
                updMap.put("card_used",card_used);
                updMap.put("card_left",card_left);
                updMap.put("card_total",card_total);
                channelMapper.update(updMap);
            }
        }
    }

}
