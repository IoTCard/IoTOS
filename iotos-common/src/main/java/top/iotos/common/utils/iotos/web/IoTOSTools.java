package top.iotos.common.utils.iotos.web;

import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.synApi.utils.iotos.common.ListCompare;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 常用工具
 */
@Component
public class IoTOSTools {

    @Resource
    private RedisCache redisCache;

    /**
     * 判断是否访问超频次
     * @param ip
     * @param method
     * @param times
     * @return
     */
    public boolean overclock(String ip, String method, int times) {
        String key = ip + "_IoTOS_" + method;
        Long count = redisCache.redisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            redisCache.expire(key, 60, TimeUnit.SECONDS);
        }
        if (count > times) {
            return true;
        }
        return false;
    }


    /**
     * map 指定list 切换成 String 【多参数】
     * @param map
     * @param keys
     * @return
     */
    public static Map<String, Object> getMapListToString(Map<String, Object> map,String... keys){
        if (map != null && keys != null)
        {
            for (String key : keys)
            {
                map = getMapListToString(map,key);
            }
        }
        return map;
    }


    /**
     * map 指定list 切换成 String
     * @param map
     * @param key
     * @return
     */
    public static Map<String, Object> getMapListToString(Map<String, Object> map,String key){
        if(map.get(key)!=null && map.get(key)!="" && map.get(key).toString().length()>0){
            List<String> sync_field =(List<String>) map.get(key);
            StringBuffer str = new StringBuffer();
            if (sync_field!=null && sync_field.size()>0){
                str.append(sync_field.get(0));
                for (int i = 1; i < sync_field.size(); i++) {
                    str.append(","+sync_field.get(i));
                }
            }
            map.put(key,str.toString());
        }
        return map;
    }


    /**
     * 过滤 排序条件
     * @param map
     * @param fieldArr
     * @return
     */
    public static Map<String, Object> filterOrderByType(Map<String, Object> map,String fieldArr[]){
        if(map.get("order_by_type")!=null && map.get("order_by_type")!="" && map.get("order_by_type").toString().length()>0){
            String order_by_type = map.get("order_by_type").toString();
            if(!ListCompare.Is_existence(fieldArr,order_by_type)){//判断是否表格字段不是直接赋值 为 null
                map.put("order_by_type",null);
            }
        }
        return map;
    }

    /**
     * 追加 getMap 指定key 至  rMap 的 newKey 中
     * @param rMap
     * @param getMap
     * @param keys
     * @return
     */
    public static Map<String, Object> getAppendMapData(Map<String, Object> rMap,Map<String, Object> getMap,String... keys){
        for (int i = 0; i < keys.length; i++) {
            String obj = keys[i];
            String keyVal[] = obj.split(":");
            String key = keyVal[0];
            String newKey = keyVal[1];
            if(getMap.get(key)!=null && getMap.get(key).toString().length()>0){
                rMap.put(newKey,getMap.get(key));
            }
        }
        return rMap;
    }


}
