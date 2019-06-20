package cn.csg.jobschedule.dao;

import cn.csg.common.vo.DeviceRedis;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.codis.jodis.JedisResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class DeviceRedisDao {
    private final Logger logger = LoggerFactory.getLogger(DeviceRedisDao.class);

    @Autowired
    private JedisResourcePool jedisResourcePool;

    public DeviceRedis findDeviceByGuid(String guid){
        Jedis jedis = jedisResourcePool.getResource();
        DeviceRedis device = null;
        try{
            logger.info("hget deviceInfoGuidbd {}", guid);
            String deviceStr = jedis.hget("deviceInfoGuidbd", guid);
            device = JSONObject.parseObject(deviceStr, new TypeReference<DeviceRedis>(){});
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return device;
    }
}
