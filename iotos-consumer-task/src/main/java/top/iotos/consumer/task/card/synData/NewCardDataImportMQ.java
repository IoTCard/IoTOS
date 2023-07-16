package top.iotos.consumer.task.card.synData;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.mapper.mysql.card.synData.UpstreamCardMapper;
import top.iotos.common.mapper.mysql.sys.DictTypeMapper;
import top.iotos.common.utils.poi.WriteCSV;
import top.iotos.synApi.mapper.mysql.channel.ChannelInfoMapper;
import top.iotos.synApi.mapper.mysql.channel.ChannelMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * 上游卡号 标记数据执行新增 消费者
 */
@Slf4j
@Component
public class NewCardDataImportMQ {

    @Resource
    private UpstreamCardMapper upstreamCardMapper;
    @Resource
    private DictTypeMapper dictTypeMapper;
    @Resource
    private ChannelInfoMapper channelInfoMapper;
    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private WriteCSV writeCSV;
    @Resource
    private MQAide mQAide;
    private int outSize = 50;//每 50条数据输出一次


    @RabbitHandler
    @RabbitListener(queues = "task_newCardDataImport_queue")
    public void contrastCardDifference(String msg) {
        execute(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx_task_newCardDataImport_queue")
    public void dlxContrastCardDifference(String msg) {
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
            log.error(">>错误 - 上游卡号 对比差异 标记 消费者:{}<<", e.getMessage());
        }
    }


    public void execution(Map<String, Object> map){

        Map<String, Object> findMap = new HashMap<>();
        findMap.put("dictType","syn_supported_template");//支持上游同步模板
        List<Map<String, Object>> supportedList =  dictTypeMapper.selectDictDataList(findMap);

        if(supportedList!=null && supportedList.size()>0){
            List<String> status = new ArrayList<>();
            List<String> templateList = new ArrayList<>();
            for (int i = 0; i < supportedList.size(); i++) {
                Map<String, Object> obj = supportedList.get(i);
                templateList.add(obj.get("dictValue").toString());
            }
            status.add("0");
            map.put("status",status);
            map.put("templateList",templateList);
            List<Map<String, Object>> list = channelMapper.getList(map);//通道状态正常
            if(list!=null && list.size()>0){

                Map<String, Object> user = new HashMap<>();
                Map<String, Object> dept = new HashMap<>();
                dept.put("deptName", "总平台");
                dept.put("deptId", "100");
                user.put("dept", dept);


                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> cMap = list.get(i);
                    cMap.put("w_sync_upstream","1");
                    Map<String, Object> sendMap = channelInfoMapper.find(cMap);
                    if(sendMap!=null){
                        String channel_id = sendMap.get("c_no").toString();
                        String sync_change_notification = sendMap.get("sync_change_notification")!=null?sendMap.get("sync_change_notification").toString():"0";
                        user.put("userName", "系统自动同步");
                        user.put("importAutoRemark", "通道编号["+channel_id+"]");

                        Map<String, Object> fMap = new HashMap<>();
                        fMap.put("w_new","1");
                        List<Map<String, Object>> needAddList = upstreamCardMapper.getList(fMap);// 获取标记 新增卡号
                        if (needAddList != null && needAddList.size() > 0) {
                            List<Map<String, Object>> addList = new ArrayList<>();
                            boolean imsi_bool = false, activate_date_bool = false, open_date_bool = false, remark_bool = false;//判断那些需要 修改或新增的字段
                            String syncfieldArr[] = sendMap.get("sync_field") != null ? sendMap.get("sync_field").toString().split(",") : null;//同步的字段
                            if (syncfieldArr != null) {
                                for (int j = 0; j < syncfieldArr.length; j++) {
                                    String str_toLowerCase = syncfieldArr[j] != null ? syncfieldArr[j].toLowerCase() : "";
                                    switch (str_toLowerCase) {
                                        case "imsi":
                                            imsi_bool = true;
                                            break;
                                        case "activate_date":
                                            activate_date_bool = true;
                                            break;
                                        case "open_date":
                                            open_date_bool = true;
                                            break;
                                        case "remark":
                                            remark_bool = true;
                                            break;
                                    }
                                }
                            }
                            for (int j = 0; j < needAddList.size(); j++) {
                                Map<String, Object> recentSync_Map = needAddList.get(j);
                                String msisdn = recentSync_Map.get("msisdn") != null ? recentSync_Map.get("msisdn").toString() : null;
                                String iccid = recentSync_Map.get("iccid") != null ? recentSync_Map.get("iccid").toString() : null;
                                String imsi = recentSync_Map.get("imsi") != null ? recentSync_Map.get("imsi").toString() : null;
                                String activate_date = recentSync_Map.get("activate_date") != null ? recentSync_Map.get("activate_date").toString() : null;
                                String open_date = recentSync_Map.get("open_date") != null ? recentSync_Map.get("open_date").toString() : null;
                                String remark = recentSync_Map.get("remark") != null ? recentSync_Map.get("remark").toString() : null;
                                if (msisdn != null && iccid != null) {
                                    //默认同步字段 msisdn，iccid channel_id 且不能为空
                                    Map<String, Object> add_Map = new HashMap<>();
                                    add_Map.put("msisdn", msisdn);
                                    add_Map.put("iccid", iccid);
                                    add_Map.put("channel_id", channel_id);
                                    if (imsi_bool) {
                                        add_Map.put("imsi", imsi);
                                    }
                                    if (activate_date_bool) {
                                        add_Map.put("activate_date", activate_date);
                                    }
                                    if (open_date_bool) {
                                        add_Map.put("open_date", open_date);
                                    }
                                    if (remark_bool) {
                                        add_Map.put("remark", remark);
                                    }
                                    addList.add(add_Map);
                                }
                            }
                            if (addList != null && addList.size() > 0) {
                                //模拟文件上传动作 发送指令
                                try {
                                    String newName = UUID.randomUUID().toString().replace("-", "") + "_cardImport";
                                    String keys[] = {"msisdn","iccid","imsi","imei","activate_date","dept_id","channel_id","status_show_id","status_id","w_real_name","nedd_real_name","type","network_type","w_sms","w_voice","sms_number","deliver_date","storage_date","delivery_date","w_polling","w_network_break","customize_grouping","remarks","shutdown_threshold","package_id","connection_status","w_pool","pool_code","payment_key","automatic_renewal","u_code","supplier","roaming_country","internet_signal","ip_attribution","operator"};
                                    ArrayList outList = new ArrayList<Map<String,Object>>();
                                    String flieUrlRx = "/upload/importCard";
                                    for (int j = 0; j < addList.size(); j++) {
                                        outList.add(addList.get(j));
                                        if ((j+1)%outSize==0 || (j+1)==addList.size()){
                                            writeCSV.Write(newName, outList, keys, null, keys, flieUrlRx, true);
                                            outList = new ArrayList<Map<String,Object>>();
                                        }
                                    }
                                    writeCSV.csvOrExcle(flieUrlRx+"/"+newName, null, false,true);
                                    Map<String, Object> notifyMap = new HashMap<>();
                                    notifyMap.put("name",sendMap.get("name"));
                                    notifyMap.put("newCount",addList.size());

                                    boolean bool = uploadCard( flieUrlRx+"/"+newName+".xls", newName, user, sync_change_notification,notifyMap) ;
                                        if(bool){
                                        //修改 需要新增状态
                                        Map<String,Object> upd_Map = new HashMap<>();
                                        upd_Map.put("w_new","0");
                                        upd_Map.put("updList",addList);
                                        int upd_count = upstreamCardMapper.updateBatch(upd_Map);
                                        log.info(" ==== {} [w_new = 0 ] 卡Count {} 执行成功数量 {} ==== ", JSON.toJSONString(sendMap),addList.size(),upd_count);
                                    }
                                } catch (Exception e) {
                                    log.error("模拟上传 csvOrExcle 异常 {}",e.getMessage());
                                }

                            }
                        }
                        

                    }
                }
            }
        }






    }


    /**
     * 模拟发送 卡列表导入
     * @param readName
     * @param newName
     * @param user
     * @param sync_change_notification 是否通知
     * @param notifyMap 通知参数
     */
    public boolean uploadCard(String readName,String newName,Map<String, Object> user,String sync_change_notification,Map<String, Object> notifyMap) {
        boolean bool = false;
        try {
            File file2 = new File("");
            String filePath = file2.getCanonicalPath();

            //发送消息
            String queueName = "admin_cardSave_queue";
            String routingKey = "admin.cardSave.queue";
            String exchangeName = "admin_exchange";//路由

            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("filePath", filePath);//项目根目录
            sendMap.put("readName", readName);//上传新文件名
            sendMap.put("newName", newName);//输出文件名
            sendMap.put("user", user);//登录用户信息

            bool = mQAide.send(exchangeName,routingKey,30,sendMap);
           if(bool){
               //判断通道信息变更时是否需要通知 需要通知时进行 通知 [后续完善]
               if(sync_change_notification!=null && sync_change_notification.equals("1")){


               }
           }
        } catch (Exception e) {
            log.error(" 模拟发送导入卡列表 指令 异常 {}",e.getMessage());
        }
        return bool;
    }



}
