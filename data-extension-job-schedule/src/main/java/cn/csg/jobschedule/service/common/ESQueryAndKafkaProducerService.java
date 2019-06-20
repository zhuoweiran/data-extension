package cn.csg.jobschedule.service.common;

import cn.csg.common.vo.ESSecurityPolicy;
import cn.csg.jobschedule.dao.ElasticsearchDao;
import cn.csg.jobschedule.util.DatetimeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public abstract class ESQueryAndKafkaProducerService {

    @Autowired
    private KafkaProducer<String, String> producer;

    @Autowired
    private ElasticsearchDao elasticsearchDao;

    @Value("${kafka.producer.alarms.topic}")
    private String topic;

    @Value("${escluster.security_policy.index}")
    private String securityPolicyIndex;


    public abstract void exec(String query, int windowMins, int target);
    public void saveResult(List<ESSecurityPolicy> result){
        //爆发时通讯对索引名称
        String date = DatetimeUtil.getFirstDayOfWeek(new Date(), "yyyyMMdd");
        String index = securityPolicyIndex + "-" + date;

        for(ESSecurityPolicy esSecurityPolicy : result) {
            elasticsearchDao.save(
                    index,
                    esSecurityPolicy.getId(),
                    JSONObject.parseObject(JSON.toJSONString(esSecurityPolicy))
            );
        }
    }
    public void sendMessage(String message){
        producer.send(new ProducerRecord<>(topic, message));
    }

    protected void putData(ESSecurityPolicy esSecurityPolicy){

        esSecurityPolicy.setSeverity("");
        esSecurityPolicy.setAppName("");
        esSecurityPolicy.setSrcDeviceGUID("");
        esSecurityPolicy.setDestCorpId("");
        esSecurityPolicy.setSrcMac("");
        esSecurityPolicy.setDestDeviceGUID("");
        esSecurityPolicy.setSrcPort("");
        esSecurityPolicy.setSource("");
        esSecurityPolicy.setDetailType("");
        esSecurityPolicy.setProtocol("");
        esSecurityPolicy.setDestDeviceGUID("");
        esSecurityPolicy.setTag("Comm-expl");
        esSecurityPolicy.setDestMac("");
        esSecurityPolicy.setAlarmType("0");
        esSecurityPolicy.setEventCode("01");
        esSecurityPolicy.setStatus(0);
    }
}
