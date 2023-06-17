package top.iotos.system.service.impl.iotos.sys;


import org.springframework.stereotype.Service;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.common.mapper.mysql.sys.ConfigMapper;
import top.iotos.common.mapper.mysql.sys.FrontPageMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.synApi.utils.iotos.time.VeDate;
import top.iotos.system.service.iotos.sys.IFrontPageService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class FrontPageServiceimpl implements IFrontPageService {

    @Resource
    private FrontPageMapper frontPageMapper;
    @Resource
    private MQAide mQAide;
    @Resource
    private ConfigMapper configMapper;
    @Resource
    private RedisCache redisCache;


    @Override
    public Map<String, Object> find(Map map) {
        Map<String, Object> rMap = new HashMap<>();
        Map<String, Object> sendMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        boolean bool = false;

        String record_date =  VeDate.getBeforeAfterDate(VeDate.getStringDateShort(),-1);//昨天
        String dept_id = map.get("dept_id").toString();

        //查询 redis 缓存是否储存信息
        String rKey = "frontPageData:"+dept_id+":"+record_date;
        Object wdataExist = redisCache.getCacheObject(rKey);
       // if(wdataExist==null){
            sendMap.put("dept_id",dept_id);
            sendMap.put("record_date",record_date);
            map.put("record_date",record_date);

            String id = frontPageMapper.findId(map);
            if(id!=null){
                map.put("id",id);
                data = frontPageMapper.find(map);

                Map<String, Object> findMap = new HashMap<>();
                String config_key = "sys.frontPage.time";
                findMap.put("config_key",config_key);
                Object wExist = redisCache.getCacheObject(config_key);
                String expirationMinutes = "15";//失效 分钟数
                if(wExist==null){
                    Map<String, Object> configMap = configMapper.selectConfig(findMap);
                    if(configMap!=null && configMap.get("config_value")!=null){
                        expirationMinutes = configMap.get("config_value").toString();
                    }
                    redisCache.setCacheObject(config_key, expirationMinutes, Integer.parseInt(expirationMinutes)*60, TimeUnit.SECONDS);
                }else{
                    expirationMinutes = wExist.toString();
                }
                redisCache.setCacheObject(rKey, data, 15*60, TimeUnit.SECONDS);
                String last_upd_time = data.get("last_upd_time").toString();
                if(!VeDate.isWithinXMinutes(last_upd_time,Integer.parseInt(expirationMinutes))){//超过失效分钟时 发送
                    sendMap.put("type","upd");// 更新类型
                    bool = sendMsg(sendMap);
                }else {
                    bool = true;
                }
            }else {
                bool = sendMsg(sendMap);
            }
      /*  }else {
            data = redisCache.getCacheObject(rKey);
            bool = true;
        }*/

        rMap.put("data",data);
        rMap.put("bool",bool);
        return rMap;
    }

    private boolean sendMsg (Map<String, Object> sendMap){
        String queueName = "task_frontPageGenerate_queue";
        String routingKey = "task.frontPageGenerate.queue";
        String exchangeName = "task_exchange";//路由
       return mQAide.send(exchangeName, routingKey, 30, sendMap);
    }


}
