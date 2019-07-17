package cn.csg.codis.server.handler;

import cn.csg.codis.server.domain.*;
import cn.csg.codis.server.domain.common.JedisCollectDomain;
import cn.csg.codis.server.pool.RoundRobinJedisPoolEx;
import cn.csg.common.ResultData;
import cn.csg.common.ResultStatus;
import cn.csg.common.enums.StatusEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.codis.jodis.JedisResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;
import java.util.List;

/**
 * 类{@code CodisHandler} codis处理类
 *
 * @author Alex Han
 * @version 1.2
 */
@Component
public class CodisHandler {
    private static final Logger logger = LoggerFactory.getLogger(CodisHandler.class);

    @Autowired
    private JedisResourcePool jedisResourcePool;

    @Autowired
    private RedisCollects collects;

    /**
     * 通过guid查询
     * @param collectName 集合名称
     * @param guid key
     * @return ResultData
     */
    public Mono<ResultData> findByGuid(String collectName, String guid){
        Jedis jedis = null;
        JedisCollectDomain domain;
        try {
            jedis = jedisResourcePool.getResource();
            if(collectName.equals("corporation")) {
                String jsonStr = jedis.hget(collects.getCorporation(), guid);
                domain = JSON.parseObject(jsonStr, CorporationBean.class);
            }else if(collectName.equals("dcdDeviceIpbd")){
                String jsonStr = jedis.get(collects.getDcdDeviceIpbd() + ":" +guid.replace(".","_"));
                domain = JSON.parseObject(jsonStr, DeviceRegisterBean.class);
            }else if(collectName.equals("device")){
                String jsonStr = jedis.hget(collects.getDevice(), guid);
                domain = JSON.parseObject(jsonStr, DeviceBean.class);
            }else if(collectName.equals("devicetmp")){
                String jsonStr = jedis.hget(collects.getDevicetmp(), guid);
                domain = JSON.parseObject(jsonStr, DbDeviceTmpBean.class);
            }else if(collectName.equals("evilDns")){
                String jsonStr = jedis.get(collects.getEvilDns() + ":" + guid);
                domain = DNSEvilEntity.builder().evilDNS(jsonStr).build();
            }else if(collectName.equals("evilIp")){
                String jsonStr = jedis.get(collects.getEvilIp() + ":" + guid.replace(".","_"));
                domain = IPEvilEntity.builder().evilIp(jsonStr).build();
            }else {
                domain = null;
            }
        }catch (Exception e) {
            jedis.close();
            logger.error("查询失败,collect:[{}],guid[{}]", collectName, guid);
            e.printStackTrace();
            return Mono.just(new ResultData<>(
                    null, ResultStatus.initStatus(StatusEnum.ERROR)
            ));
        }finally {
            jedis.close();
        }
        return Mono.just(new ResultData<>(
                domain, ResultStatus.initStatus(StatusEnum.SUCCESS)
        ));
    }

    /**
     * * 删除资产
     * @param guid 资产guid
     * @param collectName 集合名称
     * @return ResultData
     */
    public Mono<ResultData> deleteByGuid(String collectName, String guid){
        Jedis jedis = null;
        try {
            jedis = jedisResourcePool.getResource();
            if(collectName.equals("corporation")) {
                jedis.hdel(collects.getCorporation(), guid);
            }else if(collectName.equals("dcdDeviceIpbd")){
                guid = collects.getDcdDeviceIpbd() + ":" + guid.replace(".","_");
                jedis.del(collects.getDcdDeviceIpbd(), guid);
            }else if(collectName.equals("device")){
                jedis.hdel(collects.getDevice(), guid);
            }else if(collectName.equals("devicetmp")){
                jedis.hdel(collects.getDevicetmp(), guid);
            }else if(collectName.equals("evilDns")){
                guid = collects.getEvilDns() + ":" + guid;
                jedis.del(guid);
            }else if(collectName.equals("evilIp")){
                guid = collects.getEvilIp() + ":" + guid.replace(".","_");
                jedis.del(guid);
            }else {
                return Mono.just(new ResultData<>(
                        guid, ResultStatus.initStatus(StatusEnum.DELETE_FAIL)
                ));
            }
        }catch (Exception e) {
            jedis.close();
            logger.error("删除资产失败,collect:[{}],guid[{}]", collectName, guid);
            e.printStackTrace();
            return Mono.just(new ResultData<>(
                    guid, ResultStatus.initStatus(StatusEnum.DELETE_FAIL)
            ));
        }finally {
            jedis.close();
        }
        return Mono.just(new ResultData<>(
                guid, ResultStatus.initStatus(StatusEnum.DELETE)
        ));
    }

    /**
     * 更新device 列表
     * @param collectName 集合名称
     * @param array json string
     * @return ResultData
     */
    public Mono<ResultData> updateDevices(String collectName, JSONArray array){
        Jedis jedis = null;
        try {
            jedis = jedisResourcePool.getResource();
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                updateKV(jedis, collectName, jsonObject);
            }
        }catch (Exception e){
            jedis.close();
            e.printStackTrace();
            logger.error("更新资产失败,collect:[{}],array:[{}]", collectName, array.toJSONString());
            return Mono.just(new ResultData<>(
                    "更新资产失败", ResultStatus.initStatus(StatusEnum.UPDATE_FAIL)
            ));
        }finally {
            jedis.close();
        }

        return Mono.just(new ResultData<>(
                null, ResultStatus.initStatus(StatusEnum.SUCCESS)
        ));
    }


    /**
     * 更新一个key
     * @param jedis jedis连接
     * @param collectName 集合名称
     * @param jsonObject 更新对象
     * @throws Exception 抛出异常
     */
    public void updateKV(Jedis jedis, String collectName, JSONObject jsonObject)throws Exception{
        String key;
        if(collectName.equals("corporation")) {
            key = jsonObject.getString("corpid");
            jedis.hset(collects.getCorporation(), key, JSON.toJSONString(jsonObject));
        }else if(collectName.equals("dcdDeviceIpbd")){
            key = collects.getDcdDeviceIpbd() +":"+ jsonObject.getString("ip").replace(".","_");
            jedis.set(key, JSON.toJSONString(jsonObject));
        }else if(collectName.equals("device")){
            key = jsonObject.getString("guid");
            jedis.hset(collects.getDevice(), key, JSON.toJSONString(jsonObject));
        }else if(collectName.equals("devicetmp")){
            key = jsonObject.getString("lid");
            jedis.hset(collects.getDevicetmp(), key, JSON.toJSONString(jsonObject));
        }else if(collectName.equals("evilDns")){
            key = collects.getEvilDns() + ":" + jsonObject.getString("evilDNS");
            jedis.set(key, jsonObject.getString("evilDNS"));
        }else if(collectName.equals("evilIp")){
            key = collects.getEvilIp() + ":" + jsonObject.getString("evilIp").replace(".","_");
            jedis.set(key, jsonObject.getString("evilIp"));
        }else {
            logger.warn("没有找到对应的集合");
        }
    }

    /**
     * 查看连接池状态
     * @return 连接池状态列表
     */
    public Mono<ResultData> showStatus(){
        List<PoolStatus> statuses  = ((RoundRobinJedisPoolEx)jedisResourcePool).status();
        return Mono.just(new ResultData<>(
                statuses, ResultStatus.initStatus(StatusEnum.SUCCESS)
        ));
    }
}
