package cn.csg.msg.producer.controller;

import cn.csg.common.ResultData;
import cn.csg.common.ResultStatus;
import cn.csg.common.enums.StatusEnum;
import cn.csg.common.vo.DeviceRedis;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.codis.jodis.JedisResourcePool;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private JedisResourcePool jedisResourcePool;

    @ApiOperation(value ="初始化资产",httpMethod = "GET")
    @GetMapping("/init/device")
    public void initJedisDevice(){
        Jedis jedis = jedisResourcePool.getResource();
        Object[] guids = jedis.hkeys("deviceInfoGuidbd").toArray();
        for(Object guid : guids){
            jedis.del("deviceGuidSet");
            jedis.sadd("deviceGuidSet",guid.toString());
        }
        System.out.println("add all over");
        jedis.close();
    }
    @ApiOperation(value ="初始化未知资产",httpMethod = "GET")
    @GetMapping("/init/tmpDevice")
    public void initJedisTmpDevice(){
        Jedis jedis = jedisResourcePool.getResource();
        Object[] guids = jedis.hkeys("deviceTmpInfoLidbd").toArray();
        for(Object guid : guids){
            jedis.del("tmpDeviceGuidSet");
            jedis.sadd("tmpDeviceGuidSet",guid.toString());
        }
        System.out.println("add all over");
        jedis.close();
    }
    @ApiOperation(value ="初始化自己的资产",httpMethod = "POST")
    @PostMapping("/init/selfDevice")
    public ResultData initJedisSelfDevice(String setName,String array){
        JSONArray jsonArray = JSONArray.parseArray(array);
        Jedis jedis = jedisResourcePool.getResource();
        for(int i = 0 ; i < jsonArray.size() ; i ++){
            jedis.sadd(setName,jsonArray.get(i).toString());
        }
        jedis.close();
        return new ResultData<>(null, ResultStatus.initStatus(StatusEnum.SUCCESS));
    }
    @ApiOperation(value ="测试redis资产序列化为Object",httpMethod = "GET")
    @GetMapping("/init/testJedisToObj")
    public ResultData testJedisToObj(){
        Jedis jedis = jedisResourcePool.getResource();
        String sandDeviceGuid = jedis.srandmember("deviceGuidSet");
        String deviceJson = jedis.hget("deviceInfoGuidbd", sandDeviceGuid);
        System.out.println(deviceJson);
        DeviceRedis deviceRedis = JSONObject.parseObject(deviceJson, new TypeReference<DeviceRedis>(DeviceRedis.class){});
        jedis.close();
        return new ResultData<>(deviceRedis, ResultStatus.initStatus(StatusEnum.SUCCESS));
    }
}
