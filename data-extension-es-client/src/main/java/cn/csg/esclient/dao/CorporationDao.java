package cn.csg.esclient.dao;

import cn.csg.common.vo.Corporation;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.codis.jodis.JedisResourcePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;


@Component
public class CorporationDao {
    @Autowired
    private JedisResourcePool jedisResourcePool;

    public Corporation findByCropId(String corpId){

        Jedis jedis = jedisResourcePool.getResource();
        String corporationStr = jedis.hget("ui_corporationbd",corpId);

        Jedis jedis2 = jedisResourcePool.getResource();
        corporationStr = jedis2.hget("ui_corporationbd",corpId);

        Corporation corporation = JSONObject.parseObject(corporationStr, new TypeReference<Corporation>(){});
        return corporation;
    }
}
