package top.iotos.consumer.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.common.utils.iotos.common.ExcelConfig;
import top.iotos.common.utils.iotos.service.PerformTaskUtil;
import top.iotos.common.utils.poi.WriteCSV;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.synApi.utils.iotos.common.ListCompare;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 导入卡列表 MQ
 */
@Slf4j
@Component
public class ImportCard {

    @Resource
    private WriteCSV writeCSV;
    @Resource
    private CardMapper cardMapper;
    @Resource
    private CardInfoMapper cardInfoMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private PerformTaskUtil performTaskUtil;

    
    private String outcolumns[] = {"执行总数", "成功总数", "失败总数", "执行描述", "执行人", "执行结果"};
    private String keys[] = {"count", "scsCon", "filCon", "describe", "deptName", "result"};
    private int OutSize = 50;//每 50条数据输出一次

    /**
     * 导入卡列表
     * @param msg
     * @throws IOException
     */
    @RabbitHandler
    @RabbitListener(queues = "admin_cardSave_queue")
    public void cardSave(String msg) throws IOException {
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            String filePath = map.get("filePath").toString();//项目根目录
            String readName = map.get("readName").toString();//上传新文件名
            String newName = map.get("newName").toString();//输出文件名
            Map<String,Object> user =  ( Map<String,Object>)map.get("user");//登录用户信息

            Map<String, Object> pTaskMap = null;
            try {
                pTaskMap =  map.get("pTaskMap")!=null? ( Map<String,Object>)map.get("pTaskMap"):null;
            }catch (Exception e){
                System.out.println("pTaskMap 获取异常 "+e.getMessage());
            }
            String prefix = "admin_cardSave_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            Object  wExecute = redisCache.getCacheObject(prefix+":"+ readName);
            if(wExecute==null){
                redisCache.setCacheObject(prefix+":"+ readName, msg, 30, TimeUnit.SECONDS);//30 秒缓存 避免 重复消费
                ImportCar(filePath,readName,user,newName,pTaskMap);//执行导入
            }
        } catch (Exception e) {
            log.error(">>错误 - 导入卡列表 消费者:{}<<", e.getMessage());
        }
    }


    /**
     * 导卡执行
     * @param filePath
     * @param readName
     * @param user
     * @param newName
     */
    public void ImportCar(String filePath, String readName,Map<String,Object> user,String newName,Map<String, Object> pTaskMap){
        String path = filePath +  readName;
        ExcelConfig excelConfig = new ExcelConfig();
        String columns[] = {"msisdn","iccid","imsi","imei","activate_date","dept_id","channel_id","status_show_id","status_id","w_real_name","nedd_real_name","type","network_type","w_sms","w_voice","sms_number","deliver_date","storage_date","delivery_date","w_polling","w_network_break","customize_grouping","remarks","shutdown_threshold","package_id","connection_status","w_pool","pool_code","payment_key","automatic_renewal","u_code","supplier","roaming_country","internet_signal","ip_attribution","operator","open_date"};
        String maxCNo  = cardMapper.findMaxCNO();
        maxCNo = maxCNo!=null?maxCNo:"18600000000";
        Long maxCNoInt = Long.parseLong(maxCNo);
        List<Map<String, String>> list = excelConfig.getExcelListMap(path,columns,maxCNoInt);
        Map<String, String> dept = (Map<String, String>)user.get("dept");

        String remark = user.get("importAutoRemark")!=null?user.get("importAutoRemark").toString():null;//自动化导入备注标记

        String  deptName = " [ "+dept.get("deptName")+" ] - "+" [ "+user.get("userName")+" ] ";
        String outcolumns[] = {"iccid","操作描述","执行时间","执行结果","执行人"};
        String keys[] = {"iccid","description","time","result","deptName"};

        if(list!=null && list.size()>0){
            //筛选出  iccid 重复项
            HashMap<String, Object> map = new HashMap<>();
            map.put("lists",list);
            map.put("batchType","1");
            List<String>  iccidarr = cardMapper.wExist(map);

            String saveUrl = "/getcsv/"+newName+".csv";
            String task_name ="卡列表 [导入] ";
            Map<String, Object> pTasks = new HashMap<>();
            Map<String, Object> tFile = new HashMap<>();


            if(pTaskMap!=null){
                pTasks = pTaskMap;
            }else{
                String t_no = "task-"+VeDate.getNo(4);

                pTasks.put("t_no",t_no);
                pTasks.put("name",task_name);
                pTasks.put("ts_type","cardImport");
                pTasks.put("remark",remark);

                tFile.put("t_no",t_no);
                tFile.put("url",saveUrl);
                tFile.put("show_type","_cardImport");//
                Map<String, Object> rMap = performTaskUtil.saveTask(t_no,null,pTasks,dept,user,tFile);
                pTasks = (Map<String, Object>) rMap.get("pTasks");
                List<Map<String, Object>> rTFiles = (List<Map<String, Object>>) rMap.get("rTFiles");
                tFile = rTFiles.get(0);
            }
            //1.判断上传数据与数据库现有数据做对比 大于 0 证明有 以存在数据
            if(iccidarr.size()>0){
                //上传数据>数据库查询 赛选出
                List<String> list1 = new ArrayList<>();
                for (int i = 0; i <list.size() ; i++) {
                    list1.add(list.get(i).get("iccid"));
                }
                //找出与数据库已存在 相同 ICCID 去除 重复 iccid
                List<Map<String, String>> Out_list_ListCompare = ListCompare.getIdentical(list1,iccidarr,"iccid");
                if(Out_list_ListCompare.size()>0){
                    writeCSV.outCSV(Out_list_ListCompare,newName,"ICCID重复导入失败！",deptName,"导入失败",outcolumns,keys);
                }
                list = ListCompare.delIdentical(list,iccidarr,"iccid");//删除相同的数据 进行批量上传
            }
            map.put("lists",list);//更新 list

            try {
                if(list.size()>0){
                    int  sInt = 0,sInfoInt = 0;
                    List<Map<String, String>> addList = new ArrayList<>();
                    Map<String, List<Map<String, String>>> addMap=new HashMap<>();
                    int maxCount = 2000;//导入批量处理 2k 条
                    for(int i=0;i<list.size();i++) {
                        addList.add(list.get(i));
                        if((i+1)%maxCount==0 || (i+1)==list.size()) {
                            addMap.put(""+i, addList);
                            addList=new ArrayList<>();
                        }
                    }
                    for(String key : addMap.keySet()){
                        List<Map<String, String>> lists = addMap.get(key);
                        map.put("lists",lists);//更新 list
                        sInt += cardMapper.save(map);
                        sInfoInt += cardInfoMapper.save(map);
                    }
                    if(sInt>0){
                        writeCSV.outCSV(list,newName,"成功导入 卡列表 数据 sInt ["+sInt+"] 条, sInfoInt ["+sInfoInt+"] 条 ",deptName,"导入成功",outcolumns,keys);
                    }
                }else{
                    log.error(">> admin-消费者- 上传数据已全部在数据库，无需上传卡信息！ :{}<<");
                }
            }catch (DuplicateKeyException e){
                System.out.println("===="+e.getCause().toString());
                writeCSV.outCSV(list,newName,e.getCause().toString(),deptName,"导入失败",outcolumns,keys);
                log.error(">> admin-消费者- 上传excel异常 [插入数据 DuplicateKeyException ] :{}<<", e.getMessage());
            }catch (Exception e){
                writeCSV.outCSV(list,newName,e.getCause().toString(),deptName,"导入失败",outcolumns,keys);
                log.error(">>admin-消费者- 卡列表带入 消费者:{}<<", e.getMessage());
            }
            performTaskUtil.endOfTaskFile(tFile);//文件结束
            performTaskUtil.endOfTask(pTasks);//任务结束
        }else{
            log.error( "admin-消费者 上传表格无数据！无需执行导入");
        }

    }




}