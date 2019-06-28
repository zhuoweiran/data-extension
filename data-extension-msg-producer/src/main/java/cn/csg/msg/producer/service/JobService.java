package cn.csg.msg.producer.service;


import cn.csg.msg.producer.bean.MsgJob;
import cn.csg.msg.producer.task.MsgTask;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>类{@code IndexController} 页面跳转控制
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */
@Component
public class JobService {
    private final Logger logger = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private MsgJobService msgJobService;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void addAllJobs(){
        List<MsgJob> jobs = msgJobService.enableList();
        for(MsgJob job : jobs) {
            try {
                addJob(job);
            }catch (Exception e){
                e.printStackTrace();
                logger.info("添加[{}]任务失败", job.getName());
            }
        }
    }
    @Async
    public void addJob(MsgJob job) throws Exception{

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        String groupName = "default";
        try {
            JobKey jobKey = JobKey.jobKey("job_" + job.getId(), groupName);
            if (scheduler.checkExists(jobKey)) {
                logger.info("job job_{} is in scheduler", job.getId());
                //todo 重新启动任务
            }
            TriggerKey triggerKey = TriggerKey.triggerKey("job_"+ job.getId(), groupName);
            Trigger trigger = scheduler.getTrigger(triggerKey);

            if(trigger == null){
                SimpleScheduleBuilder simpleScheduleBuilder =
                        SimpleScheduleBuilder.repeatSecondlyForever(job.getWindow());
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(simpleScheduleBuilder)
                        .build();

                JobDetail jobDetail = initJobDetail(job.getId(), groupName);
                jobDetail.getJobDataMap().put("jobId",job.getId());
                scheduler.scheduleJob(jobDetail, trigger);

                logger.info("{} job_{} will run at {} next time!",
                        job.getName(),
                        job.getId(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(scheduler.getTrigger(triggerKey).getNextFireTime()));
            }else{
                logger.info("{} job_{} is in scheduling",job.getName(),job.getId());
            }

        }catch (SchedulerException e){
            logger.error("添加任务 job_{} 失败", job.getId());
            e.printStackTrace();
        }

    }

    private JobDetail initJobDetail(String jobId, String groupName){
        JobDetail jobDetail = JobBuilder.newJob(MsgTask.class)
                .withIdentity("job_" + jobId, groupName)
                .build();
        return jobDetail;
    }

    /**
     * 修改调度时间
     * @param job
     * @param groupName
     * @throws Exception
     */
    public void updateWindow(MsgJob job, String groupName) throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey("job_"+ job.getId(), groupName);
        SimpleScheduleBuilder simpleScheduleBuilder =
                SimpleScheduleBuilder.repeatSecondlyForever(job.getWindow());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(simpleScheduleBuilder)
                .build();
        if(trigger == null){//调度里面没有此任务，重建任务
            addJob(job);
        }else{
            logger.info("更新调度任务[{}],频率为[{}s]每次", job.getName(), job.getWindow());
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 删除调度的任务
     * @param job
     * @param groupName
     * @throws SchedulerException
     */
    public void deleteJob(MsgJob job, String groupName) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey("job_"+ job.getId(), groupName);
        scheduler.deleteJob(jobKey);
    }
}
