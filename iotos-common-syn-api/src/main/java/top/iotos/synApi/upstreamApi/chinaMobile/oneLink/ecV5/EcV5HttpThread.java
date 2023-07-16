package top.iotos.synApi.upstreamApi.chinaMobile.oneLink.ecV5;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import top.iotos.synApi.utils.iotos.time.VeDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


/**
 * 多线程请求类
 */

public class EcV5HttpThread  implements Callable<String> {

    private String jsonStr;
    private InquireEcV5 inquire = null;

    private ChangeEcV5 change = null;
    private Map<String,Object> pMap;

    private Map<String,Object> cardMap;

    private String functionName;

    public void setCardMap(Map<String, Object> cardMap) {
        this.cardMap = cardMap;
    }

    public Map<String, Object> getCardMap() {
        return this.cardMap;
    }
    public void setPMap(Map<String, Object> pMap) {
        this.pMap = pMap;
    }

    public Map<String, Object> getPMap() {
        return this.pMap;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getJsonStr() {
        return this.jsonStr;
    }

    public EcV5HttpThread(InquireEcV5 inquire,Map<String,Object> pMap,String functionName,Map<String,Object> cardMap) {
        this.inquire = inquire;
        this.setPMap(pMap);
        this.functionName = functionName;
        this.setCardMap(cardMap);
    }
    public EcV5HttpThread(ChangeEcV5 change,Map<String,Object> pMap,String functionName,Map<String,Object> cardMap) {
        this.change = change;
        this.setPMap(pMap);
        this.functionName = functionName;
        this.setCardMap(cardMap);
    }
    public EcV5HttpThread(InquireEcV5 inquire,ChangeEcV5 change,Map<String,Object> pMap,String functionName,Map<String,Object> cardMap) {
        this.inquire = inquire;
        this.change = change;
        this.setPMap(pMap);
        this.functionName = functionName;
        this.setCardMap(cardMap);
    }
    @Override
    public String call() throws Exception {
            processor();//执行处理逻辑
            String starting_time = VeDate.getStringDate();
            String executionDate = starting_time.split(" ")[0];
            String jsonStr = this.getJsonStr();

            Map<String,Object> rMap = new HashMap<>();
            rMap.put("functionName",functionName);//多线程区分请求函数 后续便于处理返回数据
            rMap.put("jsonStr",jsonStr);
            rMap.put("pMap",this.getPMap());
            rMap.put("iccid",getTostr("iccid"));
            rMap.put("starting_time",starting_time );
            rMap.put("executionDate",executionDate);
            rMap.put("cardMap",this.getCardMap());
            this.setJsonStr(JSON.toJSONString(rMap));
        return this.getJsonStr();
    }

    public String processor(){

         if(change!=null){
            switch (functionName){
                case "changeSimStatus":
                    this.setJsonStr(change.changeSimStatus(getTostr("key"),getTostr("value"),getTostr("operType")));
                    break;
                case "simGprsStatusReset":
                    this.setJsonStr(change.simGprsStatusReset(getTostr("key"),getTostr("value")));
                    break;
                case "simApnFunction":
                    //获取当前卡号 apnName
                    Map<String, Object> jsonObject = JSONObject.parseObject(inquire.querySimCommunicationFunctionStatus(getTostr("key"),getTostr("value")));
                    Map<String, Object> data = ((List<Map<String, Object>>) jsonObject.get("result")).get(0);
                    Map<String, Object> serviceTypeList = ((List<Map<String, Object>>) data.get("serviceTypeList")).get(0);
                    String apnName = serviceTypeList.get("apnName").toString();
                    this.setJsonStr(change.simApnFunction(getTostr("key"),getTostr("value"),getTostr("operType"),apnName));
                    break;
            }
        }else if(inquire!=null){
            switch (functionName){
                case "queryGroupInfo":
                    this.setJsonStr(inquire.queryGroupInfo(getTostr("startNum"),getTostr("pageSize")));
                    break;
                case "queryGroupMember":
                    this.setJsonStr(inquire.queryGroupMember(getTostr("groupId"),getTostr("startNum"),getTostr("pageSize")));
                    break;
                case "querySimChangeHistory":
                    this.setJsonStr(inquire.querySimChangeHistory(getTostr("key"),getTostr("value")));
                    break;
                case "querySimImei":
                    this.setJsonStr(inquire.querySimImei(getTostr("key"),getTostr("value")));
                    break;
                case "querySimStatus":
                    this.setJsonStr(inquire.querySimStatus(getTostr("key"),getTostr("value")));
                    break;
                case "querySimStopReason":
                    this.setJsonStr(inquire.querySimStopReason(getTostr("key"),getTostr("value")));
                    break;
                case "querySimDataMargin":
                    this.setJsonStr(inquire.querySimDataMargin(getTostr("key"),getTostr("value")));
                    break;
                case "querySimDataUsagenDailyBatch":
                    this.setJsonStr(inquire.querySimDataUsagenDailyBatch(getTostr("key"),getTostr("value")));
                    break;
                case "querySimDataUsagenInpool":
                    this.setJsonStr(inquire.querySimDataUsagenInpool(getTostr("key"),getTostr("value")));
                    break;
                case "querySimDataUsagenMonthlyBatch":
                    this.setJsonStr(inquire.querySimDataUsagenMonthlyBatch(getTostr("key"),getTostr("value"),getTostr("queryDate")));
                    break;
                case "querySimDataUsage":
                    this.setJsonStr(inquire.querySimDataUsage(getTostr("key"),getTostr("value")));
                    break;
                case "queryCardBindStatus":
                    this.setJsonStr(inquire.queryCardBindStatus(getTostr("msisdn"),getTostr("testType","0")));
                    break;
                case "querySimBasicInfo":
                    this.setJsonStr(inquire.querySimBasicInfo(getTostr("key"),getTostr("value")));
                    break;
                case "querySimCommunicationFunctionStatus":
                    this.setJsonStr(inquire.querySimCommunicationFunctionStatus(getTostr("key"),getTostr("value")));
                    break;
                case "queryGroupDataMargin":
                    this.setJsonStr(inquire.queryGroupDataMargin(getTostr("groupId")));
                    break;
                case "querySimSession":
                    this.setJsonStr(inquire.querySimSession(getTostr("key"),getTostr("value")));
                    break;
                case "queryOnOffStatus":
                    this.setJsonStr(inquire.queryOnOffStatus(getTostr("key"),getTostr("value")));
                    break;
                case "queryOrderedOfferings":
                    this.setJsonStr(inquire.queryOrderedOfferings(getTostr("queryType"),getTostr("groupId"),getTostr("msisdn")));
                    break;
                case "queryApnInfo":
                    this.setJsonStr(inquire.queryApnInfo(getTostr("key"),getTostr("value")));
                    break;
                case "queryChangeableSimDate":
                    this.setJsonStr(inquire.queryChangeableSimDate(getTostr("changeDate"),getTostr("cardStatus"),getTostr("startNum"),getTostr("pageSize")));
                    break;
                case "queryEcSimStatusNumber":
                    this.setJsonStr(inquire.queryEcSimStatusNumber());
                    break;
                case "regionLimitStatus":
                    this.setJsonStr(inquire.regionLimitStatus(getTostr("key"),getTostr("value")));
                    break;
                case "querySimRegionLimitArea":
                    this.setJsonStr(inquire.querySimRegionLimitArea(getTostr("key"),getTostr("value")));
                    break;
                case "querySimRealNameStatus":
                    this.setJsonStr(inquire.querySimRealNameStatus(getTostr("key"),getTostr("value")));
                    break;
                case "riskSceneCardList":
                    this.setJsonStr(inquire.riskSceneCardList(getTostr("startNum"),getTostr("pageSize"),getTostr("riskScene"),getTostr("queryDate")));
                    break;
                case "risk020Detail":
                    this.setJsonStr(inquire.risk020Detail(getTostr("msisdn"),getTostr("queryDate")));
                    break;
                case "risk007Detail":
                    this.setJsonStr(inquire.risk007Detail(getTostr("msisdn"),getTostr("queryDate")));
                    break;
                case "risk011Detail":
                    this.setJsonStr(inquire.risk011Detail(getTostr("msisdn"),getTostr("queryDate")));
                    break;
                case "risk010Detail":
                    this.setJsonStr(inquire.risk010Detail(getTostr("msisdn"),getTostr("queryDate")));
                    break;
                case "risk015Detail":
                    this.setJsonStr(inquire.risk015Detail(getTostr("msisdn"),getTostr("queryDate")));
                    break;
                case "risk012Detail":
                    this.setJsonStr(inquire.risk012Detail(getTostr("msisdn"),getTostr("queryDate")));
                    break;
                case "risk013Detail":
                    this.setJsonStr(inquire.risk013Detail(getTostr("msisdn"),getTostr("queryDate")));
                    break;
                case "risk017Detail":
                    this.setJsonStr(inquire.risk017Detail(getTostr("msisdn"),getTostr("queryDate")));
                    break;
                case "querySim2gActiveInfo":
                    this.setJsonStr(inquire.querySim2gActiveInfo(getTostr("startNum"),getTostr("pageSize"),getTostr("beId"),getTostr("queryDate"),getTostr("regionId")));
                    break;
                case "querySimUsedSignalTypeNum":
                    this.setJsonStr(inquire.querySimUsedSignalTypeNum(getTostr("startNum"),getTostr("pageSize"),getTostr("dateType"),getTostr("queryDate")));
                    break;
                case "queryLocationMonitorAlarm":
                    this.setJsonStr(inquire.queryLocationMonitorAlarm(getTostr("key"),getTostr("value"),getTostr("startDate"),getTostr("endDate"),getTostr("pageSize"),getTostr("startNum")));
                    break;
                case "queryHistoricalLocation":
                    this.setJsonStr(inquire.queryHistoricalLocation(getTostr("key"),getTostr("value"),getTostr("startDate"),getTostr("endDate")));
                    break;
                case "queryLocationSavedCards":
                    this.setJsonStr(inquire.queryLocationSavedCards(getTostr("key"),getTostr("value"),getTostr("pageSize"),getTostr("startNum")));
                    break;
                case "querySimManageStopRestartStatus":
                    this.setJsonStr(inquire.querySimManageStopRestartStatus(getTostr("key"),getTostr("value")));
                    break;
                case "queryOfferingsDetail":
                    this.setJsonStr(inquire.queryOfferingsDetail(getTostr("offeringId")));
                    break;




            }
        }
        return jsonStr;
    }



    public String getTostr(String key){
        String str = getPMap().get(key)!=null?getPMap().get(key).toString():null;
        return str;
    }
    public String getTostr(String key,String defVal){
        String str = getPMap().get(key)!=null?getPMap().get(key).toString():defVal;
        return str;
    }



}