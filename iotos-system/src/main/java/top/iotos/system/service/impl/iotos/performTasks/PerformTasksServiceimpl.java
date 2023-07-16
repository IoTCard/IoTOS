package top.iotos.system.service.impl.iotos.performTasks;


import org.springframework.stereotype.Service;
import top.iotos.common.core.domain.entity.SysDictData;
import top.iotos.common.mapper.mysql.performTasks.PerformTasksMapper;
import top.iotos.common.mapper.mysql.performTasks.TaskFlieDownloadMapper;
import top.iotos.common.mapper.mysql.performTasks.TaskFlieMapper;
import top.iotos.common.mapper.mysql.performTasks.TasksDetailsMapper;
import top.iotos.common.utils.iotos.service.PageUtil;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.system.service.ISysDictTypeService;
import top.iotos.system.service.iotos.performTasks.IPerformTasksService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformTasksServiceimpl implements IPerformTasksService {

    @Resource
    private PerformTasksMapper performTasksMapper;
    @Resource
    private TaskFlieMapper taskFlieMapper;
    @Resource
    private TasksDetailsMapper tasksDetailsMapper;
    @Resource
    private TaskFlieDownloadMapper taskFlieDownloadMapper;
    @Resource
    private MQAide mQAide;
    @Resource
    private ISysDictTypeService iSysDictTypeService;



    @Override
    public Map<String, Object> getList(Map map) {
        Map<String, Object> rMap = new HashMap<String, Object>();
        Integer rowCount = performTasksMapper.getListCount(map);
        PageUtil Pu = new PageUtil(rowCount, map.get("pageNum").toString(), map.get("pageSize").toString());//初始化分页工具类
        map.put("starRow", Pu.getStarRow());
        map.put("pageSize", Pu.getPageSize());
        rMap.put("Pu", Pu);
        rMap.put("data", performTasksMapper.getList(map));
        return rMap;
    }

    @Override
    public List<Map<String, Object>> findFile(Map map) {
        return taskFlieMapper.find(map);
    }

    @Override
    public boolean update(Map map) {
        boolean bool = false;

        return bool;
    }


    @Override
    public boolean delete(Map<String, Object> map) {
        boolean bool = false;

        return bool;
    }

    @Override
    public boolean save(Map<String, Object> map) {
        boolean bool = false;

        return bool;
    }

    @Override
    public Map<String, Object> downloadList(Map<String, Object> map) {
        Map<String, Object> rMap = new HashMap<String, Object>();
        Integer rowCount = taskFlieDownloadMapper.getListCount(map);
        PageUtil Pu = new PageUtil(rowCount, map.get("pageNum").toString(), map.get("pageSize").toString());//初始化分页工具类
        map.put("starRow", Pu.getStarRow());
        map.put("pageSize", Pu.getPageSize());
        rMap.put("Pu", Pu);
        rMap.put("data", taskFlieDownloadMapper.getList(map));
        return rMap;
    }



    @Override
    public Map<String, Object> tasksDetailsList(Map<String, Object> map) {
        Map<String, Object> rMap = new HashMap<String, Object>();
        Integer rowCount = tasksDetailsMapper.getListCount(map);
        PageUtil Pu = new PageUtil(rowCount, map.get("pageNum").toString(), map.get("pageSize").toString());//初始化分页工具类
        map.put("starRow", Pu.getStarRow());
        map.put("pageSize", Pu.getPageSize());
        rMap.put("Pu", Pu);
        rMap.put("data", tasksDetailsMapper.getList(map));
        return rMap;
    }

    @Override
    public boolean tasksDetailsExport(Map<String, Object> map) {
        boolean bool = false;
        map.remove("pageNum");
        map.remove("pageSize");
        Integer rowCount =  tasksDetailsMapper.getListCount(map);//当前任务是否有权限查看
        rowCount = rowCount !=null?rowCount:0;
        if(rowCount>0){
            List<SysDictData> tasksDetailsTypeOptions = iSysDictTypeService.selectDictDataByType("tasks_details_type");//字典 【执行任务详情类型】
            List<SysDictData> resultStatusOptions = iSysDictTypeService.selectDictDataByType("result_status");//字典 【通用结果状态】
            try {
                //发送消息
                String queueName = "admin_tasksDetailsExport_queue";
                String routingKey = "admin.tasksDetailsExport.queue";
                String exchangeName = "admin_exchange";//路由
                Map<String, Object> sendMap = new HashMap<>();
                sendMap.put("map",map);
                sendMap.put("tasksDetailsTypeOptions",tasksDetailsTypeOptions);
                sendMap.put("resultStatusOptions",resultStatusOptions);
                bool = mQAide.send(exchangeName, routingKey, 30, sendMap);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return bool;
    }


}
