package cn.csg.msg.producer.service;

import cn.csg.common.vo.DeviceRedis;
import cn.csg.common.vo.DeviceTmpRedis;
import cn.csg.msg.producer.bean.MsgRules;
import cn.csg.msg.producer.bean.ValueType;
import cn.csg.msg.producer.dao.MsgRulesDao;
import com.alibaba.fastjson.*;
import com.google.common.collect.Maps;
import io.codis.jodis.JedisResourcePool;
import org.apache.tomcat.util.digester.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class MsgRulesService {
    private final Logger logger = LoggerFactory.getLogger(MsgRulesService.class);
    @Autowired
    private MsgRulesDao msgRulesDao;

    @Autowired
    private JedisResourcePool jedisResourcePool;

    public MsgRules save(MsgRules msgRules){
        return msgRulesDao.save(msgRules);
    }

    public Map<String, Object> findAllRulesByJobId(String jobId) throws Exception {
        List<MsgRules> msgRulesList = msgRulesDao.findAllByJobId(jobId);
        Map<String, Object> result = Maps.newHashMap();
        for(int i = 0 ; msgRulesList != null && i<msgRulesList.size() ; i++){
            String key = msgRulesList.get(i).getKey();
            String value = msgRulesList.get(i).getValue();
            ValueType valueType = msgRulesList.get(i).getValueType();
            if(valueType == ValueType.String){
                result.put(key, value);
            }else if(valueType == ValueType.Int) {
                result.put(key, Integer.valueOf(value));
            }else if(valueType == ValueType.Date){
                if("Today".equals(value)){
                    result.put(key, new Date());
                }else if("Yesterday".equals(value)) {
                    result.put(key, new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
                }else {
                    result.put(key, new Date());
                }
            }else if(valueType == ValueType.Object){
                try {
                    JSONObject jsonObject = JSON.parseObject(value);
                    result.put(key, jsonObject);
                }catch (JSONException e){
                    logger.error("值[{}]配置有误" ,value);
                    throw new Exception(e);
                }
            }else if(valueType == ValueType.Table_Device){
                Jedis jedis = jedisResourcePool.getResource();
                String sandDeviceGuid = jedis.srandmember("deviceGuidSet");
                String deviceJson = jedis.hget("deviceInfoGuidbd", sandDeviceGuid);
                DeviceRedis deviceRedis = JSONObject.parseObject(deviceJson, new TypeReference<DeviceRedis>(){});
                result.put(key, deviceRedis);
                jedis.close();
            }else if(valueType == ValueType.Table_TmpDevice){
                Jedis jedis = jedisResourcePool.getResource();
                String sandDeviceGuid = jedis.srandmember("tmpDeviceGuidSet");
                String deviceJson = jedis.hget("deviceTmpInfoLidbd", sandDeviceGuid);
                DeviceTmpRedis deviceTmpRedis = JSONObject.parseObject(deviceJson, new TypeReference<DeviceTmpRedis>(){});
                result.put(key, deviceTmpRedis);
                jedis.close();

            }else if(valueType == ValueType.Array){
                try {
                    JSONArray array = JSONArray.parseArray(value);
                    Random random = new Random();
                    int randIndex = random.nextInt(array.size());
                    result.put(key, array.get(randIndex));
                }catch (JSONException e){
                    logger.error("值[{}]配置有误" ,value);
                    throw new Exception(e);
                }

            }else {
                result.put(key, value);
            }
        }
        return result;
    }
    public List<MsgRules> list(){
        return msgRulesDao.findAll();
    }
    public List<MsgRules> findAllByJobId(String jobId){
        return msgRulesDao.findAllByJobId(jobId);
    }

    public MsgRules findById(String id){
        return msgRulesDao.getOne(id);
    }
    public void delete(MsgRules rules){
        msgRulesDao.delete(rules);
    }
    public void deleteAll(List<MsgRules> rulesList){
        for(MsgRules rules : rulesList){
            delete(rules);
        }
    }
    public void deleteAllByJobId(String jobId){
        deleteAll(findAllByJobId(jobId));
    }

}
