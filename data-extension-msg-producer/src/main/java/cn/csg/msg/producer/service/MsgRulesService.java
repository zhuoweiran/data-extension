package cn.csg.msg.producer.service;

import cn.csg.common.vo.DeviceRedis;
import cn.csg.common.vo.DeviceTmpRedis;
import cn.csg.msg.producer.bean.MsgRules;
import cn.csg.msg.producer.bean.ValueType;
import cn.csg.msg.producer.dao.MsgRulesDao;
import com.alibaba.fastjson.*;
import com.google.common.collect.Maps;
import io.codis.jodis.JedisResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;


/**
 * 类{@code MsgRulesService} 参数数据访问对象
 *
 * <p>用于参数的增删改查
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 *
 */
@Service
public class MsgRulesService {
    private final Logger logger = LoggerFactory.getLogger(MsgRulesService.class);
    @Autowired
    private MsgRulesDao msgRulesDao;

    @Autowired
    private JedisResourcePool jedisResourcePool;

    /**
     * 保存参数
     * @param msgRules 参数
     * @return {@code MsgRules}
     */
    public MsgRules save(MsgRules msgRules){
        return msgRulesDao.save(msgRules);
    }

    /**
     * 根据任务id查询所有参数组成Map
     *
     * <p>根据任务id查询所有参数组成Map,Map以key作为key，以value作为value.
     *
     * @param jobId 任务id
     * @return {@code Map<String, Object>}
     * @throws Exception 所有异常
     */
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
                //去掉对date的支持，freemarker自带的日期及函数已足够使用
            /*}else if(valueType == ValueType.Date){
                if("Today".equals(value)){
                    result.put(key, new Date());
                }else if("Yesterday".equals(value)) {
                    result.put(key, new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
                }else {
                    result.put(key, new Date());
                }*/
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
                    throw new RuntimeException(e);
                }

            }else if(valueType == ValueType.Random_int){//新增随机int类型

                JSONArray array = JSONArray.parseArray(value);
                if(array.size() != 2){
                    logger.error("值[{}]配置有误" ,value);
                    throw new RuntimeException("值[{" + value + "}]配置有误");
                }
                int randomInt = rangeRandomInt(array.getInteger(0), array.getInteger(1));
                result.put(key, randomInt);
            }else if(valueType == ValueType.GUID){//新增随机GUID类型
                result.put(key, UUID.randomUUID());
            }else {
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * 生成一个范围的随机数
     * @param start 范围开始，包含
     * @param end 范围结束，不包含
     * @return
     */
    private int rangeRandomInt(int start, int end){
        Random random = new Random();
        int randIndex = random.nextInt(end - start);
        return randIndex + start;
    }

    /**
     * 查询所有参数
     *
     * @return {@code List<MsgRules>}
     */
    public List<MsgRules> list(){
        return msgRulesDao.findAll();
    }

    /**
     * 根据任务id查询参数，返回List
     *
     * @param jobId 任务id
     * @return {@code List<MsgRules>}
     */
    public List<MsgRules> findAllByJobId(String jobId){
        return msgRulesDao.findAllByJobId(jobId);
    }

    /**
     * 根据参数id查询参数
     *
     * @param id 参数id
     * @return {@code MsgRules}
     */
    public MsgRules findById(String id){
        return msgRulesDao.getOne(id);
    }

    /**
     * 删除参数
     *
     * @param rules 参数
     */
    public void delete(MsgRules rules){
        msgRulesDao.delete(rules);
    }
    public void deleteAll(List<MsgRules> rulesList){
        for(MsgRules rules : rulesList){
            delete(rules);
        }
    }

    /**
     * 根据任务id删除参数
     *
     * @param jobId 任务id
     */
    public void deleteAllByJobId(String jobId){
        deleteAll(findAllByJobId(jobId));
    }

}
