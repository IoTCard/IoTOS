package top.iotos.consumer.task.card.synData.oneLink;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.common.mapper.mysql.card.synData.UpstreamCardMapper;
import top.iotos.common.mapper.mysql.card.synData.oneLink.Ecv5GroupMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.common.utils.iotos.service.PageUtil;
import top.iotos.common.utils.iotos.web.IoTOSTools;
import top.iotos.synApi.upstreamApi.chinaMobile.oneLink.ecV5.InquireEcV5;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 同步上游卡号 消费者
 */
@Slf4j
@Component
public class SynEcV5MsisdnMQ {

    @Resource
    private UpstreamCardMapper upstreamCardMapper;
    @Resource
    private Ecv5GroupMapper ecv5GroupMapper;
    @Resource
    private MQAide mQAide;
    @Resource
    private RedisCache redisCache;

    @RabbitHandler
    @RabbitListener(queues = "task_synEcV5Msisdn_queue")
    public void synEcV5Msisdn(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_task_synEcV5Msisdn_queue")
    public void dlxSynCard(String msg) {
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
            log.error(">>错误 - 同步上游卡号 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(Map<String, Object> map){

        String group_id = map.get("group_id").toString();
        Map<String, Object> channel = (Map<String, Object>) map.get("channel");

        Map<String, Object> initMap = new HashMap<>();
        String template = channel.get("template").toString();
        initMap.put("url",channel.get("url"));
        initMap.put("parameter_one",channel.get("parameter_one"));
        initMap.put("parameter_tow",channel.get("parameter_tow"));
        initMap.put("parameter_three",channel.get("parameter_three"));
        InquireEcV5 inquire = new InquireEcV5(initMap,template);
        int startNum = 1,pageSize = 50;
        Integer totalCount = null;
        //获取总数进行请求
        String jsonStr = inquire.queryGroupMember(group_id,""+startNum,""+pageSize);
        if(jsonStr!=null && jsonStr.length()>0){
            Map<String, Object> obj = JSON.parseObject(jsonStr);
            String status = obj.get("status").toString();
            if(status.equals("0")){
                Map<String,Object> index1 = ((List<Map<String, Object>>) obj.get("result")).get(0);
                totalCount = index1.get("totalCount")!=null?Integer.parseInt(index1.get("totalCount").toString()):totalCount;
            }
        }

        if(totalCount!=null && totalCount>0){
            PageUtil Pu = new PageUtil(totalCount, ""+startNum,""+pageSize);//初始化分页工具类
            int pageCount = Pu.getPageCount();
            for (int i = 0; i < pageCount; i++) {
                Map<String,Object> pMap = new HashMap<>();
                pMap.put("groupId",group_id);
                pMap.put("startNum",startNum);
                pMap.put("pageSize",pageSize);
                try {
                    //发送 同步分组下 MSISDN 队列任务
                    String queueName =  "task_synEcV5MsisdnExecution_queue";
                    String routingKey = "task.synEcV5MsisdnExecution.queue";
                    String exchangeName = "task_exchange";//路由
                    Map<String, Object> sendMap = new HashMap<>();
                    sendMap.put("initMap",initMap);
                    sendMap.put("pMap",pMap);
                    sendMap.put("channel",channel);
                    mQAide.send(exchangeName, routingKey, 30, sendMap);
                }catch (Exception e){
                    log.error("task_synEcV5Msisdn_queue send 异常 {}",e.getMessage());
                }

                startNum+=1;//翻页
            }

        }
    }


    @RabbitHandler
    @RabbitListener(queues = "task_synEcV5MsisdnExecution_queue",containerFactory = "customContainerFactory")
    public void synExecute(String msg) {
        turnThePage(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_task_synEcV5MsisdnExecution_queue",containerFactory = "customContainerFactory")
    public void dlxSynExecute(String msg) {
        turnThePage(msg);
    }

    /**
     * 翻页执行开始
     * @param msg
     */
    private void turnThePage(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            turnThePageExecute(map);
        } catch (Exception e) {
            log.error(">>错误 - 翻页执行开始 消费者:{}<<", e.getMessage());
        }
    }


    public void turnThePageExecute(Map<String, Object> map){

        Map<String, Object> initMap = (Map<String, Object>) map.get("initMap");
        Map<String, Object> pMap = (Map<String, Object>) map.get("pMap");
        Map<String, Object> channel = (Map<String, Object>) map.get("channel");
        String template = channel.get("template").toString();
        InquireEcV5 inquire = new InquireEcV5(initMap,template);

        String groupId = pMap.get("groupId").toString();
        String startNum = pMap.get("startNum").toString();
        String pageSize = pMap.get("pageSize").toString();
        String jsonStr = inquire.queryGroupMember(groupId,startNum,pageSize);
        if(jsonStr!=null && jsonStr.length()>0){
            //System.out.println(jsonStr);
            Map<String, Object> obj = JSON.parseObject(jsonStr);
            String status = obj.get("status").toString();
            if(status.equals("0")){
                Map<String,Object> index1 = ((List<Map<String, Object>>) obj.get("result")).get(0);
                Integer totalCount = index1.get("totalCount")!=null?Integer.parseInt(index1.get("totalCount").toString()):0;
                if(index1.get("memberinfoList")!=null){
                    List<Map<String, Object>> memberinfoList =  (List<Map<String, Object>>) index1.get("memberinfoList");
                    storageDb(memberinfoList,channel,groupId,initMap,totalCount);
                }
            }
        }

    }







    /**
     * 储存数据到 数据库中 [存在时修改，不存在时新增]
     * @param addList
     */
    public void storageDb(List<Map<String, Object>> addList,Map<String, Object> channel,String groupId,Map<String, Object> initMap,Integer totalCount){


        String channel_id = channel.get("c_no").toString();
        for (int i = 0; i < addList.size(); i++) {
            Map<String, Object> map = addList.get(i);
            Map<String, Object> addMap = new HashMap<>();
            addMap.put("channel_id",channel_id);
            addMap.put("group_id",groupId);
            addMap.put("msisdn",map.get("memberNum"));
            addMap.put("return_data",JSON.toJSONString(map));
            Map<String, Object> dbMap = upstreamCardMapper.find(addMap);
            if(dbMap!=null && dbMap.get("id")!=null){
                addMap.put("id",dbMap.get("id"));
                upstreamCardMapper.update(addMap);
            }else {
                try {
                    upstreamCardMapper.save(addMap);
                }catch (Exception e){
                    log.error("e {}",e.getMessage());
                }
            }

            if(totalCount>0){//修改分组上游 资费组下卡总数
                Map<String, Object> dbGroupMap = ecv5GroupMapper.find(addMap);
                if(dbGroupMap!=null && dbGroupMap.get("id")!=null){//
                    Map<String, Object> updMap = new HashMap<>();
                    updMap.put("id",dbGroupMap.get("id"));
                    updMap.put("up_total_count",totalCount);
                    updMap.put("channel_id",channel_id);
                    ecv5GroupMapper.update(updMap);
                }
            }


            try {
                //发送 MSISDN 获取卡号信息 队列任务
                String queueName =  "task_synEcV5MsisdnGetInfo_queue";
                String routingKey = "task.synEcV5MsisdnGetInfo.queue";
                String exchangeName = "task_exchange";//路由
                Map<String, Object> sendMap = new HashMap<>();
                sendMap.put("initMap",initMap);
                sendMap.put("msisdn",addMap.get("msisdn"));
                sendMap.put("id",addMap.get("id"));
                sendMap.put("channel",channel);
                mQAide.send(exchangeName, routingKey, 30, sendMap);
            }catch (Exception e){
                log.error("task_synEcV5Msisdn_queue send 异常 {}",e.getMessage());
            }

        }

    }



    @RabbitHandler
    @RabbitListener(queues = "task_synEcV5MsisdnGetInfo_queue",containerFactory = "customContainerFactory")
    public void msisdnGetInfo(String msg) {
        getInfoCommon(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_task_synEcV5MsisdnGetInfo_queue",containerFactory = "customContainerFactory")
    public void dlxMsisdnGetInfo(String msg) {
        getInfoCommon(msg);
    }


    private void getInfoCommon(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            String msisdn = map.get("msisdn").toString();
            String prefix = "task_synEcV5MsisdnGetInfo_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            Object  wExecute = redisCache.getCacheObject(prefix+":"+ msisdn);
            if(wExecute==null){
                redisCache.setCacheObject(prefix+":"+ msisdn, map, 10, TimeUnit.SECONDS);//10 秒缓存 避免 重复消费
                getInfoExecute(map);
            }
        } catch (Exception e) {
            log.error(">>错误 - MSISDN 获取卡信息 消费者:{}<<", e.getMessage());
        }
    }


    public void getInfoExecute(Map<String, Object> map){

        Map<String, Object> initMap = (Map<String, Object>) map.get("initMap");
        Map<String, Object> channel = (Map<String, Object>) map.get("channel");
        String template = channel.get("template").toString();

        String msisdn = map.get("msisdn").toString();

        String id = map.get("id").toString();
        String channel_id = channel.get("c_no").toString();

        InquireEcV5 inquire = new InquireEcV5(initMap,template);
        String jsonStr = inquire.querySimBasicInfo("msisdn",msisdn);
        if(jsonStr!=null && jsonStr.length()>0){
            //System.out.println(jsonStr);
            Map<String, Object> obj = JSON.parseObject(jsonStr);
            String status = obj.get("status").toString();
            if(status.equals("0")){
                Map<String,Object> index1 = ((List<Map<String, Object>>) obj.get("result")).get(0);
                    Map<String, Object> updMap = new HashMap<>();
                    updMap.put("return_data",jsonStr);
                    updMap.put("id",id);
                    updMap.put("channel_id",channel_id);
                    updMap = IoTOSTools.getAppendMapData(updMap,index1,"imsi:imsi","iccid:iccid","activeDate:activate_date","openDate:open_date","remark:remark");//获取数据写入修改
                    upstreamCardMapper.update(updMap);
            }
        }

    }






}


