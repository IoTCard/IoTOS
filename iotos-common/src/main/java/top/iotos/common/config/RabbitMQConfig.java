package top.iotos.common.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ 操作
 * 更多API教程了解 [https://github.com/rabbitmq/rabbitmq-java-client]
 */
@Component
public class RabbitMQConfig {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public Connection connection ;




    @Resource
    private RabbitTemplate rabbitTemplate;

    public Integer getCount(String EXCHANGE_NAME,String queue,String Key,String Type) throws IOException {
        Integer queueCount = null;
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        try {
            connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false);
            // 通道关联交换机
            channel.exchangeDeclare(EXCHANGE_NAME, Type);
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclarePassive(queue);//直连交换机类型
            //获取队列中的消息个数
            queueCount = declareOk.getMessageCount();
            channel.close();
        }catch (Exception e){
            log.error("===== creatExchangeQueue connection.getCount() [Start] =====");
            log.error(e.getMessage());
            log.error("===== creatExchangeQueue connection.getCount() [End] =====");
        }
        finally {
            connection.close();
            connection = null;
        }
        return queueCount;
    }


    /**
     * 清空指定队列
     * @param EXCHANGE_NAME
     * @param queue
     * @param Key
     * @param Type
     * @return
     * @throws IOException
     */
    public boolean purgeMessage(String EXCHANGE_NAME,String queue,String Key,String Type) throws IOException {
        Integer operateCount = null;
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        try {
            connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false);
            // 通道关联交换机
            channel.exchangeDeclare(EXCHANGE_NAME, Type);
            AMQP.Queue.PurgeOk declareOk =  channel.queuePurge(queue);//清空
            //获取队列中的消息个数
            operateCount = declareOk.getMessageCount();
            channel.close();
            log.info("EXCHANGE_NAME = {} ,queue = {},Key ={} 已清空队列数量 {} ",EXCHANGE_NAME,queue,Key,operateCount);
        }catch (Exception e){
            log.error("===== creatExchangeQueue connection.getCount() [Start] =====");
            log.error(e.getMessage());
            log.error("===== creatExchangeQueue connection.getCount() [End] =====");
        }
        finally {
            connection.close();
            connection = null;
        }
        return operateCount>0;
    }








    /**
     * 创建 队列绑定 路由器
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param del_exchangeName
     * @param del_queueName
     * @param del_routingKey
     * @param type  交换机类型
     * @throws IOException
     */
    public void creatExchangeQueue(String exchangeName, String queueName, String routingKey, String del_exchangeName, String del_queueName, String del_routingKey, BuiltinExchangeType type) throws IOException {
        Channel channel = null;
        com.rabbitmq.client.Connection connection = null;
        try {
            connection = connection!=null ?connection:RabbitMQConnection.getConnection();
        }catch (Exception e){
            log.error("===== creatExchangeQueue connection.getConnection() [Start] =====");
            log.error(e.getMessage());
            log.error("===== creatExchangeQueue connection.getConnection() [End] =====");
            connection = RabbitMQConnection.getConnection();
        }
        try {
            channel = connection.createChannel();
            type = type!=null?type:BuiltinExchangeType.DIRECT;
        }catch (Exception e){
            log.error("===== creatExchangeQueue connection.createChannel() [Start] =====");
            log.error(e.getMessage());
            log.error("===== creatExchangeQueue connection.createChannel() [End] =====");
            connection = RabbitMQConnection.getConnection();
            channel = connection.createChannel();
        }
        // 首先声明一个Exchange
        // String exchangeName = "JFK airport";
        // String queueName = "New York city";
        try {
            AMQP.Exchange.DeclareOk exchangeDeclare = channel.exchangeDeclare(exchangeName,type );//直连交换机类型
        }catch (IOException e){
            log.error("以存在交换机"+exchangeName);
            channel = connection.createChannel();//操作异常时 会断开本次连接 ，这里进行重连
        }

        if(del_exchangeName!=null){
            try {
                AMQP.Exchange.DeclareOk del_exchangeDeclare = channel.exchangeDeclare(del_exchangeName, type);//直连交换机类型
            }catch (IOException e){
                log.error("以存在死信交换机"+del_exchangeName);
                channel = connection.createChannel();
            }
        }
        if(del_exchangeName!=null && del_queueName!=null && del_routingKey!=null ){
            //创建死信队列 绑定到 信交换机
            try {
                com.rabbitmq.client.AMQP.Queue.DeclareOk del_queueDeclare = channel.queueDeclare(del_queueName, true, false, false,null);
                com.rabbitmq.client.AMQP.Queue.BindOk del_queueBind = channel.queueBind(del_queueName, del_exchangeName, del_routingKey);
            }catch (IOException e){
                log.error("以存在死信 队列"+del_queueName);
                channel = connection.createChannel();
            }
        }
        try {
            // 队列绑定 死信交换机 队列
            if(del_exchangeName!=null && del_queueName!=null && del_routingKey!=null ){
                Map<String, Object> arguments = new HashMap<>(2);
                arguments.put("x-dead-letter-exchange", del_exchangeName);
                arguments.put("x-dead-letter-routing-key", del_routingKey);
                com.rabbitmq.client.AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(queueName, true, false, false,arguments);
                //这个才是将queue绑定到exchange上面,注意这里的routingKey不能为null
                com.rabbitmq.client.AMQP.Queue.BindOk queueBind = channel.queueBind(queueName, exchangeName, routingKey);
            }else{
                com.rabbitmq.client.AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(queueName, true, false, false,null);
                com.rabbitmq.client.AMQP.Queue.BindOk queueBind = channel.queueBind(queueName, exchangeName, routingKey);
            }
        }catch (IOException e){
            log.error("以存在 队列"+del_queueName);
            channel = connection.createChannel();
        }
        //String queue = queueDeclare.getQueue();
        //int messageCount = queueDeclare.getMessageCount();
        //int consumerCount = queueDeclare.getConsumerCount();
        //log.info("队列名称=" + queueName + ",当前队列的消息数量=" + messageCount + ",当前队列的消费者数量=" + consumerCount);
        // 最后通过Bindings方式将Exchange 绑定到Queue上面
        // 使用Exchange 绑定到Queue上面,destination 目的地，source来源第，routingKey 路由键null
        //BindOk exchangeBind = channel.exchangeBind(exchangeName, queue, "");// 绑定一个exchange到另外一个exchange
        //log.info(queueBind);
        finally {
            connection.close();
            connection = null;
        }
    }


    /**
     * 获取连接 对象
     * @return
     */
    public Connection getConnection(){
        try {
            ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
            connection = connectionFactory.createConnection();
        }catch (Exception e){
            log.error("getConnection Exception "+e.getMessage());
        }
        return  connection;
    }




    /**
     * 手动发送消息
     * @param EXCHANGE_NAME 交换机
     * @param queue 队列
     * @param Key 网线
     * @param Type 交换机类型
     * @param msg 发送消息
     * @throws IOException
     * @throws TimeoutException
     */
    public void send(String EXCHANGE_NAME,String queue,String Key,String Type,String msg) throws IOException, TimeoutException {
        // 创建Channel
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        connection = connectionFactory.createConnection();
        try {
            BindingBuilder.bind(new Queue(queue)).to(new DirectExchange(EXCHANGE_NAME)).with(Key);// 队列 绑定 交换机
            Channel channel = connection.createChannel(false);
            // 通道关联交换机
            channel.exchangeDeclare(EXCHANGE_NAME, Type, true);
            channel.basicPublish(EXCHANGE_NAME, Key, null, msg.getBytes("utf-8"));
            channel.close();
        } catch (Exception e) {
            log.error("===== creatExchangeQueue connection.createChannel() [Start] =====");
            log.error(e.getMessage());
            log.error("===== creatExchangeQueue connection.createChannel() [End] =====");
        } finally {
            connection.close();
            connection = null;
        }
    }



}