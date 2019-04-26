package cn.csg.jobschedule;

import cn.csg.jobschedule.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EntityScan(basePackages = {"cn.csg.common.vo"})
public class DataExtensionJobScheduleApplication extends SpringBootServletInitializer {
    @Autowired
    private ApplicationProperties applicationProperties;

    public static void main(String[] args) {
        SpringApplication.run(DataExtensionJobScheduleApplication.class,args);
    }

}
