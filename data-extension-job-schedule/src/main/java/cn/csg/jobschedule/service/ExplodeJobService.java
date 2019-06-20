package cn.csg.jobschedule.service;

import cn.csg.common.enums.RuleKeyEnum;
import cn.csg.common.vo.ExplodeJob;
import cn.csg.common.vo.ExplodeVo;
import cn.csg.jobschedule.dao.ElasticsearchDao;
import cn.csg.jobschedule.dao.ExplodeDao;
import cn.csg.jobschedule.dao.ExplodeJobDao;
import cn.csg.jobschedule.job.ExplodeTask;
import cn.csg.jobschedule.util.DatetimeUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExplodeJobService {
    private final Logger logger = LoggerFactory.getLogger(ExplodeJobService.class);

    @Value(value = "${schedule.group.name}")
    private String groupName;

    @Autowired
    private ElasticsearchDao elasticsearchDao;
    @Autowired
    private ExplodeDao explodeDao;
    @Autowired
    private ExplodeJobDao explodeJobDao;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void addJob(String explodeId){
        ExplodeVo explode = explodeDao.getOne(explodeId);
        addJob(explode);
    }
    public void addJob(ExplodeVo explode) {
        //根据explodeId找到需要调度的任务
        BigDecimal window = explode.getValByName(RuleKeyEnum.WINDOWN.getKey());
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        //设置任务参数,默认为default分组

        try {
            JobKey jobKey = JobKey.jobKey("job_" + explode.getId(), groupName);
            if (scheduler.checkExists(jobKey)) {
                logger.info("job job_{} is in scheduler", explode.getId());
                //todo 重新启动任务
            }
            TriggerKey triggerKey = TriggerKey.triggerKey("job_"+ explode.getId(), groupName);
            Trigger trigger = scheduler.getTrigger(triggerKey);

            if(trigger == null){
                SimpleScheduleBuilder simpleScheduleBuilder =
                        SimpleScheduleBuilder.repeatSecondlyForever(window.intValue());
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(simpleScheduleBuilder)
                        .build();

                JobDetail jobDetail = initJobDetail(explode.getId());
                jobDetail.getJobDataMap().put("jobId",explode.getId());
                scheduler.scheduleJob(jobDetail, trigger);

                logger.info("{} job_{} will run at {} next time!",
                        explode.getName(),
                        explode.getId(),
                        DatetimeUtil.formatDate(scheduler.getTrigger(triggerKey).getNextFireTime(),"yyyy-MM-dd HH:mm:ss.SSS"));
            }else{
                logger.info("{} job_{} is in scheduling",explode.getName(),explode.getId());
            }

        }catch (SchedulerException e){
            logger.error("添加任务 job_{} 失败", explode.getId());
            e.printStackTrace();
        }
    }

    public void addAllJobs(){
        List<ExplodeVo> explodeVos = explodeDao.findAll();
        for(ExplodeVo explode : explodeVos){
            addJob(explode);
        }
    }

    private JobDetail initJobDetail(String jobId){
        JobDetail jobDetail = JobBuilder.newJob(ExplodeTask.class)
                .withIdentity("job_" + jobId, groupName)
                .build();
        return jobDetail;
    }

    public ExplodeJob findExplodeJobByExplodeId(String explodeId){
        return explodeJobDao.findFirstByExplodeId(explodeId);
    }

    public ExplodeJob save(ExplodeJob explodeJob){
        return explodeJobDao.save(explodeJob);
    }

    /**
     * 更新任务
     * @param explode
     */
    public void updateJob(ExplodeVo explode) throws SchedulerException {
        BigDecimal window = explode.getValByName(RuleKeyEnum.WINDOWN.getKey());
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey("job_"+ explode.getId(), groupName);

        SimpleScheduleBuilder simpleScheduleBuilder =
                SimpleScheduleBuilder.repeatSecondlyForever(window.intValue());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(simpleScheduleBuilder)
                .build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }
}
