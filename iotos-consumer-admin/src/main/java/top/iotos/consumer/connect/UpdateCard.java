package top.iotos.consumer.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.common.utils.iotos.common.ExcelConfig;
import top.iotos.synApi.utils.iotos.common.DbFieldArr;
import top.iotos.synApi.utils.iotos.common.ListCompare;
import top.iotos.synApi.utils.iotos.service.CardUpdate;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.common.utils.iotos.service.PerformTaskUtil;
import top.iotos.synApi.utils.iotos.time.VeDate;
import top.iotos.common.utils.poi.WriteCSV;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 批量上传 卡列表修改  消费者
 */
@Slf4j
@Component
public class UpdateCard {


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
    private CardUpdate cardUpdate;


    private String outcolumns[] =  {"c_no","msisdn","iccid","imsi","imei","activate_date","dept_id","channel_id","status_show_id","status_id","w_real_name","nedd_real_name","type","network_type","w_sms","w_voice","sms_number","deliver_date","storage_date","delivery_date","w_polling","w_network_break","customize_grouping","remarks","shutdown_threshold","package_id","connection_status","w_pool","pool_code","payment_key","automatic_renewal","u_code","supplier","roaming_country","internet_signal","ip_attribution","operator","执行人","执行描述","执行结果"};
    private String keys[] =        {"c_no","msisdn","iccid","imsi","imei","activate_date","dept_id","channel_id","status_show_id","status_id","w_real_name","nedd_real_name","type","network_type","w_sms","w_voice","sms_number","deliver_date","storage_date","delivery_date","w_polling","w_network_break","customize_grouping","remarks","shutdown_threshold","package_id","connection_status","w_pool","pool_code","payment_key","automatic_renewal","u_code","supplier","roaming_country","internet_signal","ip_attribution","operator","deptName","message","result"};
    private int outSize = 50;//每 50条数据输出一次

    /**
     * 卡列表修改
     * @param msg
     * @throws IOException
     */
    @RabbitHandler
    @RabbitListener(queues = "admin_cardUpdate_queue")
    public void cardImportReplace(String msg) {
        execute(msg);
    }



    /**
     * 卡列表修改
     * @param msg
     * @throws IOException
     */
    @RabbitHandler
    @RabbitListener(queues = "dlx_admin_cardUpdate_queue")
    public void dlxCardImportReplace(String msg) {
        execute(msg);
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
            String BUPName = map.get("BUPName").toString();//
            String prefix = "admin_cardUpdate_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            Object  wExecute = redisCache.getCacheObject(prefix+":"+ readName);
            if(wExecute==null){
                redisCache.setCacheObject(prefix+":"+ readName, msg, 3, TimeUnit.SECONDS);//3 秒缓存 避免 重复消费
                execution(filePath,readName,pMap,user,null,newName,BUPName);//执行卡列表修改
            }
        } catch (Exception e) {
            log.error(">>错误 - 卡列表修改 消费者:{}<<", e.getMessage());
        }
    }



    /**
     * 变更 执行
     */
    public void execution(String filePath,String readName,Map<String,Object> pMap,Map<String,Object> user,Map<String,Object> pTaskMap,String newName,String BUPName){
        //1.读取 上传文件
        String path = filePath +  readName;
        ExcelConfig excelConfig = new ExcelConfig();
        String columns[] = {"c_no","msisdn","iccid","imsi","imei","activate_date","dept_id","channel_id","status_show_id","status_id","w_real_name","nedd_real_name","type","network_type","w_sms","w_voice","sms_number","deliver_date","storage_date","delivery_date","w_polling","w_network_break","customize_grouping","remarks","shutdown_threshold","package_id","connection_status","w_pool","pool_code","payment_key","automatic_renewal","u_code","supplier","roaming_country","internet_signal","ip_attribution","operator"};
        List<Map<String, Object>> list = excelConfig.getExcelListMap(path,columns);
        Map<String, String> dept = (Map<String, String>)user.get("dept");
        String  create_by = " [ "+dept.get("deptName")+" ] - "+" [ "+user.get("userName")+" ] ";
        String deptId  = user.get("deptId").toString();

        Map<String, Object> pTasks = new HashMap<>();
        Map<String, Object> tFile = new HashMap<>();
        Map<String, Object> bk_tFile = new HashMap<>();

        String task_name ="卡列表 [更新] ";
        String saveUrl = "/getcsv/"+newName+".csv";
        String bkUrl = "/getcsv/"+BUPName+".csv";


        if(pTaskMap!=null){
            pTasks = pTaskMap;
        }else{
            String t_no = "task-"+VeDate.getNo(4);

            pTasks.put("t_no",t_no);
            pTasks.put("name",task_name);
            pTasks.put("ts_type","cardUpdate");

            tFile.put("t_no",t_no);
            tFile.put("url",saveUrl);
            tFile.put("show_type","_cardUpdate");//

            bk_tFile.put("t_no",t_no);
            bk_tFile.put("url",bkUrl);
            bk_tFile.put("show_type","_cardUpdateBUP");//


            Map<String, Object> rMap = performTaskUtil.saveTask(t_no,null,pTasks,dept,user,tFile,bk_tFile);
            pTasks = (Map<String, Object>) rMap.get("pTasks");
            List<Map<String, Object>> rTFiles = (List<Map<String, Object>>) rMap.get("rTFiles");
            tFile = rTFiles.get(0);
            bk_tFile = rTFiles.get(1);
        }

       Integer batchType = Integer.parseInt(pMap.get("batchType").toString());
        String lie = "";
        switch (batchType){
            case 0:
                lie = "c_no";
                break;
            case 1:
                lie = "iccid";
                break;
            case 2:
                lie = "msisdn";
                break;
            case 3:
                lie = "imsi";
                break;
        }

        if(list!=null && list.size()>0) {
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


            //查询数据库中 匹对iccid 是否存在
            HashMap<String, Object> map = new HashMap<>();
            //添加修改数据
            map.put("lists",list);
            map.put("batchType",batchType);
            map.put("batchIn","in");

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
                            Map<String, Object> defoutColumns = new HashMap<>();
                            defoutColumns.put("message","["+lie+"] 不在数据库中！请核对["+lie+"]卡号！");
                            defoutColumns.put("deptName",create_by);
                            defoutColumns.put("result","操作失败");
                            writeCSV.outCSVObj(out_list_Different, newName,outcolumns, keys,defoutColumns,outSize);
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
                                        Map<String, Object> defoutColumns = new HashMap<>();
                                        defoutColumns.put("message","越权操作！ iccid 不在操作企业下！请核对iccid卡号！");
                                        defoutColumns.put("deptName",create_by);
                                        defoutColumns.put("result","操作失败");
                                        writeCSV.outCSVObj(out_list_Different, newName,outcolumns, keys,defoutColumns,outSize);
                                    }
                                }
                            }
                            map.put("lists", list);//更新 list

                            if(list.size()>0){
                                //1.备份 修改前数据 主表
                               List<String> batchList = new ArrayList<>();
                                for (int i = 0; i < list.size(); i++) {
                                    batchList.add(list.get(i).get(lie).toString());
                                }
                                map.put("batchList",batchList);
                                List<Map<String, Object>> BakUpArr =  cardInfoMapper.getList(map);
                                if(BakUpArr!=null && BakUpArr.size()>0){
                                    Map<String, Object> defoutColumns = new HashMap<>();
                                    defoutColumns.put("message","操作前数据备份 操作时间："+ VeDate.getStringDate());
                                    defoutColumns.put("deptName",create_by);
                                    defoutColumns.put("result","操作成功");
                                    //备份导出列
                                   writeCSV.outCSVObj(BakUpArr, BUPName,outcolumns, keys,defoutColumns,outSize);
                                }

                                int updCount = 0;
                                int updInfoCount = 0;
                                List<Map<String, Object>> outCardArr = new ArrayList<>();
                                //2.修改主表
                                for (int i = 0; i < list.size(); i++) {
                                    Map<String, Object> uObj = list.get(i);
                                    String Msg = "",result = "";
                                    try {
                                        //System.out.println(uObj);
                                        uObj.put("batchType",batchType);
                                        uObj.put("bValue",uObj.get(lie));

                                        updCount += cardUpdate.cardUpdate(uObj);
                                        updInfoCount += cardUpdate.cardInfoUpdate(uObj);//同步 详情信息

                                        result = "操作成功";
                                    }catch (Exception e){
                                        Msg = e.getMessage();
                                        log.error(">>错误 - cardMapper.update :{} | {}<<", Msg);
                                        Msg = Msg.length()>100?Msg.substring(0,100):Msg;
                                        result = "操作失败";
                                    }
                                    uObj.put("message",Msg);
                                    uObj.put("deptName",create_by);
                                    uObj.put("result",result);
                                    outCardArr.add(uObj);
                                }
                                if(outCardArr.size()>0){
                                    writeCSV.outCSVObj(outCardArr, newName,outcolumns, keys,null,outSize);
                                }
                                log.info("本次更新数据 t_no {} <br/> updCount:{} , updInfoCount{}",pTasks.get("t_no"),updCount,updInfoCount);
                            }
                        }
                    } catch (Exception e) {
                        Map<String, Object> defoutColumns = new HashMap<>();
                        defoutColumns.put("message",e.getCause().toString());
                        defoutColumns.put("deptName",create_by);
                        defoutColumns.put("result","操作失败");
                        writeCSV.outCSVObj(list, newName,outcolumns, keys,defoutColumns,outSize);
                        log.error(">> cardSet-消费者- 上传excel异常 [ Exception  ] :{}<<", e.getMessage());
                    }
            } else {
                log.info("上传 "+lie+" 不在数据库中！请核对后重试！");
                Map<String, Object> defoutColumns = new HashMap<>();
                defoutColumns.put("message","操作 "+lie+" 平台中未找到！查询取消！");
                defoutColumns.put("deptName",create_by);
                defoutColumns.put("result","操作失败");
                writeCSV.outCSVObj(list, newName,outcolumns, keys,defoutColumns,outSize);
            }
        }else {
            log.error( "admin_cardUpdate_queue-消费者 上传表格无数据！无需执行");
        }
        performTaskUtil.endOfTaskFile(tFile);//文件结束
        performTaskUtil.endOfTaskFile(bk_tFile);//文件结束
        performTaskUtil.endOfTask(pTasks);//任务结束
    }



}
