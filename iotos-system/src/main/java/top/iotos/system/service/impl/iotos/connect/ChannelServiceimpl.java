package top.iotos.system.service.impl.iotos.connect;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.iotos.synApi.mapper.mysql.channel.ChannelInfoMapper;
import top.iotos.synApi.mapper.mysql.channel.ChannelMapper;
import top.iotos.common.utils.iotos.service.PageUtil;
import top.iotos.synApi.utils.iotos.time.VeDate;
import top.iotos.common.utils.iotos.web.IoTOSTools;
import top.iotos.system.service.iotos.connect.IChannelService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChannelServiceimpl implements IChannelService {

    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private ChannelInfoMapper channelInfoMapper;

    @Override
    public Map<String, Object> getList(Map map) {
        Map<String, Object> rMap = new HashMap<String, Object>();
        Integer rowCount = channelMapper.getListCount(map);
        PageUtil Pu = new PageUtil(rowCount, map.get("pageNum").toString(), map.get("pageSize").toString());//初始化分页工具类
        map.put("starRow", Pu.getStarRow());
        map.put("pageSize", Pu.getPageSize());
        rMap.put("Pu", Pu);
        rMap.put("data", channelMapper.getList(map));
        return rMap;
    }

    @Override
    public Map<String, Object> find(Map map) {
        Map<String, Object> rMap = new HashMap<String, Object>();
        rMap.put("pMap",channelMapper.find(map));
        rMap.put("aMap",channelInfoMapper.find(map));
        return rMap;
    }

    @Override
    @Transactional
    public boolean update(Map map) {
        boolean bool = false;
        try {
            Map<String, Object> pMap = (Map<String, Object>) map.get("pMap");
            Map<String, Object> aMap = (Map<String, Object>) map.get("aMap");
            int pInt = channelMapper.update(pMap);
            int aInt = 0;
            if(pInt>0){
                aMap.putAll(pMap);
                aMap = IoTOSTools.getMapListToString(aMap,"sync_field");
                aInt = channelInfoMapper.update(aMap);
            }
            if(pInt>0 && aInt>0){
                bool = true;
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("ChannelSerciceimpl update 回滚 》  setRollbackOnly " + e.getMessage());
        }
        if(!bool){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("ChannelSerciceimpl update 回滚 》[bool = false]  setRollbackOnly " );
        }
        return bool;
    }


    @Override
    @Transactional
    public boolean delete(Map<String, Object> map) {
        boolean bool = false;
        try {
            int pInt = channelMapper.delete(map);
            int aInt = 0;
            if(pInt>0){
                aInt = channelInfoMapper.delete(map);
            }
            if(pInt>0 && aInt>0){
                bool = true;
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("ChannelSerciceimpl delete 回滚 》  setRollbackOnly " + e.getMessage());
        }
        if(!bool){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("ChannelSerciceimpl delete 回滚 》[bool = false]  setRollbackOnly " );
        }
        return bool;
    }

    @Override
    @Transactional
    public boolean save(Map<String, Object> map) {
        boolean bool = false;
        try {
            Map<String, Object> pMap = (Map<String, Object>) map.get("pMap");
            Map<String, Object> aMap = (Map<String, Object>) map.get("aMap");
            String c_no = "chanel-"+ VeDate.getNo(4);
            pMap.put("c_no",c_no);
            pMap.put("dept_id",map.get("dept_id"));

            int pInt = channelMapper.save(pMap);
            int aInt = 0;
            if(pInt>0){
                aMap.putAll(pMap);
                aMap = IoTOSTools.getMapListToString(aMap,"sync_field");
                aInt = channelInfoMapper.save(aMap);
            }
            if(pInt>0 && aInt>0){
                bool = true;
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("ChannelSerciceimpl save 回滚 》  setRollbackOnly " + e.getMessage());
        }
        if(!bool){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("ChannelSerciceimpl save 回滚 》[bool = false]  setRollbackOnly " );
        }
        return bool;
    }

    @Override
    public List<Map<String, Object>> getNameOpen(Map<String, Object> map,boolean headquarters) {
        if(headquarters){
            return channelMapper.getName(map);
        }else {
            return channelMapper.getNameOpen(map);
        }
    }

    @Override
    public List<Map<String, Object>> getName(Map<String, Object> map) {
        return channelMapper.getName(map);
    }


}
