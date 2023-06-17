package top.iotos.synApi.utils.iotos.common;

import java.util.*;

/**
 * list Map 排序工具
 * @Description:
 */
public class MyListMapSort {


    /**
     * 排序  List<Map<String, Object>> 版
     * @param sList
     * @param sortType
     * @param conditions
     * @return
     */
    public static List<Map<String, String>> listMapObjectSort(List<Map<String, Object>> sList,String sortType,String... conditions){
        List<Map<String, String>> StrArr = new ArrayList<>();
        for (int i = 0; i < sList.size(); i++) {
            Map<String, Object> map = sList.get(i);
            Map<String, String> sMap = new HashMap<>();
            for(String key:map.keySet()){
                //System.out.println("key="+key+"and value=" +map.get(key));
                sMap.put(key,""+map.get(key));
            }
            StrArr.add(sMap);
        }
        return  listMapSort(StrArr,sortType,conditions);
    }




    /**
     * 排序
     * @param sList 需要排序的 List<Map<String, String>>
     * @param sortType 排序类型 asc【升序】 desc【降序】
     * @param conditions 排序条件
     * @return
     */
    public static List<Map<String, String>> listMapSort(List<Map<String, String>> sList,String sortType,String... conditions){
        //先根据 conditions 传入参数 进行排序
        Collections.sort(sList, new Comparator<Map<String, String>>() {
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                String sort1 = "";//从list中拿出来第一个的 conditions 拼接到一起
                String sort2 = "";//从list中拿出来第二个的 conditions 拼接到一起
                for (int i = 0; i < conditions.length; i++) {
                    sort1 += o1.get(conditions[i]);
                    sort2 += o2.get(conditions[i]);
                }
                if(sortType.equalsIgnoreCase("asc")){
                    return sort1.compareTo(sort2);//利用String类的compareTo方法
                }else{
                    return sort2.compareTo(sort1);//利用String类的compareTo方法
                }
            }
        });
        return  sList;
    }

    public static List<Map<String, Object>> listMapSortValToStr(List<Map<String, Object>> pList,String sortType,String... conditions){
        List<Map<String, String>> sList = new ArrayList<>();

        for (int i = 0; i < pList.size(); i++) {
            Map<String, Object> obj = pList.get(i);
            Map<String, String> newObj = new HashMap<>();
            for (Map.Entry<String, Object> entry : obj.entrySet()) {
                newObj.put(""+entry.getKey(),""+entry.getValue());
            }
            sList.add(newObj);
        }
        //先根据 conditions 传入参数 进行排序
        Collections.sort(sList, new Comparator<Map<String, String>>() {
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                String sort1 = "";//从list中拿出来第一个的 conditions 拼接到一起
                String sort2 = "";//从list中拿出来第二个的 conditions 拼接到一起
                for (int i = 0; i < conditions.length; i++) {
                    sort1 += o1.get(conditions[i]);
                    sort2 += o2.get(conditions[i]);
                }
                if(sortType.equalsIgnoreCase("asc")){
                    return sort1.compareTo(sort2);//利用String类的compareTo方法
                }else{
                    return sort2.compareTo(sort1);//利用String类的compareTo方法
                }
            }
        });
        List<Map<String, Object>> rList = new ArrayList<>();
        for (int i = 0; i < sList.size(); i++) {
            Map<String, String> obj = sList.get(i);
            Map<String, Object> newObj = new HashMap<>();
            for (Map.Entry<String, String> entry : obj.entrySet()) {
                newObj.put(""+entry.getKey(),""+entry.getValue());
            }
            rList.add(newObj);
        }
        return  rList;
    }


}
