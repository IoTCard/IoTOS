package top.iotos.synApi.upstreamApi.chinaMobile.oneLink.ecV5;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.iotos.synApi.upstreamApi.config.RedisUtil;
import top.iotos.synApi.utils.iotos.common.Arith;
import top.iotos.synApi.utils.iotos.common.GetMapUtil;
import top.iotos.synApi.utils.iotos.common.ListCompare;
import top.iotos.synApi.utils.sync.CardStateTransition;
import top.iotos.synApi.utils.sync.MQSyncSend;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * EcV5 同步算法策略
 */
@Component
public class EcV5SyncAlgorithm {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CardStateTransition cardStateTransition;

    @Resource
    private MQSyncSend mQSyncSend;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String []updArr = {"querySimSession","querySimBasicInfo","querySimRealNameStatus","querySimImei","querySimStatus"};// 需要执行 放入修改中的 函数名
    /**
     * 开始同步算法
     * @param channel
     * @param cardMap
     */
    public void startRun(Map<String, Object> channel,Map<String, Object> cardMap,InquireEcV5 inquire,Map<String, Object> pMap){

        String w_real_name = GetMapUtil.getValueToStr(cardMap, "w_real_name", "0");
        String nedd_real_name = GetMapUtil.getValueToStr(cardMap, "nedd_real_name", "0");
        String network_type = GetMapUtil.getValueToStr(cardMap, "network_type", "0");
        String activate_date = GetMapUtil.getValueToStr(cardMap, "activate_date", null);
        String open_date = GetMapUtil.getValueToStr(cardMap, "open_date", null);

        List<String> fNameList = new ArrayList<>();//后续这个 可以做到 单个通道配置中控制
        fNameList.add("querySimImei");//单卡实时使用终端IMEI查询
        fNameList.add("querySimStatus");//单卡状态查询

        if(network_type.equals("1")){//NB 卡一般都按套餐累计来结算
            fNameList.add("querySimDataMargin");//单卡本月套餐内流量使用量实时查
        }else {
            fNameList.add("querySimDataUsage");//单卡本月流量累计使用量查询
        }
        if(nedd_real_name.equals("1") && w_real_name.equals("0")){
            fNameList.add("querySimRealNameStatus");//物联卡实名登记状态查询
        }
        if(activate_date == null || open_date==null){// 没有 ‘激活时间’ 或 ‘开卡时间’ 时 获取 激活时间
            fNameList.add("querySimBasicInfo");//单卡基本信息查询 【激活时间（首次）、开卡时间、备注(默认这个不会直接同步)】
        }
        fNameList.add("querySimSession");//单卡在线信息实时查询


        ecV5CPU(channel,cardMap,fNameList,inquire,null,"inquire",pMap);

    }

    /**
     * 默认查询业务处理
     * @param channel
     * @param cardMap
     * @param functionName
     * @return
     */
    public List<Map<String, Object>> ecV5CPU(Map<String, Object> channel,Map<String, Object> cardMap,String functionName){
        List<String> fNameList = new ArrayList<>();
        fNameList.add(functionName);
        return ecV5CPU(channel,cardMap,fNameList,null,null,"inquire",null);
    }
    public List<Map<String, Object>> ecV5CPU(Map<String, Object> channel,Map<String, Object> cardMap,String functionName,Map<String, Object> pMap){
        List<String> fNameList = new ArrayList<>();
        fNameList.add(functionName);
        return ecV5CPU(channel,cardMap,fNameList,null,null,"inquire",pMap);
    }
    public List<Map<String, Object>> ecV5CPUChange(Map<String, Object> channel,Map<String, Object> cardMap,String functionName,Map<String, Object> pMap){
        List<String> fNameList = new ArrayList<>();
        fNameList.add(functionName);
        return ecV5CPU(channel,cardMap,fNameList,null,null,"change",pMap);
    }

    public List<Map<String, Object>> ecV5CPU(Map<String, Object> channel,Map<String, Object> cardMap,String functionName,InquireEcV5 inquire,ChangeEcV5 change,String operateType,Map<String, Object> pMap){
        List<String> fNameList = new ArrayList<>();
        fNameList.add(functionName);
        return ecV5CPU(channel,cardMap,fNameList,inquire,change,operateType,pMap);
    }


    /**
     * ecV5 中央处理器
     * @param channel
     * @param cardMap
     * @param fNameList 需要执行的函数请求
     */
    public List<Map<String, Object>> ecV5CPU(Map<String, Object> channel,Map<String, Object> cardMap,List<String> fNameList,InquireEcV5 inquire,ChangeEcV5 change,String operateType,Map<String, Object> pMap){
        List<Map<String, Object>> retuenList = new ArrayList<>();

        String iccid = GetMapUtil.getValueToStr(cardMap, "iccid", null);
        String msisdn = GetMapUtil.getValueToStr(cardMap, "msisdn", null);
        String dept_id = GetMapUtil.getValueToStr(cardMap, "dept_id", "100");

        String template = channel.get("template").toString();

        if(inquire==null && change==null){
            Map<String, Object> initMap = new HashMap<>();
            initMap.put("url",channel.get("url"));
            initMap.put("parameter_one",channel.get("parameter_one"));
            initMap.put("parameter_tow",channel.get("parameter_tow"));
            initMap.put("parameter_three",channel.get("parameter_three"));
            if(operateType.equalsIgnoreCase("inquire")){
                inquire = new InquireEcV5(initMap,template);
            } else  {
                change  = new ChangeEcV5(initMap,template);
            }
        }




        ExecutorService executorService = Executors.newFixedThreadPool(fNameList.size());//每个函数一个线程 独立处理
        List<Map<String, Object>> rList = new ArrayList<>();
        try {
            CompletionService<String> completionService = new ExecutorCompletionService<String>(executorService);
            List<Future<String>> futures = new ArrayList<>();
            if(pMap==null){
                pMap = new HashMap<>();
                pMap.put("key","msisdn");
                pMap.put("value",msisdn);
            }
            pMap.put("iccid",iccid);//用来标记后续返回数据区分 校验
            for (int i = 0; i < fNameList.size(); i++) {
                String functionName = fNameList.get(i);
                if(operateType.equalsIgnoreCase("inquire")){
                    futures.add(completionService.submit(new EcV5HttpThread( inquire, pMap,functionName,cardMap)));
                } else  {
                    if (functionName.equalsIgnoreCase("simApnFunction")){//特殊情况需要调用查询类
                        inquire = new InquireEcV5(change.initMap,change.template);
                        futures.add(completionService.submit(new EcV5HttpThread( inquire,change, pMap,functionName,cardMap)));
                    }else {
                        futures.add(completionService.submit(new EcV5HttpThread( change, pMap,functionName,cardMap)));
                    }
                }
            }

            for (int i = 0; i < fNameList.size(); i++) {
                try {
                    Future<String> future = completionService.take();
                    String result = future.get();
                    if (result != null) {
                        rList.add(JSON.parseObject(result));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        }catch (Exception e){
            logger.error("EcV5SyncAlgorithm 多线程请求 异常 {}",e.getMessage());
        }finally {
            executorService.shutdown();
        }

        if(rList.size()>0){

            List<Map<String, Object>> exList = new ArrayList<>();

            for (int i = 0; i < rList.size(); i++) {
                Map<String, Object> rMap = rList.get(i);
                if(rMap.get("jsonStr")!=null){
                    String jsonStr = rMap.get("jsonStr").toString();
                    String functionName = rMap.get("functionName").toString();
                    String executionDate = rMap.get("executionDate").toString();//请求执行成功时间
                    String starting_time = rMap.get("starting_time").toString();//请求执行开始时间

                    Map<String,Object> cardMap_i = (Map<String, Object>) rMap.get("cardMap");//避免多线程 返回数据不一致
                    Map<String,Object> pMap_i = (Map<String, Object>) rMap.get("pMap");//避免多线程 请求数据


                    System.out.println(jsonStr);
                    String iccid_i = rMap.get("iccid").toString();
                    if(jsonStr.length()>0){
                        Map<String, Object> updCardMap = new HashMap<>();
                        Map<String, Object> rData = new HashMap<>();//返回数据

                        Map<String, Object> obj = JSON.parseObject(jsonStr);
                        String status = obj.get("status").toString();
                        boolean success = false;
                        if(status.equals("0")){
                            success = true;
                            updCardMap.put("iccid",iccid_i);//这里 需要校验 多线线程运行时是否会出现读取到其他请求返回结果情况

                            List<Map<String, Object>> result = obj.get("result")!=null?(List<Map<String, Object>>) obj.get("result"):null;
                            Map<String,Object> index1 = null;
                            index1 = result!=null && result.size()>0?result.get(0):null;

                            if(functionName.equals("querySimImei")){
                                String imei = GetMapUtil.getValueToStr(index1, "imei", null);
                                updCardMap = GetMapUtil.putMap(updCardMap,"imei",imei);
                                rData.put("imei",imei);
                            }else if(functionName.equals("querySimStatus")){
                                Integer pCode = Integer.parseInt(index1.get("cardStatus").toString());
                                Map<String,Object> ecV5StatusMap = cardStateTransition.getEcV5Status(pCode);
                                String status_id = GetMapUtil.getValueToStr(ecV5StatusMap, "status_id", null);
                                String status_show_id = GetMapUtil.getValueToStr(ecV5StatusMap, "status_show_id", null);
                                updCardMap = GetMapUtil.putMap(updCardMap,"status_id",status_id);
                                updCardMap = GetMapUtil.putMap(updCardMap,"status_show_id",status_show_id);
                                updCardMap.put("state_sync_time",starting_time);
                                rData.put("status_id",status_id);
                                rData.put("status_show_id",status_show_id);
                            }else if(functionName.equals("querySimDataMargin")){
                                List<Map<String, Object>> accmMarginList = ((List<Map<String, Object>>) index1.get("accmMarginList"));
                                Map<String, Object> accmMargin = accmMarginList.get(0);
                                double kb = Double.parseDouble(accmMargin.get("useAmount").toString());
                                double used = Arith.div(kb,1024,2);//KB 转换 MB
                                if(used>=0){
                                    mQSyncSend.sendUsedResultProcessing(cardMap_i,used,executionDate,starting_time);
                                }
                                rData.put("result",result);
                            }else if(functionName.equals("querySimDataUsage")){
                                double kb = Double.parseDouble(index1.get("dataAmount").toString());
                                double used = Arith.div(kb,1024,2);//KB 转换 MB
                                if(used>=0){
                                    mQSyncSend.sendUsedResultProcessing(cardMap_i,used,executionDate,starting_time);
                                }
                            }else if(functionName.equals("querySimRealNameStatus")){
                                String realNameStatus = GetMapUtil.getValueToStr(index1, "realNameStatus", null);
                                updCardMap = GetMapUtil.putMap(updCardMap,"w_real_name",realNameStatus);
                                rData.put("w_real_name",realNameStatus);
                            }else if(functionName.equals("querySimBasicInfo")){
                                String imsi_i = GetMapUtil.getValueToStr(index1, "imsi", null);
                                String activeDate = GetMapUtil.getValueToStr(index1, "activeDate", null);
                                String openDate = GetMapUtil.getValueToStr(index1, "openDate", null);
                                //String msisdn_i = GetMapUtil.getValueToStr(index1, "msisdn", null);
                                // String remark = GetMapUtil.getValueToStr(index1, "remark", null);
                                updCardMap = GetMapUtil.putMap(updCardMap,"imsi",imsi_i);
                                updCardMap = GetMapUtil.putMap(updCardMap,"activate_date",activeDate);
                                updCardMap = GetMapUtil.putMap(updCardMap,"open_date",openDate);
                                rData.put("imsi",imsi_i);
                                rData.put("activate_date",activeDate);
                                rData.put("open_date",openDate);
                            }else if(functionName.equals("querySimSession")){
                                Map<String, Object> simSession = ((List<Map<String, Object>>) index1.get("simSessionList")).get(0);
                                updCardMap = GetMapUtil.putMap(updCardMap,"simSessionMap",simSession);
                                rData = simSession;
                            }else if(functionName.equals("changeSimStatus")){
                                String source_data = cardMap_i.get("status_show_id").toString();
                                rData.put("source_data",source_data);
                                rData.put("pMap",pMap_i);
                                rData.put("bool",true);
                            }else if(functionName.equals("simGprsStatusReset")){
                                String status_show_id = cardMap_i.get("status_show_id").toString();
                                rData.put("source_data",status_show_id);
                                rData.put("pMap",pMap_i);
                                rData.put("bool",true);
                            }else if(functionName.equals("simApnFunction")){
                                String source_data = GetMapUtil.getValueToStr(cardMap_i, "connection_status", "0");
                                rData.put("source_data",source_data);
                                rData.put("pMap",pMap_i);
                                rData.put("bool",true);
                            }else if(functionName.equals("querySimRegionLimitArea")){
                                rData.put("result",result);
                            }else if(functionName.equals("querySimCommunicationFunctionStatus")){
                                rData.put("result",result);
                            }else if(functionName.equals("regionLimitStatus")){
                                //（同一卡号 1 小时内仅可调用一次 【所以调用成功后缓存60分钟】
                                rData.put("result",result);
                                setCache(functionName,iccid,result,60);//缓存数据
                            }else if(functionName.equals("querySimManageStopRestartStatus")){
                                rData.put("result",result);
                            }else if(functionName.equals("querySimStopReason")){
                                rData.put("result",result);
                            }else if(functionName.equals("queryCardBindStatus")){
                                rData.put("result",result);
                            }else if(functionName.equals("queryOrderedOfferings")){
                                //资费订购实时查询 [待完善 做表存储]
                                rData.put("result",result);
                            }else if(functionName.equals("querySimChangeHistory")){
                                rData.put("result",result);
                            }else if(functionName.equals("queryOnOffStatus")){
                                rData.put("result",result);
                            }




                            if(ListCompare.Is_existence(updArr,functionName)){// 需修改同步的函数 才放入
                                exList.add(updCardMap);
                            }
                        }else {
                            //特殊业务反复订购  反馈 如果是 反复订购 说明已是目标状态 == ‘成功’
                            if(functionName.equals("simApnFunction")){
                                if(status.equals("12047") || status.equals("12048")){
                                    success = true;
                                    String source_data = GetMapUtil.getValueToStr(cardMap_i, "connection_status", "0");
                                    rData.put("source_data",source_data);
                                    rData.put("pMap",pMap_i);
                                    rData.put("bool",true);
                                }
                            }
                            if(functionName.equals("regionLimitStatus")){//该卡未订购/开通区域限制或订购失效
                                if(status.equals("12199")){
                                    success = true;
                                    List<Map<String, Object>> result = obj.get("result")!=null?(List<Map<String, Object>>) obj.get("result"):null;
                                    if(result!=null){
                                        setCache(functionName,iccid,result,60);//缓存数据
                                        rData.put("result",result);
                                    }
                                }
                            }
                        }


                        if(success){
                            Map<String, Object> retuenMap = new HashMap<>();
                            retuenMap.put("functionName",functionName);//多线程区分请求函数 后续便于处理返回数据
                            retuenMap.put("iccid",iccid_i);
                            retuenMap.put("rData",rData);
                            retuenList.add(retuenMap);
                        }

                    }
                }
            }

            if(exList.size()>0){
                Map<String, Object>  uMap = new HashMap<>();
                Map<String, Object>  addSimSessionMap = null;

                for (int i = 0; i < exList.size(); i++) {
                    Map<String, Object>  exMap = exList.get(i);
                    if(exMap.get("iccid")!=null){
                        String upd_iccid = exMap.get("iccid").toString();

                        if(exMap.get("simSessionMap")!=null){//在线信息 放入在线信息修改中
                            addSimSessionMap = new HashMap<>();
                            addSimSessionMap.put("iccid",upd_iccid);
                            addSimSessionMap.put("template",template);
                            addSimSessionMap.put("return_data",JSON.toJSONString(exMap.get("simSessionMap")));
                            addSimSessionMap.put("dept_id",dept_id);
                        }else {

                            Map<String, Object>  updMap = null;
                            if(uMap.get(upd_iccid)!=null){
                                updMap = (Map<String, Object>) uMap.get(upd_iccid);


                                updMap.putAll(exMap);//追加当前修改元素
                            }else {
                                updMap = exMap;
                            }
                            uMap.put(upd_iccid,updMap);
                        }

                    }
                }
                if(addSimSessionMap!=null){
                    mQSyncSend.sendAddSimSession(addSimSessionMap);//发送 增加在线信息队列
                }
                mQSyncSend.sendUpdCardBatch(uMap);//发送修改卡信息
            }

        }

        return retuenList;
    }

    /**
     * 缓存结果
     * @param functionName
     * @param iccid
     * @param result
     * @param timeout
     */
    private void setCache(String functionName,String iccid,List<Map<String, Object>> result,int timeout){
        try {
            if(redisUtil!=null){
                redisUtil.redisTemplate.opsForValue().set(functionName+":"+iccid, JSON.toJSONString(result), timeout, TimeUnit.MINUTES);
            }
        }catch (Exception e){

        }
    }


}
