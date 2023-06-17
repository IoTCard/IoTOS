package top.iotos.quartz.task.iotos;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import top.iotos.common.config.RabbitMQConfig;
import top.iotos.common.utils.iotos.common.JsonUtil;

import javax.annotation.Resource;
import java.util.Map;


@Component("initMQ")
public class InitMQ {
    @Resource
    private RabbitMQConfig rabbitMQConfig;


    /**
     * 初始化 Mq 读取JSON配置
     */
    public void initMQConfig(){
        //读取 JSON 配置文件
        String mQInit_jsonStr = JsonUtil.readJsonFile("config/MQInitQueue.json"); //始化队列
        JSONArray queueArr = JSONObject.parseArray(mQInit_jsonStr);
        for (int i = 0; i < queueArr.size(); i++) {
            Map<String,Object> Obj = (Map<String, Object>) queueArr.get(i);
            String exchangeName = Obj.get("exchangeName").toString();
            String queueName = Obj.get("queueName").toString();
            String routingKey = Obj.get("routingKey").toString();
            String del_exchangeName = "dlx_"+exchangeName,del_queueName = "dlx_"+queueName, del_routingKey = "dlx_"+routingKey;
            try {
                if(Obj.get("w_noDxl")!=null){
                    rabbitMQConfig.creatExchangeQueue(exchangeName, queueName, routingKey, null, null, null,null);
                }else{
                    rabbitMQConfig.creatExchangeQueue(exchangeName, queueName, routingKey, del_exchangeName, del_queueName, del_routingKey,null);
                }
            }catch (Exception e){
                System.out.println("exchangeName "+exchangeName+" Mqinit 初始化失败 ……"+e.getMessage());
            }
        }



    }




}