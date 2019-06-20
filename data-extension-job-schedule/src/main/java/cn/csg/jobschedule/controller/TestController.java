package cn.csg.jobschedule.controller;

import cn.csg.common.enums.ExplodeType;
import cn.csg.common.enums.RuleKeyEnum;
import cn.csg.common.vo.CommunicationAlarm;
import cn.csg.common.vo.ExplodeJob;
import cn.csg.common.vo.ExplodeVo;
import cn.csg.jobschedule.dao.DcdRedisDao;
import cn.csg.jobschedule.dao.ElasticsearchDao;
import cn.csg.jobschedule.service.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.codis.jodis.JedisResourcePool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Api(value = "jobsParamsController",description = "程序测试")
@RestController
@RequestMapping("/test")
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private ElasticsearchDao elasticsearchDao;

    @Autowired
    private ExplodeJobService service;
    @Autowired
    private ExplodeService explodeService;
    @Autowired
    private SrcIpAndDestPortCountQueryService srcIpAndDestPortCountQueryService;
    @Autowired
    private SrcIpAndDestIpCountQueryService srcIpAndDestIpCountQueryService;
    @Autowired
    private SrcIpSumQueryService srcIpSumQueryService;

    @Autowired
    private DcdRedisDao dcdRedisDao;

    @ApiOperation(value ="爆发式通讯对测试",httpMethod = "POST")
    @PostMapping(value = "/query")
    public ExplodeJob SrcIpAndDestIpCountQueryTest1(
            @RequestParam(name = "start",defaultValue = "2019-06-20T16:14:18+08:00") String start,
            @RequestParam(name = "end",defaultValue = "2019-06-21T16:15:18+08:00") String end,
            @RequestParam(name = "explodeId",defaultValue = "4028a1816a57eb4f016a57eba9670006") String explodeId
    ){
        ExplodeJob job = service.findExplodeJobByExplodeId(explodeId);
        ExplodeVo explode = explodeService.findById(explodeId);

        String query = null;
        //使用freemarker模版
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        Configuration configuration = new Configuration();
        stringLoader.putTemplate("job_" + explodeId,job.getQuery());
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

        if(ExplodeType.SRC_IP_AND_DEST_IP_COUNT.equals(explode.getExplodeType())){
            srcIpAndDestIpCountQueryService.exec(
                    query,
                    explode.getValByName(RuleKeyEnum.WINDOWN.getKey()).intValue()/60 ,
                    explode.getValByName(RuleKeyEnum.TARGET.getKey()).intValue()
            );
        }else if(ExplodeType.SRC_IP_AND_DEST_PORT_COUNT.equals(explode.getExplodeType())){
            srcIpAndDestPortCountQueryService.exec(
                    query,
                    explode.getValByName(RuleKeyEnum.WINDOWN.getKey()).intValue()/60 ,
                    explode.getValByName(RuleKeyEnum.TARGET.getKey()).intValue()
            );
        }else if(ExplodeType.SRC_IP_SUM.equals(explode.getExplodeType())){
            srcIpSumQueryService.exec(
                    query,
                    explode.getValByName(RuleKeyEnum.WINDOWN.getKey()).intValue()/60 ,
                    explode.getValByName(RuleKeyEnum.TARGET.getKey()).intValue()
            );
        }
        return job;
    }

    @ApiOperation(value ="爆发式es测试",httpMethod = "GET")
    @GetMapping("/test")
    public JSONObject test(){
        CommunicationAlarm alarm = new CommunicationAlarm();
        String id = UUID.randomUUID().toString();
        alarm.setId(id);
        alarm.setCountData(100);
        alarm.setSessionStartTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date()));
        alarm.setSrcIp("192.168.10.1");
        alarm.setDeviceGUID("66c6e37898c5436cb766967d72720524");
        return elasticsearchDao.save("20190506", id, JSONObject.parseObject(JSON.toJSONString(alarm)));
    }

    @ApiOperation(value ="爆发式redis测试",httpMethod = "POST")
    @PostMapping("/findDcd")
    public Boolean findDcdGuid(String ip){
        return dcdRedisDao.isDcd(ip);
    }
}
