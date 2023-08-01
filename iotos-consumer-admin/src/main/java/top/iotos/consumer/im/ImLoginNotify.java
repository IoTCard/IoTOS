package top.iotos.consumer.im;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.common.mapper.mysql.sys.NoticeMapper;
import top.iotos.common.utils.iotos.web.IMUtils;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * IM 登录通知检测  消费者
 */
@Slf4j
@Component
public class ImLoginNotify {


    @Resource
    private RedisCache redisCache;
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private IMUtils iMUtils;


    @RabbitHandler
    @RabbitListener(queues = "admin_imLoginNotify_queue")
    public void imLoginNotify(String msg) {
        execute(msg);
    }

    @RabbitListener(queues = "dlx_admin_imLoginNotify_queue")
    public void dlxImLoginNotify(String msg) {
        execute(msg);
    }


    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> pMap = MQAide.getParameter(msg);
            Map<String,Object> user = (Map<String, Object>) pMap.get("user");
            String deptId = user.get("deptId").toString();
            String userId = user.get("userId").toString();
            String prefix = "admin_cardExport_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            String key_val = prefix+":"+ deptId+":"+ userId;
            Object  wExecute = redisCache.getCacheObject(key_val);
            if(wExecute==null){
                redisCache.setCacheObject(key_val, msg, 10, TimeUnit.SECONDS);//10 秒缓存 避免 重复消费
                execution(pMap,user);
            }
        } catch (Exception e) {
            log.error(">>错误 - 登录通知检测 消费者:{}<<", e.getMessage());
        }
    }



    /**
     * 检测需要通知信息进行通知
     */
    public void execution(Map<String, Object> pMap,Map<String,Object> user){
        //查询公告表
        Map<String,Object> map = new HashMap<>();
        map.put("notice_type","2");
        map.put("normal","1");
        map.put("status",0);
        map.put("starRow",0);
        map.put("pageSize",1);
        String userName = user.get("userName").toString();//系统账号
        //获取最新公告 1 条
        List<Map<String, Object>> bulletinList = noticeMapper.getList(map);
        if(bulletinList!=null && bulletinList.size()>0){
            Map<String, Object> bulletin = bulletinList.get(0);
            String notice_content = bulletin.get("notice_content").toString();
            String notice_title = bulletin.get("notice_title").toString();
            Map<String,Object> msgObj = new HashMap<>();
            msgObj.put("content",notice_content);
            msgObj.put("title",notice_title);
            iMUtils.send(IMUtils.CHAT_TYPE_PRIVATE,IMUtils.COMMAND_CHAT_REQ, JSON.toJSONString(msgObj),"IoTOSAdmin",IMUtils.MSG_TYPE_NEWS,userName);
        }
        //获取最新 通知 3 条
        map.put("notice_type","1");
        map.put("pageSize",3);
        List<Map<String, Object>> infoList =  noticeMapper.getList(map);
        if(infoList!=null && infoList.size()>0){
            for (int i = 0; i < infoList.size(); i++) {
                Map<String, Object> bulletin = infoList.get(i);
                String notice_content = bulletin.get("notice_content").toString();
                String notice_title = bulletin.get("notice_title").toString();
                Map<String,Object> msgObj = new HashMap<>();
                msgObj.put("url",notice_content);
                msgObj.put("content",notice_title);
                iMUtils.send(IMUtils.CHAT_TYPE_PRIVATE,IMUtils.COMMAND_CHAT_REQ,JSON.toJSONString(msgObj),"IoTOSAdmin",IMUtils.MSG_TYPE_TEXT,userName);
            }
        }
    }


}