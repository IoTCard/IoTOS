package top.iotos.consumer.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.common.utils.iotos.service.PerformTaskUtil;
import top.iotos.common.utils.iotos.web.IoTOSTools;
import top.iotos.common.utils.poi.WriteCSV;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.OneLinkEcV5SessionMapper;
import top.iotos.synApi.utils.iotos.service.DictUtil;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 导出指定卡号会话记录  消费者
 */
@Slf4j
@Component
public class ExportCardSession {


    @Resource
    private RedisCache redisCache;
    @Resource
    private WriteCSV writeCSV;
    @Resource
    private CardInfoMapper cardInfoMapper;
    @Resource
    private PerformTaskUtil performTaskUtil;
    @Resource
    private DictUtil dictUtil;
    @Resource
    private OneLinkEcV5SessionMapper oneLinkEcV5SessionMapper;


    private int outSize = 50;//每 50条数据输出一次

    /**
     * 导出指定卡号会话记录
     * @param msg
     * @throws IOException
     */
    @RabbitHandler
    @RabbitListener(queues = "admin_exportSession_queue")
    public void cardExport(String msg) {
        execute(msg);
    }




    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> pMap = MQAide.getParameter(msg);
            Map<String,Object> map = (Map<String, Object>) pMap.get("map");
            Map<String,Object> user = (Map<String, Object>) map.get("user");
            String deptId = user.get("deptId").toString();

            String prefix = "admin_exportSession_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            Object  wExecute = redisCache.getCacheObject(prefix+":"+ deptId);
            if(wExecute==null){
                redisCache.setCacheObject(prefix+":"+ deptId, msg, 3, TimeUnit.SECONDS);//x 秒缓存 避免 重复消费
                execution(pMap,map,user,deptId);
            }
        } catch (Exception e) {
            log.error(">>错误 - 导出指定卡号会话记录 消费者:{}<<", e.getMessage());
        }
    }



    /**
     * 变更 执行
     */
    public void execution(Map<String, Object> pMap,Map<String, Object> map,Map<String,Object> user,String deptId){

        String iccid = map.get("iccid").toString();
        String onlyCreateDate = map.get("onlyCreateDate").toString();
        String template = map.get("template").toString();
        List<Map<String, Object>> exportArr = null;
        List<Map<String, Object>> onelinkSessionStatusOptions  = null;
        List<Map<String, Object>> onelinkRatTypeOptions  = null;
        String outcolumns[] = {};
        String keys[] = {};
        if(template.equals("oneLink_ECV5")){
            if(onlyCreateDate.equals("1")){//过滤重复会话时间
                exportArr = oneLinkEcV5SessionMapper.getListOnlyCreateDate(map);
            }else {
                exportArr = oneLinkEcV5SessionMapper.getList(map);
            }
            onelinkSessionStatusOptions = (List<Map<String, Object>>) pMap.get("onelinkSessionStatusOptions");//oneLink在线状态
            onelinkRatTypeOptions = (List<Map<String, Object>>) pMap.get("onelinkRatTypeOptions");//onelink_rat接入方式
            outcolumns = new String[]{"APN","在线状态","IP","IPV6 地址前缀","IPV6 地址接口","会话创建时间","接入方式","平台记录时间"};
            keys = new String[]{"apn_id","status","ip","ipv6_prefix","ipv6","create_date","rat","create_time",};
        }
        if(exportArr!=null && exportArr.size()>0){

            Map<String, String> dept = (Map<String, String>)user.get("dept");
            String ts_type = "cardSessionExport";
            String newName = UUID.randomUUID().toString().replace("-","")+"_"+ts_type;

            String task_name ="会话["+iccid+"][导出]";
            String saveUrl = "/getcsv/"+newName+".csv";

            Map<String, Object> pTasks = new HashMap<>();
            Map<String, Object> tFile = new HashMap<>();

            String t_no = "task-"+VeDate.getNo(4);

            pTasks.put("t_no",t_no);
            pTasks.put("name",task_name);
            pTasks.put("ts_type",ts_type);

            tFile.put("t_no",t_no);
            tFile.put("url",saveUrl);
            tFile.put("show_type","_"+ts_type);

            Map<String, Object> rMap = performTaskUtil.saveTask(t_no,null,pTasks,dept,user,tFile);
            pTasks = (Map<String, Object>) rMap.get("pTasks");
            List<Map<String, Object>> rTFiles = (List<Map<String, Object>>) rMap.get("rTFiles");
            tFile = rTFiles.get(0);


            List<Map<String,Object>> out_list = new ArrayList<Map<String,Object>>();
            //循环添加单卡数据信息
            for (int i = 0; i < exportArr.size(); i++) {
                Map<String,Object> carMap =  exportArr.get(i);
                if(template.equals("oneLink_ECV5")) {
                    carMap = dictUtil.writeDict(carMap, onelinkSessionStatusOptions, "status", "status");//oneLink在线状态
                    carMap = dictUtil.writeDict(carMap, onelinkRatTypeOptions, "rat", "rat"); //onelink_rat接入方式
                }

                out_list.add(carMap);
                if ((i+1)%outSize==0 || (i+1)==exportArr.size()){
                    writeCSV.Write(newName,out_list, outcolumns, null,keys);
                    out_list = new ArrayList<>();
                }
            }
            performTaskUtil.endOfTaskFile(tFile);//文件结束
            performTaskUtil.endOfTask(pTasks);//任务结束
        }
    }


}