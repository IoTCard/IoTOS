package top.iotos.quartz.task.iotos.card.channe.polling;


import org.springframework.stereotype.Component;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("pollingStartTask")
public class PollingStartTask {

    @Resource
    private MQAide mQAide;
    @Resource
    private CardInfoMapper cardInfoMapper;




    public void run(){
        String exchangeName = "polling_exchange";//路由
        String queueName  = "polling_cardInfo_queue";
        String routingKey = "polling.cardInfo.queue";
        Map<String, Object> sendMap = new HashMap<>();
        mQAide.send(exchangeName, routingKey, 30, sendMap);
    }


    /**
     * 轮询补偿
     * @param status_show_id
     * @param xDaysAgo
     * @param pageSize
     * @param functionName
     */
    public void compensation(String status_show_id,String xDaysAgo,Integer pageSize,String functionName){
        String exchangeName = "polling_exchange";
        String queueName  = "polling_compensation_queue";
        String routingKey = "polling.compensation.queue";

        pageSize = pageSize!=null?pageSize:300;//默认 每个通道发送数据300 [任务按每分钟触发一次] 1.避免单个账号接口调用每分钟频次问题 2.平均使得每个账号都能得到补偿同步
        Map<String, Object> sendMap = new HashMap<>();
        if(status_show_id!=null){
            List<String> statusShowIdList = new ArrayList<>();
            if(status_show_id.indexOf(",")!=-1){
                String []strArr = status_show_id.split(",");
                for (int i = 0; i <strArr.length ; i++) {
                    statusShowIdList.add(strArr[i]);
                }
            }else {
                statusShowIdList.add(status_show_id);
            }
            sendMap.put("statusShowIdList",statusShowIdList);
        }

        String  startDate = VeDate.getNextDay(VeDate.getStringDateShort(),"-"+xDaysAgo)+" 00:00:00";

        sendMap.put("startDate",startDate);
        sendMap.put("starRow",0);
        sendMap.put("pageSize",pageSize);
        sendMap.put("functionName",functionName);//函数名

        mQAide.send(exchangeName, routingKey, 30, sendMap);
    }

    /**
     * 初始化补偿 记录时间
     */
    public void initTime() {
        Map<String,Object> updMap = new HashMap<>();
        String yyyy = VeDate.getStringDateShort().split("-")[0];
        updMap.put("traffic_sync_time",yyyy+"-01-01 00:00:00");
        int updTrafficCount = cardInfoMapper.initTime(updMap);
        updMap.remove("traffic_sync_time");
        updMap.put("state_sync_time",yyyy+"-01-01 00:00:00");
        int updStateCount = cardInfoMapper.initTime(updMap);
        System.out.println(" initTime updTrafficCount = {"+updTrafficCount+"} updStateCount = {"+updStateCount+"}");
    }



}
