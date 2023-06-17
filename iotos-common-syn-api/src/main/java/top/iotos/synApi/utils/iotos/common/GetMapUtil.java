package top.iotos.synApi.utils.iotos.common;

import com.alibaba.fastjson2.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Map 帮助类
 */
public class GetMapUtil {


    /**
     * 获取 pMap 》 key
     * @param pMap
     * @param key
     * @param defaultsValue
     * @return
     */
    public static String getValueToStr(Map<String,Object> pMap,String key,String defaultsValue){
        Object obj = getValue(pMap,key,defaultsValue);
        String str = obj!=null?obj.toString():defaultsValue;
        return str;
    }

    public static Object getValue(Map<String,Object> pMap,String key,Object defaultsValue){
        Object value = pMap.get(key)!=null?pMap.get(key):defaultsValue;
        return value;
    }


    /**
     * 给 map 一个默认值 和一个key
     * @param pMap
     * @param key
     * @param defaultsValue
     * @return
     */
    public static Map<String,Object> getMap(Map<String,Object> pMap,String key,Object defaultsValue){
        if(pMap.get(key)==null){
            pMap.put(key,defaultsValue);
        }
        return pMap;
    }

    public static String getValueToStr(Map<String,Object> pMap,String key){
        return getValue(pMap,key,"").toString();
    }
    public static String getValueToStr(Map<String,Object> pMap,String key,Object defaultsValue){
        return getValue(pMap,key,defaultsValue).toString();
    }
    /**
     * 给Map 赋值
     * @param map
     * @param key
     * @param value
     * @return
     */
    public static Map<String,Object> putMap(Map<String,Object> map,String key,Object value){
        if(value!=null){
            map.put(key,value);
        }
        return map;
    }

    public static Map<String,Object> putMapJsonStr(Map<String,Object> map,String key,Object value){
        if(value!=null ){
            map.put(key, JSON.toJSONString(value));
        }
        return map;
    }



    public static Map<String,Object> putMap(Map<String,Object> map,String key,Object value,Object defaultsValue){
        if(value!=null){
            map.put(key,value);
        }else {
            map.put(key,defaultsValue);
        }
        return map;
    }


    public static void main(String[] args) {
        Map<String,Object> pMap = new HashMap<>();
        System.out.println(pMap);
        pMap = GetMapUtil.getMap(pMap,"iccid",null);
        System.out.println(pMap);

    }

}
