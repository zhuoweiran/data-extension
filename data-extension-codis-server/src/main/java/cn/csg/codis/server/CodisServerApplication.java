package cn.csg.codis.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类{@code CodisServerApplication}微服务启动入口
 * <p>使用webflux框架，对于多线程和高并发支持更好
 *
 * @author Alex Han
 * @version 1.2
 */
@SpringBootApplication
public class CodisServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodisServerApplication.class, args);
    }
}
