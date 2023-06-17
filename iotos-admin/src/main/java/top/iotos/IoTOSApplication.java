package top.iotos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author iotos.top
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class IoTOSApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(IoTOSApplication.class, args);
        System.out.println("  IoTOS启动成功   ﾞ  \n" +
                "  更多资讯关注官网 www.iotos.top ");
    }
}
