package top.iotos.synApi.utils.iotos.service;


import org.springframework.stereotype.Component;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.synApi.utils.iotos.common.DbFieldArr;
import top.iotos.synApi.utils.iotos.common.ListCompare;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 数据库表格字段 标记存储
 */
@Component
public class CardUpdate {
    @Resource
    private CardMapper cardMapper;
    @Resource
    private CardInfoMapper cardInfoMapper;

    public int cardUpdate(Map<String, Object> updMap){
        int updCount = 0;
        if(ListCompare.Is_existence(DbFieldArr.os_card_fieldArr,updMap)){//updMap 是否在 该表中
            updCount = cardMapper.update(updMap);//同步主表信息
        }
        return updCount;
    }

    public int cardInfoUpdate(Map<String, Object> updMap){
        int updCount = 0;
        if(ListCompare.Is_existence(DbFieldArr.os_card_info_fieldArr,updMap)){//updMap 是否在 该表中
            updCount = cardInfoMapper.update(updMap);//同步主表信息
        }
        return updCount;
    }





}
