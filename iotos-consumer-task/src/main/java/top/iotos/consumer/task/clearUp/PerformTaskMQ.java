package top.iotos.consumer.task.clearUp;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.common.utils.iotos.service.PerformTaskUtil;
import top.iotos.synApi.utils.iotos.time.VeDate;
import top.iotos.common.utils.poi.WriteCSV;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行日志清理 消费者
 */
@Slf4j
@Component
public class PerformTaskMQ {

    @Resource
    private PerformTaskUtil performTaskUtil;
    @Resource
    private WriteCSV writeCSV;


    @RabbitHandler
    @RabbitListener(queues = "task_tasksClearUp_queue")
    public void tasksClearUp(String msg) {
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
            log.error(">>错误 - 执行日志清理 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(Map<String, Object> map){

        //获取快到期 任务
        map.put("timeType","4");//失效日期
        map.put("startDate","2000-01-01");
        map.put("endDate", VeDate.getStringDateShort());
        List<Map<String, Object>> pList = performTaskUtil.getListPTasks(map);
        if(pList!=null && pList.size()>0){
            try {
                List<String> t_noArr = new ArrayList<>();
                for (int i = 0; i < pList.size(); i++) {
                    Map<String, Object> pMap = pList.get(i);

                    String t_no = pMap.get("t_no").toString();
                    Map<String,Object> findMap = new HashMap<>();
                    findMap.put("t_no",t_no);
                    List<Map<String, Object>> fList = performTaskUtil.getTaskFlie(findMap);
                    if (fList!=null && fList.size()>0){
                        for (int j = 0; j < fList.size(); j++) {
                            Map<String, Object> fMap = fList.get(j);
                            String url = fMap.get("url")!=null?fMap.get("url").toString():null;
                            if(url!=null){
                                url = WriteCSV.getDlUrl(url,false);//解析文件地址
                                try {
                                    writeCSV.deleteFile(new File(url));
                                }catch (Exception e){
                                    log.error("deleteFile  {} 异常 {}",url,e.getCause().toString());
                                }
                                try {
                                    String sArr[] = url.split("\\.");
                                    url =  sArr[0]+".xls";
                                    writeCSV.deleteFile(new File(url));
                                }catch (Exception e){
                                    log.error("deleteFile  {} 异常 {}",url,e.getCause().toString());
                                }
                            }
                        }
                    }
                   t_noArr.add(t_no);
                }
                if(t_noArr.size()>0){
                    Map<String,Object> delMap = new HashMap<>();
                    delMap.put("t_noArr",t_noArr);
                    Map<String,Object> rMap = performTaskUtil.delTask(delMap);
                    Object bool = rMap.get("bool");
                    Object fInt = rMap.get("fInt");
                    Object dInt = rMap.get("dInt");
                    Object fdInt = rMap.get("fdInt");
                    Object pInt = rMap.get("pInt");
                    log.info("本次 到期执行任务信息 bool:{} ,fInt:{} ,dInt:{} ,fdInt:{} ,pInt:{}  ",bool,fInt,dInt,fdInt,pInt);
                }

            }catch (Exception e){
                log.error("清理的到期执行任务异常 {}",e.getMessage());
            }
        }else {
            log.info("没有需要清理的到期执行任务");
        }

    }


}
