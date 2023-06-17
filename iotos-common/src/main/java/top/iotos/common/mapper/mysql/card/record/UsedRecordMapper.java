package top.iotos.common.mapper.mysql.card.record;

import java.util.List;
import java.util.Map;

public interface UsedRecordMapper {

    public List<Map<String, Object>> getList(Map map);

    public Integer getListCount(Map map);

    public int update(Map map);

    public int save(Map map);

    public Map<String, Object> find(Map map);

    public int delete(Map map);

    public Map<String, Object> findLastDay(Map map);

    public List<Map<String, Object>> findTotalDayUsed(Map map);

    public List<Map<String, Object>> findLeaderboard(Map map);

    public List<Map<String, Object>> findTotalMonthUsed(Map map);


}
