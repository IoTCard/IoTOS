package top.iotos.synApi.utils.sync;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 卡状态 转换辅助类
 */
@Component
public class CardStateTransition {

    //卡状态描述 数组 [字典更新增加后需要增加！]
    private String[] stateArr = {"0,未知","1,库存","2,可测试","3,待激活","4,已激活","5,已停机","6,预销户","7,已销户","9,单项停机","10,预销号","11,过户","12,休眠","13,测试期正常","14,测试期停机","15,停机保号","16,机卡分离停机","17,空套餐","18,运营商管理状态","19,测试去激活","20,拆机","21,销号","22,违章停机","23,断网","24,开始","25,已失效","26,已清除","27,已更换","28,用户注销","29,停断网","30,挂失","31,故障卡","32,未实名停机","33,超量停机","34,已锁定","35,已过期","36,已终止"};

    /**
     * 获取卡号对应状态
     * @param key
     * @return
     */
    public Map<String,Object> getStateMap(String key){
        Map<String,Object> rMap = new HashMap<>();
        Integer status_id = 0;
        String statusMessage = "";

        for (int i = 0; i < stateArr.length; i++) {
            String arr[] = stateArr[i].split(",");
            if(arr[0].equals(key)){
                status_id = Integer.parseInt(arr[0]);
                statusMessage = arr[1];
                break;
            }
        }
        rMap.put("status_id",status_id);
        rMap.put("status_show_id",getStatusShowId(status_id));
        rMap.put("statusMessage",statusMessage);
        return rMap;
    }


    /**
     * 获取 status_show_id
     * @param status_id
     * @return
     */
    public Integer getStatusShowId(Integer status_id){
        Integer status_show_id = 0;
        if(status_id==0){
            status_show_id = 8;
        }else if(status_id== 1 || status_id==17 || status_id==18 || status_id==24 ){
            status_show_id = 1;
        }else if(status_id== 2 ){
            status_show_id = 2;
        }else if(status_id== 3 || status_id==13 || status_id==19 ){
            status_show_id = 3;
        }else if(status_id== 4 ){
            status_show_id = 4;
        }else if(status_id== 5 || status_id==9 || status_id==14 || status_id==15 || status_id==16 ||
                status_id==22 || status_id==23 || status_id==29 || status_id==31 || status_id==32 || status_id==33|| status_id==34|| status_id==35){
            status_show_id = 5;
        }else if(status_id== 6 || status_id==10 ){
            status_show_id = 6;
        }else if(status_id== 7 || status_id==11 || status_id==12 || status_id==20 || status_id==25 ||
                status_id==26 || status_id==28 || status_id==30 || status_id==36 || status_id==27 ){
            status_show_id = 7;
        }
        return status_show_id;
    }




    /**
     * OneLink EcV5 卡状态 转换为系统状态
     * @param pCode
     * @return
     */
    public Map<String,Object> getEcV5Status(Integer pCode){
        Map<String,Object> rMap = new HashMap<>();
        // 1：待激活 2：已激活4：停机6：可测试 7：库存 8：预销户
        if (pCode == 1 ) {
            rMap = getStateMap("3");
        }else if (pCode == 2) {
            rMap = getStateMap("4");
        }else if (pCode == 4) {
            rMap = getStateMap("5");
        } else if (pCode == 6) {
            rMap = getStateMap("2");
        } else if (pCode == 7 ) {
            rMap = getStateMap("1");
        }else if (pCode == 8) {
            rMap = getStateMap("6");
        }
        return rMap;
    }




}
