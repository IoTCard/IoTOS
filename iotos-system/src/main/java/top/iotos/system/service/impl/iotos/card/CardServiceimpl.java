package top.iotos.system.service.impl.iotos.card;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import top.iotos.common.core.domain.entity.SysDept;
import top.iotos.common.core.domain.entity.SysDictData;
import top.iotos.common.core.domain.entity.SysUser;
import top.iotos.synApi.mapper.mysql.card.CardApiBusinessMapper;
import top.iotos.synApi.mapper.mysql.card.CardInfoMapper;
import top.iotos.synApi.mapper.mysql.card.CardMapper;
import top.iotos.common.utils.file.FileUtils;
import top.iotos.synApi.utils.iotos.common.DbFieldArr;
import top.iotos.synApi.utils.iotos.service.MQAide;
import top.iotos.common.utils.iotos.service.PageUtil;
import top.iotos.common.utils.iotos.web.IoTOSTools;
import top.iotos.system.service.ISysDictTypeService;
import top.iotos.system.service.impl.SysDeptServiceImpl;
import top.iotos.system.service.impl.iotos.connect.ChannelServiceimpl;
import top.iotos.system.service.iotos.card.ICardService;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Service
public class CardServiceimpl implements ICardService {

    //表结构字段 [过滤排序条件等]
    public  String fieldArr[] = DbFieldArr.os_card_info_fieldArr;

    @Resource
    private CardMapper cardMapper;
    @Resource
    private CardInfoMapper cardInfoMapper;
    @Resource
    private MQAide mQAide;
    @Resource
    private SysDeptServiceImpl sysDeptServiceImpl;
    @Resource
    private ChannelServiceimpl channelServiceimpl;
    @Resource
    private ISysDictTypeService iSysDictTypeService;
    @Resource
    private CardApiBusinessMapper cardApiBusinessMapper;



    @Override
    public Map<String, Object> getList(Map map) {
        Map<String, Object> rMap = new HashMap<String, Object>();
        boolean advancedSearch = map.get("advancedSearch")!=null?(boolean) map.get("advancedSearch"):false;
        if(map.get("deleted")==null){
            map.put("deleted",0);
        }
        map = IoTOSTools.filterOrderByType(map,fieldArr);//过滤 排序条件

        Integer rowCount = advancedSearch?cardInfoMapper.getListCount(map):cardMapper.getListCount(map);
        PageUtil Pu = new PageUtil(rowCount, map.get("pageNum").toString(), map.get("pageSize").toString());//初始化分页工具类
        map.put("starRow", Pu.getStarRow());
        map.put("pageSize", Pu.getPageSize());
        List<Map<String, Object>> Data = advancedSearch?cardInfoMapper.getList(map):cardMapper.getList(map);
        rMap.put("Pu", Pu);
        rMap.put("data", Data);
        return rMap;
    }

    @Override
    public Map<String, Object> find(Map map) {
        return cardInfoMapper.find(map);
    }

    @Override
    public boolean update(MultipartFile file, SysUser user,Map<String, Object> map) {
      boolean bool = false;
        try {
            String filename = file.getOriginalFilename();
            String readName = UUID.randomUUID().toString().replace("-", "") + filename;
            String newName = UUID.randomUUID().toString().replace("-", "") + "_cardUpdate";
            String BUPName = UUID.randomUUID().toString().replace("-", "") + "_cardUpdateBUP";

            String flieUrlRx = "/upload/updateCard/";
            readName = flieUrlRx + readName;
            try {
                String filePath = FileUtils.initUrl(flieUrlRx);
                File newFile = new File(filePath + readName );
                file.transferTo(newFile);//写入新的文件名 文件
                //发送消息
                String queueName = "admin_cardUpdate_queue";
                String routingKey = "admin.cardUpdate.queue";
                String exchangeName = "admin_exchange";//路由

                Map<String, Object> sendMap = new HashMap<>();
                sendMap.put("filePath", filePath);//项目根目录
                sendMap.put("readName", readName);//上传新文件名
                sendMap.put("newName", newName);//输出文件名
                sendMap.put("BUPName", BUPName);//输出文件名
                sendMap.put("user", user);//登录用户信息
                sendMap.put("pMap", map);//参数

                bool = mQAide.send(exchangeName,routingKey,30,sendMap);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {

        }
        return bool;
    }


    @Override
    @Transactional
    public boolean delete(Map<String, Object> map) {
        boolean bool = false,pBool = false,iBool = false;
        try {
            map.remove("pageNum");
            map.remove("pageSize");
            map.put("status_show_id","7");
            List<String> deleteIccidArr =  cardInfoMapper.selIccid(map);//获取需要操作的 iccid
            if (deleteIccidArr != null && deleteIccidArr.size() > 0) {
                //这里暂时只做修改删除状态
                Map<String, Object> updMap = new HashMap<>();
                updMap.put("batchType","1");
                updMap.put("batchList",deleteIccidArr);
                updMap.put("deleted","1");
                pBool = cardMapper.updateBatch(updMap)>0;//同步主表信息
                iBool = cardInfoMapper.updateBatch(updMap)>0;//同步 详情信息
                if(pBool==true && iBool==true){
                    bool = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(!bool){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.err.println("CardServiceimpl update 回滚 》[bool = false]  setRollbackOnly " );
        }
        return bool;
    }

    @Override
    public boolean save(MultipartFile file, SysUser user) {
        boolean bool = false;
        String filename = file.getOriginalFilename();
        String readName = UUID.randomUUID().toString().replace("-", "") + filename;
        String newName = UUID.randomUUID().toString().replace("-", "") + "_cardImport";
        String flieUrlRx = "/upload/importCard/";
        readName = flieUrlRx + readName;
        try {
            String filePath = FileUtils.initUrl(flieUrlRx);
            File newFile = new File(filePath + readName );
            file.transferTo(newFile);//写入新的文件名 文件
            //发送消息
            String queueName  = "admin_cardSave_queue";
            String routingKey = "admin.cardSave.queue";
            String exchangeName = "admin_exchange";//路由

            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("filePath", filePath);//项目根目录
            sendMap.put("readName", readName);//上传新文件名
            sendMap.put("newName", newName);//输出文件名
            sendMap.put("user", user);//登录用户信息
            
            bool = mQAide.send(exchangeName,routingKey,30,sendMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean divideCard(Map<String, Object> map) {
        boolean bool = false;
        try {
            map.remove("pageNum");
            map.remove("pageSize");
            List<String> dividIccidArr =  cardInfoMapper.selIccid(map);//获取需要操作的 iccid
            if (dividIccidArr != null && dividIccidArr.size() > 0) {
                //发送消息
                String queueName = "admin_cardDivide_queue";
                String routingKey = "admin.cardDivide.queue";
                String exchangeName = "admin_exchange";//路由
                Map<String, Object> sendMap = map;
                sendMap.put("dividIccidArr",dividIccidArr);
                bool = mQAide.send(exchangeName, routingKey, 30, sendMap);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean rollbackDivid(Map<String, Object> map) {
        boolean bool = false;
        try {
            //发送消息
            String queueName = "admin_cardDivideRollback_queue";
            String routingKey = "admin.cardDivideRollback.queue";
            String exchangeName = "admin_exchange";//路由
            Map<String, Object> sendMap = map;
            bool = mQAide.send(exchangeName, routingKey, 30, sendMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean export(Map<String, Object> map) {
        boolean bool = false;
        map.remove("pageNum");
        map.remove("pageSize");
        List<String> iccidArr =  cardInfoMapper.selIccid(map);//获取需要操作的 iccid

        if(iccidArr!=null && iccidArr.size()>0){
            map.put("iccidArr",iccidArr);
            SysUser user = (SysUser) map.get("user");
            SysDept dept = user.getDept();
            boolean headquarters = dept.getDeptId().toString().equals("100")?true:false;
            map.put("headquarters",headquarters);
            List<Map<String, Object>> deptsOptions = sysDeptServiceImpl.getDeptName();//企业
            List<Map<String, Object>> channelOptions = channelServiceimpl.getNameOpen(null,headquarters);//通道
            List<SysDictData> cardStatusShowOptions = iSysDictTypeService.selectDictDataByType("card_status_show_id");//字典 【卡状态】
            List<SysDictData> cardTypeOptions = iSysDictTypeService.selectDictDataByType("card_type");//字典 【卡类型】
            List<SysDictData> cardNetworkTypeOptions = iSysDictTypeService.selectDictDataByType("card_network_type");//字典 【卡网络类型】
            List<SysDictData> whetherOptions = iSysDictTypeService.selectDictDataByType("iotos_whether");//字典 【是否】
            List<SysDictData> cardConnectionStatusOptions = iSysDictTypeService.selectDictDataByType("card_connection_status");//字典 【断开网状态】
            List<SysDictData> cardStatusOptions = iSysDictTypeService.selectDictDataByType("card_status_id");//字典 【卡状态描述】




            try {
                //发送消息
                String queueName = "admin_cardExport_queue";
                String routingKey = "admin.cardExport.queue";
                String exchangeName = "admin_exchange";//路由
                Map<String, Object> sendMap = new HashMap<>();
                sendMap.put("map",map);
                sendMap.put("deptsOptions",deptsOptions);
                sendMap.put("channelOptions",channelOptions);
                sendMap.put("cardStatusShowOptions",cardStatusShowOptions);
                sendMap.put("cardTypeOptions",cardTypeOptions);
                sendMap.put("cardNetworkTypeOptions",cardNetworkTypeOptions);
                sendMap.put("whetherOptions",whetherOptions);
                sendMap.put("cardConnectionStatusOptions",cardConnectionStatusOptions);
                sendMap.put("cardStatusOptions",cardStatusOptions);


                bool = mQAide.send(exchangeName, routingKey, 30, sendMap);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return bool;
    }

    @Override
    public List<String> getGrouping(Map map) {
        return cardInfoMapper.getGrouping(map);
    }

    @Override
    public boolean businessHandling(MultipartFile file, SysUser user, Map<String, Object> map) {
        boolean bool = false;
        try {
            String filename = file.getOriginalFilename();
            String readName = UUID.randomUUID().toString().replace("-", "") + filename;
            String newName  = UUID.randomUUID().toString().replace("-", "") + "_cardBusinessHandling";

            String flieUrlRx = "/upload/businessHandling/";
            readName = flieUrlRx + readName;
            try {
                String filePath = FileUtils.initUrl(flieUrlRx);
                File newFile = new File(filePath + readName );
                file.transferTo(newFile);//写入新的文件名 文件
                //发送消息
                String queueName  = "admin_cardBusinessHandling_queue";
                String routingKey = "admin.cardBusinessHandling.queue";
                String exchangeName = "admin_exchange";//路由

                Map<String, Object> sendMap = new HashMap<>();
                sendMap.put("filePath", filePath);//项目根目录
                sendMap.put("readName", readName);//上传新文件名
                sendMap.put("newName", newName);//输出文件名
                sendMap.put("user", user);//登录用户信息
                sendMap.put("pMap", map);//参数

                bool = mQAide.send(exchangeName,routingKey,30,sendMap);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {

        }
        return bool;
    }

    @Override
    public List<Map<String, Object>> getApiBusinessList(Map map) {
        return cardApiBusinessMapper.getList(map);
    }

    @Override
    public boolean editCardPublic(Map<String, Object> map) {

        Map<String, Object> updMap = new HashMap<>();
        updMap.put("iccid",map.get("iccid"));
        updMap.put("remarks",map.get("remarks"));
        updMap.put("delivery_date",map.get("delivery_date"));
        updMap.put("imei",map.get("imei"));
        updMap.put("customize_grouping",map.get("customize_grouping"));

        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("updMap",updMap);
        String exchangeName = "polling_exchange";
        String queueName = "polling_updCard_queue";
        String routingKey = "polling.updCard.queue";
        boolean bool = mQAide.send(exchangeName,routingKey,30,sendMap);
        return bool;
    }
}
