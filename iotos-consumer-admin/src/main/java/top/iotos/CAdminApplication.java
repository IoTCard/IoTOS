package top.iotos;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("top.iotos.common.mapper")
@MapperScan("top.iotos.synApi.mapper")
class CAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(CAdminApplication.class);
    }
}
