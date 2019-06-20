package cn.csg.jobschedule.job;

import cn.csg.common.enums.ExplodeType;
import cn.csg.common.enums.RuleKeyEnum;
import cn.csg.common.vo.ExplodeJob;
import cn.csg.common.vo.ExplodeVo;
import cn.csg.jobschedule.service.*;
import com.google.common.collect.Maps;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Component
public class ExplodeTask extends QuartzJobBean {
    private final Logger logger = LoggerFactory.getLogger(ExplodeTask.class);

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    private final SimpleDateFormat logFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private ExplodeJobService explodeJobService;
    @Autowired
    private ExplodeService explodeService;
    @Autowired
    private SrcIpAndDestPortCountQueryService srcIpAndDestPortCountQueryService;
    @Autowired
    private SrcIpAndDestIpCountQueryService srcIpAndDestIpCountQueryService;
    @Autowired
    private SrcIpSumQueryService srcIpSumQueryService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();


        String explodeId = dataMap.getString("jobId");
        ExplodeJob explodeJob = explodeJobService.findExplodeJobByExplodeId(explodeId);
        ExplodeVo explode = explodeService.findById(explodeId);

        //换算开始和结束时间
        BigDecimal window = explode.getValByName(RuleKeyEnum.WINDOWN.getKey());

        Date startTime = explodeJob.getLastSuccessTime();
        if(startTime == null){
            startTime = new Date();
        }
        Date now = new Date();

        long dalyTimes = Math.floorDiv(now.getTime() - startTime.getTime(),window.longValue() * 1000);

        Date endTime = null;

        if(dalyTimes <= 0 ){
            logger.warn(
                    "<警告>任务[{}]的最后更新时间与当前时间相等，如果不是第一次运行，逻辑不合理！排查程序是否正常调度！",
                    explode.getName()
            );
            logger.debug("错误的延迟次数为[{}]",dalyTimes);
            explodeJob.setLastSuccessTime(now);
            explodeJobService.save(explodeJob);
        }else if(dalyTimes > 1) {//如果漏数据应该马上把漏掉的数据立刻补回来
            for(;dalyTimes > 1; dalyTimes --){
                endTime = new Date(startTime.getTime() + window.intValue() * 1000);
                logger.info("<补充>即将运行[{}],时间段为[{}]~[{}]",
                        explode.getName(),
                        logFormat.format(startTime),
                        logFormat.format(endTime));
                //多线程执行
                alarm(startTime, explodeJob ,explode);
                startTime = endTime;
            }
        }
        //执行当前任务

        if(dalyTimes == 1){
            endTime = new Date(startTime.getTime() + window.intValue() * 1000);
            logger.info("<正常>即将运行[{}],时间段为[{}]~[{}]",
                    explode.getName(),
                    logFormat.format(startTime),
                    logFormat.format(endTime));

            alarm(startTime, explodeJob ,explode);
            //存储最终成功时间
            explodeJob.setLastSuccessTime(endTime);
            explodeJobService.save(explodeJob);
        }
    }
    @Async
    public void alarm(Date startTime, ExplodeJob job, ExplodeVo explode){

        String start = simpleDateFormat.format(startTime);

        Date endTime = new Date(startTime.getTime() + explode.getValByName(RuleKeyEnum.WINDOWN.getKey()).intValue() * 1000);
        String end = simpleDateFormat.format(endTime);

        String query = null;
        //使用freemarker模版
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        Configuration configuration = new Configuration();
        stringLoader.putTemplate("job_" + job.getExplodeId(),job.getQuery());
        configuration.setTemplateLoader(stringLoader);
        try {
            Template template = configuration.getTemplate("job_" + job.getExplodeId(),"utf-8");

            //设置模版参数
            Map<String, Object> configMap = Maps.newHashMap();
            configMap.put("start", start);
            configMap.put("end", end);
            configMap.put("target", explode.getValByName(RuleKeyEnum.TARGET.getKey()).intValue());

            //渲染模版为最终执行语句
            StringWriter writer = new StringWriter();
            template.process(configMap, writer);
            query = writer.toString();

        } catch (IOException | TemplateException e) {
            logger.error("freemarker template init query script fail!");
            e.printStackTrace();
        }
        //执行任务
        if(ExplodeType.SRC_IP_AND_DEST_IP_COUNT.getName().equals(explode.getName())){
            srcIpAndDestIpCountQueryService.exec(
                    query,
                    explode.getValByName(RuleKeyEnum.WINDOWN.getKey()).intValue()/60 ,
                    explode.getValByName(RuleKeyEnum.TARGET.getKey()).intValue()
            );
        }else if(ExplodeType.SRC_IP_AND_DEST_PORT_COUNT.getName().equals(explode.getName())){
            srcIpAndDestPortCountQueryService.exec(
                    query,
                    explode.getValByName(RuleKeyEnum.WINDOWN.getKey()).intValue()/60 ,
                    explode.getValByName(RuleKeyEnum.TARGET.getKey()).intValue()
            );
        }else if(ExplodeType.SRC_IP_SUM.getName().equals(explode.getName())){
            srcIpSumQueryService.exec(
                    query,
                    explode.getValByName(RuleKeyEnum.WINDOWN.getKey()).intValue()/60 ,
                    explode.getValByName(RuleKeyEnum.TARGET.getKey()).intValue()
            );
        }
    }

}
