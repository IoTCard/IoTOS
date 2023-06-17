package top.iotos.synApi.utils.iotos.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DictUtil {


    /**
     * 字典获取
     * @param wMap 写入Map
     * @param dictArr 字典 list
     * @param basis 获取依据 字段 与 dictValue 判断
     * @param fieldName 返回字段名称
     * @return
     */
    public static Map<String,Object> writeDict (Map<String,Object> wMap, List<Map<String, Object>> dictArr, String basis, String fieldName){
        String status_id = wMap.get(basis)!=null?wMap.get(basis).toString():null;
        boolean bool = false;
        if(status_id!=null){
            for (int i = 0; i < dictArr.size(); i++) {
                Map<String, Object>  dictData =  dictArr.get(i);
                String dictValue = dictData.get("dictValue").toString();
                if(dictValue.equals(status_id)){
                    wMap.put(fieldName,dictData.get("dictLabel"));
                    bool = true;
                    break;
                }
            }
        }
        //字段 默认值
        if(!bool){
            wMap.put(fieldName,"");
        }
        return wMap;
    }


    /**
     * 获取字典值
     * @param wMap
     * @param dictArr
     * @param basis
     * @return
     */
    public static String getDictValue (Map<String,Object> wMap, List<Map<String, Object>> dictArr, String basis){
        String value = wMap.get(basis)!=null?wMap.get(basis).toString():null;
        String val= null;
        if(value!=null){
            for (int i = 0; i < dictArr.size(); i++) {
                Map<String, Object>  dictData =  dictArr.get(i);
                String dictLabel = dictData.get("dictLabel").toString();
                if(dictLabel.equals(value)){
                    val = dictData.get("dictValue").toString();
                    break;
                }
            }
        }
        return val;
    }

    public static String getDictLabel (List<Map<String, Object>> dictArr, String basis){
        String value = basis;
        String val= null;
        if(value!=null){
            for (int i = 0; i < dictArr.size(); i++) {
                Map<String, Object>  dictData =  dictArr.get(i);
                String dictValue = dictData.get("dictValue").toString();
                if(dictValue.equals(value)){
                    val = dictData.get("dictLabel").toString();
                    break;
                }
            }
        }
        return val;
    }



    /**
     * 写入 企业名称
     * @param carMap
     * @param deptsOptions
     * @return
     */
    public static Map<String,Object> writeDept (Map<String,Object> carMap,List<Map<String,Object>> deptsOptions){
        String dept_id = carMap.get("dept_id")!=null?carMap.get("dept_id").toString():null;
        boolean bool = false;
        if(deptsOptions!=null && dept_id!=null){
            for (int i = 0; i < deptsOptions.size(); i++) {
                Map<String,Object> depts =  deptsOptions.get(i);
                String f_dept_id = depts.get("dept_id").toString();
                if(f_dept_id.equals(dept_id)){
                    carMap.put("dept_name",depts.get("dept_name"));
                    bool = true;
                    break;
                }
            }
        }
        if(!bool){
            carMap.put("dept_name","");
        }
        return carMap;
    }




}
