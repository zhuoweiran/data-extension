package cn.csg.jobschedule.job;

import org.quartz.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 动态更改任务执行周期
 * Created by hjw on 20190426
 */
@Configuration
@EnableScheduling
@Component
public class CommunicationAlarmScheduleRefresh {

    @Resource(name="jobDetail")
    private JobDetail jobDetail;

    @Resource(name="jobTrigger")
    private CronTrigger cronTrigger;

    @Resource(name="scheduler")
    private Scheduler scheduler;

    @Scheduled(fixedDelay  = 30000)
    public void scheduleUpdateCronTrigger() throws SchedulerException {
        CronTrigger trigger =(CronTrigger)scheduler.getTrigger(cronTrigger.getKey());
        String oldCron = trigger.getCronExpression();
        //TODO 获取数据库中的cron表达式
        String searchCron = "0/40 * * * * ?";
        System.out.println("当前定时任务使用的cron表达式:"+oldCron);
        System.out.println("更新后的cron表达式:"+searchCron);
        if (oldCron.equals(searchCron)){
        }else{
            //更改执行周期
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);
            trigger=(CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
            trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey())
                    .withSchedule(cronScheduleBuilder).build();
            scheduler.rescheduleJob(cronTrigger.getKey(),trigger);
        }
    }
}
