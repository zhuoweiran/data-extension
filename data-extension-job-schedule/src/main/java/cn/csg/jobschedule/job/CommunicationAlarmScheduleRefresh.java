package cn.csg.jobschedule.job;

import org.quartz.*;
import org.quartz.impl.triggers.SimpleTriggerImpl;
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

    @Resource(name="srcIpSumTrigger")
    private SimpleTrigger srcIpSumTrigger;

    @Resource(name="srcIpAndDestIpCountTrigger")
    private SimpleTrigger srcIpAndDestIpCountTrigger;

    @Resource(name="srcIpAndDestPortCountTrigger")
    private SimpleTrigger srcIpAndDestPortCountTrigger;

    @Resource(name="scheduler")
    private Scheduler scheduler;

    @Scheduled(fixedDelay  = 30000)
    public void scheduleUpdateCronTrigger() throws SchedulerException {
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        SimpleTriggerImpl oneTrigger =(SimpleTriggerImpl)scheduler.getTrigger(srcIpSumTrigger.getKey());
        long oneOldInterval = oneTrigger.getRepeatInterval();

        SimpleTriggerImpl twoTrigger =(SimpleTriggerImpl)scheduler.getTrigger(srcIpAndDestIpCountTrigger.getKey());
        long twoOldInterval = twoTrigger.getRepeatInterval();

        SimpleTriggerImpl threeTrigger =(SimpleTriggerImpl)scheduler.getTrigger(srcIpAndDestPortCountTrigger.getKey());
        long threeOldInterval = twoTrigger.getRepeatInterval();

        //TODO 获取数据库中的cron表达式
        long searchInterval = 15*1000;
        System.out.println("当前的定时任务周期:"+oneOldInterval);
        System.out.println("更新后的的定时任务周期:"+searchInterval);

        if (oneOldInterval == searchInterval){
        }else{
            //更改执行周期
            oneTrigger.setRepeatInterval(searchInterval);
            scheduler.rescheduleJob(srcIpSumTrigger.getKey(),oneTrigger);
        }

        if (twoOldInterval == searchInterval){
        }else{
            //更改执行周期
            twoTrigger.setRepeatInterval(searchInterval);
            scheduler.rescheduleJob(srcIpAndDestIpCountTrigger.getKey(),twoTrigger);
        }

        if (threeOldInterval == searchInterval){
        }else{
            //更改执行周期
            threeTrigger.setRepeatInterval(searchInterval);
            scheduler.rescheduleJob(srcIpAndDestPortCountTrigger.getKey(),threeTrigger);
        }
    }
}
