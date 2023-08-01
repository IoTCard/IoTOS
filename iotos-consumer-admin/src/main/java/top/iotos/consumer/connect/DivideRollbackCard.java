package top.iotos.consumer.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.common.utils.iotos.service.PerformTaskUtil;
import top.iotos.synApi.utils.iotos.time.VeDate;
import top.iotos.common.utils.poi.WriteCSV;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 卡列表划分回滚  消费者
 */
@Slf4j
@Component
public class DivideRollbackCard {


    @Resource
    private RedisCache redisCache;
    @Resource
    private PerformTaskUtil performTaskUtil;
    @Resource
    private CardMapper cardMapper;
    @Resource
    private CardInfoMapper cardInfoMapper;

    private int outSize = 50;//每 50条数据输出一次

    /**
     * 卡列表划分
     * @param msg
     * @throws IOException
     */
    @RabbitHandler
    @RabbitListener(queues = "admin_cardDivideRollback_queue")
    public void cardDivideRollback(String msg) {
        execute(msg);
    }







    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            String t_no = map.get("t_no").toString();

            String prefix = "admin_cardDivideRollback_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            Object  wExecute = redisCache.getCacheObject(prefix+":"+ t_no);
            if(wExecute==null){
                redisCache.setCacheObject(prefix+":"+ t_no, msg, 10, TimeUnit.SECONDS);//10 秒缓存 避免 重复消费
                execution(t_no);
            }
        } catch (Exception e) {
            log.error(">>错误 - 卡列表划分 消费者:{}<<", e.getMessage());
        }
    }



    /**
     * 变更 执行
     */
    public void execution(String t_no){


        Map<String, Object> map = new HashMap<>();
        map.put("t_no",t_no);
        map.put("show_type","_cardDividBUP");//划卡备份文件
        List<Map<String,Object>> bUPList = performTaskUtil.getTaskFlie(map);


        map.put("show_type","_cardDivid");//划卡备份文件
        List<Map<String,Object>> dividList = performTaskUtil.getTaskFlie(map);
        try {

            if(bUPList!=null && bUPList.size()>0 && dividList!=null && dividList.size()>0){
                Map<String,Object> bUPMap = bUPList.get(0);
                Map<String,Object> dividMap = dividList.get(0);

                String bUPUrl = bUPMap.get("url").toString();
                bUPUrl = WriteCSV.getDlUrl(bUPUrl,false);//备份文件地址

                String dividUrl = dividMap.get("url").toString();
                dividUrl = WriteCSV.getDlUrl(dividUrl,false);//操作执行结果


                List<Map<String,Object>> dataList = WriteCSV.readCSV(bUPUrl,null,false);
                List<Map<String,Object>> bUPReadList = WriteCSV.readCSV(dividUrl,null,true);

                if(bUPReadList!=null && bUPReadList.size()>0 && dataList!=null && dataList.size()>0){
                    String set_dept_id = bUPReadList.get(0).get("set_dept_id").toString();
                    //分组  dept_id
                    Map<String, List<Map<String,Object>>> collect = dataList.stream().collect(Collectors.groupingBy(scope->scope.get("dept_id").toString()));
                    int sum_updCount = 0,sum_updInfoCount = 0 ;
                    for(Map.Entry<String, List<Map<String, Object>>> entry:collect.entrySet()){
                        List<Map<String, Object>>  iccidArr = entry.getValue();
                        String dept_id = entry.getKey();

                        Map<String, Object> setMap = new HashMap<>();
                        setMap.put("dept_id",dept_id);
                        setMap.put("batchListMap",iccidArr);
                        setMap.put("batchType","1");
                        setMap.put("set_dept_id",set_dept_id);
                        int updInfoCount = cardInfoMapper.updateBatch(setMap);
                        int updCount = cardMapper.updateBatch(setMap);

                        sum_updCount += updCount;
                        sum_updInfoCount += updInfoCount;
                        log.info(" 总修改 ：sum_updCount:"+sum_updCount+" /sum_updInfoCount "+sum_updInfoCount+"  ，本次 【"+ VeDate.getStringDate()+"】 ["+entry.getKey()+"] 修改 updCount:"+updCount+" / updInfoCount:"+updInfoCount+"  iccidArr.size() "+iccidArr.size());
                    }
                    log.info(" 修改完成 "+VeDate.getStringDate()+"  sum_updCount "+sum_updCount+" / sum_updInfoCount:"+sum_updInfoCount);
                }

            }else {
                log.error("未找到 划卡备份文件 {} ",map);
            }
        }catch (Exception e){
            log.error("卡列表划分 回滚异常 {} ",e.getMessage());
        }

    }







}
