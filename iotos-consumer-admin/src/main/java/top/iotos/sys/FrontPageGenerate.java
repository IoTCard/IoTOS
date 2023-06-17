package top.iotos.sys;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.common.mapper.mysql.card.record.UsedRecordCommonMapper;
import top.iotos.common.mapper.mysql.card.record.UsedRecordMapper;
import top.iotos.common.mapper.mysql.sys.FrontPageMapper;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.synApi.utils.iotos.common.GetMapUtil;
import top.iotos.synApi.utils.iotos.common.MyListMapSort;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 首页数据生成
 */
@Slf4j
@Component
public class FrontPageGenerate {
    @Resource
    private RedisCache redisCache;
    @Resource
    private UsedRecordMapper usedRecordMapper;
    @Resource
    private UsedRecordCommonMapper usedRecordCommonMapper;
    @Resource
    private CardInfoMapper cardInfoMapper;
    @Resource
    private CardMapper cardMapper;
    @Resource
    private FrontPageMapper frontPageMapper;
    @Resource
    private MQAide mQAide;



    @RabbitHandler
    @RabbitListener(queues = "task_frontPageGenerate_queue",containerFactory = "customContainerFactory")
    public void frontPageGenerate(String msg) {
        execute(msg);
    }



    @RabbitHandler
    @RabbitListener(queues = "dlx_task_frontPageGenerate_queue",containerFactory = "customContainerFactory")
    public void dlxFrontPageGenerate(String msg) {
        execute(msg);
    }

    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            String dept_id = map.get("dept_id").toString();
            String record_date = map.get("record_date").toString();
            String type = map.get("type")!=null?map.get("type").toString():"add";

            String prefix = "task_frontPageGenerate_queue";

            String key_val = prefix+":"+ dept_id+":"+record_date;
            Object  wExecute = redisCache.getCacheObject(key_val);
            //if(wExecute==null){ //执行前判断 redis 是否存在 执行数据 存在时 不执行
                redisCache.setCacheObject(key_val, msg, 1, TimeUnit.MINUTES);//1 分钟 缓存 避免 重复消费
                execution(record_date,dept_id,type);
             //}
        }catch (Exception e){
            log.error(">>错误 - 首页数据生成 消费者:{}<<", e.getMessage());
        }
    }

    public void execution(String record_date,String dept_id,String type) {
        Map<String, Object> addMap = new HashMap<>();
        addMap.put("dept_id",dept_id);
        addMap.put("record_date",record_date);

        boolean headquarters = dept_id.equals("100")?true:false;
        //获取下级企业id
        List<String> find_dept_id = new ArrayList<>();
        if(!headquarters) {
            Map<String, Object> findMap = new HashMap<>();
            findMap.put("deptId", dept_id);
            String arrStr = cardInfoMapper.queryChildrenAreaInfo(findMap);
            if (arrStr != null) {
                String dept_idArr[] = arrStr.split(",");
                if (dept_idArr != null) {
                    for (int i = 0; i < dept_idArr.length; i++) {
                        String f_dept_id = dept_idArr[i] != null && dept_idArr[i].length() > 0 ? dept_idArr[i] : null;
                        if (f_dept_id != null) {
                            find_dept_id.add(f_dept_id);
                        }
                    }
                } else {
                    find_dept_id.add(dept_id);
                }
            }
        }


        Map<String, Object> usage_line_map = new HashMap<>();//用量增长趋势
        Map<String, Object> usage_table_map = new HashMap<>();//用量排行榜
        Map<String, Object> life_cycle_pie_map = new HashMap<>();//生命周期占比


        Integer login_ip_count = 0;//本月登录IP总数
        Integer card_count = 0;//卡总数
        Integer agent_card_count = 0;//代理卡总数
        Integer dept_card_count = 0;//直属卡总数
        Integer dept_count = 0;//企业总数
        Integer card_add_count = 0;//本月新增卡号
        Integer card_activation_count = 0;//本月激活卡号




        String startDate = VeDate.getNextDay(record_date, "-6");//六天前日期
        //用量增长趋势 [日/月]
        Map<String, Object> usage_day_find = new HashMap<>();

        Map<String, Object> commonMap = new HashMap<>();
        if(!headquarters){
            commonMap.put("dept_id",find_dept_id);
        }
        String times[] = startDate.split("-");
        String firstDayMonth = times[0]+"-"+times[1]+"-01";
        String currentTime[] = record_date.split("-");
        String current_month = currentTime[0]+"-"+currentTime[1];//当前 记录日期 年月
        List<Map<String, Object>> activate_line = null;
        if(!type.equalsIgnoreCase("upd")){

            usage_day_find.putAll(commonMap);
            usage_day_find.put("startDate",startDate);
            usage_day_find.put("endDate",record_date);
            String []sevenDay = VeDate.getPreviousXDay(7,"yyyy-MM-dd");//获取 x 天 之前的日期

            List<Map<String, Object>> usage_day_list = usedRecordMapper.findTotalDayUsed(usage_day_find);//日用量
            usage_day_list = getList(usage_day_list,sevenDay,"record_date","record_date","total_day_used",0.0);
            usage_line_map.put("usage_day",usage_day_list);

            List<Map<String, Object>> usage_month_list = new ArrayList<>();

            //获取前五个月用量

            //先去查找 该企业之前有没有生成过数据 如果有直接取值之前生成的前几个月的 统计数据
            Map<String, Object> findIdMap = new HashMap<>();
            findIdMap.put("dept_id",dept_id);
            String id = frontPageMapper.findLastId(findIdMap);
            String []sixMonths = VeDate.getPreviousXMonth(6,"yyyy-MM");//获取 x 个月之前的日期
            String months5Ago = sixMonths[sixMonths.length-1];// 最小 日期
            if(id!=null){
                //匹对获取 对应月份用量
                findIdMap.put("id",id);
                Map<String, Object> lastPage = frontPageMapper.find(findIdMap);
                Map<String, Object> last_usage_line = JSON.parseObject(lastPage.get("usage_line").toString());
                List<Map<String, Object>> last_usage_month = (List<Map<String, Object>>) last_usage_line.get("usage_month");
                for (int i = sixMonths.length-1; i >=0 ; i--) {
                    String months = sixMonths[i];
                    for (int j = 0; j < last_usage_month.size(); j++) {
                        Map<String, Object> usage_month_map = last_usage_month.get(j);
                        String j_record_date = usage_month_map.get("record_date").toString();
                        if(j_record_date.equals(months)){//匹对月份数据增加
                            usage_month_list.add(usage_month_map);
                        }
                    }
                }
            }else {
                //查询历史用量分表信息
                for (int i = sixMonths.length-1; i >=0 ; i--) {
                    String months = sixMonths[i];
                    String monthsArr[] = months.split("-");
                    String yyyyMM = monthsArr[0]+monthsArr[1];
                    Map<String, Object> findEMap = new HashMap<>();
                    findEMap.putAll(commonMap);
                    findEMap.put("yyyyMM",yyyyMM);
                    String exists = usedRecordCommonMapper.findExists(findEMap);//查询是否有 该表
                    Map<String, Object> usage_month_map = new HashMap<>();
                    boolean isNull = false;
                    if(exists!=null){
                        usage_month_map = usedRecordCommonMapper.findTotalDayUsed(findEMap);
                        if(usage_month_map!=null){
                            usage_month_map.put("record_date",months);
                        }else {
                            isNull = true;
                        }
                    }else {
                        isNull = true;
                    }
                    if(isNull){
                        usage_month_map = new HashMap<>();
                        usage_month_map.put("record_date",months);
                        usage_month_map.put("total_day_used",0.0);
                    }

                    usage_month_list.add(usage_month_map);
                }
            }
            //本月用量
            usage_day_find.put("startDate",firstDayMonth);
            usage_day_find.put("endDate",record_date);
            List<Map<String, Object>> month_list = usedRecordMapper.findTotalMonthUsed(usage_day_find);
            if(month_list!=null && month_list.size()>0){
                Map<String, Object> lastMonth = usage_day_list.get(usage_day_list.size()-1);
                Map<String, Object> month_map = new HashMap<>();
                month_map.put("record_date",current_month);
                month_map.put("total_day_used",lastMonth.get("total_day_used"));
                usage_month_list.add(month_map);
            }
            usage_line_map.put("usage_month",usage_month_list);

            //激活增长趋势
            Map<String, Object> find_activate_map = new HashMap<>();
            find_activate_map.putAll(commonMap);
            find_activate_map.put("dateType","activate_date");
            find_activate_map.put("startDate",months5Ago+"-01");
            find_activate_map.put("endDate",record_date);
            find_activate_map.put("groupType","activateDateyyyyMM");

            activate_line = cardMapper.findDateGroup(find_activate_map);
            //定义一个新的 7个 月数组
            String []sevenMonths = new String[sixMonths.length + 1];
            System.arraycopy(sixMonths, 0, sevenMonths, 0, sixMonths.length);
            // 在新的数组的最后一个位置插入新的值
            sevenMonths[sevenMonths.length - 1] = current_month;
            activate_line = getList(activate_line,sevenMonths,"groupType","groupType","count","0");
            // 排序 groupType asc
            activate_line = MyListMapSort.listMapSortValToStr(activate_line,"asc","groupType");

            //用量排行榜 [日/月]
            Map<String, Object> find_table_day_map = new HashMap<>();
            find_table_day_map.putAll(commonMap);
            find_table_day_map.put("format","yyyyMMdd");
            find_table_day_map.put("record_date",record_date);
            List<Map<String, Object>> usage_table_day = usedRecordMapper.findLeaderboard(find_table_day_map);//日排行


            find_table_day_map.put("format","yyyyMM");
            find_table_day_map.put("record_date",current_month);
            List<Map<String, Object>> usage_table_month = usedRecordMapper.findLeaderboard(find_table_day_map);//月排行
            usage_table_map.put("usage_table_day",usage_table_day);
            usage_table_map.put("usage_table_month",usage_table_month);

        }else {
            usage_line_map = null;
            activate_line = null;
            usage_table_map = null;
        }

        //生命周期占比 [卡状态/卡状态描述(仅为管理员时生成)]
        Map<String, Object> find_life_cycle_map = new HashMap<>();
        find_life_cycle_map.putAll(commonMap);
        find_life_cycle_map.put("groupType","status_show_id");
        List<Map<String, Object>> statusShowList = cardMapper.findDateGroup(find_life_cycle_map);//卡状态
        List<Map<String, Object>> statusList = null;
        if(headquarters){
            find_life_cycle_map.put("groupType","status_id");
            statusList = cardMapper.findDateGroup(find_life_cycle_map);//卡状态描述
            // 排序 groupType asc
            statusList = MyListMapSort.listMapSortValToStr(statusList,"asc","groupType");
        }
        // 排序 groupType asc
        statusShowList = MyListMapSort.listMapSortValToStr(statusShowList,"asc","groupType");


        life_cycle_pie_map.put("statusShowList",statusShowList);
        life_cycle_pie_map.put("statusList",statusList);
        //企业拿卡占比
        Map<String, Object> find_dept_proportion_map = new HashMap<>();
        find_dept_proportion_map.putAll(commonMap);
        find_dept_proportion_map.put("groupType","dept_id");
        List<Map<String, Object>> dept_proportion_pie = cardMapper.findDateGroup(find_dept_proportion_map);
        //企业激活占比
        Map<String, Object> find_dept_activate_pie_map = new HashMap<>();
        find_dept_activate_pie_map.putAll(commonMap);
        find_dept_activate_pie_map.put("groupType","dept_id");
        find_dept_activate_pie_map.put("activateDateIsNull",0);//已有激活时间卡号
        List<Map<String, Object>> dept_activate_pie = cardMapper.findDateGroup(find_dept_activate_pie_map);
        //企业用量占比
        Map<String, Object> find_dept_usage_map = new HashMap<>();
        find_dept_usage_map.putAll(commonMap);
        find_dept_usage_map.put("groupType","c_used");
        List<Map<String, Object>> dept_usage_pie = cardMapper.findDateGroup(find_dept_usage_map);
        List<Map<String, Object>> channel_pie = null;
        List<Map<String, Object>> login_list = null;
        List<Map<String, Object>> login_map_list = null;
        if(headquarters){

            //通道卡号占比
            Map<String, Object> find_channel_map = new HashMap<>();
            find_channel_map.putAll(commonMap);
            find_channel_map.put("groupType","channel_id");
            channel_pie = cardMapper.findDateGroup(find_channel_map);


            //本月登录IP总数
            Map<String, Object> find_ip_count_map = new HashMap<>();
            find_ip_count_map.put("yyyyMM",current_month);
            find_ip_count_map.put("groupType","login_location");
            List<Map<String, Object>> ip_count_list = frontPageMapper.findLogin(find_ip_count_map);
            if(ip_count_list!=null && ip_count_list.size()>0){
                login_ip_count = ip_count_list.size();
            }
            //企业总数
            dept_count = frontPageMapper.findDeptCount(null);
            //登录次数用户占比
            Map<String, Object> find_login_map = new HashMap<>();
            find_login_map.put("yyyyMM",current_month);
            find_login_map.put("groupType","user_name");
            login_list = frontPageMapper.findLogin(find_login_map);
            //登录用户地图标记
            Map<String, Object> find_login_map_map = new HashMap<>();
            find_login_map_map.put("yyyyMM",current_month);
            find_login_map_map.put("groupType","login_location");
            login_map_list = frontPageMapper.findLogin(find_login_map_map);
        }
        //卡总数
        card_count = cardMapper.getListCount(commonMap);
        card_count = card_count!=null?card_count:0;
        //直属卡总数
        Map<String, Object> find_dept_card_map = new HashMap<>();
        List<String> deptList = new ArrayList<>();
        deptList.add(dept_id);
        find_dept_card_map.put("dept_id",deptList);
        dept_card_count = cardMapper.getListCount(find_dept_card_map);
        dept_card_count = dept_card_count!=null?dept_card_count:0;
        //代理卡总数 = 卡总数 - 直属卡总数
        agent_card_count = card_count-dept_card_count;
         //本月新增卡号
        Map<String, Object> find_card_map = new HashMap<>();
        find_card_map.putAll(commonMap);
        find_card_map.put("dateType","create_time");
        find_card_map.put("startDate",firstDayMonth);
        find_card_map.put("endDate",record_date);
        card_add_count = cardMapper.getListCount(find_card_map);
         //本月激活卡号
        find_card_map.put("dateType","activate_date");
        card_activation_count = cardMapper.getListCount(find_card_map);


        addMap =  GetMapUtil.putMapJsonStr(addMap,"usage_line",usage_line_map);
        addMap =  GetMapUtil.putMapJsonStr(addMap,"activate_line",activate_line);
        addMap =  GetMapUtil.putMapJsonStr(addMap,"usage_table",usage_table_map);
        addMap =  GetMapUtil.putMapJsonStr(addMap,"life_cycle_pie",life_cycle_pie_map);
        addMap =  GetMapUtil.putMapJsonStr(addMap,"dept_proportion_pie",dept_proportion_pie);
        addMap =  GetMapUtil.putMapJsonStr(addMap,"dept_activate_pie",dept_activate_pie);
        addMap =  GetMapUtil.putMapJsonStr(addMap,"dept_usage_pie",dept_usage_pie);
        addMap =  GetMapUtil.putMapJsonStr(addMap,"channel_pie",channel_pie);
        addMap =  GetMapUtil.putMap(addMap,"card_count",card_count,0);
        addMap =  GetMapUtil.putMap(addMap,"dept_card_count",dept_card_count,0);
        addMap =  GetMapUtil.putMap(addMap,"agent_card_count",agent_card_count,0);
        addMap =  GetMapUtil.putMap(addMap,"dept_count",dept_count,0);
        addMap =  GetMapUtil.putMap(addMap,"login_ip_count",login_ip_count,0);
        addMap =  GetMapUtil.putMapJsonStr(addMap,"login_pie",login_list);
        addMap =  GetMapUtil.putMapJsonStr(addMap,"login_map",login_map_list);
        addMap =  GetMapUtil.putMap(addMap,"card_add_count",card_add_count,0);
        addMap =  GetMapUtil.putMap(addMap,"card_activation_count",card_activation_count,0);


        String queueName = "task_frontPageSave_queue";
        String routingKey = "task.frontPageSave.queue";
        String exchangeName = "task_exchange";//路由
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("addMap",addMap);
        mQAide.send(exchangeName, routingKey, 30, sendMap);

    }


    /**
     * 没有数据时补 0
     * @param list
     * @param sevenDay
     * @param jKey
     * @param key
     * @param key2
     * @param defaults
     * @return
     */
    private List<Map<String, Object>> getList( List<Map<String, Object>> list,String []sevenDay,String jKey,String key,String key2,Object defaults){
        list = list!=null?list: new ArrayList<>();
        if(list.size()!=7){//不等于 7 时说明有部分日期没有获取到数据 开始 补 0
            List<Map<String, Object>> new_list = new ArrayList<>();//准备一个新的 list
            for (int i = sevenDay.length-1; i >=0; i--) {
                String yMd = sevenDay[i];
                Map<String, Object> map = new HashMap<>();
                boolean bool = false;
                for (int j = 0; j < list.size(); j++) {
                    Map<String, Object> jObj = list.get(j);
                    String j_jKey = jObj.get(jKey).toString();
                    if(yMd.equals(j_jKey)){
                        bool = true;
                        map = jObj;
                        break;
                    }
                }
                if(!bool){
                    map.put(key,yMd);
                    map.put(key2,defaults);
                }
                new_list.add(map);
            }
            list = new_list;
        }
        return list;
    }

    private List<Map<String, String>> getList( List<Map<String, String>> list,String []sevenDay,String jKey,String key,String key2,String defaults){
        list = list!=null?list: new ArrayList<>();
        if(list.size()!=7){//不等于 7 时说明有部分日期没有获取到数据 开始 补 0
            List<Map<String, String>> new_list = new ArrayList<>();//准备一个新的 list
            for (int i = sevenDay.length-1; i >=0; i--) {
                String yMd = sevenDay[i];
                Map<String, String> map = new HashMap<>();
                boolean bool = false;
                for (int j = 0; j < list.size(); j++) {
                    Map<String, String> jObj = list.get(j);
                    String j_jKey = jObj.get(jKey);
                    if(yMd.equals(j_jKey)){
                        bool = true;
                        map = jObj;
                        break;
                    }
                }
                if(!bool){
                    map.put(key,yMd);
                    map.put(key2,defaults);
                }
                new_list.add(map);
            }
            list = new_list;
        }
        return list;
    }


}