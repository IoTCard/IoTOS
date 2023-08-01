package top.iotos.synApi.utils.sync;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.channel.ChannelInfoMapper;
import top.iotos.synApi.upstreamApi.chinaMobile.oneLink.ecV5.EcV5SyncAlgorithm;
import top.iotos.synApi.upstreamApi.chinaMobile.oneLink.ecV5.InquireEcV5;
import top.iotos.synApi.upstreamApi.config.RedisUtil;
import top.iotos.synApi.utils.iotos.common.GetMapUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * API 请求处理器
 */
@Component
public class ApiProcessor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisUtil redisUtil;
    private final int successStatus = 200;//成功
    private final int errorStatus = 500;//错误
    private final int notConfigStatus = 417;//未配置数据
    private final int disabledStatus = 405;//禁用

    public final String synInfo = "synInfo";//一键同步
    public final String synFlow = "synFlow";//同步用量
    public final String querySimStatus = "querySimStatus";//同步卡状态
    public final String querySimSession = "querySimSession";//同步 在线信息
    public final String synActivateDate = "synActivateDate";//同步 激活时间
    public final String querySimRealNameStatus = "querySimRealNameStatus";//同步 实名状态
    public final String querySimImei = "querySimImei";//同步 IMEI
    public final String changeSimStatus = "changeSimStatus";//变更 卡状态
    public final String simGprsStatusReset = "simGprsStatusReset";//重置网络
    public final String simApnFunction = "simApnFunction";//变更 网络状态

    public final String queryOfferingsDetail = "queryOfferingsDetail";//资费详情实时查询
    public final String querySimManageStopRestartStatus = "querySimManageStopRestartStatus";//物联卡管理停复机冻结状态查询
    public final String queryLocationSavedCards = "queryLocationSavedCards";//查询物联卡轨迹存储
    public final String queryHistoricalLocation = "queryHistoricalLocation";//物联卡历史轨迹信息查询
    public final String queryLocationMonitorAlarm = "queryLocationMonitorAlarm";//物联卡位置变动告警记录查询
    public final String querySimStopReason = "querySimStopReason";//单卡停机原因查询
    public final String querySimCommunicationFunctionStatus = "querySimCommunicationFunctionStatus";//单卡通信功能开通查询
    public final String regionLimitStatus = "regionLimitStatus";//物联卡区域限制状态查询
    public final String querySimRegionLimitArea = "querySimRegionLimitArea";//物联卡区域限制区域查询
    public final String queryOnOffStatus = "queryOnOffStatus";//单卡开关机状态实时查询
    public final String queryCardBindStatus = "queryCardBindStatus";//物联卡机卡分离状态查询
    public final String querySimChangeHistory = "querySimChangeHistory";//单卡状态变更历史查询







    @Resource
    private CardInfoMapper cardInfoMapper;
    @Resource
    private EcV5SyncAlgorithm ecV5SyncAlgorithm;
    @Resource
    private ChannelInfoMapper channelInfoMapper;


    public Map<String, Object> synInfo(String iccid,Map<String, Object> channel,Object inquire) {
        return request(iccid, channel,synInfo,inquire,null);
    }

    public Map<String, Object> synInfo(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,synInfo,null,null);
    }
    public Map<String, Object> synFlow(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,synFlow,null,null);
    }
    public Map<String, Object> synStatus(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,querySimStatus,null,null);
    }
    public Map<String, Object> synRealName(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,querySimRealNameStatus,null,null);
    }
    public Map<String, Object> synSession(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,querySimSession,null,null);
    }
    public Map<String, Object> synActivateDate(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,synActivateDate,null,null);
    }
    public Map<String, Object> synImei(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,querySimImei,null,null);
    }
    public Map<String, Object> changeSimStatus(String iccid,Map<String, Object> channel,Map<String, Object> pMap) {
        return request(iccid, channel,changeSimStatus,null,pMap);
    }
    public Map<String, Object> simGprsStatusReset(String iccid,Map<String, Object> channel,Map<String, Object> pMap) {
        return request(iccid, channel,simGprsStatusReset,null,pMap);
    }
    public Map<String, Object> simApnFunction(String iccid,Map<String, Object> channel,Map<String, Object> pMap) {
        return request(iccid, channel,simApnFunction,null,pMap);
    }
    public Map<String, Object> queryOfferingsDetail(String iccid,Map<String, Object> channel,Map<String, Object> pMap) {
        return request(iccid, channel,queryOfferingsDetail,null,pMap);
    }
    public Map<String, Object> querySimManageStopRestartStatus(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,querySimManageStopRestartStatus,null,null);
    }
    public Map<String, Object> queryLocationSavedCards(String iccid,Map<String, Object> channel,Map<String, Object> pMap) {
        return request(iccid, channel,queryLocationSavedCards,null,pMap);
    }
    public Map<String, Object> queryHistoricalLocation(String iccid,Map<String, Object> channel,Map<String, Object> pMap) {
        return request(iccid, channel,queryHistoricalLocation,null,pMap);
    }
    public Map<String, Object> queryLocationMonitorAlarm(String iccid,Map<String, Object> channel,Map<String, Object> pMap) {
        return request(iccid, channel,queryLocationMonitorAlarm,null,pMap);
    }
    public Map<String, Object> querySimStopReason(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,querySimStopReason,null,null);
    }
    public Map<String, Object> querySimCommunicationFunctionStatus(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,querySimCommunicationFunctionStatus,null,null);
    }
    public Map<String, Object> regionLimitStatus(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,regionLimitStatus,null,null);
    }
    public Map<String, Object> querySimRegionLimitArea(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,querySimRegionLimitArea,null,null);
    }
    public Map<String, Object> queryOnOffStatus(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,queryOnOffStatus,null,null);
    }
    public Map<String, Object> queryCardBindStatus(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,queryCardBindStatus,null,null);
    }
    public Map<String, Object> querySimChangeHistory(String iccid,Map<String, Object> channel) {
        return request(iccid, channel,querySimChangeHistory,null,null);
    }



    /**
     * 根据 卡号获取
     * @param iccid
     * @return
     */
     public Map<String, Object> getChannel(String iccid) {
         boolean bool = false;
         int status = notConfigStatus;
         Map<String, Object> channel = null;
         Map<String, Object> rMap = new HashMap<>();
         Map<String, Object> cardMap = getCard(iccid);
         String channel_id = GetMapUtil.getValueToStr(cardMap, "channel_id", null);
         if(channel_id!=null){
             Map<String, Object> findMap = new HashMap<>();
             findMap.put("c_no",channel_id);
             Map<String, Object> cMap = channelInfoMapper.findSyncChannel(findMap);
             if(cMap!=null){
                 channel = cMap;
                 bool = true;
             }else {
                 status = disabledStatus;
             }
         }
         rMap.put("bool",bool);
         rMap.put("status",status);
         rMap.put("channel",channel);
         rMap.put("cardMap",cardMap);
         return rMap;
     }

    /**
     * 获取卡号
     * @param iccid
     * @return
     */
    public Map<String, Object> getCard(String iccid) {
        Map<String, Object> findMap = new HashMap<>();
        findMap.put("iccid",iccid);
        Map<String, Object> cardMap = cardInfoMapper.getSyncInfo(findMap);
        return cardMap;
    }





    /**
     * API请求执行
     * @param iccid
     * @param channel
     * @return
     */
    private Map<String, Object> request(String iccid, Map<String, Object> channel,String functionName,Object inquire,Map<String, Object> pMap) {
        Map<String, Object> rMap = new HashMap<>();
        List<Map<String, Object>> retuenList = null;
        String template = "";
        int status = errorStatus;
        String message = "";
        try {
            Map<String, Object> cardMap = null;
            if(channel==null){//未传入通达配置时 查找通道配置
                Map<String, Object> channelMap = getChannel(iccid);
                boolean bool = (boolean) GetMapUtil.getValue(channelMap, "bool", false);
                if(bool){
                    Map<String, Object> cMap = (Map<String, Object>) GetMapUtil.getValue(channelMap, "channel", null);
                    channel = cMap;
                    cardMap = (Map<String, Object>) GetMapUtil.getValue(channelMap, "cardMap", null);
                }else {
                    status =  (int) GetMapUtil.getValue(channelMap, "status", status);
                }
            }else {
                cardMap = getCard(iccid);
            }



            if(channel!=null && cardMap!=null){
                //判断通道模板 直接走对应的通道同步算法 》算法同步后直接
                template = GetMapUtil.getValueToStr(channel,"template");

                Map<String, Object> parameter = null;
                String onStop = null,onOff = null;
                if(functionName.equals(changeSimStatus)){
                    onStop = pMap.get("onStop").toString();
                    onStop = onStop.equals("on")?"1":onStop.equals("off")?"0":onStop;

                }else if(functionName.equals(simApnFunction)) {
                    onOff = pMap.get("onOff").toString();
                    onOff = onOff.equals("on")?"0":onOff.equals("off")?"1":onOff;
                }




                if(template.equalsIgnoreCase("oneLink_ECV5")){
                    if(functionName.equals(synInfo)){
                        ecV5SyncAlgorithm.startRun(channel,cardMap, (InquireEcV5) inquire,null);
                    }else if(functionName.equals(synFlow)){
                        String network_type = GetMapUtil.getValueToStr(cardMap, "network_type", "0");
                        String exName = "";
                        if(network_type.equals("1")){//NB 卡一般都按套餐累计来结算
                            exName = "querySimDataMargin";//单卡本月套餐内流量使用量实时查
                        }else {
                            exName = "querySimDataUsage";//单卡本月流量累计使用量查询
                        }
                        ecV5SyncAlgorithm.ecV5CPU(channel,cardMap,exName);
                    }else if(functionName.equals(synActivateDate)){
                        retuenList = ecV5SyncAlgorithm.ecV5CPU(channel,cardMap,"querySimBasicInfo");
                    }else{
                        if(functionName.equals(changeSimStatus)){
                            parameter = parameter!=null?parameter:new HashMap<>();
                            parameter.put("operType",onStop);
                            parameter.put("key","msisdn");
                            parameter.put("value",cardMap.get("msisdn"));
                            retuenList = ecV5SyncAlgorithm.ecV5CPUChange(channel,cardMap,functionName,parameter);
                        }else if(functionName.equals(simApnFunction)){
                            parameter = parameter!=null?parameter:new HashMap<>();
                            parameter.put("operType",onOff);
                            parameter.put("key","msisdn");
                            parameter.put("value",cardMap.get("msisdn"));
                            retuenList = ecV5SyncAlgorithm.ecV5CPUChange(channel,cardMap,functionName,parameter);
                        }else if(functionName.equals(simGprsStatusReset)){
                            parameter = parameter!=null?parameter:new HashMap<>();
                            parameter.put("key","msisdn");
                            parameter.put("value",cardMap.get("msisdn"));
                            retuenList = ecV5SyncAlgorithm.ecV5CPUChange(channel,cardMap,functionName,parameter);
                        }else {
                            boolean req = true;
                            JSONObject json = null;
                            //有缓存数据的直接取缓存数据 【特殊接口又请求间隔时间 有间隔时间时读取缓存】
                            if(functionName.equals(regionLimitStatus)){
                                String resultStr = (String) redisUtil.redisTemplate.opsForValue().get(functionName+":"+iccid);
                                if(resultStr!=null && resultStr.length()>0){
                                    json = JSON.parseObject(resultStr);
                                    req = false;
                                }
                            }else if(functionName.equals(queryCardBindStatus)){
                                parameter = parameter!=null?parameter:new HashMap<>();
                                parameter.put("msisdn",cardMap.get("msisdn"));
                                parameter.put("testType","0");
                            }
                            if(req){
                                retuenList = ecV5SyncAlgorithm.ecV5CPU(channel,cardMap,functionName,parameter);
                            }else if(json!=null){
                                retuenList = new ArrayList<>();
                                Map<String, Object> retuenMap = new HashMap<>();
                                retuenMap.put("functionName",functionName);//多线程区分请求函数 后续便于处理返回数据
                                retuenMap.put("iccid",iccid);
                                retuenMap.put("rData",json);
                                retuenList.add(retuenMap);
                            }
                        }
                    }






                }else {
                    logger.error("模板配置未找到！");
                    status = notConfigStatus;
                }



                if(status == errorStatus){
                    status = successStatus;
                }
            }else {
                status = errorStatus;
            }
        }catch (Exception e){
            logger.error("ApiProcessor  异常 {}",e.getMessage());
        }

        String msg = "";
        if(status==notConfigStatus){
            msg = "common.notConfigChannel";
        }else if(status==disabledStatus){
            msg = "common.disabledChannel";
        }
        rMap.put("status",status);
        rMap.put("message",message);
        rMap.put("msg",msg);
        rMap.put("retuenList",retuenList);
        rMap.put("iccid",iccid);
        rMap.put("template",template);
        return rMap;
    }









}
