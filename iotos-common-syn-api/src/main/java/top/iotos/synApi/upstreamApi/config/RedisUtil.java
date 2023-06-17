package top.iotos.synApi.upstreamApi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    public static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    public Long increment(String key,int i){
       return redisTemplate.opsForValue().increment(key, i);
    }

    public void set(String key,String val,int timeout,TimeUnit type){
        redisTemplate.opsForValue().set(key, val, timeout, type);
    }
    public void set(String key,String val){
         redisTemplate .opsForValue().set(key, val);
    }
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public boolean expire(String key,int timeout,TimeUnit type){
        return  redisTemplate.expire(key, timeout, type);
    }

    public void setAccessToken(Object o){
        redisTemplate.opsForValue().set("accessToken",o,2,TimeUnit.HOURS);
    }

    /**
     * 描述： 删除指定的key
     */
    public boolean delKey(String key){
        return redisTemplate.delete(key);
    }

    public void set(String key,Object o){

        redisTemplate.opsForValue().set(key,o,4,TimeUnit.HOURS);
    }

}
