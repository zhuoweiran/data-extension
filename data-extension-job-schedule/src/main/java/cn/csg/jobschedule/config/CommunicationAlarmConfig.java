package cn.csg.jobschedule.config;

import cn.csg.jobschedule.job.CommunicationAlarmJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * 爆发式通信对定时任务初始化
 * Created by hjw on 20190426
 */
@Configuration
public class CommunicationAlarmConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean(name = "srcIpSumJobDetail")
    public MethodInvokingJobDetailFactoryBean srcIpSumJobDetail(CommunicationAlarmJob srcIpSumJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setTargetObject(srcIpSumJob);
        jobDetail.setTargetMethod("executeSrcIpSumTask");
        return jobDetail;
    }

    @Bean(name = "srcIpSumTrigger")
    public SimpleTriggerFactoryBean firstTrigger(JobDetail srcIpSumJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(srcIpSumJobDetail);
        // 每5秒执行一次
        trigger.setRepeatInterval(5000);
        return trigger;
    }

    @Bean(name = "srcIpAndDestIpCountJobDetail")
    public MethodInvokingJobDetailFactoryBean secondJobDetail(CommunicationAlarmJob srcIpAndDestIpCountJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(true);
        jobDetail.setTargetObject(srcIpAndDestIpCountJob);
        jobDetail.setTargetMethod("executeSrcIpAndDestIpCountTask");
        return jobDetail;
    }

    @Bean(name = "srcIpAndDestIpCountTrigger")
    public SimpleTriggerFactoryBean secondTrigger(JobDetail srcIpAndDestIpCountJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(srcIpAndDestIpCountJobDetail);
        //每5秒执行一次
        trigger.setRepeatInterval(5000);
        return trigger;
    }

    @Bean(name = "srcIpAndDestPortCountJobDetail")
    public MethodInvokingJobDetailFactoryBean thirdJobDetail(CommunicationAlarmJob srcIpAndDestPortCountJobDetail) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(true);
        jobDetail.setTargetObject(srcIpAndDestPortCountJobDetail);
        jobDetail.setTargetMethod("executeSrcIpAndDestPortCountTask");
        return jobDetail;
    }

    @Bean(name = "srcIpAndDestPortCountTrigger")
    public SimpleTriggerFactoryBean thirdTrigger(JobDetail srcIpAndDestPortCountJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(srcIpAndDestPortCountJobDetail);
        //每5秒执行一次
        trigger.setRepeatInterval(5000);
        return trigger;
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger srcIpSumTrigger, Trigger srcIpAndDestIpCountTrigger,Trigger srcIpAndDestPortCountTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setStartupDelay(1);
        bean.setTriggers(srcIpSumTrigger,srcIpAndDestIpCountTrigger,srcIpAndDestPortCountTrigger);
        return bean;
    }



}
