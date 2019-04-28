package cn.csg.jobschedule.config;

import cn.csg.jobschedule.job.CommunicationAlarmJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.List;
import java.util.Map;

/**
 * 爆发式通信对定时任务初始化
 * Created by hjw on 20190426
 */
@Configuration
public class CommunicationAlarmConfig {

    private final static Logger logger = LoggerFactory.getLogger(CommunicationAlarmConfig.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean(name = "srcIpSumJobDetail")
    public MethodInvokingJobDetailFactoryBean srcIpSumJobDetail(CommunicationAlarmJob srcIpSumJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setTargetObject(srcIpSumJob);
        jobDetail.setTargetMethod("executeSrcIpSumTask");
        return jobDetail;
    }

    @Bean(name = "srcIpSumTrigger")
    public SimpleTriggerFactoryBean srcIpSumTrigger(JobDetail srcIpSumJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(srcIpSumJobDetail);
        List dataList = null;
        try {
            String sql = "select rule_value from tb_explode te left join tb_rule tr on te.id = tr.rule where rule_key = 'window' and name = 'srcIpSum'";
            dataList = jdbcTemplate.queryForList(sql);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("前往数据库中查询srcIpSumTrigger定时周期失败："+e.getMessage());
        }
        long ruleValue = 60000;
        if(dataList != null && dataList.size() > 0){
            Map map = (Map)dataList.get(0);
            ruleValue = Double.valueOf(map.get("rule_value")+"").longValue()*1000L;

        }
        logger.info("-----------srcIpSumTrigger Configuration定时周期------------"+ruleValue+"毫秒-->"+(ruleValue/60/1000)+"分");
        trigger.setRepeatInterval(ruleValue);
        return trigger;
    }

    @Bean(name = "srcIpAndDestIpCountJobDetail")
    public MethodInvokingJobDetailFactoryBean srcIpAndDestIpCountJobDetail(CommunicationAlarmJob srcIpAndDestIpCountJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(true);
        jobDetail.setTargetObject(srcIpAndDestIpCountJob);
        jobDetail.setTargetMethod("executeSrcIpAndDestIpCountTask");
        return jobDetail;
    }

    @Bean(name = "srcIpAndDestIpCountTrigger")
    public SimpleTriggerFactoryBean srcIpAndDestIpCountTrigger(JobDetail srcIpAndDestIpCountJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(srcIpAndDestIpCountJobDetail);
        List dataList = null;
        try {
            String sql = "select rule_value from tb_explode te left join tb_rule tr on te.id = tr.rule where rule_key = 'window' and name = 'srcIpAndDestIpCount'";
            dataList = jdbcTemplate.queryForList(sql);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("前往数据库中查询srcIpAndDestIpCountTrigger定时周期失败："+e.getMessage());
        }
        long ruleValue = 60000;
        if(dataList != null && dataList.size() > 0){
            Map map = (Map)dataList.get(0);
            ruleValue = Double.valueOf(map.get("rule_value")+"").longValue()*1000L;

        }
        trigger.setRepeatInterval(ruleValue);
        logger.info("-----------srcIpAndDestIpCountTrigger Configuration定时周期------------"+ruleValue+"毫秒-->"+(ruleValue/60/1000)+"分");
        return trigger;
    }

    @Bean(name = "srcIpAndDestPortCountJobDetail")
    public MethodInvokingJobDetailFactoryBean srcIpAndDestPortCountJobDetail(CommunicationAlarmJob srcIpAndDestPortCountJobDetail) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(true);
        jobDetail.setTargetObject(srcIpAndDestPortCountJobDetail);
        jobDetail.setTargetMethod("executeSrcIpAndDestPortCountTask");
        return jobDetail;
    }

    @Bean(name = "srcIpAndDestPortCountTrigger")
    public SimpleTriggerFactoryBean srcIpAndDestPortCountTrigger(JobDetail srcIpAndDestPortCountJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(srcIpAndDestPortCountJobDetail);
        List dataList = null;
        try {
            String sql = "select rule_value from tb_explode te left join tb_rule tr on te.id = tr.rule where rule_key = 'window' and name = 'srcIpAndDestPortCount'";
            dataList = jdbcTemplate.queryForList(sql);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("前往数据库中查询srcIpAndDestPortCountTrigger定时周期失败："+e.getMessage());
        }
        long ruleValue = 60000;
        if(dataList != null && dataList.size() > 0){
            Map map = (Map)dataList.get(0);
            ruleValue = Double.valueOf(map.get("rule_value")+"").longValue()*1000L;

        }
        trigger.setRepeatInterval(ruleValue);
        logger.info("-----------srcIpAndDestPortCountTrigger Configuration定时周期------------"+ruleValue+"毫秒-->"+(ruleValue/60/1000)+"分");
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
