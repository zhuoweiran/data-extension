package cn.csg.jobschedule.config;

import cn.csg.jobschedule.service.ExplodeJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @title 配置quertz任务调度初始化
 * @author alex han
 */
@Component
public class QuertzConfiguation implements CommandLineRunner {
    @Autowired
    private ExplodeJobService explodeJobService;

    @Override
    public void run(String... args) throws Exception {
        //初始化添加所有任务到调度器
        explodeJobService.addAllJobs();
    }
}
