package top.iotos.common.mapper.mysql.performTasks;

import java.util.List;
import java.util.Map;

public interface TaskFlieMapper {

    public List<Map<String, Object>> find(Map map);

    public int update(Map map);

    public int save(Map map);

    public int delete(Map map);
    public int deleteArr(Map map);
}
