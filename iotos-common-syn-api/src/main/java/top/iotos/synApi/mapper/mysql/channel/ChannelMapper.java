package top.iotos.synApi.mapper.mysql.channel;

import java.util.List;
import java.util.Map;

public interface ChannelMapper {

    public List<Map<String, Object>> getList(Map map);

    public Integer getListCount(Map map);

    public Map<String, Object> find(Map map);

    public int update(Map map);

    public int save(Map map);

    public int delete(Map map);

    public List<Map<String, Object>> getNameOpen(Map map);
    public List<Map<String, Object>> getName(Map map);

}
