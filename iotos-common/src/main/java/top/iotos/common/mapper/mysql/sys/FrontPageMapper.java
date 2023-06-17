package top.iotos.common.mapper.mysql.sys;

import java.util.List;
import java.util.Map;

public interface FrontPageMapper {

    public String findId(Map map);

    public Map<String, Object> find(Map map);

    public int save(Map map);

    public int update(Map map);

    public String findLastId(Map map);

    public List<Map<String, Object>> findLogin(Map map);

    public Integer findDeptCount(Map map);




}
