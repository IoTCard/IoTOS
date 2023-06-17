package top.iotos.common.mapper.mysql.sys;

import java.util.List;
import java.util.Map;

public interface DictTypeMapper {

    public List<Map<String, Object>> selectDictDataList(Map map);

}
