package cn.csg.jobschedule.job;

import org.quartz.*;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 动态更改任务执行周期
 * Created by hjw on 20190426
 */
@Configuration
@EnableScheduling
@Component
public class CommunicationAlarmScheduleRefresh {
    private final static Logger logger = LoggerFactory.getLogger(CommunicationAlarmScheduleRefresh.class);

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedDelay  = 30000)
    public void scheduleUpdateCronTrigger() throws SchedulerException {
        SimpleTriggerImpl srcIpSumOldTrigger =(SimpleTriggerImpl)scheduler.getTrigger(srcIpSumTrigger.getKey());
        long srcIpSumOldInterval = srcIpSumOldTrigger.getRepeatInterval();

        SimpleTriggerImpl srcIpAndDestIpCountOldTrigger =(SimpleTriggerImpl)scheduler.getTrigger(srcIpAndDestIpCountTrigger.getKey());
        long srcIpAndDestIpCountOldInterval = srcIpAndDestIpCountOldTrigger.getRepeatInterval();

        SimpleTriggerImpl srcIpAndDestPortCountOldTrigger =(SimpleTriggerImpl)scheduler.getTrigger(srcIpAndDestPortCountTrigger.getKey());
        long srcIpAndDestPortCountOldInterval = srcIpAndDestPortCountOldTrigger.getRepeatInterval();

        String sql = "select name,rule_value from tb_explode te left join tb_rule tr on te.id = tr.rule where rule_key = 'window'";
        List dataList = jdbcTemplate.queryForList(sql);
        long srcIpSumInterval = 60000;
        long srcIpAndDestPortCountInterval = 60000;
        long srcIpAndDestIpCountInterval = 60000;
        if(dataList != null && dataList.size() > 0){
            Iterator it = dataList.iterator();
            while (it.hasNext()){
                Map ruleMap = (Map)it.next();
                if("srcIpSum".equals(ruleMap.get("name"))){
                    srcIpSumInterval = Double.valueOf(ruleMap.get("rule_value")+"").longValue()*1000L;
                }
                if("srcIpAndDestIpCount".equals(ruleMap.get("name"))){
                    srcIpAndDestIpCountInterval = Double.valueOf(ruleMap.get("rule_value")+"").longValue()*1000L;
                }
                if("srcIpAndDestPortCount".equals(ruleMap.get("name"))){
                    srcIpAndDestPortCountInterval = Double.valueOf(ruleMap.get("rule_value")+"").longValue()*1000L;
                }
            }
        }

        logger.info("srcIpSumInterval============================="+srcIpSumInterval+"毫秒==>"+(srcIpSumInterval/60/1000)+"分");
        logger.info("srcIpAndDestIpCountInterval=================="+srcIpAndDestIpCountInterval+"毫秒==>"+(srcIpAndDestIpCountInterval/60/1000)+"分");
        logger.info("srcIpAndDestPortCountInterval================"+srcIpAndDestPortCountInterval+"毫秒==>"+(srcIpAndDestPortCountInterval/60/1000)+"分");

        if (srcIpSumOldInterval == srcIpSumInterval){
        }else{
            srcIpSumOldTrigger.setRepeatInterval(srcIpSumInterval);
            scheduler.rescheduleJob(srcIpSumTrigger.getKey(),srcIpSumOldTrigger);
        }

        if (srcIpAndDestIpCountOldInterval == srcIpAndDestIpCountInterval){
        }else{
            srcIpAndDestIpCountOldTrigger.setRepeatInterval(srcIpAndDestIpCountInterval);
            scheduler.rescheduleJob(srcIpAndDestIpCountTrigger.getKey(),srcIpAndDestIpCountOldTrigger);
        }

        if (srcIpAndDestPortCountOldInterval == srcIpAndDestPortCountInterval){
        }else{
            srcIpAndDestPortCountOldTrigger.setRepeatInterval(srcIpAndDestPortCountInterval);
            scheduler.rescheduleJob(srcIpAndDestPortCountTrigger.getKey(),srcIpAndDestPortCountOldTrigger);
        }
    }
}
