package top.iotos.consumer.task.card.synData;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.mapper.mysql.card.synData.UpstreamCardMapper;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.utils.iotos.common.ListCompare;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上游卡号 对比差异 标记 消费者
 */
@Slf4j
@Component
public class SynUpstreamCardMQ {

    @Resource
    private UpstreamCardMapper upstreamCardMapper;
    @Resource
    private CardInfoMapper cardInfoMapper;



    @RabbitHandler
    @RabbitListener(queues = "task_contrastCardDifference_queue")
    public void contrastCardDifference(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_task_contrastCardDifference_queue")
    public void dlxContrastCardDifference(String msg) {
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
            log.error(">>错误 - 上游卡号 对比差异 标记 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(Map<String, Object> map){

        Map<String, Object> findMap = new HashMap<>();
        String channel_id = map.get("c_no").toString();
        findMap.put("channel_id",channel_id);
        findMap.put("iccidNotNull","1");//iccid 不为空
        List<Map<String, Object>> sList = cardInfoMapper.findSynced(findMap);//已同步卡号
        String endDate = VeDate.getStringDateShort();
        String startDate =  VeDate.getBeforeAfterDate(endDate,-4);//获取近期5天内的数据进行同步
        findMap.put("dateType","last_upd_time");
        findMap.put("startDate",startDate);
        findMap.put("endDate",endDate);
        findMap.put("w_new","0");//不是 新数据 标记卡号

        List<Map<String, Object>> addList = new ArrayList<>();
        List<Map<String, Object>> updList = new ArrayList<>();
        String sync_data_type = map.get("sync_data_type")!=null ?map.get("sync_data_type").toString():null;//'上游返回的数据-同步类型'

        List<Map<String, Object>> uList = upstreamCardMapper.getList(findMap);//去除已同步卡号外 需要同步的新数据 进行同步导入
        if (sList != null && sList.size() > 0) {

            if(uList != null && uList.size() > 0) {
                List<String> msisdnList = new ArrayList<>();
                for (int j = 0; j < sList.size(); j++) {
                    Map<String, Object> obj = sList.get(j);
                    String msisdn = obj.get("msisdn")!=null?obj.get("msisdn").toString():null;
                    if(msisdn!=null){
                        msisdnList.add(msisdn);
                    }
                }
                Map<String, Object> getNotRepeatingMap_DB = ListCompare.getNotRepeating(uList, msisdnList, "msisdn");//获取 筛选不重复的某列值 和 重复的
                uList = (List<Map<String, Object>>) getNotRepeatingMap_DB.get("Repeatlist");//更新 数据
                addList.addAll(uList);
                List<Map<String, Object>> listDifferent = (List<Map<String, Object>>) getNotRepeatingMap_DB.get("Rlist");//更新 设置数据
                //找出与数据库已存在 相同 msisdn 进行对比 iccid 是否一致 不一致的放入修改 队列
                if (listDifferent.size() > 0) {
                    for (int j = 0; j < listDifferent.size(); j++) {
                        Map<String, Object>  differentMap = listDifferent.get(j);
                        String msisdn = differentMap.get("msisdn")!=null?differentMap.get("msisdn").toString():null;
                        String iccid = differentMap.get("iccid")!=null?differentMap.get("iccid").toString():null;
                        if(msisdn!=null && iccid!=null){
                            for (int k = 0; k < sList.size(); k++) {
                                Map<String, Object>  exMap = sList.get(k);
                                String ex_msisdn = exMap.get("msisdn")!=null?exMap.get("msisdn").toString():null;
                                String ex_iccid = exMap.get("iccid")!=null?exMap.get("iccid").toString():null;
                                //开始对比
                                if(ex_msisdn!=null && ex_iccid!=null) {
                                    boolean bool_msisdn = msisdn.equals(ex_msisdn);
                                    boolean bool_iccid = iccid.equals(ex_iccid);
                                    if(bool_msisdn){
                                        if(!bool_iccid){
                                            //准备放入修改数组 ；1. 判断 同步类型 [不存在时更新 0 ] [覆盖式更新 1 ]
                                            Map<String,Object> upd_Map = new HashMap<>();
                                            if(sync_data_type.equals("1")){
                                                upd_Map.put("msisdn",msisdn);
                                                updList.add(upd_Map);//需要修改 updList 的数组
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else {
            if(uList!=null && uList.size()>0){//卡号都不在 卡列表中
                addList.addAll(uList);
            }else{
                log.info(" ==== {} [近期没有需要同步的卡号数据] ==== ", JSON.toJSONString(map));
            }
        }
        if(addList!=null && addList.size()>0){
            Map<String,Object> upd_Map = new HashMap<>();
            upd_Map.put("w_new","1");
            upd_Map.put("updList",addList);
            int upd_count = upstreamCardMapper.updateBatch(upd_Map);
            log.info(" ==== {} [w_new = 1 ] 卡数量 {} 成功数量 {}", JSON.toJSONString(map),addList.size(),upd_count);
        }
        if(updList!=null && updList.size()>0){
            Map<String,Object> upd_Map = new HashMap<>();
            upd_Map.put("inconsistent_iccid","1");
            upd_Map.put("updList",updList);
            int upd_count = upstreamCardMapper.updateBatch(upd_Map);
            log.info(" ==== {} [inconsistent_iccid = 1 ] 卡数量 {} 成功数量 {}", JSON.toJSONString(map),updList.size(),upd_count);
        }


    }



}
