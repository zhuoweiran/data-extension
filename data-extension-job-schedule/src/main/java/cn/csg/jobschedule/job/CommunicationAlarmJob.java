package cn.csg.jobschedule.job;

import cn.csg.jobschedule.service.MetadataService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hjw on 2019-04-22
 * 爆发式通信对告警统计
 */
public class CommunicationAlarmJob extends QuartzJobBean {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public MetadataService metadataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("----------start-------------");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.error(simpleDateFormat.format(new Date())+":爆发式通信对告警处理！");
        String queryStr = "{\"aggs\":{\"corpIdCount\":{\"aggs\":{\"srcIpCount\":{\"aggs\":{\"destIpCount\":{\"aggs\":{\"destPortCount\":{\"terms\":{\"field\":\"destPort\"}}},\"terms\":{\"field\":\"destIp\"}}},\"terms\":{\"field\":\"srcIp\"}}},\"terms\":{\"field\":\"corpId\"}}},\"query\":{\"range\":{\"createTime\":{\"gte\":\"2019-04-24T15:00:00+08:00\",\"lt\":\"2019-04-24T16:00:00+08:00\"}}},\"size\":0}";
        System.out.println(metadataService.getMetadataByHttp(queryStr));
        System.out.println("----------end-------------");
    }
}
