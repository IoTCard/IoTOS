package top.iotos.common.utils.iotos.service;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.iotos.common.core.domain.entity.SysUser;
import top.iotos.common.mapper.mysql.performTasks.PerformTasksMapper;
import top.iotos.common.mapper.mysql.performTasks.TaskFlieDownloadMapper;
import top.iotos.common.mapper.mysql.performTasks.TaskFlieMapper;
import top.iotos.common.mapper.mysql.performTasks.TasksDetailsMapper;
import top.iotos.common.mapper.mysql.sys.DictTypeMapper;
import top.iotos.synApi.mapper.mysql.card.CardApiBusinessMapper;
import top.iotos.synApi.utils.iotos.service.DictUtil;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 执行任务辅助类
 */
@Component
public class PerformTaskUtil {
    @Resource
    private PerformTasksMapper performTasksMapper;
    @Resource
    private TaskFlieMapper taskFlieMapper;
    @Resource
    private TasksDetailsMapper tasksDetailsMapper;
    @Resource
    private TaskFlieDownloadMapper taskFlieDownloadMapper;
    @Resource
    private DictTypeMapper dictTypeMapper;
    @Resource
    private DictUtil dictUtil;
    @Resource
    private CardApiBusinessMapper cardApiBusinessMapper;

    /**
     * 新增执行任务 【不带任务详情版】
     * @param t_no
     * @param pTasks
     * @param dept
     * @param user
     * @param tFiles
     * @return
     */
    public Map<String, Object> saveTask(String t_no,String expiration_date,Map<String, Object> pTasks,Map<String, String> dept,Map<String,Object> user,Map<String, Object>... tFiles){
        Map<String, Object> rMap = new HashMap<>();
        boolean pBool =false,tBool = false;
        String day = "7";
        if(expiration_date==null){
            Map<String, Object> findMap = new HashMap<>();
            findMap.put("dictType","task_flie_expiration_date");//执行任务储存天数
            List<Map<String, Object>> expirationDateOptions =  dictTypeMapper.selectDictDataList(findMap);
            if(expirationDateOptions!=null){
                day = dictUtil.getDictValue(pTasks,expirationDateOptions,"ts_type");//写入过期时间
                day = day!=null?day:"7";
            }
        }
        expiration_date =  expiration_date!=null?expiration_date:VeDate.getNextDay(day);//获取七天后日期 [默认未传入时保存七天]

        pTasks.put("dept_id", dept.get("deptId"));
        pTasks.put("dept_name",dept.get("deptName"));
        pTasks.put("user_id",user.get("userId"));
        pTasks.put("user_name",user.get("userName"));
        pTasks.put("expiration_date",expiration_date);
        pBool = performTasksMapper.save(pTasks)>0;//添加执行 任务表

        List<Map<String, Object>> rTFiles = new ArrayList<>();
        if (tFiles != null)
        {
            for (Map<String, Object> tFile : tFiles)
            {
                tFile.put("t_no",t_no);
                tFile.put("dept_id",dept.get("deptId"));
                tFile.put("type","1");//下载文件
                tBool = taskFlieMapper.save(tFile)>0;//添加执行任务 文件表
                rTFiles.add(tFile);
            }
        }else {
            tBool = true;
        }

        rMap.put("pTasks",pTasks);
        rMap.put("rTFiles",rTFiles);
        rMap.put("pBool",pBool);
        rMap.put("tBool",tBool);
        return rMap;
    }


    /**
     * 新增执行任务 带任务详情
     * @param t_no
     * @param expiration_date
     * @param pTasks
     * @param dept
     * @param user
     * @param listType
     * @param list
     * @param tFiles
     * @return
     */
    public Map<String, Object> saveTask(String t_no,String expiration_date,Map<String, Object> pTasks,Map<String, String> dept,Map<String,Object> user,List<String> listType,List<Map<String, Object>> list,Map<String, Object>... tFiles){
        Map<String, Object> rMap = new HashMap<>();
        boolean pBool =false,tBool = false,dBool = false;
        String day = "7";
        if(expiration_date==null){
            Map<String, Object> findMap = new HashMap<>();
            findMap.put("dictType","task_flie_expiration_date");//执行任务储存天数
            List<Map<String, Object>> expirationDateOptions =  dictTypeMapper.selectDictDataList(findMap);
            if(expirationDateOptions!=null){
                day = dictUtil.getDictValue(pTasks,expirationDateOptions,"ts_type");//写入过期时间
                day = day!=null?day:"7";
            }
        }
        expiration_date =  expiration_date!=null?expiration_date:VeDate.getNextDay(day);//获取七天后日期 [默认未传入时保存七天]
        Object dept_id = dept.get("deptId");
        pTasks.put("dept_id",dept_id);
        pTasks.put("dept_name",dept.get("deptName"));
        pTasks.put("user_id",user.get("userId"));
        pTasks.put("user_name",user.get("userName"));
        pTasks.put("expiration_date",expiration_date);
        pTasks.put("w_details",1);
        pBool = performTasksMapper.save(pTasks)>0;//添加执行 任务表

        List<Map<String, Object>> rTFiles = new ArrayList<>();
        if (tFiles != null)
        {
            for (Map<String, Object> tFile : tFiles)
            {
                tFile.put("t_no",t_no);
                tFile.put("dept_id",dept.get("deptId"));
                tFile.put("type","1");//下载文件
                tBool = taskFlieMapper.save(tFile)>0;//添加执行任务 文件表
                rTFiles.add(tFile);
            }
        }else {
            tBool = true;
        }
        Map<String, Object> saveMap = new HashMap<>();
        saveMap.put("t_no",t_no);
        saveMap.put("dept_id",dept_id);

        if(listType!=null && listType.size()>0 && list!=null && list.size()>0){
            for (int i = 0; i < listType.size(); i++) {
                String type = listType.get(i);
                saveMap.put("type",type);
                saveMap.put("lists",list);
                dBool = tasksDetailsMapper.save(saveMap)>0;
            }
        }

        rMap.put("pTasks",pTasks);
        rMap.put("rTFiles",rTFiles);
        rMap.put("pBool",pBool);
        rMap.put("tBool",tBool);
        rMap.put("dBool",dBool);
        return rMap;
    }






    /**
     * 任务结束
     * @param pTasks
     */
    public void endOfTask(Map<String, Object> pTasks){
        pTasks.put("end_time", VeDate.getStringDate());
        performTasksMapper.update(pTasks);
    }

    /**
     * 任务文件结束
     * @param tFile
     */
    public void endOfTaskFile(Map<String, Object> tFile){
        tFile.put("end_time",VeDate.getStringDate());
        taskFlieMapper.update(tFile);
    }


    /**
     * 修改执行任务详情
     * @param updMap
     * @return
     */
    public int updDetails(Map<String, Object> updMap){
       return tasksDetailsMapper.update(updMap);
    }


    /**
     * 批量修改执行任务 详情
     * @param updMap
     * @return
     */
    public int updBatchDetails(Map<String, Object> updMap){
        return tasksDetailsMapper.updateBatch(updMap);
    }


    public int updBatchDetails(String t_no,List<Map<String, Object>> iccidList,String state,String remark,String starting_time,String end_time){
        Map<String, Object> updMap = new HashMap<>();
        updMap.put("t_no",t_no);
        updMap.put("iccidList",iccidList);
        updMap.put("state",state);
        updMap.put("remark",remark);
        updMap.put("starting_time",starting_time);
        updMap.put("end_time",end_time);
        return tasksDetailsMapper.updateBatch(updMap);
    }

    /**
     * 新增下载记录信息
     * @param user
     * @param t_no
     * @param ip
     */
    public boolean downloadAddRecord(SysUser user,String t_no,String ip,String fid){
        boolean bool = false,bool_a = false,bool_b = false;
        Map<String, Object> tFile = new HashMap<>();
        tFile.put("t_no",t_no);
        tFile.put("id",fid);
        tFile.put("download_times","1");
        bool_a = taskFlieMapper.update(tFile)>0;//下载次数增加

        Map<String, Object> dMap = new HashMap<>();
        dMap.put("t_no",t_no);
        dMap.put("dept_id",user.getDeptId());
        dMap.put("user_id",user.getUserId());
        dMap.put("ip",ip);
        dMap.put("fid",fid);
        bool_b = taskFlieDownloadMapper.save(dMap)>0;
        if(bool_a==true && bool_b==true){
            bool = true;
        }
        return bool;
    }

    public List<Map<String,Object>> getTaskFlie(Map<String, Object> map){
      return taskFlieMapper.find(map);
    }


    public List<Map<String, Object>> getListPTasks(Map map){
        return performTasksMapper.getList(map);
    };


    @Transactional
    public Map<String, Object> delTask(Map map){
        Map<String, Object> rMap = new HashMap<>();
        boolean bool = false;
        int fInt = 0,dInt = 0,fdInt = 0,pInt = 0;
        try {
             fInt = taskFlieMapper.deleteArr(map);
             dInt = tasksDetailsMapper.deleteArr(map);
             fdInt = taskFlieDownloadMapper.deleteArr(map);
             pInt = performTasksMapper.deleteArr(map);
            if(pInt>0){
                bool = true;
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("PerformTaskUtil delTask 回滚 》  setRollbackOnly " + e.getMessage());
        }
        if(!bool){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("PerformTaskUtil delTask 回滚 》[bool = false]  setRollbackOnly " );
        }
        rMap.put("bool",bool);
        rMap.put("fInt",fInt);
        rMap.put("dInt",dInt);
        rMap.put("fdInt",fdInt);
        rMap.put("pInt",pInt);
        return rMap;
    };


    /**
     * 新增 API业务变更
     * @param map
     * @return
     */
    public int saveApiBs(Map map){
        return cardApiBusinessMapper.save(map);
    };





}
