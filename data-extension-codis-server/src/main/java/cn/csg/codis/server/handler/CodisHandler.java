package cn.csg.codis.server.handler;

import cn.csg.codis.server.domain.DeviceBean;
import cn.csg.codis.server.domain.PoolStatus;
import cn.csg.codis.server.pool.RoundRobinJedisPoolEx;
import cn.csg.common.ResultData;
import cn.csg.common.ResultStatus;
import cn.csg.common.enums.StatusEnum;
import com.alibaba.fastjson.JSONObject;
import io.codis.jodis.JedisResourcePool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * 类{@code CodisHandler} codis处理类
 *
 * @author Alex Han
 * @version 1.0
 */
@Component
public class CodisHandler {

    @Autowired
    private JedisResourcePool jedisResourcePool;


    public Mono<Integer> findPoolSatus(){
        return null;
    }

    /**
     * 通过guid查询资产
     * @param guid
     * @return Mono<DeviceBean>
     */
    public Mono<ResultData> findByGuid(String guid){
        Jedis jedis = null;
        DeviceBean device = null;
        try {
            jedis = jedisResourcePool.getResource();
            String jsonStr = jedis.hget("deviceInfoGuidbd", guid);
            device = JSONObject.parseObject(jsonStr, DeviceBean.class);
        }catch (Exception e) {
            jedis.close();
            e.printStackTrace();
            return Mono.just(new ResultData<>(
                    null, ResultStatus.initStatus(StatusEnum.ERROR)
            ));
        }
        return Mono.just(new ResultData<>(
                device, ResultStatus.initStatus(StatusEnum.SUCCESS)
        ));
    }

    /**
     * 更新device 列表
     * @param deviceBeanList
     * @return Mono<ResultData>
     */
    public Mono<ResultData> updateDevices(List<DeviceBean> deviceBeanList){
        Jedis jedis = null;
        try {
            jedis = jedisResourcePool.getResource();
            for(DeviceBean device : deviceBeanList){
                String key = device.getGuid();
                if(StringUtils.isNotEmpty(key))
                    jedis.hset("deviceInfoGuidbd", key, JSONObject.toJSONString(device));
            }

        }catch (Exception e) {
            jedis.close();
            e.printStackTrace();
            return Mono.just(new ResultData<>(
                    null, ResultStatus.initStatus(StatusEnum.ERROR)
            ));
        }
        return Mono.just(new ResultData<>(
                null, ResultStatus.initStatus(StatusEnum.SUCCESS)
        ));

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
