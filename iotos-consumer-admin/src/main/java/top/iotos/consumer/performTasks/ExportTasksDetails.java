package top.iotos.consumer.performTasks;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.common.mapper.mysql.performTasks.TasksDetailsMapper;
import top.iotos.common.utils.iotos.service.PerformTaskUtil;
import top.iotos.common.utils.poi.WriteCSV;
import top.iotos.synApi.utils.iotos.service.DictUtil;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 执行任务详情导出  消费者
 */
@Slf4j
@Component
public class ExportTasksDetails {


    @Resource
    private RedisCache redisCache;
    @Resource
    private WriteCSV writeCSV;
    @Resource
    private TasksDetailsMapper tasksDetailsMapper;
    @Resource
    private PerformTaskUtil performTaskUtil;
    @Resource
    private DictUtil dictUtil;

    private int outSize = 50;//每 50条数据输出一次
    private String outcolumns[] = {"编号","ICCID","状态","业务类型","开始时间","结束时间","创建时间","备注"};
    private String keys[] = {"t_no","iccid","type_value","state_value","starting_time","end_time","create_time","remark"};


    @RabbitHandler
    @RabbitListener(queues = "admin_tasksDetailsExport_queue")
    public void tasksDetailsExport(String msg) {
        execute(msg);
    }

    @RabbitListener(queues = "dlx_admin_tasksDetailsExport_queue")
    public void dlxTasksDetailsExport(String msg) {
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
            String t_no = map.get("t_no").toString();
            String prefix = "admin_cardExport_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            String key_val = prefix+":"+ deptId+":"+t_no;
            Object  wExecute = redisCache.getCacheObject(key_val);
            if(wExecute==null){
                redisCache.setCacheObject(key_val, msg, 10, TimeUnit.SECONDS);//10 秒缓存 避免 重复消费
                execution(pMap,map,user,deptId);
            }
        } catch (Exception e) {
            log.error(">>错误 - 执行任务详情导出 消费者:{}<<", e.getMessage());
        }
    }



    /**
     * 变更 执行
     */
    public void execution(Map<String, Object> pMap,Map<String, Object> map,Map<String,Object> user,String deptId){

        String pT_no = map.get("t_no").toString();
        String state = map.get("state").toString();
        List<Map<String, Object>> exportArr = tasksDetailsMapper.getList(map);
        if(exportArr!=null && exportArr.size()>0){
            List<Map<String, Object>> tasksDetailsTypeOptions = (List<Map<String, Object>>) pMap.get("tasksDetailsTypeOptions");//字典 【执行任务详情类型】
            List<Map<String, Object>> resultStatusOptions = (List<Map<String, Object>>) pMap.get("resultStatusOptions");//字典 【通用结果状态】
            String ts_type = "tasksDetailsExport";
            String show_type = "_"+ts_type;
            Map<String, String> dept = (Map<String, String>)user.get("dept");
            String newName = UUID.randomUUID().toString().replace("-","")+show_type;

            String task_name ="任务详情[导出]-["+dictUtil.getDictLabel(resultStatusOptions,state)+"]-["+pT_no+"]";
            String saveUrl = "/getcsv/"+newName+".csv";

            Map<String, Object> pTasks = new HashMap<>();
            Map<String, Object> tFile = new HashMap<>();

            String t_no = "task-"+VeDate.getNo(4);
            pTasks.put("t_no",t_no);
            pTasks.put("name",task_name);
            pTasks.put("ts_type",ts_type);

            tFile.put("t_no",t_no);
            tFile.put("url",saveUrl);
            tFile.put("show_type",show_type);

            Map<String, Object> rMap = performTaskUtil.saveTask(t_no,null,pTasks,dept,user,tFile);
            pTasks = (Map<String, Object>) rMap.get("pTasks");
            List<Map<String, Object>> rTFiles = (List<Map<String, Object>>) rMap.get("rTFiles");
            tFile = rTFiles.get(0);
            List<Map<String,Object>> out_list = new ArrayList<Map<String,Object>>();
            //循环添加单卡数据信息
            for (int i = 0; i < exportArr.size(); i++) {
                Map<String,Object> carMap =  exportArr.get(i);
                carMap = dictUtil.writeDict(carMap,tasksDetailsTypeOptions,"type","type_value");//执行任务详情类型
                carMap = dictUtil.writeDict(carMap,resultStatusOptions,"state","state_value"); //通用结果状态
                //删除导出不需要的字段
                carMap.remove("type");
                carMap.remove("state");
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