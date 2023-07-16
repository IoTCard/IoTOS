package top.iotos.consumer.task.card.updDB;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.mapper.mysql.card.record.UsedRecordMapper;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.synApi.utils.iotos.common.Arith;
import top.iotos.synApi.utils.iotos.common.GetMapUtil;
import top.iotos.synApi.utils.iotos.service.CardUpdate;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用量处理 消费者
 */
@Slf4j
@Component
public class CardUsageProcessorMQ {

    @Resource
    private CardMapper cardMapper;
    @Resource
    private CardInfoMapper cardInfoMapper;
    @Resource
    private UsedRecordMapper usedRecordMapper;
    @Resource
    private CardUpdate cardUpdate;


    @RabbitHandler
    @RabbitListener(queues = "polling_usedResultProcessing_queue",containerFactory = "customContainerFactory")
    public void updCard(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_polling_usedResultProcessing_queue",containerFactory = "customContainerFactory")
    public void dlxUpdCard(String msg) {
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
            log.error(">>错误 - 用量处理 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(Map<String, Object> map){
        if(map.get("cardMap")!=null && map.get("used")!=null){
            Map<String, Object> cardMap = (Map<String, Object>) map.get("cardMap");
            double reveal_used = Double.parseDouble(map.get("used").toString());
            String executionDate = map.get("executionDate").toString();
            String starting_time = map.get("starting_time").toString();
            String iccid = GetMapUtil.getValueToStr(cardMap,"iccid");
            double c_total = Double.parseDouble(GetMapUtil.getValueToStr(cardMap,"c_total","0"));

            String first_time_using = GetMapUtil.getValueToStr(cardMap,"first_time_using",null);

            String tims[] = executionDate.split("-");
            String dept_id = cardMap.get("dept_id").toString();
            String yyyyMM = tims[0]+tims[1];
            try {
                //用量同步 【处理逻辑计算用量后 放入  updMap】 （目前不做逻辑处理直接同步 已用 ）


                double c_used = reveal_used;
                double c_left = Arith.sub(c_total,c_used);

                Map<String, Object> updMap = new HashMap<>();
                updMap.put("c_used",c_used);
                updMap.put("traffic_sync_time",starting_time);
                updMap.put("c_left",c_left);
                updMap.put("batchType","1");//iccid
                updMap.put("bValue",iccid);

                if(first_time_using==null){
                    updMap.put("first_time_using",starting_time);
                }
                //updMap.put("c_total",c_total);// 【后续该值 由订购有效期内套餐来获取总和】

               int uCCount = cardUpdate.cardUpdate(updMap);//同步主表信息
               int uCInfoCount =  cardUpdate.cardInfoUpdate(updMap);

               if(uCInfoCount>0){
                   //同步 日用量记录表

                   double day_used = 0.0;//天用量
                   double month_used = reveal_used;//月用量
                   double xi = 1.0; // 后续根据 资费计划 更新
                   Map<String, Object> findMap = new HashMap<>();
                   findMap.put("iccid",iccid);
                   findMap.put("record_date",executionDate);
                   findMap.put("yyyyMM",yyyyMM);
                   Map<String, Object> lastMap = usedRecordMapper.findLastDay(findMap);
                   if(lastMap!=null && lastMap.get("reveal_month_used")!=null){
                       double reveal_month_used = Double.parseDouble(lastMap.get("reveal_month_used").toString());
                       day_used = Arith.sub(reveal_used,reveal_month_used);
                   }
                   double reveal_day_used = Arith.mul(day_used,xi);//展示-天用量
                   double reveal_month_used = Arith.mul(month_used,xi);//展示-月用量

                   Map<String, Object> updUsedMap = new HashMap<>();
                   updUsedMap.put("day_used",day_used);
                   updUsedMap.put("iccid",iccid);
                   updUsedMap.put("record_date",executionDate);
                   updUsedMap.put("month_used",month_used);
                   updUsedMap.put("reveal_day_used",reveal_day_used);
                   updUsedMap.put("reveal_month_used",reveal_month_used);
                   updUsedMap.put("dept_id",dept_id);

                   Map<String, Object> fMap = usedRecordMapper.find(findMap);
                   if(fMap!=null){
                       updUsedMap.put("id",fMap.get("id"));
                       usedRecordMapper.update(updUsedMap);
                   }else {
                       usedRecordMapper.save(updUsedMap);
                   }
               }
               log.info("{} polling_usedResultProcessing_queue uCCount = {} ， uCInfoCount = {}",iccid,uCCount,uCInfoCount);
            }catch (Exception e){
                log.error("polling_usedResultProcessing_queue 异常 {}",e.getMessage());
            }
            //下方处理用量 逻辑 如 超出套餐 、已达停机阈值 、 达量停机


        }
    }


}
