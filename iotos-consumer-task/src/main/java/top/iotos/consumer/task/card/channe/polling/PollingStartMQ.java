package top.iotos.consumer.task.card.channe.polling;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.channel.ChannelInfoMapper;
import top.iotos.synApi.mapper.mysql.channel.ChannelMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 轮询卡信息 消费者
 */
@Slf4j
@Component
public class PollingStartMQ {

    @Resource
    private CardInfoMapper cardInfoMapper;
    @Resource
    private ChannelInfoMapper channelInfoMapper;
    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private MQAide mQAide;
    @Resource
    private RedisCache redisCache;



    @RabbitHandler
    @RabbitListener(queues = "polling_cardInfo_queue")
    public void pollingStart(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_polling_cardInfo_queue")
    public void dlxPollingStart(String msg) {
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
            log.error(">>错误 - 轮询卡信息 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(Map<String, Object> pMap){
        List<String> status = new ArrayList<>();
        status.add("0");
        Map<String, Object> map = new HashMap<>();
        map.put("status",status);
        List<Map<String, Object>> list = channelMapper.getList(map);//通道状态正常

        String exchangeName = "polling_exchange";//路由
        String queueName  = "polling_synInfo_queue";
        String routingKey = "polling.synInfo.queue";

        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> cMap = list.get(i);
                Map<String, Object> channel = channelInfoMapper.find(cMap);
                if(channel!=null){
                    List<String> channel_id = new ArrayList<>();
                    channel_id.add(channel.get("c_no").toString());
                    Map<String, Object> findMap = new HashMap<>();
                    findMap.put("channel_id",channel_id);
                    findMap.put("w_polling","1");
                    List<Map<String, Object>> cardList = cardInfoMapper.getList(findMap);

                    if(cardList!=null && cardList.size()>0){
                        Map<String, Object> sendMap = new HashMap<>();
                        sendMap.put("channel",channel);
                        for (int j = 0; j < cardList.size(); j++) {
                            Map<String, Object> card = cardList.get(j);
                            String iccid = card.get("iccid").toString();
                            sendMap.put("iccid",iccid);
                            mQAide.send(exchangeName, routingKey, 45, sendMap);
                        }
                    }
                }
            }
        }
    }


    //========================[轮询补偿 ↓ ]===

    @RabbitHandler
    @RabbitListener(queues = "polling_compensation_queue")
    public void pollingCompensation(String msg) {
        executeCompensation(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_polling_compensation_queue")
    public void dlxPollingCompensation(String msg) {
        executeCompensation(msg);
    }


    private void executeCompensation(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            executionCompensation(map);
        } catch (Exception e) {
            log.error(">>错误 - 轮询补偿 消费者:{}<<", e.getMessage());
        }
    }


    public void executionCompensation(Map<String, Object> pMap){
        List<String> status = new ArrayList<>();
        status.add("0");
        Map<String, Object> map = new HashMap<>();
        map.put("status",status);
        List<Map<String, Object>> list = channelMapper.getList(map);//通道状态正常

        String exchangeName = "polling_exchange";
        String queueName  = "polling_compensationExecute_queue";
        String routingKey = "polling.compensationExecute.queue";
        String functionName =  pMap.get("functionName").toString();
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> cMap = list.get(i);

                Map<String, Object> channel = channelInfoMapper.find(cMap);
                if(channel!=null){
                    String c_no = channel.get("c_no").toString();
                    List<String> channel_id = new ArrayList<>();
                    channel_id.add(c_no);
                    pMap.put("channel_id",channel_id);
                    pMap.put("w_polling","1");

                    String key_val = queueName+":"+ c_no+":"+functionName;
                    try {
                        Object  wExecute = redisCache.getCacheObject(key_val);
                        if(wExecute!=null) {
                            JSONArray notInIccidList =  JSON.parseArray(wExecute.toString());
                            pMap.put("notInIccidList",notInIccidList);//刚刚发送的 那组数据 本次 不再获取 【避免错误卡号数据 导致补偿不能正常运行 反复在跑错误的数据】
                        }
                    }catch (Exception e){
                        log.error("put notInIccidList 异常 {}",e.getMessage());
                    }

                    List<Map<String, Object>> cardList = cardInfoMapper.findChannelIdIccid(pMap);
                    if(cardList!=null && cardList.size()>0){
                        Map<String, Object> sendMap = new HashMap<>();
                        sendMap.put("channel",channel);
                        sendMap.put("functionName",functionName);

                        redisCache.setCacheObject(key_val, JSON.toJSONString(cardList), 60, TimeUnit.SECONDS);//90 秒缓存 记录上一次刚发送的队列

                        for (int j = 0; j < cardList.size(); j++) {
                            Map<String, Object> card = cardList.get(j);
                            String iccid = card.get("iccid").toString();
                            sendMap.put("iccid",iccid);
                            mQAide.send(exchangeName, routingKey, 45, sendMap);
                        }
                    }
                }
            }
        }
    }


}
