package top.iotos.synApi.mapper.mysql.card;

import java.util.List;
import java.util.Map;

public interface CardMapper {

    public List<Map<String, Object>> getList(Map map);

    public Integer getListCount(Map map);

    public int update(Map map);

    public int save(Map map);

    public int delete(Map map);

    public int updateBatch(Map map);

    public String findMaxCNO();

    /**
     * 是否存在
     * @param map
     * @return
     */
    public List<String> wExist(Map<String, Object> map);

    public List<Map<String, Object>> findDateGroup(Map map);

    public List<Map<String, Object>> getChannelSum(Map map);

    public List<Map<String, Object>> getChannelCount(Map map);

}
