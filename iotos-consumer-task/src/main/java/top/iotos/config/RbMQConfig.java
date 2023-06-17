package top.iotos.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 线程配置类
 */
@Configuration
public class RbMQConfig {

    /**
     *  初始化数量 及 最大并发数量 的设置仅对单个队列设置并非为整个程序
     */

    public static final int DEFAULT_CONCURRENT = 10;      // 并发消费者的初始化数量
    public static final int MAX_CONCURRENT = 100;         // 设置消费者最大并发数量

    @Bean("customContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                 ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(DEFAULT_CONCURRENT);
        factory.setMaxConcurrentConsumers(MAX_CONCURRENT);
        configurer.configure(factory, connectionFactory);
        return factory;
    }



}
