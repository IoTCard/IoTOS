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
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 卡列表划分  消费者
 */
@Slf4j
@Component
public class DivideCard {


    @Resource
    private RedisCache redisCache;
    @Resource
    private WriteCSV writeCSV;
    @Resource
    private CardMapper cardMapper;
    @Resource
    private CardInfoMapper cardInfoMapper;
    @Resource
    private PerformTaskUtil performTaskUtil;

    private int outSize = 50;//每 50条数据输出一次

    /**
     * 卡列表划分
     * @param msg
     * @throws IOException
     */
    @RabbitHandler
    @RabbitListener(queues = "admin_cardDivide_queue")
    public void cardDivide(String msg) {
        execute(msg);
    }







    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            String set_dept_name = map.get("set_dept_name").toString();
            List<String> dividIccidArr = (List<String>) map.get("dividIccidArr");
            Map<String,Object> user = (Map<String, Object>) map.get("user");

            String prefix = "admin_cardDivide_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            Object  wExecute = redisCache.getCacheObject(prefix+":"+ set_dept_name);
            if(wExecute==null){
                redisCache.setCacheObject(prefix+":"+ set_dept_name, msg, 10, TimeUnit.SECONDS);//10 秒缓存 避免 重复消费
                execution(map,dividIccidArr,user);
            }
        } catch (Exception e) {
            log.error(">>错误 - 卡列表划分 消费者:{}<<", e.getMessage());
        }
    }



    /**
     * 变更 执行
     */
    public void execution(Map<String, Object> map,List<String> dividIccidArr,Map<String,Object> user){

        String message = "";
        if(dividIccidArr!=null && dividIccidArr.size()>0){

            //1.备份现有卡所属信息
            String set_dept_name = map.get("set_dept_name").toString();

            Map<String, String> dept = (Map<String, String>)user.get("dept");

            String  create_by = " [ "+dept.get("deptName")+" ] - "+" [ "+user.get("userName")+" ] ";
            String newName = UUID.randomUUID().toString().replace("-","")+"_cardDivid";
            String backName = UUID.randomUUID().toString().replace("-","")+"_cardDividBUP";
            String set_dept_id = map.get("set_dept_id").toString();


            String task_name ="卡列表 [划卡]";
            String remark = "[划卡 - "+dividIccidArr.size()+"] 至 ["+set_dept_name+"] ";
            String saveUrl = "/getcsv/"+newName+".csv";
            String bkUrl = "/getcsv/"+backName+".csv";

            Map<String, Object> pTasks = new HashMap<>();
            Map<String, Object> tFile = new HashMap<>();
            Map<String, Object> bk_tFile = new HashMap<>();

            String t_no = "task-"+VeDate.getNo(4);

            pTasks.put("t_no",t_no);
            pTasks.put("name",task_name);
            pTasks.put("ts_type","cardDivid");
            pTasks.put("remark",remark);

            tFile.put("t_no",t_no);
            tFile.put("url",saveUrl);
            tFile.put("show_type","_cardDivid");

            bk_tFile.put("t_no",t_no);
            bk_tFile.put("url",bkUrl);
            bk_tFile.put("show_type","_cardDividBUP");

            Map<String, Object> rMap = performTaskUtil.saveTask(t_no,null,pTasks,dept,user,tFile,bk_tFile);
            pTasks = (Map<String, Object>) rMap.get("pTasks");
            List<Map<String, Object>> rTFiles = (List<Map<String, Object>>) rMap.get("rTFiles");
            tFile = rTFiles.get(0);
            bk_tFile = rTFiles.get(1);

            List<Map<String,Object>> list =  cardInfoMapper.backupDept();
            if(list!=null && list.size()>0){
                String outcolumns[] = {"iccid","dept_id","执行时间","执行人"};
                String keys[] = {"iccid","dept_id","time","deptName"};
                Map<String, Object> defoutColumns = new HashMap<>();
                defoutColumns.put("deptName",create_by);
                defoutColumns.put("time",VeDate.getStringDate());
                writeCSV.outCSVObj(list, backName,outcolumns, keys,defoutColumns,outSize);
            }


            //划卡
            Map<String, Object> setMap = new HashMap<>();
            setMap.put("dept_id",set_dept_id);
            setMap.put("batchList",dividIccidArr);
            setMap.put("batchType","1");
            int updInfoCount = cardInfoMapper.updateBatch(setMap);
            int updCount = cardMapper.updateBatch(setMap);

            if(updCount>0){
                message = "操作成功 划分的数据 [ "+dividIccidArr.size()+" ] 条，成功划分 updInfoCount [ "+updInfoCount+" ] / updCount ["+updCount+"] 条，至 [ "+set_dept_name+" ]！";
                log.info(message);
            }else{
                message = "操作失败 划分的数据 [ "+dividIccidArr.size()+" ] 条，成功划分 updInfoCount [ "+updInfoCount+" ] / updCount ["+updCount+"] 条，至 [ "+set_dept_name+" ]……";
                log.error(message);
            }
            String outcolumns[] = {"iccid","set_dept_id","执行时间","执行人","执行结果"};
            String keys[] = {"iccid","set_dept_id","time","deptName","message"};
            Map<String, Object> defoutColumns = new HashMap<>();
            defoutColumns.put("deptName",create_by);
            defoutColumns.put("set_dept_id",set_dept_id);
            defoutColumns.put("time",VeDate.getStringDate());
            defoutColumns.put("message",message);
            writeCSV.outCSVListStr(dividIccidArr,"iccid", newName,outcolumns, keys,defoutColumns,outSize);

            performTaskUtil.endOfTaskFile(tFile);//文件结束
            performTaskUtil.endOfTaskFile(bk_tFile);//文件结束
            performTaskUtil.endOfTask(pTasks);//任务结束
        }else{
            log.info("当前筛选条件下未找到需要划分的数据！请核对后重试！");
        }
    }


}