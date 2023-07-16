package top.iotos.system.service.iotos.card;



import org.springframework.web.multipart.MultipartFile;
import top.iotos.common.core.domain.entity.SysUser;

import java.util.List;
import java.util.Map;

public interface ICardService {

    /**
     * 查询
     */
    public Map<String,Object> getList(Map map);

    public Map<String,Object> querySession(Map map);

    public Map<String,Object> getCard(Map map);

    public Map<String,Object> cardMatch(Map map);


    /**
     * 删除
     */
    public boolean delete(Map<String, Object> map);


    /**
     * 修改
     */
    public boolean update(MultipartFile file, SysUser user,Map<String, Object> map);

    /**
     * 新增
     */
    public boolean save(MultipartFile file, SysUser user);


    /**
     * 详情
     * @return
     */
    public Map<String, Object> find(Map<String, Object> map);

    /**
     * 划卡
     * @param map
     * @return
     */
    public boolean divideCard(Map<String, Object> map);

    /**
     * 划卡撤销
     * @param map
     * @return
     */
    public boolean rollbackDivid(Map<String, Object> map);

    /**
     * 撤销
     * @param map
     * @return
     */
    public boolean export(Map<String, Object> map);

    public List<String> getGrouping(Map map);

    /**
     * 修改
     */
    public boolean businessHandling(MultipartFile file, SysUser user,Map<String, Object> map);


    /**
     * 获取API 业务变更记录
     * @param map
     * @return
     */
    public List<Map<String, Object>> getApiBusinessList(Map map);

    /**
     * 修改卡信息
     * @param map
     * @return
     */
    public boolean editCardPublic(Map<String, Object> map);



    public boolean exportSession(Map<String, Object> map);


}