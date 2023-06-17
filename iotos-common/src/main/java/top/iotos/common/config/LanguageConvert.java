package top.iotos.common.config;


import com.alibaba.fastjson2.JSONObject;
import top.iotos.common.utils.iotos.common.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LanguageConvert {

    private static final Map<String,Object> EN_MAP = loadMap("config/text_en.json");
    private static final Map<String,Object> ZH_CN_MAP = loadMap("config/text_zh_CN.json");
    private static final Map<String,Object> ZH_TW_MAP = loadMap("config/text_zh_TW.json");




    public static String getMessage(Locale locale,String code) {
        List<String> codeList = new ArrayList<>();
        if(code.indexOf(".")!=-1) {//是否多级别
            String codeArr[] = code.split("\\.");
            for (int i = 0; i < codeArr.length; i++) {
                codeList.add(codeArr[i]);
            }
        }else {
            codeList.add(code);
        }
        String key = codeList.get(codeList.size()-1);
        Map<String,Object> rMap = getMaps(locale,code,null);
        String rValue = rMap!=null&&rMap.get(key)!=null?rMap.get(key).toString():"";
        return rValue;
    }



        private static Map<String,Object> getMaps(Locale locale,String code,Map<String,Object> data) {
        if(data==null){
            data = getMaps(locale);//获取语言包
        }
        if(code.indexOf(".")!=-1){//是否多级别
            String codeArr[] = code.split("\\.");
            String key = codeArr[0];
            Map<String,Object> rMap =  getMaps(data,key);
            if(rMap!=null){
                data = rMap;
                code = code.substring(code.indexOf(".")+1);
                if(code.length()>0){
                    return getMaps(locale,code,data);
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }else {
            return data;
        }
    }




    private static Map<String,Object> getMaps(Map<String,Object> map,String key) {
        Map<String,Object> rMap = null;
        if(map!=null && key!=null){
            try {
                rMap = (Map<String, Object>) map.get(key);
            }catch (Exception e){
                System.out.println("getMaps 转换获取"+key+" 失败 ");
            }
        }
        return rMap;
    }





    private static Map<String,Object> getMaps(Locale locale) {
        switch (locale.toString()) {
            case "zh_CN":
                return ZH_CN_MAP;
            case "zh_TW":
                return ZH_TW_MAP;
            default:
                return EN_MAP;
        }
    }



    private static Map<String,Object> loadMap(String resourceName){
        String jsonStr = JsonUtil.readJsonFile(resourceName);
        Map<String,Object> jsonMap = JSONObject.parseObject(jsonStr);
        return  jsonMap;
    }






}

