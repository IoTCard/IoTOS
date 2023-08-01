package top.iotos.consumer.connect;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.common.mapper.mysql.sys.DictTypeMapper;
import top.iotos.common.utils.iotos.common.ExcelConfig;
import top.iotos.common.utils.iotos.service.PerformTaskUtil;
import top.iotos.common.utils.poi.WriteCSV;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.synApi.utils.iotos.common.GetMapUtil;
import top.iotos.synApi.utils.iotos.common.ListCompare;
import top.iotos.synApi.utils.iotos.service.DictUtil;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;
import top.iotos.synApi.utils.sync.ApiProcessor;
import top.iotos.synApi.utils.sync.CardStateTransition;
import top.iotos.synApi.utils.sync.MQSyncSend;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 批量API业务办理  消费者
 */
@Slf4j
@Component
public class BusinessHandlingCard {


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
    @Resource
    private ApiProcessor apiProcessor;
    @Resource
    private DictTypeMapper dictTypeMapper;
    @Resource
    private DictUtil dictUtil;
    @Resource
    private CardStateTransition cardStateTransition;
    @Resource
    private MQSyncSend mQSyncSend;

    private String outcolumns[] =  {"iccid","执行人","执行描述","执行结果"};
    private String keys[] =        {"iccid","deptName","message","result"};
    private int outSize = 50;//每 X 条数据输出一次
    private int dbWSize = 500;//每 X 条数据 批量写入一次数据库


    @RabbitHandler
    @RabbitListener(queues = "admin_cardBusinessHandling_queue")
    public void cardBusinessHandling(String msg) {
        execute(msg);
    }




    @RabbitHandler
    @RabbitListener(queues = "dlx_admin_cardBusinessHandling_queue")
    public void dlxCardBusinessHandling(String msg) {
        execute(msg);
    }


    //文本域业务操作
    @RabbitHandler
    @RabbitListener(queues = "admin_textFieldHandling_queue")
    public void textFieldHandling(String msg) {
        tfExecute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_admin_textFieldHandling_queue")
    public void dlxTextFieldHandling(String msg) {
        tfExecute(msg);
    }





    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            String filePath = map.get("filePath").toString();//项目根目录
            String readName = map.get("readName").toString();//上传新文件名
            Map<String,Object> pMap =  ( Map<String,Object>)map.get("pMap");//参数
            Map<String,Object> user =  ( Map<String,Object>)pMap.get("user");//登录用户信息
            String newName = map.get("newName").toString();//
            String key = "admin_cardBusinessHandling_queue"+":"+ readName;
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            Object  wExecute = redisCache.getCacheObject(key);
            if(wExecute==null){
                redisCache.setCacheObject(key, msg, 3, TimeUnit.SECONDS);//3 秒缓存 避免 重复消费
                execution(filePath,readName,pMap,user,null,newName);
            }
        } catch (Exception e) {
            log.error(">>错误 - 批量API业务办理 消费者:{}<<", e.getMessage());
        }
    }

    private void tfExecute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            Map<String,Object> pMap =  ( Map<String,Object>)map.get("pMap");//参数
            Map<String,Object> user =  ( Map<String,Object>)pMap.get("user");//登录用户信息
            List<Map<String, Object>> list = (List<Map<String, Object>>) pMap.get("iccidList");
            String newName = map.get("newName").toString();//
            String key = "admin_textFieldHandling_queue"+":"+ newName;
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            Object  wExecute = redisCache.getCacheObject(key);
            if(wExecute==null){
                redisCache.setCacheObject(key, msg, 3, TimeUnit.SECONDS);//3 秒缓存 避免 重复消费

                execution(list,pMap,user,null,newName);
            }
        } catch (Exception e) {
            log.error(">>错误 - 批量API业务办理[文本域] 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(String filePath,String readName,Map<String,Object> pMap,Map<String,Object> user,Map<String,Object> pTaskMap,String newName) {
        //1.读取 上传文件
        String path = filePath +  readName;
        String columns[] = {"iccid"};
        ExcelConfig excelConfig = new ExcelConfig();
        List<Map<String, Object>> list = excelConfig.getExcelListMap(path,columns);
        execution(list,pMap,user,null,newName);
    }

    /**
     * 变更 执行
     */
    public void execution(List<Map<String, Object>> list,Map<String,Object> pMap,Map<String,Object> user,Map<String,Object> pTaskMap,String newName){

        Map<String, String> dept = (Map<String, String>)user.get("dept");
        String  create_by = " [ "+dept.get("deptName")+" ] - "+" [ "+user.get("userName")+" ] ";
        String deptId  = user.get("deptId").toString();


        List<String> listType = new ArrayList<>();
        String activeName = pMap.get("activeName").toString();//当前选择操作类型
        String w_openStop = null;
        String w_openClose = null;
        String w_reset = null;
        String flexibleChange = null;
        String flexibleChangeValue = "";
        boolean bool = false;
        Map<String, Object> findMap = new HashMap<>();
        List<Map<String, Object>> cardFlexiblechangeOptions = null;
        List<Map<String, Object>> cardConnectionStatusOptions = null;
        List<Map<String, Object>> cardStatusShowIdOptions = null;


        switch (activeName){
            case "openClose":
                w_openStop  = GetMapUtil.getValueToStr(pMap,"w_openStop",null);
                w_openClose = GetMapUtil.getValueToStr(pMap,"w_openClose",null);
                if(w_openStop!=null || w_openClose!=null){
                    if(!w_openStop.equals("0") || !w_openClose.equals("0")){
                        bool = true;
                        if(!w_openStop.equals("0")){
                            String openStop = w_openStop.equals("1")?"startUp":"shutdown";
                            listType.add(openStop);
                        }
                        if(!w_openClose.equals("0")){
                            String openClose = w_openClose.equals("1")?"openNet":"disconnected";
                            listType.add(openClose);
                        }
                        findMap.put("dictType","card_status_show_id");
                        cardStatusShowIdOptions = dictTypeMapper.selectDictDataList(findMap);
                        findMap.put("dictType","card_connection_status");
                        cardConnectionStatusOptions = dictTypeMapper.selectDictDataList(findMap);
                    }
                }
                break;
            case "gprsReset":
                w_reset = GetMapUtil.getValueToStr(pMap,"w_reset",null);
                if(w_reset!=null){
                    if(!w_reset.equals("0")){
                        bool = true;
                        listType.add("networkReset");
                    }
                }
                break;
            case "flexibleChange":
                flexibleChange = GetMapUtil.getValueToStr(pMap,"flexibleChange",null);
                if(flexibleChange!=null){
                    if(!flexibleChange.equals("0")){
                        bool = true;
                        listType.add("flexibleChange");
                        findMap.put("dictType","card_flexiblechange");//
                        cardFlexiblechangeOptions = dictTypeMapper.selectDictDataList(findMap);
                        flexibleChangeValue = dictUtil.getDictLabel(cardFlexiblechangeOptions,flexibleChange);//写入 灵活变更;
                    }
                }
                break;
        }

         if(bool){
          if(list!=null && list.size()>0) {
              String change_value = "";
              for (int i = 0; i < listType.size(); i++) {
                  String type = listType.get(i);
                  if(i==0){
                      change_value = getChangeValue(type,flexibleChangeValue);
                  }else {
                      change_value = change_value+","+getChangeValue(type,flexibleChangeValue);
                  }
              }


          Integer batchType = 1;
          String lie = "iccid";
          //筛选出  iccid 卡号 重复项
          Map<String, Object> getNotRepeatingMap =  ListCompare.getNotRepeating(list,lie);//获取 筛选不重复的某列值 和 重复的
          list = (List<Map<String, Object>>) getNotRepeatingMap.get("Rlist");//更新 查询数据
          List<Map<String, Object>> Repeatlist = (List<Map<String, Object>>) getNotRepeatingMap.get("Repeatlist");
          if(Repeatlist.size()>0){
              Map<String, Object> defoutColumns = new HashMap<>();
              defoutColumns.put("message"," ["+lie+"] 重复操作失败！同一 ["+lie+"] 同批次，无需多次操作！");
              defoutColumns.put("deptName",create_by);
              defoutColumns.put("result","操作失败");
              writeCSV.outCSVObj(Repeatlist, newName,outcolumns, keys,defoutColumns,outSize);
          }

            Map<String, Object> pTasks = new HashMap<>();
            Map<String, Object> tFile = new HashMap<>();

            String task_name ="卡列表-业务办理 ["+change_value+"] ";
            String saveUrl = "/getcsv/"+newName+".csv";
              String t_no = "";
            if(pTaskMap!=null){
                pTasks = pTaskMap;
                t_no = pTasks.get("t_no").toString();
            }else{
                t_no = "task-"+VeDate.getNo(4);
                pTasks.put("t_no",t_no);
                pTasks.put("name",task_name);
                pTasks.put("ts_type","cardBusinessHandling");

                tFile.put("t_no",t_no);
                tFile.put("url",saveUrl);
                tFile.put("show_type","_cardBusinessHandling");

                Map<String, Object> rMap = performTaskUtil.saveTask(t_no,null,pTasks,dept,user,listType,list,tFile);
                pTasks = (Map<String, Object>) rMap.get("pTasks");
                List<Map<String, Object>> rTFiles = (List<Map<String, Object>>) rMap.get("rTFiles");
                tFile = rTFiles.get(0);
            }

                //查询数据库中 匹对iccid 是否存在
                HashMap<String, Object> map = new HashMap<>();
                //添加修改数据
                map.put("lists",list);
                map.put("batchType",batchType);
                map.put("batchIn","in");

                String starting_time = VeDate.getStringDate();

                List<String>  iccidarr = cardMapper.wExist(map);
                if (iccidarr != null && iccidarr.size() > 0) {
                    //1.判断 查询卡号是否都在库里
                        //库中查询出卡号与上传卡号数量 不一致 说明有卡号不在数据库中
                        if (!(iccidarr.size() == list.size())) {
                            // 获取 数组去重数据 和 重复值
                            Map<String, Object> getNotRepeatingMap_DB = ListCompare.getNotRepeating(list, iccidarr, lie);//获取 筛选不重复的某列值 和 重复的
                            list = (List<Map<String, Object>>) getNotRepeatingMap_DB.get("Rlist");//更新 查询数据
                            List<Map<String, Object>> out_list_Different = (List<Map<String, Object>>) getNotRepeatingMap_DB.get("Repeatlist");//更新 查询数据
                            //找出与数据库已存在 相同 lie 去除 重复 lie
                            if (out_list_Different.size() > 0) {
                                String remark = "["+lie+"] 不在数据库中！请核对["+lie+"]卡号！";
                                Map<String, Object> defoutColumns = new HashMap<>();
                                defoutColumns.put("message",remark);
                                defoutColumns.put("deptName",create_by);
                                defoutColumns.put("result","操作失败");
                                writeCSV.outCSVObj(out_list_Different, newName,outcolumns, keys,defoutColumns,outSize);
                                performTaskUtil.updBatchDetails(t_no,out_list_Different,"2",remark,starting_time,VeDate.getStringDate());//执行任务 结束
                            }
                        }
                        map.put("lists", list);//更新 list
                        try {
                            if(list!=null && list.size()>0) {
                                //判断当前 操作用户所属企业 过滤不属于自己企业的卡
                                if(!deptId.equals("100")){
                                    Map<String, Object> countMap = new HashMap<>();
                                    countMap.putAll(map);
                                    countMap.put("deptId",deptId);
                                    countMap.put("findDeptId",cardInfoMapper.queryChildrenAreaInfo(countMap));//查询企业所属下级企业
                                    List<String> AgentIccidArr = cardInfoMapper.findDeptIccid(countMap);
                                    //库中查询出【该企业下】卡号 与上传卡号数量 不一致 说明有卡号不在 其名下
                                    if (!(AgentIccidArr.size() == list.size())) {
                                        // 获取 数组去重数据 和 重复值
                                        Map<String, Object> getNotRepeatingMap_DB = ListCompare.getNotRepeating(list, AgentIccidArr, "iccid");//获取 筛选不重复的某列值 和 重复的
                                        list = (List<Map<String, Object>>) getNotRepeatingMap_DB.get("Rlist");//更新 查询数据
                                        List<Map<String, Object>> out_list_Different = (List<Map<String, Object>>) getNotRepeatingMap_DB.get("Repeatlist");//更新 查询数据
                                        //找出与数据库已存在 相同 ICCID 去除 重复 iccid
                                        if (out_list_Different.size() > 0) {
                                            String remark = "越权操作！ iccid 不在操作企业下！请核对iccid卡号！";
                                            Map<String, Object> defoutColumns = new HashMap<>();
                                            defoutColumns.put("message",remark);
                                            defoutColumns.put("deptName",create_by);
                                            defoutColumns.put("result","操作失败");
                                            writeCSV.outCSVObj(out_list_Different, newName,outcolumns, keys,defoutColumns,outSize);
                                            performTaskUtil.updBatchDetails(t_no,out_list_Different,"2",remark,starting_time,VeDate.getStringDate());//执行任务 结束
                                        }
                                    }
                                }
                                map.put("lists", list);//更新 list

                                if(list.size()>0){
                                    //执行 API 业务

                                    //业务执行 分 X 个结果 进行一次数据库同步写入 反馈结果

                                    for (int i = 0; i < listType.size(); i++) {

                                        String type = listType.get(i);
                                        Map<String, Object> parameter = new HashMap<>();
                                        String functionName = "";
                                        if(type.equalsIgnoreCase("startUp") || type.equalsIgnoreCase("shutdown") || type.equalsIgnoreCase("flexibleChange")){
                                            functionName = apiProcessor.changeSimStatus;
                                            if(type.equalsIgnoreCase("startUp")){
                                                parameter.put("onStop","on");
                                            }else if(type.equalsIgnoreCase("shutdown")){
                                                parameter.put("onStop","off");
                                            }else{
                                                parameter.put("onStop",flexibleChange);
                                            }

                                        }else if(type.equalsIgnoreCase("openNet") || type.equalsIgnoreCase("disconnected")){
                                            functionName = apiProcessor.simApnFunction;
                                            if(type.equalsIgnoreCase("openNet")){
                                                parameter.put("onOff","on");
                                            }else if(type.equalsIgnoreCase("disconnected")){
                                                parameter.put("onOff","off");
                                            }
                                        }else if(type.equalsIgnoreCase("networkReset") ){
                                            functionName = apiProcessor.simGprsStatusReset;
                                        }
                                        List<Map<String,Object>> addList = new ArrayList<>();
                                        for (int j = 0; j < list.size(); j++) {
                                            Map<String, Object> rMap = null;
                                            Map<String, Object> iccidMap = list.get(j);
                                            String iccid = iccidMap.get("iccid").toString();
                                            if(functionName.equalsIgnoreCase(apiProcessor.changeSimStatus)){
                                                rMap = apiProcessor.changeSimStatus(iccid,null,parameter);
                                            }else if(functionName.equalsIgnoreCase(apiProcessor.simApnFunction)){
                                                rMap = apiProcessor.simApnFunction(iccid,null,parameter);
                                            }else if(functionName.equalsIgnoreCase(apiProcessor.simGprsStatusReset)){
                                                rMap = apiProcessor.simGprsStatusReset(iccid,null,null);
                                            }
                                            if(rMap!=null){//拿到返回结果
                                                addList.add(rMap);
                                                if(addList.size()%dbWSize==0 || (j+1)==list.size()){//达到一定数量时进行处理
                                                    storageDB(addList,newName,create_by,t_no,type,flexibleChangeValue,cardConnectionStatusOptions,cardStatusShowIdOptions);
                                                    addList = new ArrayList<>();//清空
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Map<String, Object> defoutColumns = new HashMap<>();
                            defoutColumns.put("message",e.getCause().toString());
                            defoutColumns.put("deptName",create_by);
                            defoutColumns.put("result","操作失败");
                            writeCSV.outCSVObj(list, newName,outcolumns, keys,defoutColumns,outSize);
                            log.error(">> BusinessHandlingCard-消费者- 批量业务执行 异常 [ Exception ] :{}<<", e.getMessage());
                        }
                } else {
                    String remark = "上传 "+lie+" 不在数据库中！请核对后重试！";
                    log.info(remark);
                    Map<String, Object> defoutColumns = new HashMap<>();
                    defoutColumns.put("message","操作 "+lie+" 平台中未找到！查询取消！");
                    defoutColumns.put("deptName",create_by);
                    defoutColumns.put("result","操作失败");
                    writeCSV.outCSVObj(list, newName,outcolumns, keys,defoutColumns,outSize);

                    performTaskUtil.updBatchDetails(t_no,list,"2",remark,starting_time,VeDate.getStringDate());//执行任务 结束

                }
              performTaskUtil.endOfTaskFile(tFile);//文件结束
              performTaskUtil.endOfTask(pTasks);//任务结束
            }else {
                log.error( "admin_cardUpdate_queue-消费者 上传表格无数据！无需执行");
            }
        }else {
             log.error("未找到需要操作的业务类型 操作取消 {}", JSON.toJSONString(pMap));
         }
    }







    /**
     * 变更业务信息写入 数据库 【执行任务详情、API业务记录表】
     * @param addList
     * @param newName
     * @param create_by
     * @param t_no
     * @param type
     * @param flexibleChangeValue
     * @param cardConnectionStatusOptions
     * @param cardStatusShowIdOptions
     */
    public void storageDB(List<Map<String,Object>> addList,String newName,String create_by,String t_no,String type,String flexibleChangeValue,List<Map<String, Object>> cardConnectionStatusOptions,List<Map<String, Object>> cardStatusShowIdOptions){

        List<Map<String,Object>> outArr = new ArrayList<>();
        String change_value = getChangeValue(type,flexibleChangeValue);
        String end_time = VeDate.getStringDate();

        for (int i = 0; i < addList.size(); i++) {
            Map<String, Object> rMap = addList.get(i);
            //开始写入到数据库 1.执行任务详情 2.os_card_api_business
            String status = rMap.get("status").toString();
            String msg = rMap.get("msg").toString();
            String iccid = rMap.get("iccid").toString();
            int state = 2;
            Map<String, Object> updMap = new HashMap<>();
            updMap.put("t_no",t_no);
            updMap.put("type",type);
            Map<String, Object> addMap = new HashMap<>();
            addMap.put("type",type);
            String source_data = "";
            Map<String, Object> outMap = new HashMap<>();
            String starting_time = end_time;
            if(status.equals("200") && rMap.get("retuenList")!=null){
                try {
                    List<Map<String, Object>> retuenList = (List<Map<String, Object>>) rMap.get("retuenList");
                    if(retuenList!=null && retuenList.size()>0){
                        Map<String, Object> retuenMap = retuenList.get(0);
                        //String fName = retuenMap.get("functionName").toString();
                        starting_time =  retuenMap.get("starting_time")!=null?retuenMap.get("starting_time").toString():VeDate.getStringDate();//请求执行 开始 时间

                        String iccid_i = retuenMap.get("iccid").toString();
                        Map<String, Object> rData = (Map<String, Object>) retuenMap.get("rData");
                        boolean bool = (boolean) rData.get("bool");
                        source_data = rData.get("source_data").toString();
                        state = bool?1:2;
                        updMap.put("iccid",iccid_i);
                        if(type.equals("startUp") || type.equals("shutdown") || type.equals("flexibleChange")){
                            source_data = dictUtil.getDictLabel(cardStatusShowIdOptions,source_data);//写入 源状态;
                        } else if (type.equals("openNet") || type.equals("disconnected")) {
                            source_data = dictUtil.getDictLabel(cardConnectionStatusOptions,source_data);//写入 源状态;
                        }


                        try {
                            if(!type.equals("networkReset")){//业务类型为 重置网络时 无需变更 信息
                                Map<String, Object> updCardMap = new HashMap<>();
                                updCardMap.put("iccid",iccid_i);
                                //修改 卡状态 或 断开网状态
                                if(type.equals("startUp") || type.equals("shutdown") || type.equals("flexibleChange")){
                                    String key = "";
                                    if(type.equals("startUp") || type.equals("networkReset") || flexibleChangeValue.equals("1") || flexibleChangeValue.equals("2") || flexibleChangeValue.equals("5") || flexibleChangeValue.equals("6")){
                                        key = "4";
                                    }else if(type.equals("shutdown") || flexibleChangeValue.equals("0")){
                                        key = "5";
                                    }else if(flexibleChangeValue.equals("3") || flexibleChangeValue.equals("8")){
                                        key = "1";
                                    }else if(flexibleChangeValue.equals("4") || flexibleChangeValue.equals("9")){
                                        key = "3";
                                    }
                                    Map<String,Object> ecV5StatusMap = cardStateTransition.getStateMap(key);
                                    String status_id = GetMapUtil.getValueToStr(ecV5StatusMap, "status_id", null);
                                    String status_show_id = GetMapUtil.getValueToStr(ecV5StatusMap, "status_show_id", null);
                                    updCardMap = GetMapUtil.putMap(updCardMap,"status_id",status_id);
                                    updCardMap = GetMapUtil.putMap(updCardMap,"status_show_id",status_show_id);

                                } else if (type.equals("openNet")) {
                                    updCardMap.put("connection_status","1");
                                }else if (type.equals("disconnected")) {
                                    updCardMap.put("connection_status","2");
                                }
                                mQSyncSend.sendUpdCard(updCardMap);
                            }

                        }catch (Exception ex){
                            log.error(" mQSyncSend.sendUpdCard 发送指令  异常 {}",ex.getMessage());

                        }
                    }else {
                        //updMap.put("iccid",iccid);
                    }
                }catch (Exception e){
                    log.error(" retuenList 解析  异常 {}",e.getMessage());
                }
            } else {
                //放入失败队列 批量新增 失败信息
                //updMap.put("iccid",iccid);
            }
            try {
                if(updMap.get("iccid")==null){
                    updMap.put("iccid",iccid);
                }
                updMap.put("starting_time",starting_time);
                updMap.put("end_time",end_time);
                updMap.put("state",state);
                performTaskUtil.updDetails(updMap);//修改执行任务详情
                int bsState = state==1?1:0;
                addMap.put("iccid",updMap.get("iccid"));
                addMap.put("state",bsState);
                addMap.put("source_data",source_data);
                addMap.put("source_type","batch_processing");//批量业务办理
                addMap.put("remark",msg);
                addMap.put("change_value",change_value);
                performTaskUtil.saveApiBs(addMap);//新增 APi 业务办理记录

                String result = state==1?"操作成功！":"操作失败……";
                outMap.put("iccid",updMap.get("iccid"));
                outMap.put("message",change_value);
                outMap.put("result",result);
                outMap.put("deptName",create_by);
                outArr.add(outMap);
            }catch (Exception e){
                log.error(" performTaskUtil [updDetails/saveApiBs]  异常 {}",e.getMessage());

            }

        }
        if(outArr.size()>0){
            writeCSV.outCSVObj(outArr, newName,outcolumns, keys,null,outSize);
        }
    }

    /**
     * 获取变更类型
     * @param type
     * @return
     */
    private String getChangeValue(String type,String flexibleChangeValue){
        String change_value = "";
        switch (type){
            case "startUp":
                change_value = "开机";
                break;
            case "shutdown":
                change_value = "停机";
                break;
            case "openNet":
                change_value = "开网";
                break;
            case "disconnected":
                change_value = "断网";
                break;
            case "networkReset":
                change_value = "网络重置";
                break;
            case "flexibleChange":
                change_value = flexibleChangeValue;
                break;
        }
        return change_value;
    }




}
