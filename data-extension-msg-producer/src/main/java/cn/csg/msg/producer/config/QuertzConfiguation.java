package cn.csg.msg.producer.config;

import cn.csg.msg.producer.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class QuertzConfiguation implements CommandLineRunner {
    @Autowired
    private JobService jobService;

    @Override
    public void run(String... args) throws Exception {
        //初始化添加所有任务到调度器
        jobService.addAllJobs();
    }
}