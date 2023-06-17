package top.iotos.common.mapper.mysql.card.synData;

import java.util.List;
import java.util.Map;

public interface UpstreamCardMapper {

    public List<Map<String, Object>> getList(Map map);

    public Integer getListCount(Map map);

    public int update(Map map);

    public int save(Map map);

    public int delete(Map map);

    public Map<String, Object> find(Map map);

    public int updateBatch(Map map);

    public List<Map<String, Object>> findIccidRepeat(Map map);

    public List<Map<String, Object>> findIccidNull(Map map);


}
