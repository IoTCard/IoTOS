package top.iotos.system.service.impl.iotos.card;


import org.springframework.stereotype.Service;
import top.iotos.common.mapper.mysql.card.record.UsedRecordCommonMapper;
import top.iotos.common.mapper.mysql.card.record.UsedRecordMapper;
import top.iotos.common.utils.iotos.service.PageUtil;
import top.iotos.synApi.utils.iotos.time.VeDate;
import top.iotos.system.service.iotos.card.IUsedRecordService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsedRecordServiceimpl implements IUsedRecordService {

    @Resource
    private UsedRecordMapper usedRecordMapper;
    @Resource
    private UsedRecordCommonMapper usedRecordCommonMapper;

    @Override
    public Map<String, Object> getList(Map map) {
        Map<String, Object> rMap = new HashMap<String, Object>();
        String record_date = map.get("record_date").toString();
        String yyyMM = VeDate.getyyyyMM();
        boolean pBool = true;
        if(!yyyMM.equals(record_date)){//判断是否当月如果是当月获取 主记录数据
            pBool = false;
        }
        String exists = null;
        if(!pBool) {
             exists = usedRecordCommonMapper.findExists(map);//查询是否有 该表
        }
        Integer rowCount = pBool?usedRecordMapper.getListCount(map):exists!=null?usedRecordCommonMapper.getListCount(map):0;
        PageUtil Pu = new PageUtil(rowCount, map.get("pageNum").toString(), map.get("pageSize").toString());//初始化分页工具类
        map.put("starRow", Pu.getStarRow());
        map.put("pageSize", Pu.getPageSize());
        rMap.put("Pu", Pu);
        List<Map<String, Object>> data = pBool?usedRecordMapper.getList(map):exists!=null?usedRecordCommonMapper.getList(map):null;
        rMap.put("data", data);
        return rMap;
    }







}
