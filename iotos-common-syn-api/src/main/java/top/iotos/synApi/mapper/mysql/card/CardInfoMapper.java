package top.iotos.synApi.mapper.mysql.card;

import java.util.List;
import java.util.Map;

public interface CardInfoMapper {

    public List<Map<String, Object>> getList(Map map);

    public Integer getListCount(Map map);

    public Map<String, Object> find(Map map);

    public int update(Map map);

    public int save(Map map);

    public int delete(Map map);

    public int updateBatch(Map map);

    public String queryChildrenAreaInfo(Map map);

    public List<String> findDeptIccid(Map map);

    public List<String> selIccid(Map map);

    public List<Map<String, Object>> backupDept();

    public List<String> getGrouping(Map map);

    public List<Map<String, Object>> findSynced(Map map);

    public Map<String, Object> getSyncInfo(Map map);

    public List<Map<String, Object>> findChannelIdIccid(Map map);

    public int initTime(Map map);

}
