package cn.csg.codis.server.handler;

import cn.csg.codis.server.domain.*;
import cn.csg.codis.server.domain.common.JedisCollectDomain;
import cn.csg.codis.server.enums.CollectType;
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
 * 类{@code CodisHandlerEx} codis处理类,暂时废弃
 *
 * @author Alex Han
 * @version 1.2
 */
//@Component
public class CodisHandlerEx {
    private static Logger logger = LoggerFactory.getLogger(CodisHandlerEx.class);

    @Autowired
    private JedisResourcePool jedisResourcePool;


    /**
     * 查询redis
     * @param collectType 集合类型
     * @param collectName 集合名称
     * @param guid 集合的key
     * @return ResultData
     */
    public Mono<ResultData> findByGuid(String collectType,String collectName, String guid){
        Jedis jedis = null;
        String result;
        try {
            jedis = jedisResourcePool.getResource();
            if(CollectType.Hash.name().equals(collectType)){
                result = jedis.hget(collectName, guid);
            }else if(CollectType.String.name().equals(collectType)){
                result = jedis.get(collectName + ":" + guid);
            }else{
                result = null;
            }
        }catch (Exception e) {
            jedis.close();
            logger.error("查询失败");
            e.printStackTrace();
            return Mono.just(new ResultData<>(
                    null, ResultStatus.initStatus(StatusEnum.ERROR)
            ));
        }finally {
            jedis.close();
        }
        JSONObject o;
        if(result.startsWith("{")){
            o = JSONObject.parseObject(result);
            return Mono.just(new ResultData<>(
                    o, ResultStatus.initStatus(StatusEnum.SUCCESS)
            ));
        }

        return Mono.just(new ResultData<>(
                result, ResultStatus.initStatus(StatusEnum.SUCCESS)
        ));
    }

    /**
     * 删除key
     * @param collectType 集合类型
     * @param collect 集合名称
     * @param guid 集合key
     * @return ResultData
     */
    public Mono<ResultData> deleteByGuid(String collectType, String collect, String guid){
        Jedis jedis = null;
        String key;
        try {
            jedis = jedisResourcePool.getResource();
            if(CollectType.String.name().equals(collectType)){
                key = collect + ":" + guid;
                jedis.del(key);
            }else if(CollectType.Hash.name().equals(collectType)){
                key = guid;
                jedis.hdel(collect, guid);
            }else{
                key = null;
            }
        }catch (Exception e) {
            jedis.close();
            logger.error("删除资产失败");
            e.printStackTrace();
            return Mono.just(new ResultData<>(
                    null, ResultStatus.initStatus(StatusEnum.ERROR)
            ));
        }finally {
            jedis.close();
        }
        return Mono.just(new ResultData<>(
                key, ResultStatus.initStatus(StatusEnum.DELETE)
        ));
    }

    /**
     * 更新集合
     * @param collectType 集合类型
     * @param collect 集合名称
     * @param key 集合的key
     * @param listStr 集合的value
     * @return ResultData
     */
    public Mono<ResultData> updateDevices(String collectType, String collect, String key, String listStr){
        Jedis jedis = null;
        try {
            jedis = jedisResourcePool.getResource();
            if (listStr.startsWith("{")) {
                updateKV(jedis, collectType, collect, key, listStr);
            } else {
                JSONArray array = JSONArray.parseArray(listStr);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    updateKV(jedis, collectType, collect, key, jsonObject.toJSONString());
                }
            }
        }catch (Exception e){
            jedis.close();
            e.printStackTrace();
            logger.error("更新资产失败");
        }finally {
            jedis.close();
        }

        return Mono.just(new ResultData<>(
                null, ResultStatus.initStatus(StatusEnum.SUCCESS)
        ));
    }

    public void updateKV(
            Jedis jedis, String collectType, String collect, String key, String jsonStr
    )throws Exception{

        if(CollectType.String.name().equals(collectType)){
            jedis.set(collect + ":" + key , jsonStr);
        }else if(CollectType.Hash.name().equals(collectType)){
            jedis.hset(collect, key , jsonStr);
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
