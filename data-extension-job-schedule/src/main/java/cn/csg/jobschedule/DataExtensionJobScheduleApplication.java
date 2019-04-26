package cn.csg.jobschedule;

import cn.csg.jobschedule.config.ApplicationProperties;
import cn.csg.jobschedule.job.CommunicationAlarmJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableFeignClients
public class DataExtensionJobScheduleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DataExtensionJobScheduleApplication.class,args);
    }
}
