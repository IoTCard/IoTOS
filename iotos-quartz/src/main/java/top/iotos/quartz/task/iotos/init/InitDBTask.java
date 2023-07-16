package top.iotos.quartz.task.iotos.init;


import org.springframework.stereotype.Component;
import top.iotos.common.mapper.mysql.card.record.UsedRecordCommonMapper;
import top.iotos.common.mapper.mysql.card.record.UsedRecordMapper;
import top.iotos.synApi.utils.iotos.time.VeDate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 初始化 关于 数据库的表
 */

@Component("initDBTask")
public class InitDBTask {

    @Resource
    private UsedRecordCommonMapper usedRecordCommonMapper;
    @Resource
    private UsedRecordMapper usedRecordMapper;


    /**
     * 用量记录分月迁移
     */
    public void monthlyMigration(Integer monthsToAdd)
    {
        String yyyy_MM = VeDate.getMonth(monthsToAdd,"yyyy-MM");
        String times[] = yyyy_MM.split("-");
        String yyyyMM = times[0]+times[1];
        Map<String,Object> map = new HashMap<>();
        map.put("yyyyMM", yyyyMM);
        int i = usedRecordCommonMapper.createTable(map);//初始化 上月表格
        //开始迁移上月数据
        int saveCount = usedRecordCommonMapper.save(map);
        if(saveCount>0){
            usedRecordMapper.delete(map);//删除已迁移 过的记录
        }
    }




}
