package cn.csg.jobschedule.dao;

import cn.csg.common.vo.DeviceTmpRedis;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.codis.jodis.JedisResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class DeviceTmpRedisDao {
    private final Logger logger = LoggerFactory.getLogger(DeviceTmpRedisDao.class);

    @Autowired
    private JedisResourcePool jedisResourcePool;

    public DeviceTmpRedis findDeviceByGuid(String guid){
        Jedis jedis = jedisResourcePool.getResource();
        DeviceTmpRedis deviceTmp = null;
        try{
            logger.info("hget deviceTmpInfoLidbd {}", guid);
            String deviceStr = jedis.hget("deviceTmpInfoLidbd", guid);
            deviceTmp = JSONObject.parseObject(deviceStr, new TypeReference<DeviceTmpRedis>(){});
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return deviceTmp;
    }
}
