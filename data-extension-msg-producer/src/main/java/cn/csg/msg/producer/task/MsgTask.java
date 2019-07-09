package cn.csg.msg.producer.task;

import cn.csg.msg.producer.bean.MsgJob;
import cn.csg.msg.producer.bean.MsgType;
import cn.csg.msg.producer.service.MsgJobService;
import cn.csg.msg.producer.service.MsgRulesService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Component
public class MsgTask extends QuartzJobBean {
    private final Logger logger = LoggerFactory.getLogger(MsgTask.class);
    @Autowired
    private MsgJobService msgJobService;

    @Autowired
    private MsgRulesService rulesService;

    @Autowired
    private KafkaProducer producer;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jobId = dataMap.getString("jobId");
        MsgJob job = msgJobService.findById(jobId);

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        Configuration configuration = new Configuration();
        stringLoader.putTemplate("job_" + job.getId(), job.getTemplate());
        configuration.setTemplateLoader(stringLoader);
        String msg = null;
        try {
            Template template = configuration.getTemplate("job_" + job.getId(), "utf-8");
            //设置模版参数
            Map<String, Object> configMap = rulesService.findAllRulesByJobId(job.getId());

            //渲染模版为最终报文
            StringWriter writer = new StringWriter();
            template.process(configMap, writer);
            msg = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg != null) {
            MsgType msgType = job.getMsgType();
            if (msgType == MsgType.Json) {
                //添加header 和 end
                msg = "<?begn?>\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" + msg + "<?endn?>";
            }else if(msgType == MsgType.CommPair){
                msg = "<?begn?>\u0000\u0003\u0000\u0001\u0000\u0001\u0000\u0001" + msg + "<?endn?>";
            }else if(msgType == MsgType.UnidentifiedFile){
                msg = "<?begn?>\u0000\u0005\u0000\u0001\u0000\u0001\u0000\u0001" + msg + "1234567812345678123456781234567812345678123456781234567812345678**content**<?endn?>";
            }else if(msgType == MsgType.UnAnalysisFile) {
                msg = "<?begn?>\u0000\u0004\u0000\u0001\u0000\u0001\u0000\u0001" + msg + "<?endn?>";
            }
            producer.send(new ProducerRecord<>(job.getTopic(), msg));
        } else {
            logger.error("添加任务[{}]失败", job.getName());
        }
    }
}
