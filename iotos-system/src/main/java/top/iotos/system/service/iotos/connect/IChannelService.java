package top.iotos.system.service.iotos.connect;



import java.util.List;
import java.util.Map;

public interface IChannelService {



    /**
     * 查询
     */
    public Map<String,Object> getList(Map map);


    /**
     * 删除
     */
    public boolean delete(Map<String, Object> map);


    /**
     * 修改
     */
    public boolean update(Map<String, Object> map);

    /**
     * 新增
     */
    public boolean save(Map<String, Object> map);


    /**
     * 详情
     * @return
     */
    public Map<String, Object> find(Map<String, Object> map);

    public List<Map<String, Object>> getNameOpen(Map<String, Object> map,boolean headquarters);

    public List<Map<String, Object>> getName(Map<String, Object> map);




}















