package top.iotos.synApi.upstreamApi.chinaMobile.oneLink.ecV5;



import java.util.Map;

/**
 *
 *   /batch 批量查询仅 100张
 */
public class InquireEcV5 extends EcV5Api {

    public InquireEcV5(Map<String, Object> initMap, String template){
        super(initMap,template);
    }
    public InquireEcV5() {
        super();
    }

    private final String query = "/query";
    private final String location = "/location";





    /**
     * 5.1.1CMIOT_API23M27-单卡操作订单处理情况查询
     * @param orderNum 订单编号
     * @return
     */
    public String orderInfo(String orderNum) {
        String functionNm = query+"/order-info";
        pMap.put("orderNum", orderNum);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.1.2 CMIOT_API23M10-物联卡业务批量办理结果查询
     * @param orderNum 订单编号
     * @return
     */
    public String querySimBatchResult(String orderNum) {
        String functionNm =   query+"/sim-batch-result";
        pMap.put("orderNum", orderNum);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.1.3 CMIOT_API23M40-物联卡管理停复机冻结状态查询
     * @param key [msisdn/iccid/imsi]
     * @param value 对应号码
     * @return
     */
    public String querySimManageStopRestartStatus(String key,String value) {
        String functionNm =   query+"/sim-manage-stop-restart-status";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }


    /**
     * 5.1.4CMIOT_API23S00-单卡基本信息查询
     * @param key [msisdn/iccid/imsi]
     * @param value 对应号码
     * @return
     */
    public String querySimBasicInfo(String key,String value) {
        String functionNm =   query+"/sim-basic-info";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.1.5CMIOT_API23S02-单卡状态变更历史查询
     * @param key
     * @param value
     * @return
     */
    public String querySimChangeHistory(String key,String value) {
        String functionNm =   query+"/sim-change-history";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.1.6CMIOT_API23S04-单卡实时使用终端IMEI查询
     * @param key
     * @param value
     * @return
     */
    public String querySimImei(String key,String value) {
        String functionNm =   query+"/sim-imei";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.1.7CMIOT_API25S04-单卡状态查询
     * @param key
     * @param value
     * @return
     */
    public String querySimStatus(String key,String value) {
        String functionNm =   query+"/sim-status";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.1.8CMIOT_API25S05-码号信息批量查询
     * @param key [msisdns,iccids,imsis]
     * @param value
     * @return
     */
    public String querySimCardInfoBatch(String key,String value) {
        String functionNm =   query+"/sim-card-info/batch";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.1.9 CMIOT_API25S06-物联卡归属平台批量查询
     * @param key [msisdns,iccids,imsis]
     * @param value
     * @return
     */
    public String querySimPlatformBatch(String key,String value) {
        String functionNm =   query+"/sim-platform/batch";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.1.10 CMIOT_API25S02-单卡停机原因查询
     * @param key
     * @param value
     * @return
     */
    public String querySimStopReason(String key,String value) {
        String functionNm =   query+"/sim-stop-reason";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[5.2物联卡管理-用量类（流量）]

    /**
     * 5.2.1CMIOT_API23U07-单卡本月套餐内流量使用量实时查
     * @param key
     * @param value
     * @return
     */
    public String querySimDataMargin(String key,String value) {
        String functionNm =   query+"/sim-data-margin";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.2.2CMIOT_API25U04-单卡本月流量累计使用量查询
     * @param key
     * @param value
     * @return
     */
    public String querySimDataUsage(String key,String value) {
        String functionNm =   query+"/sim-data-usage";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.2.3CMIOT_API25U01-物联卡单日GPRS流量使用量批量
     * @param key
     * @param value
     * @return
     */
    public String querySimDataUsagenDailyBatch(String key,String value) {
        String functionNm =   query+"/sim-data-usage-daily/batch";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.2.4CMIOT_API23U12-单卡流量池内使用量实时查询
     * @param key
     * @param value
     * @return
     */
    public String querySimDataUsagenInpool(String key,String value) {
        String functionNm =   query+"/sim-data-usage-inpool";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.2.5CMIOT_API25U03-物联卡单月GPRS流量使用量批量
     * @param key
     * @param value
     * @param queryDate yyyyMM
     * @return
     */
    public String querySimDataUsagenMonthlyBatch(String key,String value,String queryDate) {
        String functionNm =   query+"/sim-data-usage-monthly/batch";
        pMap.put(key, value);
        pMap.put("queryDate", queryDate);// 查询最近6个月中的某月，其中本月数据截止为前一天，日期格式为yyyyMM
        return commonRequest(functionNm,pMap);
    }

    //------------[5.3物联卡管理-用量类（群组流量）]

    /**
     * 5.3.1CMIOT_API23U00-群组本月流量累计使用量实时查询
     * @param key
     * @param value
     * @return
     */
    public String queryGroupDataUsagen(String key,String value) {
        String functionNm =   query+"/group-data-usage";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.3.2CMIOT_API23U04-群组本月套餐内流量使用量实时查
     * @param groupId
     * @return
     */
    public String queryGroupDataMargin(String groupId) {
        String functionNm =   query+"/group-data-margin";
        if(groupId!=null){
            pMap.put("groupId", groupId);
        }
        return commonRequest(functionNm,pMap);
    }

    //------------[5.4物联卡管理-套餐类]

    /**
     * 5.4.1CMIOT_API23R00-资费订购实时查询
     * @param queryType [1：客户接入类型/2：群组接入类型/3：sim接入类型]
     * @param groupId
     * @param msisdn
     * @return
     */
    public String queryOrderedOfferings(String queryType,String groupId,String msisdn) {
        String functionNm =   query+"/ordered-offerings";
        pMap.put("queryType", queryType);
        if(queryType.equals("2")){
            pMap.put("groupId", groupId);
        } else if (queryType.equals("3")) {
            pMap.put("msisdn", msisdn);
        }
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.4.2CMIOT_API23R01-资费详情实时查询
     * @param offeringId
     * @return
     */
    public String queryOfferingsDetail(String offeringId) {
        String functionNm =   query+"/offerings-detail";
        pMap.put("offeringId", offeringId);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.4.3CMIOT_API23R02-可订购资费实时查询
     * @param queryType
     * @param groupId
     * @param msisdn
     * @param pageSize
     * @param startNum
     * @param catalogId
     * @param categoryId
     * @return
     */
    public String queryPurchasableOfferings(String queryType,String groupId,String msisdn,String pageSize,String startNum,String catalogId,String categoryId) {
        String functionNm =   query+"/purchasable-offerings";
        pMap.put("queryType", queryType);
        if(queryType.equals("2")){
            pMap.put("groupId", groupId);
        } else if (queryType.equals("3")) {
            pMap.put("msisdn", msisdn);
        }
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        pMap.put("pageSize", pageSize);
        pMap.put("startNum", startNum);//开始页，从 1 开始
        pMap.put("catalogId", catalogId);//目录 id，详细信息参考接口 CMIOT_API23R07 目录节点实时查询
        pMap.put("categoryId", categoryId);//节点 id，多个节点用下划线隔开，例如 XXXX_XXXX。详细信息参考接口 CMIOT_API23R07 目录节点实时查询
        return commonRequest(functionNm,pMap);
    }


    /**
     * 5.4.4CMIOT_API23R03-可变更资费实时查询
     * @param queryType
     * @param groupId
     * @param msisdn
     * @param pageSize
     * @param startNum
     * @param descOfferingId
     * @return
     */
    public String queryChangeableOfferings(String queryType,String groupId,String msisdn,String pageSize,String startNum,String descOfferingId) {
        String functionNm =   query+"/changeable-offerings";
        pMap.put("queryType", queryType);
        if(queryType.equals("2")){
            pMap.put("groupId", groupId);
        } else if (queryType.equals("3")) {
            pMap.put("msisdn", msisdn);
        }
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        pMap.put("pageSize", pageSize);
        pMap.put("startNum", startNum);//开始页，从 1 开始
        pMap.put("descOfferingId", descOfferingId);//原资费ID
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.4.5CMIOT_API23R07-目录节点实时查询
     * @param queryScenes
     * @return
     */
    public String queryCategories(String queryScenes) {
        String functionNm =   query+"/categories";
        pMap.put("queryScenes", queryScenes);
        return commonRequest(functionNm,pMap);
    }

    //------------[5.5物联卡管理-用量类（短信）]

    /**
     * 5.5.1CMIOT_API23U06-单卡本月套餐内短信使用量实时查询
     * @param key
     * @param value
     * @return
     */
    public String querySimSmsMargin(String key,String value) {
        String functionNm =   query+"/sim-sms-margin";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.5.2CMIOT_API25U00-物联卡单日短信使用量批量查询
     * @param key
     * @param value
     * @return
     */
    public String querySimSmsUsageDailyBatch(String key,String value,String queryDate) {
        String functionNm =   query+"/sim-sms-usage-daily/batch";
        pMap.put(key, value);
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.5.3CMIOT_API25U02-物联卡单月短信使用量批量查询
     * @param key
     * @param value
     * @param queryDate
     * @return
     */
    public String querySimSmsUsageMonthlyBatch(String key,String value,String queryDate) {
        String functionNm =   query+"/sim-sms-usage-monthly/batch";
        pMap.put(key, value);
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.5.4CMIOT_API25U05-单卡本月短信累计使用量查询
     * @param key
     * @param value
     * @return
     */
    public String querySimSmsUsage(String key,String value) {
        String functionNm =  query+"/sim-sms-usage";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[5.6物联卡管理-账务类]

    /**
     * 5.6.1CMIOT_API23B00-集团客户账单实时查询
     * @param queryDate [指定月份，YYYYMM格式,只能查询当前月的前6个月账单。]
     * @return
     */
    public String queryEcBill(String queryDate) {
        String functionNm =   query+"/ec-bill";
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.6.2CMIOT_API23B01-单卡余额信息实时查询
     * @param key
     * @param value
     * @return
     */
    public String queryBalanceInfo(String key,String value) {
        String functionNm =  query+"/balance-info";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[5.7物联卡管理-通信类]

    /**
     * 5.7.1CMIOT_API25M00-单卡开关机状态实时查询
     * @param key
     * @param value
     * @return
     */
    public String queryOnOffStatus(String key,String value) {
        String functionNm =  query+"/on-off-status";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.7.2CMIOT_API25M01-单卡在线信息实时查询
     * @param key
     * @param value
     * @return
     */
    public String querySimSession(String key,String value) {
        String functionNm =  query+"/sim-session";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.7.3CMIOT_API23M02-集团客户短信白名单查询
     * @param pageSize
     * @param startNum
     * @return
     */
    public String queryEcMessageWhiteList(String pageSize,String startNum) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/ec-message-white-list";
        pMap.put("pageSize", pageSize);
        pMap.put("startNum", startNum);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.7.4CMIOT_API23M03-单卡已开通APN信息查询
     * @param key
     * @param value
     * @return
     */
    public String queryApnInfo(String key,String value) {
        String functionNm =  query+"/apn-info";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.7.5CMIOT_API23M08-单卡通信功能开通查询
     * @param key
     * @param value
     * @return
     */
    public String querySimCommunicationFunctionStatus(String key,String value) {
        String functionNm =  query+"/sim-communication-function-status";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.7.6CMIOT_API23M13-个人智能网语音信息查询
     * @param key
     * @param value
     * @param startNum
     * @param pageSize
     * @return
     */
    public String querySimSmsUsageMonthlyBatch(String key,String value,String startNum,String pageSize) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =   query+"/person-smart-voice";
        pMap.put(key, value);
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.7.7CMIOT_API23M21-个人智能网语音白名单查询
     * @param key
     * @param value
     * @return
     */
    public String queryPersonVoiceWhiteNumber(String key,String value) {
        String functionNm =  query+"/person-voice-white-number";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.7.8CMIOT_API23M22-个人智能网语音限制区域查询
     * @param key
     * @param value
     * @return
     */
    public String queryPersonVoiceLimitRegion(String key,String value) {
        String functionNm =  query+"/person-voice-limit-region";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[5.8物联卡管理-集团类]

    /**
     * 5.8.1CMIOT_API23E00-指定日期状态变更卡号信息查询
     * @param changeDate
     * @param cardStatus
     * @param startNum
     * @param pageSize
     * @return
     */
    public String queryChangeableSimDate(String changeDate,String cardStatus,String startNum,String pageSize) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/changeable-sim-date";
        pMap.put("changeDate", changeDate);
        pMap.put("cardStatus", cardStatus);
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.2CMIOT_API23E01-群组所属成员查询
     * @param groupId
     * @param startNum
     * @param pageSize
     * @return
     */
    public String queryGroupMember(String groupId,String startNum,String pageSize) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/group-member";
        pMap.put("groupId", groupId);
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.3CMIOT_API23E02-成员归属群组查询
     * @param key
     * @param value
     * @return
     */
    public String queryGroupByMember(String key,String value) {
        String functionNm =  query+"/group-by-member";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.4CMIOT_API23E03-群组成员管理
     * @param msisdn
     * @param groupId
     * @param operType
     * @param effectType
     * @return
     */
    public String queryGroupManagment(String msisdn,String groupId,String operType,String effectType) {
        String functionNm =  query+"/group-managment";
        pMap.put("msisdn", msisdn);
        pMap.put("groupId", groupId);
        pMap.put("operType", operType);
        if(effectType!=null && effectType.length()>0){
            pMap.put("effectType", effectType);
        }
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.5CMIOT_API23E05-集团生命周期各状态用户数查询
     * @return
     */
    public String queryEcSimStatusNumber() {
        String functionNm =  query+"/ec-sim-status-number";
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.6CMIOT_API23E06-集团群组信息查询
     * @param startNum
     * @param pageSize
     * @return
     */
    public String queryGroupInfo(String startNum,String pageSize) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/group-info";
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.7CMIOT_API23E10-集团账户信息查询
     * @param startNum
     * @param pageSize
     * @return
     */
    public String queryAccountInfo(String startNum,String pageSize) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/account-info";
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.8CMIOT_API25E00-集团用户数查询
     * @param queryDate
     * @return
     */
    public String querySimTotalNumEc(String queryDate) {
        String functionNm =  query+"/sim-total-num-ec";
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.9CMIOT_API23M15-成员语音白名单查询
     * @param key
     * @param value
     * @param groupId
     * @return
     */
    public String queryMemberVoiceWhitelist(String key,String value,String groupId) {
        String functionNm =  query+"/member-voice-whitelist";
        pMap.put(key, value);
        pMap.put("groupId", groupId);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.10CMIOT_API23M17-群组语音白名单查询
     * @param groupId
     * @return
     */
    public String queryGroupVoiceWhitelist(String groupId) {
        String functionNm =  query+"/group-voice-whitelist";
        pMap.put("groupId", groupId);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.8.11CMIOT_API23M14-集团智能网语音信息查询
     * @param startNum
     * @param pageSize
     * @return
     */
    public String queryGroupSmartVoice(String startNum,String pageSize) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/group-smart-voice";
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        return commonRequest(functionNm,pMap);
    }

    //------------[5.10物联卡管理 - 用量类（语音）]

    /**
     * 5.10.1CMIOT_API23U01-单卡本月语音累计使用量实时查
     * @param key
     * @param value
     * @return
     */
    public String querySimVoiceUsage(String key,String value) {
        String functionNm =  query+"/sim-voice-usage";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.10.2CMIOT_API23U05-单卡本月套餐内语音使用量实时查询
     * @param key
     * @param value
     * @return
     */
    public String querySimVoiceMargin(String key,String value) {
        String functionNm =  query+"/sim-voice-margin";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }


    /**
     * 5.10.3 CMIOT_API23U08-物联卡单日语音使用量批量查询
     * @param key
     * @param value
     * @param queryDate [查询具体某一天时间。当前时间前一天开始的 7 日内。日期格式为 yyyyMMdd]
     * @return
     */
    public String querySimVoiceUsageDailyBatch(String key,String value,String queryDate) {
        String functionNm =  query+"/sim-voice-usage-daily/batch";
        pMap.put(key, value);
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 5.10.4CMIOT_API23U09-物联卡单月语音使用量批量查询
     * @param key
     * @param value
     * @param queryDate [查询具体某一天时间。当前时间前一天开始的 7 日内。日期格式为 yyyyMM]
     * @return
     */
    public String querySimVoiceUsageMonthlyBatch(String key,String value,String queryDate) {
        String functionNm =  query+"/sim-voice-usage-monthly/batch";
        pMap.put(key, value);
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }

    //------------[6安联卫类]

    /**
     * 6.1.1CMIOT_API23A04-物联卡机卡分离状态查询
     * @param msisdn
     * @param testType [分离检测方式：0：话单侧检测 1：网络侧检测]
     * @returnquery/ordered-offerings
     */
    public String queryCardBindStatus(String msisdn,String testType) {
        String functionNm =  query+"/card-bind-status";
        pMap.put("msisdn", msisdn);
        pMap.put("testType", testType);
        return commonRequest(functionNm,pMap);
    }

    //------------[6.2安联卫-活体实名认证类※]

    /**
     * 6.2.1CMIOT_API25A13-活体实名认证请求
     * @param phone
     * @return
     */
    public String secureliveIdAuth(String phone) {
        String functionNm =  "/secure/live-id-auth";
        pMap.put("phone", phone);
        return commonRequest(functionNm,pMap);
    }

    //------------[6.3安联卫–区域限制类]

    /**
     * 6.3.1CMIOT_API23A11-物联卡区域限制状态查询
     * @param key
     * @param value
     * @return
     */
    public String regionLimitStatus(String key,String value) {
        String functionNm =  query+"/region-limit-status";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 6.3.2CMIOT_API23A15-物联卡区域限制区域查询
     * @param key
     * @param value
     * @return
     */
    public String querySimRegionLimitArea(String key,String value) {
        String functionNm =  query+"/sim-region-limit-area";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[6.4安联卫–CMIOT实名登记]

    /**
     * 6.4.1CMIOT_API23A12-CMIOT物联卡实名登记
     * @param key
     * @param value
     * @param urlType [活体认证URL类型1：互联网标准视频认证URL 2：微信小程序视频认证URL]
     * @return
     */
    public String querySimRealNameReg(String key,String value,String urlType) {
        String functionNm =  "secure/sim-real-name-reg";
        pMap.put(key, value);
        pMap.put("urlType", urlType);
        return commonRequest(functionNm,pMap);
    }

    //------------[6.5安联卫–实名认证查询]

    /**
     * 6.5.1CMIOT_API23A10-物联卡实名登记状态查询
     * @param key
     * @param value
     * @return
     */
    public String querySimRealNameStatus(String key,String value) {
        String functionNm =  query+"/sim-real-name-status";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[6.6安联卫–风险控制类]

    /**
     * 6.6.1CMIOT_API25A17-风险行为物联卡明细查询
     * @param startNum
     * @param pageSize
     * @param riskScene [风险行为编码 详细查看文档]
     * @param queryDate
     * @return
     */
    public String riskSceneCardList(String startNum,String pageSize,String riskScene,String queryDate) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/risk-scene-card-list";
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        pMap.put("riskScene", riskScene);
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 查询 风险控制 公共请求体
     * @param functionNm
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String riskDetail(String functionNm,String msisdn,String queryDate) {
        functionNm = query+functionNm;
        pMap.put("msisdn", msisdn);
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }



    /**
     * 6.6.2CMIOT_API25A18-物联卡使用非定向语音且在敏感区域漫游使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk018Detail(String msisdn,String queryDate) {
        return riskDetail("/risk018-detail",msisdn,queryDate);
    }

    /**
     * 6.6.3CMIOT_API25A19-物联卡使用非定向语音且在手机终端使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk019Detail(String msisdn,String queryDate) {
        return riskDetail("/risk019-detail",msisdn,queryDate);
    }

    /**
     * 6.6.4CMIOT_API25A20-物联卡使用非定向流量且用手机终端在敏感区域漫游使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk020Detail(String msisdn,String queryDate) {
        return riskDetail("/risk020-detail",msisdn,queryDate);
    }

    /**
     * 6.6.5CMIOT_API25A21-物联卡手机终端使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk007Detail(String msisdn,String queryDate) {
        return riskDetail("/risk007-detail",msisdn,queryDate);
    }

    /**
     * 6.6.6CMIOT_API25A22-物联卡访问人联网使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk011Detail(String msisdn,String queryDate) {
        return riskDetail("/risk011-detail",msisdn,queryDate);
    }

    /**
     * 6.6.7CMIOT_API25A23-物联卡超语音白名单使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk009Detail(String msisdn,String queryDate) {
        return riskDetail("/risk009-detail",msisdn,queryDate);
    }

    /**
     * 6.6.8CMIOT_API25A24-物联卡超短信白名单使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk008Detail(String msisdn,String queryDate) {
        return riskDetail("/risk008-detail",msisdn,queryDate);
    }

    /**
     * 6.6.9CMIOT_API25A25-物联卡突破流量访问限制使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk010Detail(String msisdn,String queryDate) {
        return riskDetail("/risk010-detail",msisdn,queryDate);
    }

    /**
     * 6.6.10CMIOT_API25A26-物联卡漫游至敏感区域使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk015Detail(String msisdn,String queryDate) {
        return riskDetail("/risk015-detail",msisdn,queryDate);
    }

    /**
     * 6.6.11CMIOT_API25A27-物联卡超短信阈值使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk014Detail(String msisdn,String queryDate) {
        return riskDetail("/risk014-detail",msisdn,queryDate);
    }

    /**
     * 6.6.12CMIOT_API25A28-物联卡机卡分离使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk012Detail(String msisdn,String queryDate) {
        return riskDetail("/risk012-detail",msisdn,queryDate);
    }

    /**
     * 6.6.13CMIOT_API25A29-物联卡跨地区使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk013Detail(String msisdn,String queryDate) {
        return riskDetail("/risk013-detail",msisdn,queryDate);
    }

    /**
     * 6.6.14CMIOT_API25A30-物联卡异常短信使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk016Detail(String msisdn,String queryDate) {
        return riskDetail("/risk016-detail",msisdn,queryDate);
    }

    /**
     * 6.6.15CMIOT_API25A31-物联卡异常流量使用场景查询
     * @param msisdn
     * @param queryDate
     * @return
     */
    public String risk017Detail(String msisdn,String queryDate) {
        return riskDetail("/risk017-detail",msisdn,queryDate);
    }

    //------------[6.7安联卫–网络感知类]

    /**
     * 6.7.1CMIOT_API25E03-物联卡2G网络活跃区域查询
     * @param startNum
     * @param pageSize
     * @param beId [查询省份ID]
     * @param queryDate
     * @param regionId [查询地市ID]
     * @return
     */
    public String querySim2gActiveInfo(String startNum,String pageSize,String beId,String queryDate,String regionId) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/sim-2g-active-info";
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        if(beId!=null){
            pMap.put("beId", beId);
        }
        if(regionId!=null){
            pMap.put("regionId", regionId);
        }
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 6.7.2CMIOT_API25E04-物联卡使用网络制式用户数查询
     * @param startNum
     * @param pageSize
     * @param dateType [01：按月02：按日]
     * @param queryDate [按月查询时支持查询最近6个月中的某月，其中本月数据截止为前一天，日期格式为yyyyMM ; 按日查询时支持查询从前一日开始的最近30日中的某日，不包含当日数据，日期格式为 yyyyMMDD]
     * @return
     */
    public String querySimUsedSignalTypeNum(String startNum,String pageSize,String dateType,String queryDate) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/sim-used-signal-type-num";
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        pMap.put("dateType", dateType);
        pMap.put("queryDate", queryDate);
        return commonRequest(functionNm,pMap);
    }

    //------------[7业务办理类]

    /**
     * 7.1.2CMIOT_API23B03-物联卡账户充值交费结果查询
     * @param startNum
     * @param pageSize
     * @param orderNo
     * @param startTime
     * @param endTime
     * @return
     */
    public String querySimAccountRechargeResult(String startNum,String pageSize,String orderNo,String startTime,String endTime) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/sim-account-recharge-result";
        pMap.put("startNum", startNum);
        pMap.put("pageSize", pageSize);
        if(orderNo!=null){
            pMap.put("orderNo", orderNo);
        }
        if(startTime!=null){
            pMap.put("startTime", startTime);
        }
        if(endTime!=null){
            pMap.put("endTime", endTime);
        }
        return commonRequest(functionNm,pMap);
    }

    //------------[8漫途定位类]

    //------------[8.1漫途定位-位置定位(A类)※]

    /**
     * 8.1.1CMIOT_API25L01-物联卡实时位置区号查询
     * @param key
     * @param value
     * @return
     */
    public String districtPositionLocationMessage(String key, String value) {
        String functionNm =  query+"/district-position-location-message";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.1.2CMIOT_API25L02-物联卡最后上网位置经纬度查询
     * @param key
     * @param value
     * @return
     */
    public String lastPositionLocation(String key, String value) {
        String functionNm =  query+"/last-position-location";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.1.3CMIOT_API25L04-物联卡最后上网位置区号查询
     * @param key
     * @param value
     * @return
     */
    public String lastDistrictLocation(String key, String value) {
        String functionNm =  query+"/last-district-position";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[8.2漫途定位-位置定位(B类)※]

    /**
     * 8.2.1CMIOT_API25L00-物联卡实时位置经纬度查询
     * @param key
     * @param value
     * @return
     */
    public String positionLocationMessage(String key, String value) {
        String functionNm =  query+"/position-location-message";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.2.2CMIOT_API25L03-物联卡最后上网位置经纬度批量查询
     * @param key
     * @param value
     * @return
     */
    public String lastPositionLocationBatch(String key, String value) {
        String functionNm =  query+"/last-position-location/batch";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.2.3CMIOT_API25L05-物联卡最后上网位置区号批量查询
     * @param key
     * @param value
     * @return
     */
    public String lastDistrictLocationBatch(String key, String value) {
        String functionNm =  query+"/last-district-position/batch";
        pMap.put(key, value);
        return commonRequest(functionNm,pMap);
    }

    //------------[8.3漫途定位-电子围栏（付费）※]

    /**
     * 8.3.1CMIOT_API25L11-物联卡围栏状态单卡查询
     * @param key
     * @param value
     * @param fenceId
     * @return
     */
    public String queryLocationState(String key, String value, String fenceId) {
        String functionNm =  query+"/location-state";
        pMap.put(key, value);
        pMap.put("fenceId", fenceId);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.3.2CMIOT_API25L12-物联卡围栏状态批量查询
     * @param key
     * @param value
     * @param fenceId
     * @return
     */
    public String queryLocationStateBatch(String key, String value, String fenceId) {
        String functionNm =  query+"/location-state/batch";
        pMap.put(key, value);
        pMap.put("fenceId", fenceId);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.3.7CMIOT_API25L28-物联卡围栏状态即时查询
     * @param key
     * @param value
     * @param fenceId
     * @return
     */
    public String queryImLocationState(String key, String value, String fenceId) {
        String functionNm =  query+"/im-location-state";
        pMap.put(key, value);
        pMap.put("fenceId", fenceId);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.3.8CMIOT_API25L43-物联卡位置变动告警记录查询
     * @param key
     * @param value
     * @param startDate
     * @param endDate
     * @param pageSize
     * @param startNum
     * @return
     */
    public String queryLocationMonitorAlarm(String key, String value, String startDate, String endDate, String pageSize, String startNum) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/location-monitor-alarm";
        pMap.put(key, value);
        pMap.put("startDate", startDate);
        pMap.put("endDate", endDate);
        pMap.put("pageSize", pageSize);
        pMap.put("startNum", startNum);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.2CMIOT_API25L08-查询围栏信息
     * @param fenceId
     * @return
     */
    public String queryFence(String fenceId) {
        String functionNm =  query+"/fence";
        pMap.put("fenceId", fenceId);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.3CMIOT_API25L09-查询围栏列表
     * @param fenceName
     * @param fenceId
     * @param shape
     * @param pageSize
     * @param startNum
     * @return
     */
    public String queryAllFence(String fenceName, String fenceId, String shape, String pageSize, String startNum) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/all-fence";
        if(fenceName!=null){
            pMap.put("fenceName", fenceName);
        }
        if(fenceId!=null){
            pMap.put("fenceId", fenceId);
        }
        if(shape!=null){
            pMap.put("shape", shape);
        }
        if(pageSize!=null && startNum!=null){
            pMap.put("pageSize", pageSize);
            pMap.put("startNum", startNum);
        }
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.10CMIOT_API25L20-查询围栏监控物联卡
     * @param key
     * @param value
     * @param fenceId
     * @param setState
     * @param pageSize
     * @param startNum
     * @return
     */
    public String queryMonitoredCard(String key,String value, String fenceId, String setState, String pageSize, String startNum) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/monitored-card";
        pMap.put(key, value);
        pMap.put("fenceId", fenceId);
        if(setState!=null){
            pMap.put("setState", setState);
        }
        pMap.put("pageSize", pageSize);
        pMap.put("startNum", startNum);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.11CMIOT_API25L21-围栏告警信息记录查询
     * @param fenceId
     * @param startDate
     * @param endDate
     * @param pageSize
     * @param startNum
     * @return
     */
    public String queryHistoryAlarm(String fenceId, String startDate, String endDate, String pageSize, String startNum) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/history-alarm";
        pMap.put("fenceId", fenceId);
        pMap.put("startDate", startDate);
        pMap.put("endDate", endDate);
        pMap.put("pageSize", pageSize);
        pMap.put("startNum", startNum);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.4.12CMIOT_API25L24-查询物联卡位置变动监控列表
     * @param key
     * @param value
     * @param pageSize
     * @param startNum
     * @return
     */
    public String queryLocationMonitoredCard(String key,String value, String pageSize, String startNum) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/location-monitored-card";
        pMap.put(key, value);
        pMap.put("pageSize", pageSize);
        pMap.put("startNum", startNum);
        return commonRequest(functionNm,pMap);
    }

    //------------[8.5漫途定位-定位辅助包（免费）]

    /**
     * 8.5.1CMIOT_API25L06-经纬度坐标系批量转换
     * @param locations
     * @param inputType
     * @param outputType
     * @return
     */
    public String queryLocationTranslateBatch(String locations,String inputType, String outputType) {
        String functionNm =  location+"/translate/batch";
        pMap.put("locations", locations);
        pMap.put("inputType", inputType);
        pMap.put("outputType", outputType);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 8.5.2CMIOT_API25L42-经纬度逆地理解析
     * @param location
     * @return
     */
    public String queryLocationRegeo(String location) {
        String functionNm =  location+"/regeo";
        pMap.put("location", location);
        return commonRequest(functionNm,pMap);
    }

    //------------[8.6漫途定位-历史轨迹（卡号类）]

    /**
     * 8.6.3CMIOT_API25L32-物联卡历史轨迹信息查询
     * @param key
     * @param value
     * @param startTime
     * @param endTime
     * @return
     */
    public String queryHistoricalLocation(String key, String value, String startTime, String endTime) {
        String functionNm =  query+"/historical-location";
        pMap.put(key, value);
        pMap.put("startTime", startTime);
        pMap.put("endTime", endTime);
        return commonRequest(functionNm,pMap);
    }

    //------------[8.7漫途定位-历史轨迹（非卡号类）]

    /**
     * 8.7.1CMIOT_API25L31-查询物联卡轨迹存储
     * @param key
     * @param value
     * @param pageSize
     * @param startNum
     * @return
     */
    public String queryLocationSavedCards(String key,String value, String pageSize, String startNum) {
        pageSize = Integer.parseInt(pageSize)>50?"50":pageSize;//每次查询查询数目，不超过 50 条
        String functionNm =  query+"/location-saved-cards";
        pMap.put(key, value);
        pMap.put("pageSize", pageSize);
        pMap.put("startNum", startNum);
        return commonRequest(functionNm,pMap);
    }

    /**
     * 10.1.1CMIOT_APIAPI25E02-OneLink能力开放平台增值商品订购情况查询
     * @return
     */
    public String queryPurchasedGoods() {
        String functionNm =  query+"/purchased-goods";
        return commonRequest(functionNm,pMap);
    }



}