package cn.csg.jobschedule.service;

import cn.csg.common.enums.ExplodeType;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JobTriggerService {
    //相同源ip发起访问数
    @Resource(name="srcIpSumTrigger")
    private SimpleTrigger srcIpSumTrigger;

    //相同源ip访问不同目的ip数
    @Resource(name="srcIpAndDestIpCountTrigger")
    private SimpleTrigger srcIpAndDestIpCountTrigger;

    //相同访问同一目的ip端口数
    @Resource(name="srcIpAndDestPortCountTrigger")
    private SimpleTrigger srcIpAndDestPortCountTrigger;

    @Resource(name="scheduler")
    private Scheduler scheduler;

    public void scheduleUpdateTrigger(ExplodeType explodeType, Long interval ) throws SchedulerException {
        if(explodeType.equals(ExplodeType.SRC_IP_SUM)){
            updateTrigger(interval, srcIpSumTrigger);
        }else if(explodeType.equals(ExplodeType.SRC_IP_AND_DEST_IP_COUNT)){
            updateTrigger(interval, srcIpAndDestIpCountTrigger);
        }else if(explodeType.equals(ExplodeType.SRC_IP_AND_DEST_PORT_COUNT)){
            updateTrigger(interval, srcIpAndDestPortCountTrigger);
        }else {
            //todo errror logger
        }
    }

    private void updateTrigger(Long interval, SimpleTrigger srcIpSumTrigger) throws SchedulerException {
        SimpleTriggerImpl oneTrigger =(SimpleTriggerImpl)scheduler.getTrigger(srcIpSumTrigger.getKey());
        long oneOldInterval = oneTrigger.getRepeatInterval();
        if (oneOldInterval == interval){
            //todo info logger
        }else{
            //更改执行周期
            oneTrigger.setRepeatInterval(interval);
            scheduler.rescheduleJob(srcIpSumTrigger.getKey(),oneTrigger);
        }
    }
}
