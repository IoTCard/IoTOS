package top.iotos.synApi.mapper.mysql.card;

import java.util.List;
import java.util.Map;

public interface CardSessionMapper {

    public List<Map<String, Object>> getList(Map map);

    public Integer getListCount(Map map);

    public int save(Map map);

    public int delete(Map map);

    public int deleteReserveDayOne(Map map);

}
