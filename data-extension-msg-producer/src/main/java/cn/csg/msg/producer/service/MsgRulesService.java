package cn.csg.msg.producer.service;

import cn.csg.msg.producer.bean.MsgRules;
import cn.csg.msg.producer.bean.ValueType;
import cn.csg.msg.producer.dao.MsgRulesDao;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class MsgRulesService {
    private final Logger logger = LoggerFactory.getLogger(MsgRulesService.class);
    @Autowired
    private MsgRulesDao msgRulesDao;

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
            }else if(valueType == ValueType.Table){

            }else {
                result.put(key, value);
            }
        }
        return result;
    }
}
