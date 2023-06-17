package top.iotos.consumer.task.card.synData.oneLink;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.mapper.mysql.card.synData.oneLink.Ecv5GroupMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.common.utils.iotos.service.PageUtil;
import top.iotos.synApi.upstreamApi.chinaMobile.oneLink.ecV5.InquireEcV5;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * EcV5 同步上游卡号 消费者
 */
@Slf4j
@Component
public class SynEcV5CardMQ {

    @Resource
    private Ecv5GroupMapper ecv5GroupMapper;
    @Resource
    private MQAide mQAide;

    @RabbitHandler
    @RabbitListener(queues = "task_upstream_oneLink_ECV5_queue")
    public void synCardECV5(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_task_upstream_oneLink_ECV5_queue")
    public void dlxSynCardECV5(String msg) {
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

        Map<String, Object> initMap = new HashMap<>();
        String template = map.get("template").toString();
        initMap.put("url",map.get("url"));
        initMap.put("parameter_one",map.get("parameter_one"));
        initMap.put("parameter_tow",map.get("parameter_tow"));
        initMap.put("parameter_three",map.get("parameter_three"));
        InquireEcV5 inquire = new InquireEcV5(initMap,template);
        int startNum = 1,pageSize = 50;
        Integer totalCount = null;
        //获取总数进行请求
        String jsonStr = inquire.queryGroupInfo(""+startNum,""+pageSize);
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
            List<Map<String, Object>> addList = new ArrayList<>();

                List<Map<String, Object>> groupList = null;
                for (int i = 0; i < pageCount; i++) {
                    String result = null;
                    if(i==0 && pageCount==1){
                        result = jsonStr;
                    }else {
                        result = inquire.queryGroupInfo(""+startNum,""+pageSize);
                    }
                    if (result != null) {
                        Map<String, Object> obj = JSON.parseObject(result);
                        String status = obj.get("status").toString();
                        if(status.equals("0")){
                            Map<String,Object> index1 = ((List<Map<String, Object>>) obj.get("result")).get(0);
                            if(index1.get("groupList")!=null){
                                groupList =  (List<Map<String, Object>>) index1.get("groupList");
                                addList.addAll(groupList);
                            }
                        }
                    }
                    if(i==pageCount-1){
                        storageDb(addList,map);
                        addList = new ArrayList<>();
                    }else if(i%10==0){
                        storageDb(addList,map);
                        addList = new ArrayList<>();
                    }
                    startNum+=1;//翻页
                }

        }
    }

    /**
     * 储存数据到 数据库中 [存在时修改，不存在时新增]
     * @param addList
     */
    public void storageDb(List<Map<String, Object>> addList,Map<String, Object> channel){


        String channel_id = channel.get("c_no").toString();
        for (int i = 0; i < addList.size(); i++) {
            Map<String, Object> map = addList.get(i);
            String groupId = map.get("groupId").toString();
            map.put("channel_id",channel_id);
            map.put("group_id",groupId);
            map.put("group_name",map.get("groupName"));
            map.put("offering_id",map.get("offeringId"));
            map.put("offering_name",map.get("offeringName"));

            Map<String, Object> dbMap = ecv5GroupMapper.find(map);
            boolean bool = false;
            if(dbMap!=null && dbMap.get("id")!=null){
                map.put("id",dbMap.get("id"));
                bool = ecv5GroupMapper.update(map)>0;
            }else {
                bool = ecv5GroupMapper.save(map)>0;
            }
            if(bool){
                try {
                    //发送 同步分组下 MSISDN 队列任务
                    String queueName = "task_synEcV5Msisdn_queue";
                    String routingKey = "task.synEcV5Msisdn.queue";
                    String exchangeName = "task_exchange";//路由
                    Map<String, Object> sendMap = new HashMap<>();
                    sendMap.put("channel",channel);
                    sendMap.put("group_id",groupId);
                    mQAide.send(exchangeName, routingKey, 30, sendMap);
                }catch (Exception e){
                    log.error("task_synEcV5Msisdn_queue send 异常 {}",e.getMessage());
                }
            }

        }
    }


}
