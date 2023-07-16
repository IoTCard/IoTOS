package top.iotos.quartz.task.iotos.card.synData;


import org.springframework.stereotype.Component;
import top.iotos.common.mapper.mysql.card.synData.UpstreamCardMapper;
import top.iotos.common.mapper.mysql.card.synData.oneLink.Ecv5GroupMapper;
import top.iotos.common.mapper.mysql.sys.DictTypeMapper;
import top.iotos.synApi.mapper.mysql.channel.ChannelInfoMapper;
import top.iotos.synApi.mapper.mysql.channel.ChannelMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("synUpstreamCardTask")
public class SynUpstreamCardTask {

    @Resource
    private ChannelInfoMapper channelInfoMapper;
    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private DictTypeMapper dictTypeMapper;
    @Resource
    private Ecv5GroupMapper ecv5GroupMapper;
    @Resource
    private UpstreamCardMapper upstreamCardMapper;
    @Resource
    private MQAide mQAide;

    /**
     * 同步上游卡列表
     */
    public void send()
    {
        common(true,null,null);
    }


    /**
     * 同步上游卡列表总数核验 数量不对时 再次发送同步指令
     */
    public void verificationTotal()
    {
        common(false,null,null);
    }

    /**
     * 对比差异 标记
     */
    public void contrastDifference()
    {
        common(true,"task.contrastCardDifference.queue",null);
    }



    public void common(boolean bool,String routingKey,String type){
        String exchangeName = "task_exchange";//路由

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
            Map<String, Object> map = new HashMap<>();
            map.put("status",status);
            map.put("templateList",templateList);
            List<Map<String, Object>> list = channelMapper.getList(map);//通道状态正常
            if(list!=null && list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> cMap = list.get(i);
                    cMap.put("w_sync_upstream","1");
                    Map<String, Object> sendMap = channelInfoMapper.find(cMap);
                    if(sendMap!=null){
                        String template = sendMap.get("template").toString();


                        Map<String, Object> initMap = new HashMap<>();
                        initMap.put("url",sendMap.get("url"));
                        initMap.put("parameter_one",sendMap.get("parameter_one"));
                        initMap.put("parameter_tow",sendMap.get("parameter_tow"));
                        initMap.put("parameter_three",sendMap.get("parameter_three"));

                         if(!bool){
                             Map<String, Object> fMap = new HashMap<>();
                             fMap.put("channel_id",cMap.get("c_no"));
                             //开始核验总数
                             switch (template){
                                 case "oneLink_ECV5":
                                     if(type!=null && type.equals("synDataRecovery")){//多线程同步 脏读数据 修复
                                         //查询通道下对表是否有数据 错误 或 没有iccid 卡号 》 发送到 msisdn 获取 卡信息 队列
                                         List<Map<String, Object>> msindnList = new ArrayList<>();
                                         List<Map<String, Object>> repeatList = upstreamCardMapper.findIccidRepeat(fMap);
                                         List<Map<String, Object>> nullList = upstreamCardMapper.findIccidNull(fMap);
                                        if(repeatList!=null && repeatList.size()>0){
                                            msindnList.addAll(repeatList);
                                        }
                                        if(nullList!=null && nullList.size()>0){
                                            msindnList.addAll(nullList);
                                        }
                                         if(msindnList!=null && msindnList.size()>0){
                                             for (int j = 0; j < msindnList.size(); j++) {
                                                 Map<String, Object> msisdnMap = msindnList.get(j);
                                                 Map<String, Object> sMap = new HashMap<>();
                                                 sMap.put("initMap",initMap);
                                                 sMap.put("channel",sendMap);
                                                 sMap.put("msisdn",msisdnMap.get("msisdn"));
                                                 sMap.put("id",msisdnMap.get("id"));
                                                 mQAide.send(exchangeName, "task.synEcV5MsisdnGetInfo.queue", 30, sMap);
                                             }
                                         }
                                     }else {

                                         List<Map<String, Object>> rList= ecv5GroupMapper.getList(fMap);
                                         if(rList!=null && rList.size()>0){
                                             for (int j = 0; j < rList.size(); j++) {
                                                 Map<String, Object> rMap = rList.get(j);
                                                 Integer up_total_count = Integer.parseInt(rMap.get("up_total_count").toString());
                                                 if(up_total_count!=null && up_total_count>0){
                                                     Integer total_count = upstreamCardMapper.getListCount(fMap);
                                                     total_count = total_count!=null?total_count:0;
                                                     if(total_count<up_total_count){
                                                         bool = true;
                                                         break;
                                                     }
                                                 }
                                             }
                                         }
                                     }

                                     break;
                             }
                         }
                        if(bool){
                            routingKey = routingKey!=null?routingKey:"task.upstream_"+template+".queue";
                            mQAide.send(exchangeName, routingKey, 30, sendMap);


                        }
                    }
                }
            }
        }
    }


    /**
     * 标记数据执行新增
     */
    public void newDataImport()
    {
        String exchangeName = "task_exchange";//路由
        String queueName  = "task_newCardDataImport_queue";
        String routingKey = "task.newCardDataImport.queue";
        Map<String, Object> sendMap = new HashMap<>();
        mQAide.send(exchangeName, routingKey, 30, sendMap);
    }


    /**
     * 多线程同步 脏读数据 修复
     */
    public void synDataRecovery()
    {

        common(false,null,"synDataRecovery");

    }





}
