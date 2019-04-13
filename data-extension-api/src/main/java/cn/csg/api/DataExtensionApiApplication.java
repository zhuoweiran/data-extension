package cn.csg.api;

import cn.csg.common.vo.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@SpringBootApplication
public class DataExtensionApiApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DataExtensionApiApplication.class,args);
    }
}
