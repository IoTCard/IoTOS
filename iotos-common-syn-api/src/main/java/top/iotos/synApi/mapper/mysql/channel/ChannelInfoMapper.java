package top.iotos.synApi.mapper.mysql.channel;

import java.util.Map;

public interface ChannelInfoMapper {
    public Map<String, Object> find(Map map);

    public int update(Map map);

    public int save(Map map);

    public int delete(Map map);


    public Map<String, Object> findSyncChannel(Map map);


}
