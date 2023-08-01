package top.iotos.consumer.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.utils.iotos.service.DictUtil;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.common.utils.iotos.service.PerformTaskUtil;
import top.iotos.synApi.utils.iotos.time.VeDate;
import top.iotos.common.utils.poi.WriteCSV;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 卡列表导出  消费者
 */
@Slf4j
@Component
public class ExportCard {


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



    private int outSize = 50;//每 50条数据输出一次

    /**
     * 卡列表导出
     * @param msg
     * @throws IOException
     */
    @RabbitHandler
    @RabbitListener(queues = "admin_cardExport_queue")
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

            String prefix = "admin_cardExport_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            Object  wExecute = redisCache.getCacheObject(prefix+":"+ deptId);
            if(wExecute==null){
                redisCache.setCacheObject(prefix+":"+ deptId, msg, 10, TimeUnit.SECONDS);//10 秒缓存 避免 重复消费
                execution(pMap,map,user,deptId);
            }
        } catch (Exception e) {
            log.error(">>错误 - 卡列表导出 消费者:{}<<", e.getMessage());
        }
    }



    /**
     * 变更 执行
     */
    public void execution(Map<String, Object> pMap,Map<String, Object> map,Map<String,Object> user,String deptId){



        List<String> iccidArr = (List<String>) map.get("iccidArr");
        Map<String, Object> findMap = new HashMap<>();
        findMap.put("batchList",iccidArr);
        findMap.put("batchType","1");
        findMap.put("headquarters",map.get("headquarters"));
        List<Map<String, Object>> exportArr = cardInfoMapper.getList(findMap);
        if(exportArr!=null && exportArr.size()>0){
            List<Map<String, Object>> deptsOptions = (List<Map<String, Object>>) pMap.get("deptsOptions");//企业
            List<Map<String, Object>> channelOptions = (List<Map<String, Object>>) pMap.get("channelOptions");//通道
            List<Map<String, Object>> cardStatusShowOptions = (List<Map<String, Object>>) pMap.get("cardStatusShowOptions");//字典 【卡状态】
            List<Map<String, Object>> cardTypeOptions = (List<Map<String, Object>>) pMap.get("cardTypeOptions");//字典 【卡类型】
            List<Map<String, Object>> cardNetworkTypeOptions = (List<Map<String, Object>>) pMap.get("cardNetworkTypeOptions");//字典 【卡网络类型】
            List<Map<String, Object>> whetherOptions = (List<Map<String, Object>>) pMap.get("whetherOptions");//字典 【是否】
            List<Map<String, Object>> cardConnectionStatusOptions = (List<Map<String, Object>>) pMap.get("cardConnectionStatusOptions");//字典 【断开网状态】
            List<Map<String, Object>> cardStatusOptions = (List<Map<String, Object>>) pMap.get("cardStatusOptions");//字典 【卡状态描述】



            Map<String, String> dept = (Map<String, String>)user.get("dept");
            String newName = UUID.randomUUID().toString().replace("-","")+"_cardExport";

            String task_name ="卡列表 [导出]";
            String saveUrl = "/getcsv/"+newName+".csv";

            Map<String, Object> pTasks = new HashMap<>();
            Map<String, Object> tFile = new HashMap<>();

            String t_no = "task-"+VeDate.getNo(4);

            pTasks.put("t_no",t_no);
            pTasks.put("name",task_name);
            pTasks.put("ts_type","cardExport");

            tFile.put("t_no",t_no);
            tFile.put("url",saveUrl);
            tFile.put("show_type","_cardExport");

            Map<String, Object> rMap = performTaskUtil.saveTask(t_no,null,pTasks,dept,user,tFile);
            pTasks = (Map<String, Object>) rMap.get("pTasks");
            List<Map<String, Object>> rTFiles = (List<Map<String, Object>>) rMap.get("rTFiles");
            tFile = rTFiles.get(0);


            String outcolumns[] = {};
            String keys[] = {};

            if(deptId.equals("100")){
                outcolumns = new String[]{"编号","自定义编号","MSISDN","ICCID","IMSI","IMEI","激活时间","所属企业","通道编号","卡状状态","卡状状态描述","资费总量-和","资费已用流量-和","资费剩余用量-和","创建时间","是否删除","首次用量使用时间","用量同步时间","状态同步时间","是否实名","是否需要实名","卡类型","网络类型","是否支持短信发送","是否支持语音功能","短信服务号","发货日期","入库日期","服务-生效时间","服务-失效时间","出库日期","是否-轮询","是否-未订购断网","自定义分组","备注","停机阈值","资费组编号","断开网状态","是否流量池","流量池编号","更新时间","单卡预存余额","单卡支付密码","是否自动续费","用卡人编号","供应商","漫游国家","网络信号","ip归属地","运营商"};
                keys = new String[]{"id","c_no","msisdn","iccid","imsi","imei","activate_date","dept_name","channel_value","status_show_value","status_value","c_total","c_used","c_left","create_time","deleted_value","first_time_using","traffic_sync_time","state_sync_time","real_name_value","nedd_real_value","type_value","network_type_value","sms_value","voice_value","sms_number","deliver_date","storage_date","prodinsteff_time","prodinstexp_time","delivery_date","polling_value","network_break_value","customize_grouping","remarks","shutdown_threshold","package_id","connection_value","pool_value","pool_code","update_date","balance","payment_key","automatic_renewal_value","u_code","supplier","roaming_country","internet_signal","ip_attribution","operator"};
            }else{
                outcolumns = new String[]{"自定义编号","MSISDN","ICCID","IMSI","IMEI","激活时间","所属企业","通道编号","卡状状态","资费总量-和","资费已用流量-和","资费剩余用量-和","创建时间","用量同步时间","状态同步时间","是否实名","是否需要实名","卡类型","网络类型","是否支持短信发送","是否支持语音功能","短信服务号","发货日期","服务-生效时间","服务-失效时间","出库日期","自定义分组","备注","资费组编号","是否自动续费","用卡人编号","漫游国家","网络信号","ip归属地","运营商"};
                keys = new String[]{"c_no","msisdn","iccid","imsi","imei","activate_date","dept_name","channel_value","status_show_value","c_total","c_used","c_left","create_time","traffic_sync_time","state_sync_time","real_name_value","nedd_real_value","type_value","network_type_value","sms_value","voice_value","sms_number","deliver_date","prodinsteff_time","prodinstexp_time","delivery_date","customize_grouping","remarks","package_id","automatic_renewal_value","u_code","roaming_country","internet_signal","ip_attribution","operator"};
            }

            List<Map<String,Object>> out_list = new ArrayList<Map<String,Object>>();
            //循环添加单卡数据信息
            for (int i = 0; i < exportArr.size(); i++) {
                Map<String,Object> carMap =  exportArr.get(i);

                carMap = dictUtil.writeDict(carMap,cardStatusShowOptions,"status_show_id","status_show_value");//卡状态
                carMap = dictUtil.writeDict(carMap,cardTypeOptions,"type","type_value"); //卡类型
                carMap = dictUtil.writeDict(carMap,whetherOptions,"w_network_break","network_break_value");//是否-未订购断网
                carMap = dictUtil.writeDict(carMap,cardConnectionStatusOptions,"connection_status","connection_value");//断开网状态
                carMap = dictUtil.writeDict(carMap,cardNetworkTypeOptions,"network_type","network_type_value");//卡板网络类型
                carMap = dictUtil.writeDict(carMap,cardStatusOptions,"status_id","status_value");//卡状态描述

                carMap = dictUtil.writeDict(carMap,whetherOptions,"w_polling","polling_value");//是否-轮询
                carMap = dictUtil.writeDict(carMap,whetherOptions,"w_pool","pool_value");//是否-流量池
                carMap = dictUtil.writeDict(carMap,whetherOptions,"w_real_name","real_name_value");//是否-实名
                carMap = dictUtil.writeDict(carMap,whetherOptions,"nedd_real_name","nedd_real_value");//是否-需要实名
                carMap = dictUtil.writeDict(carMap,whetherOptions,"automatic_renewal","automatic_renewal_value");//是否-自动续费
                carMap = dictUtil.writeDict(carMap,whetherOptions,"w_sms","sms_value");//是否-支持短信发送
                carMap = dictUtil.writeDict(carMap,whetherOptions,"w_voice","voice_value");//是否-语音
                carMap = dictUtil.writeDict(carMap,whetherOptions,"deleted","deleted_value");//是否-删除


                carMap = dictUtil.writeDict(carMap,channelOptions,"channel_id","channel_value");//通道
                carMap = dictUtil.writeDept(carMap,deptsOptions);//企业


                //删除导出不需要的字段
                carMap.remove("status_show_id");
                carMap.remove("type");
                carMap.remove("w_network_break");
                carMap.remove("connection_status");
                carMap.remove("network_type");
                carMap.remove("status_id");
                carMap.remove("w_polling");
                carMap.remove("w_pool");
                carMap.remove("w_real_name");
                carMap.remove("nedd_real_name");
                carMap.remove("automatic_renewal");
                carMap.remove("w_sms");
                carMap.remove("w_voice");
                carMap.remove("channel_id");
                carMap.remove("dept_id");
                out_list.add(carMap);

                if ((i+1)%outSize==0 || (i+1)==exportArr.size()){
                    writeCSV.Write(newName,out_list, outcolumns, null,keys);
                    out_list = new ArrayList<Map<String,Object>>();
                }
            }
            performTaskUtil.endOfTaskFile(tFile);//文件结束
            performTaskUtil.endOfTask(pTasks);//任务结束
        }
    }


}