package cn.csg.jobschedule.config;

import cn.csg.jobschedule.job.CommunicationAlarmJob;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 爆发式通信对定时任务初始化
 * Created by hjw on 20190426
 */
@Configuration
public class CommunicationAlarmConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean(name="jobDetail")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(CommunicationAlarmJob task){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setName("CommunicationAlarm");
        jobDetail.setGroup("CommunicationAlarmGroup");
        jobDetail.setTargetObject(task);
        jobDetail.setTargetMethod("executeCommunicationAlarmTask");
        return jobDetail;
    }

    @Bean(name="jobTrigger")
    public CronTriggerFactoryBean cronJobTrigger(MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean jobTrigger = new CronTriggerFactoryBean();
        jobTrigger.setJobDetail(jobDetail.getObject());
        jobTrigger.setCronExpression(applicationProperties.getCommunicationAlarmJobCorn());
        jobTrigger.setName("CommunicationAlarm");
        return jobTrigger;
    }

    @Bean(name="scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger trigger){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
//        factoryBean.setStartupDelay(5);
        factoryBean.setTriggers(trigger);
        return factoryBean;
    }

}
