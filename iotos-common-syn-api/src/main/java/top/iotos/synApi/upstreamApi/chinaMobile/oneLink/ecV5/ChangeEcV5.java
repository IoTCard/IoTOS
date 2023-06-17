package top.iotos.synApi.upstreamApi.chinaMobile.oneLink.ecV5;

import java.util.List;
import java.util.Map;

/**
 * 业务变更类
 */
public class ChangeEcV5 extends EcV5Api {

    private final String change = "/change";
    private final String recharge = "/recharge";
    private final String operate = "/operate";

    private final String order = "/order";

    private final String limit = "/limit";
    private final String config = "/config";
    private final String add = "/add";
    private final String delete = "/delete";
    private final String create = "/create";
    private final String update = "/update";

    public ChangeEcV5() {
        super();
    }

    public ChangeEcV5(Map<String, Object> initMap, String template){
        super(initMap,template);
    }


    //------------[5.9物联卡管理-短信类]

    /**
     * 5.9.1CMIOT_API25M02-物联卡短信状态重置
     * @param key
     * @param value
     * @return
     */
    public String simSmsStatus(String key,String value) {
        String functionNm =  "/reset/sim-sms-status";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[7业务办理类]

    /**
     * 7.1.1CMIOT_API23B02-物联卡账户充值交费
     * @param entityType
     * @param entityId
     * @param chargeMoney
     * @param paymentType
     * @param returnUrl
     * @param defaultBank
     * @param bankCardType
     * @return
     */
    public String rechargeSimAccount(String entityType,String entityId,String chargeMoney,String paymentType,String returnUrl,String defaultBank,String bankCardType) {
        String functionNm =  recharge+"/sim-account";
        pMap.put("entityType", entityType);
        pMap.put("entityId", entityId);
        pMap.put("chargeMoney", chargeMoney);
        pMap.put("paymentType", paymentType);
        pMap.put("returnUrl", returnUrl);
        pMap.put("defaultBank", defaultBank);
        pMap.put("bankCardType", bankCardType);
        return commonRequest(functionNm,pMap);
    }

    //------------[7.2业务办理-功能开停类]

    /**
     * 7.2.1CMIOT_API23M05-单卡语音功能开停
     * @param key
     * @param value
     * @param operType [0:开/1:停]
     * @return
     */
    public String simCallFunction(String key,String value,String operType) {
        String functionNm =  operate+"/sim-call-function";
        pMap.put(key, value);
        pMap.put("operType", operType);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.2.2CMIOT_API23M06-单卡短信功能开停
     * @param key
     * @param value
     * @param operType [0:开/1:停/barring：关停上行短信]
     * @return
     */
    public String simSmsFunction(String key,String value,String operType) {
        String functionNm =  operate+"/sim-sms-function";
        pMap.put(key, value);
        pMap.put("operType", operType);
        return commonRequest(functionNm,pMap);
    }

    //------------[7.2.3CMIOT_API23M07-单卡APN功能开停]

    /**
     * 7.2.3CMIOT_API23M07-单卡APN功能开停
     * @param key
     * @param value
     * @param operType [0:开/1:停]
     * @param apnName [所要办理的APN Name]
     * @return
     */
    public String simApnFunction(String key,String value,String operType,String apnName) {
        String functionNm =  operate+"/sim-apn-function";
        pMap.put(key, value);
        pMap.put("operType", operType);
        pMap.put("apnName", apnName);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.2.4CMIOT_API23M09-物联卡通信功能开停批量办理
     * @param msisdns
     * @param serviceType [1  基础语音通信服务 / 08  短信基础服务 / 10 国际漫游服务 / 11  数据通信服务 / 12 短信基础服务（5G SA） / 13 VOLTE语音基础服务]
     * @param operType [0:开/1:停/barring：关停上行短信 （仅针对08 短信基础服务）]
     * @param apnName [APN名称，serviceType为11时，必填]
     * @return
     */
    public String simCommunicationFunctionBatch(String msisdns,String serviceType,String operType,String apnName) {
        String functionNm =  operate+"/sim-communication-function/batch";
        pMap.put("msisdns", msisdns);
        pMap.put("serviceType", serviceType);
        pMap.put("operType", operType);
        if(apnName!=null){
            pMap.put("apnName", apnName);
        }
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.2.5CMIOT_API23M12-物联卡数据通信服务（PCC策略）开停批量办理
     * @param msisdns
     * @param pccCode
     * @param operType
     * @param apnName
     * @return
     */
    public String simGprsPccFunctionBatch(String msisdns,String pccCode,String operType,String apnName) {
        String functionNm =  operate+"/sim-gprs-pcc-function/batch";
        pMap.put("msisdns", msisdns);
        pMap.put("pccCode", pccCode);
        pMap.put("operType", operType);
        pMap.put("apnName", apnName);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.2.6CMIOT_API23M26-单卡PCC策略开停
     * @param key
     * @param value
     * @param serviceList
     * @return
     */
    public String simPccFunction(String key, String value, List<Map<String,Object>> serviceList) {
        String functionNm =  operate+"/sim-pcc-function";
        pMap.put(key, value);
        pMap.put("serviceList", serviceList);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.2.7CMIOT_API23M38-单卡5G-SA语音基础服务开停
     * @param key
     * @param value
     * @param operType
     * @return
     */
    public String sim5gSaCallFunction(String key, String value, String operType) {
        String functionNm =  operate+"/sim-5g-sa-call-function";
        pMap.put(key, value);
        pMap.put("operType", operType);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.2.8CMIOT_API23M39-单卡5G-SA短信基础服务开停
     * @param key
     * @param value
     * @param operType
     * @return
     */
    public String sim5gSaSmsFunction(String key, String value, String operType) {
        String functionNm =  operate+"/sim-5g-sa-sms-function";
        pMap.put(key, value);
        pMap.put("operType", operType);
        return commonRequest(functionNm,pMap);
    }

    //------------[7.3业务办理-流量叠加类]

    /**
     * 7.3.1CMIOT_API23R08-物联卡流量叠加包订购
     * @param msisdn
     * @param maincommoDity
     * @param packageType
     * @return
     */
    public String gprspackageOrder(String msisdn, String maincommoDity, String packageType) {
        String functionNm =  order+"/gprspackage-order";
        pMap.put("msisdn", msisdn);
        pMap.put("maincommoDity", maincommoDity);
        pMap.put("packageType", packageType);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.3.2CMIOT_API23R09-物联卡流量叠加包批量订购
     * @param msisdns
     * @param maincommoDity
     * @param packageType
     * @return
     */
    public String gprspackageOrderBatch(String msisdns, String maincommoDity, String packageType) {
        String functionNm =  order+"/gprspackage-order/batch";
        pMap.put("msisdns", msisdns);
        pMap.put("maincommoDity", maincommoDity);
        pMap.put("packageType", packageType);
        return commonRequest(functionNm,pMap);
    }

    //------------[7.4业务办理-限流设置类]

    /**
     * 7.4.1CMIOT_API23E04-群组成员流量限额设置
     * @param msisdn
     * @param groupId
     * @param offerId
     * @param apnName
     * @param limitValue
     * @param actionRule
     * @param operType
     * @return
     */
    public String groupMemberDataUsage(String msisdn, String groupId, String offerId,String apnName, String limitValue, String actionRule, String operType) {
        String functionNm =  limit+"/group-member-data-usage";
        pMap.put("msisdn", msisdn);
        pMap.put("groupId", groupId);
        pMap.put("offerId", offerId);
        pMap.put("apnName", apnName);
        pMap.put("limitValue", limitValue);
        pMap.put("actionRule", actionRule);
        pMap.put("operType", operType);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.4.2CMIOT_API23M04-单卡GPRS流量自主限速办理
     * @param key
     * @param value
     * @param apnName
     * @param serviceUsageState
     * @return
     */
    public String networkSpeed(String key, String value, String apnName, String serviceUsageState) {
        String functionNm =  operate+"/network-speed";
        pMap.put(key, value);
        pMap.put("apnName", apnName);
        pMap.put("serviceUsageState", serviceUsageState);
        return commonRequest(functionNm,pMap);
    }



    /**
     * 7.4.3CMIOT_API23M11-单卡GPRS流量（PCC策略）限速
     * @param key
     * @param value
     * @param apnName
     * @param pccCode
     * @param serviceUsageState
     * @return
     */
    public String simPccGprsSpeed(String key, String value, String apnName, String pccCode, String serviceUsageState) {
        String functionNm =  operate+"/sim-pcc-gprs-speed";
        pMap.put(key, value);
        pMap.put("apnName", apnName);
        pMap.put("pccCode", pccCode);
        pMap.put("serviceUsageState", serviceUsageState);
        return commonRequest(functionNm,pMap);
    }

    //------------[7.5业务办理-状态变更类]

    /**
     * 7.5.1CMIOT_API23S03-单卡状态变更
     * @param key
     * @param value
     * @param operType [0:申请停机(已激活转已停机) / 1:申请复机(已停机转已激活) / 2:库存转已激活 / 3:可测试转库存 / 4:可测试转待激活 / 5:可测试转已激活 / 6:待激活转已激活 / 8:待激活转库存 / 9:库存转待激活]
     * @return
     */
    public String changeSimStatus(String key, String value, String operType) {
        String functionNm =  change+"/sim-status";
        pMap.put(key, value);
        pMap.put("operType", operType);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.5.2CMIOT_API23S06-物联卡状态变更批量办理
     * @param msisdns
     * @param reason
     * @param operType [操作类型： / 1 可测试->库存； / 2可测试->待激活； / 3可测试->已激活； / 4库存->待激活 / 5库存->已激活 / 6 待激活->库存 / 7待激活->已激活 / 8 待激活->已停机（暂不支持）9已激活->已停机 / 10已停机->待激活（暂不支持）11 已停机->已激活]
     * @return
     */
    public String changeSimStatusBatch(String msisdns, String reason, String operType) {
        String functionNm =  change+"/sim-status/batch";
        pMap.put("msisdns", msisdns);
        pMap.put("operType", operType);
        if(reason!=null){
            pMap.put("reason", reason);//停复机原因：在operType为9 或11时，原因必传 01：主动停复机
        }
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.5.3CMIOT_API25M03-物联卡GPRS上网功能重置
     * @param key
     * @param value
     * @return
     */
    public String simGprsStatusReset(String key, String value) {
        String functionNm =  operate+"/sim-gprs-status-reset";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[7.6业务办理-参数配置类]

    /**
     * 7.6.1CMIOT_API23M16-成员语音白名单配置
     * @param key
     * @param value
     * @param groupId
     * @param operType
     * @param whiteNumber
     * @return
     */
    public String memberVoiceWhitelist(String key, String value, String groupId, String operType, String whiteNumber) {
        String functionNm =  config+"/member-voice-whitelist";
        pMap.put(key, value);
        pMap.put("groupId", groupId);
        pMap.put("operType", operType);
        pMap.put("whiteNumber", whiteNumber);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.6.2CMIOT_API23M18-群组语音白名单配置
     * @param groupId
     * @param operType
     * @param whiteNumber
     * @return
     */
    public String groupVoiceWhitelist( String groupId, String operType, String whiteNumber) {
        String functionNm =  config+"/group-voice-whitelist";
        pMap.put("groupId", groupId);
        pMap.put("operType", operType);
        pMap.put("whiteNumber", whiteNumber);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 7.6.3CMIOT_API23M19-物联卡节电参数变更批量办理
     * @param msisdns
     * @param operType
     * @param oldApnName
     * @param newApnName
     * @param lowPowerMode
     * @param tedrx
     * @param tauPrecisionType
     * @param tauPrecisionVal
     * @param psmPrecisionType
     * @param psmPrecisionVal
     * @return
     */
    public String simParameterNodeBatch( String msisdns, String operType, String oldApnName,String newApnName, String lowPowerMode, String tedrx,
    String tauPrecisionType, String tauPrecisionVal, String psmPrecisionType,String psmPrecisionVal) {
        String functionNm =  change+"/sim-parameter-node/batch";
        pMap.put("msisdns", msisdns);
        pMap.put("operType", operType);
        pMap.put("oldApnName", oldApnName);
        pMap.put("newApnName", newApnName);
        pMap.put("operType", operType);
        pMap.put("lowPowerMode", lowPowerMode);
        if(tedrx!=null){
            pMap.put("tedrx", tedrx);
        }
        if(tauPrecisionType!=null){
            pMap.put("tauPrecisionType", tauPrecisionType);
        }
        if(tauPrecisionVal!=null){
            pMap.put("tauPrecisionVal", tauPrecisionVal);
        }
        if(psmPrecisionType!=null){
            pMap.put("psmPrecisionType", psmPrecisionType);
        }
        if(psmPrecisionVal!=null){
            pMap.put("psmPrecisionVal", psmPrecisionVal);
        }
        return commonRequest(functionNm,pMap);
    }

    //------------[8.3漫途定位-电子围栏（付费）※]

    /**
     * 8.3.3CMIOT_API25L18-批量添加围栏监控物联卡
     * @param key
     * @param value
     * @param fenceId
     * @param setState
     * @return
     */
    public String addMonitoredCardBatch(String key, String value, String fenceId, String setState) {
        String functionNm =  add+"/monitored-card/batch";
        pMap.put(key, value);
        pMap.put("fenceId", fenceId);
        pMap.put("setState", setState);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.3.4CMIOT_API25L19-批量删除围栏监控物联卡
     * @param key
     * @param value
     * @param fenceId
     * @return
     */
    public String deleteMonitoredCardBatch(String key, String value, String fenceId) {
        String functionNm =  delete+"/monitored-card/batch";
        pMap.put(key, value);
        pMap.put("fenceId", fenceId);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.3.5CMIOT_API25L22-批量添加物联卡位置变动监控
     * @param key
     * @param value
     * @return
     */
    public String addLocationMonitoredCardBatch(String key, String value) {
        String functionNm =  add+"/location-monitored-card/batch";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.3.6CMIOT_API25L23-批量删除物联卡位置变动监控
     * @param key
     * @param value
     * @return
     */
    public String deleteLocationMonitoredCardBatch(String key, String value) {
        String functionNm =  delete+"/location-monitored-card/batch";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[8.4漫途定位-电子围栏（免费）]

    /**
     * 8.4.1CMIOT_API25L07-创建圆形围栏
     * @param centLon
     * @param centLat
     * @param radius
     * @param inputType
     * @param fenceName
     * @return
     */
    public String createCircleFence(String centLon, String centLat, String radius, String inputType, String fenceName) {
        String functionNm =  create+"/circle-fence";
        pMap.put("centLon", centLon);
        pMap.put("centLat", centLat);
        pMap.put("radius", radius);
        pMap.put("inputType", inputType);
        pMap.put("fenceName", fenceName);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.4CMIOT_API25L10-删除围栏
     * @param fenceId
     * @return
     */
    public String deleteFence(String fenceId) {
        String functionNm =  delete+"/fence";
        pMap.put("fenceId", fenceId);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.5CMIOT_API25L13-创建多边形围栏
     * @param spotLocation
     * @param inputType
     * @param fenceName
     * @return
     */
    public String createPolygonFence(String spotLocation, String inputType, String fenceName) {
        String functionNm =  create+"/polygon-fence";
        pMap.put("spotLocation", spotLocation);
        pMap.put("inputType", inputType);
        pMap.put("fenceName", fenceName);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.6CMIOT_API25L14-创建行政区域（地市级）围栏
     * @param cityCode
     * @param fenceName
     * @return
     */
    public String createDistrictFence(String cityCode,String fenceName) {
        String functionNm =  create+"/district-fence";
        pMap.put("cityCode", cityCode);
        pMap.put("fenceName", fenceName);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.7CMIOT_API25L15-更新圆形围栏
     * @param fenceId
     * @param centLon
     * @param centLat
     * @param radius
     * @param inputType
     * @param fenceName
     * @return
     */
    public String updateCircleFence(String fenceId,String centLon,String centLat,String radius,String inputType,String fenceName) {
        String functionNm =  update+"/circle-fence";
        pMap.put("fenceId", fenceId);
        pMap.put("centLon", centLon);
        pMap.put("centLat", centLat);
        pMap.put("radius", radius);
        pMap.put("inputType", inputType);
        pMap.put("fenceName", fenceName);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.8CMIOT_API25L16-更新多边形围栏
     * @param fenceId
     * @param spotLocation
     * @param inputType
     * @param fenceName
     * @return
     */
    public String updatePolygonFence(String fenceId,String spotLocation,String inputType,String fenceName) {
        String functionNm =  update+"/polygon-fence";
        pMap.put("fenceId", fenceId);
        pMap.put("spotLocation", spotLocation);
        pMap.put("inputType", inputType);
        pMap.put("fenceName", fenceName);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.9CMIOT_API25L17-更新行政区域（地市级）围栏
     * @param fenceId
     * @param cityCode
     * @param fenceName
     * @return
     */
    public String updateDistrictFence(String fenceId,String cityCode,String fenceName) {
        String functionNm =  update+"/district-fence";
        pMap.put("fenceId", fenceId);
        pMap.put("cityCode", cityCode);
        pMap.put("fenceName", fenceName);
        return commonRequest(functionNm,pMap);
    }

    //------------[8.6漫途定位-历史轨迹（卡号类）]

    /**
     * 8.6.1CMIOT_API25L29-批量开启物联卡轨迹存储
     * @param key
     * @param value
     * @return
     */
    public String addLocationSavedCardsBatch(String key,String value) {
        String functionNm =  add+"/location-saved-cards/batch";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.6.2CMIOT_API25L30-批量停止物联卡轨迹存储
     * @param key
     * @param value
     * @return
     */
    public String deleteLocationSavedCardsBatch(String key,String value) {
        String functionNm =  delete+"/location-saved-cards/batch";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[9终端控制类]

    /**
     * 9.1.1CMIOT_API25C00-物联卡终端控制下行模板短信
     * @param actionid
     * @param msisdns
     * @param params
     * @param encode
     * @return
     */
    public String operateSimMtSms(String actionid,String msisdns, String params, String encode) {
        String functionNm =  operate+"/sim-mt-sms";
        pMap.put("actionid", actionid);
        pMap.put("msisdns", msisdns);
        pMap.put("params", params);
        pMap.put("encode", encode);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 9.1.2CMIOT_API25C04-物联卡终端控制下行空短信
     * @param msisdns
     * @return
     */
    public String operateSimMtNullSms(String msisdns) {
        String functionNm =  operate+"/sim-mt-null-sms";
        pMap.put("msisdns", msisdns);
        return commonRequest(functionNm,pMap);
    }












}