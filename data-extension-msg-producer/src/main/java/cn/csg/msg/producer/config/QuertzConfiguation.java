package cn.csg.msg.producer.config;

import cn.csg.msg.producer.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
/**
 * 类{@code QuertzConfiguation}初始化Quartz调度任务
 *
 * <p>项目启动后立即添加所有的任务到Quartz调度</p>
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 *
 */
@Component
public class QuertzConfiguation implements CommandLineRunner {
    @Autowired
    private JobService jobService;

    /**
     * {@inheritDoc}
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //初始化添加所有任务到调度器
        jobService.addAllJobs();
    }
}