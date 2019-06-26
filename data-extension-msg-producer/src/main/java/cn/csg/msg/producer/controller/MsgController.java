package cn.csg.msg.producer.controller;

import cn.csg.common.ResultData;
import cn.csg.common.ResultStatus;
import cn.csg.common.enums.StatusEnum;
import cn.csg.msg.producer.bean.MsgJob;
import cn.csg.msg.producer.bean.MsgRules;
import cn.csg.msg.producer.bean.MsgType;
import cn.csg.msg.producer.bean.ValueType;
import cn.csg.msg.producer.service.JobService;
import cn.csg.msg.producer.service.MsgJobService;
import cn.csg.msg.producer.service.MsgRulesService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/msg")
public class MsgController {
    @Autowired
    private MsgJobService msgJobService;

    @Autowired
    private MsgRulesService msgRulesService;

    @Autowired
    private JobService jobService;

    @ApiOperation(value ="查询所有任务",httpMethod = "GET")
    @GetMapping(value= "/list")
    public ResultData list(){
        return new ResultData<>(
                msgJobService.list(),
                ResultStatus.initStatus(StatusEnum.SUCCESS)
        );
    }

    @ApiOperation(value ="查询所有有效任务",httpMethod = "GET")
    @GetMapping(value= "/enableList")
    public ResultData enableList(){
        return new ResultData<>(
                msgJobService.enableList(),
                ResultStatus.initStatus(StatusEnum.SUCCESS)
        );
    }
    @ApiOperation(value ="新增任务",httpMethod = "POST")
    @PostMapping(value= "/save")
    public ResultData save(MsgJob msgJob){
        System.out.println(msgJob);
        if(msgJob.isStatus()){
            try {
                jobService.addJob(msgJob);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(msgJob.getLastSuccessTime() == null){
            msgJob.setLastSuccessTime(new Date());
        }
        if(StringUtils.isEmpty(msgJob.getId())){
            msgJob.setId(null);
        }
        return new ResultData<>(
                msgJobService.save(msgJob),
                ResultStatus.initStatus(StatusEnum.UPDATE)
        );
    }

    @ApiOperation(value ="启动任务",httpMethod = "GET")
    @GetMapping(value= "/satrt/{jobId}")
    public ResultData startJob(@PathVariable(name = "jobId") String jobId){
        MsgJob job = msgJobService.findById(jobId);
        job.setStatus(true);
        try {
            jobService.addJob(job);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultData<>(msgJobService.save(job), ResultStatus.initStatus(StatusEnum.UPDATE));
    }

    @ApiOperation(value ="更新任务",httpMethod = "POST")
    @PostMapping(value= "/update")
    public ResultData updateJob(
            String jobId,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String template,
            @RequestParam(required = false, defaultValue = "") String topic,
            @RequestParam(required = false,defaultValue = "0")int window,
            @RequestParam(defaultValue = "true", required = false) boolean status
    ){
        MsgJob job = msgJobService.findById(jobId);
        try{
            if(job.isStatus()){
                if(status){
                    //todo 这里需要检查调度
                    if(window != 0 ){
                        job.setStatus(status);
                        job.setWindow(window);
                        if(!template.equals("")) {
                            job.setTemplate(template);
                        }
                        if(!name.equals("")){
                            job.setName(name);
                        }
                        if(!topic.equals("")){
                            job.setTopic(topic);
                        }
                        msgJobService.save(job);
                        jobService.updateWindow(job ,"default");
                    }
                }else{
                    //停止任务

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResultData<>(msgJobService.save(job), ResultStatus.initStatus(StatusEnum.UPDATE));
    }

    @ApiOperation(value ="删除任务",httpMethod = "GET")
    @GetMapping(value= "/delete/{jobId}")
    public ResultData deleteJob(@PathVariable(name = "jobId") String jobId){
        MsgJob job = msgJobService.findById(jobId);
        try {
            jobService.deleteJob(job, "default");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo
        msgRulesService.deleteAllByJobId(jobId);
        msgJobService.delete(job);
        return new ResultData<>(null, ResultStatus.initStatus(StatusEnum.DELETE));
    }

    @ApiOperation(value ="停止任务",httpMethod = "GET")
    @GetMapping(value= "/stop/{jobId}")
    public ResultData stopJob(@PathVariable(name = "jobId") String jobId){
        MsgJob job = msgJobService.findById(jobId);
        try {
            jobService.deleteJob(job, "default");
        } catch (Exception e) {
            e.printStackTrace();
        }
        job.setStatus(false);

        return new ResultData<>(msgJobService.save(job), ResultStatus.initStatus(StatusEnum.SUCCESS));
    }

    @ApiOperation(value ="初始化一个测试任务",httpMethod = "GET")
    @GetMapping(value= "/init")
    public ResultData init(){
        MsgJob msgJob = new MsgJob();
        msgJob.setName("test2");
        msgJob.setTopic("test");
        msgJob.setTemplate("${guid}^${now?string('yyyy-MM-dd HH:mm:ss.SSS')}^${num*100}");
        msgJob.setWindow(10);
        msgJob.setStatus(false);
        msgJob.setMsgType(MsgType.EText);
        msgJob.setLastSuccessTime(new Date());

        MsgJob result = msgJobService.save(msgJob);

        MsgRules msgRules = new MsgRules();
        msgRules.setJobId(result.getId());
        msgRules.put("guid", "guidxxxxxxxxxxxxxx");
        msgRules.setName("guid");
        msgRules.setStatus(true);
        msgRules.setValueType(ValueType.String);

        MsgRules msgRules1 = new MsgRules();
        msgRules1.setJobId(result.getId());
        msgRules1.put("now", "Today");
        msgRules1.setName("now");
        msgRules1.setStatus(true);
        msgRules1.setValueType(ValueType.Date);

        MsgRules msgRules2 = new MsgRules();
        msgRules2.setJobId(result.getId());
        msgRules2.put("num", "30");
        msgRules2.setName("num");
        msgRules2.setStatus(true);
        msgRules2.setValueType(ValueType.Int);

        msgRulesService.save(msgRules);
        msgRulesService.save(msgRules1);
        msgRulesService.save(msgRules2);

        return new ResultData<>(
                msgJob,
                ResultStatus.initStatus(StatusEnum.UPDATE)
        );
    }
    @ApiOperation(value ="通过id查询一个任务",httpMethod = "GET")
    @GetMapping(value= "/findJob/{id}")
    public ResultData findMsgJobById(@PathVariable(name = "id") String id){
        return new ResultData<>(
                msgJobService.findById(id),
                ResultStatus.initStatus(StatusEnum.SUCCESS)
        );
    }

    @ApiOperation(value ="通过id查询一个任务规则",httpMethod = "GET")
    @GetMapping(value= "/findRule/{id}")
    public ResultData findMsgRulesById(@PathVariable(name = "id") String id){
        return new ResultData<>(
                msgRulesService.findById(id),
                ResultStatus.initStatus(StatusEnum.SUCCESS)
        );
    }
    @ApiOperation(value ="更新一个任务规则",httpMethod = "POST")
    @PostMapping(value= "/saveRule")
    public ResultData saveRules(MsgRules rules){
        rules.setStatus(true);
        return new ResultData<>(
                msgRulesService.save(rules),
                ResultStatus.initStatus(StatusEnum.SUCCESS)
        );
    }
    @ApiOperation(value ="删除一个任务规则",httpMethod = "GET")
    @GetMapping(value= "/deleteRule/{id}")
    public ResultData deleteRules(@PathVariable(name = "id") String id){
        MsgRules rules = msgRulesService.findById(id);
        msgRulesService.delete(rules);
        return new ResultData<>(
                null,
                ResultStatus.initStatus(StatusEnum.DELETE)
        );
    }
    @ApiOperation(value ="验证任务模版",httpMethod = "GET")
    @GetMapping(value= "/testTemplate/{id}")
    public ResultData testTemplate(@PathVariable(name = "id") String id) {
        try {
            Map<String, Object> rulesMap = msgRulesService.findAllRulesByJobId(id);
            MsgJob job = msgJobService.findById(id);

            StringTemplateLoader stringLoader = new StringTemplateLoader();
            Configuration configuration = new Configuration();
            stringLoader.putTemplate("job_" + job.getId(), job.getTemplate());
            configuration.setTemplateLoader(stringLoader);
            Template template = configuration.getTemplate("job_" + job.getId(), "utf-8");
            //设置模版参数

            //渲染模版为最终报文
            StringWriter writer = new StringWriter();
            template.process(rulesMap, writer);
            String msg = writer.toString();

            if (msg != null) {
                MsgType msgType = job.getMsgType();
                if (msgType == MsgType.Json) {
                    //添加header 和 end
                    msg = "<?begn?>\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" + msg + "<?endn?>";
                }
            }
            return new ResultData<>(
                    msg,
                    ResultStatus.initStatus(StatusEnum.SUCCESS)
            );
        }catch (Exception e){
            return new ResultData<>(
                    e.getMessage(),
                    ResultStatus.initStatus(StatusEnum.ERROR)
            );
        }
    }
}
