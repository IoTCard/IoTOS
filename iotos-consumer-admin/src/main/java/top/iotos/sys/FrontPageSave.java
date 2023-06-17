package top.iotos.sys;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.iotos.common.core.redis.RedisCache;
import top.iotos.common.mapper.mysql.sys.FrontPageMapper;
import top.iotos.synApi.utils.iotos.service.MQAide;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 首页数据保存
 */
@Slf4j
@Component
public class FrontPageSave {

    @Resource
    private FrontPageMapper frontPageMapper;
    @Resource
    private RedisCache redisCache;

    @RabbitHandler
    @RabbitListener(queues = "task_frontPageSave_queue")
    public void frontPageSave(String msg) {
        execute(msg);
    }



    @RabbitHandler
    @RabbitListener(queues = "dlx_task_frontPageSave_queue")
    public void dlxFrontPageSave(String msg) {
        execute(msg);
    }

    private void execute(String msg){
        try {
            if (StringUtils.isEmpty(msg)) {
                return;
            }
            Map<String,Object> map = MQAide.getParameter(msg);
            Map<String,Object> addMap = (Map<String, Object>) map.get("addMap");
            String dept_id = addMap.get("dept_id").toString();
            String record_date = addMap.get("record_date").toString();
            String prefix = "task_frontPageSave_queue";
            //执行前判断 redis 是否存在 执行数据 存在时 不执行
            String key_val = prefix+":"+ dept_id+":"+record_date;
            Object  wExecute = redisCache.getCacheObject(key_val);
            if(wExecute==null){
                redisCache.setCacheObject(key_val, msg, 5, TimeUnit.SECONDS);//5 秒 缓存 避免 重复消费
                execution(addMap,dept_id,record_date);
            }
        }catch (Exception e){
            log.error(">>错误 - 首页数据生成 消费者:{}<<", e.getMessage());
        }
    }

    public void execution(Map<String,Object> addMap,String dept_id,String record_date) {

        String id = frontPageMapper.findId(addMap);
        int saveCount = 0;
        int updCount = 0;
        if(id!=null && id.length()>0){
            addMap.put("id",id);
            updCount = frontPageMapper.update(addMap);
        }else {
            saveCount = frontPageMapper.save(addMap);
        }
        log.info(" frontPageSave  dept_id {} = record_date {} |  saveCount {} / updCount {}",dept_id,record_date,saveCount,updCount);
    }



}
