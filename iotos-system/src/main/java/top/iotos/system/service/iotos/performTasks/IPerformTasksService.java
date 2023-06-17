package top.iotos.system.service.iotos.performTasks;



import java.util.List;
import java.util.Map;

public interface IPerformTasksService {



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
    public List<Map<String, Object>> findFile(Map<String, Object> map);


    public Map<String, Object> downloadList(Map<String, Object> map);

    public Map<String, Object> tasksDetailsList(Map<String, Object> map);

    public boolean tasksDetailsExport(Map<String, Object> map);

}















