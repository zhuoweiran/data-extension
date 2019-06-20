package cn.csg.jobschedule.dao;

import cn.csg.common.vo.Corporation;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.codis.jodis.JedisResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;


@Component
public class CorporationDao {
    private final Logger logger = LoggerFactory.getLogger(Corporation.class);

    @Autowired
    private JedisResourcePool jedisResourcePool;

    public Corporation findByCropId(String corpId){
        Jedis jedis = jedisResourcePool.getResource();
        Corporation corporation = null;
        try {
            logger.info("hget ui_corporationbd {}", corpId);

            String corporationStr = jedis.hget("ui_corporationbd", corpId);

            corporation = JSONObject.parseObject(corporationStr, new TypeReference<Corporation>() {
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return corporation;
    }
}
