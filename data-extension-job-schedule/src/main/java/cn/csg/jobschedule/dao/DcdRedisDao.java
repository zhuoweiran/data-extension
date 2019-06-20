package cn.csg.jobschedule.dao;

import io.codis.jodis.JedisResourcePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class DcdRedisDao {

    @Value("${redis.key.event.zz.dcdDeviceIpbd}")
    private String dcdDeviceIpbd;

    @Autowired
    private JedisResourcePool jedisResourcePool;

    public Boolean isDcd(String ip){
        ip = ip.replace(".","_");
        Jedis jedis = jedisResourcePool.getResource();
        return jedis.exists(dcdDeviceIpbd + ":" + ip);
    }
}
