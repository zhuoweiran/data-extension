package cn.csg.jobschedule.util;

import cn.csg.jobschedule.config.JedisConfig;
import io.codis.jodis.RoundRobinJedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.Properties;

/***
 * @Author: xushiyong
 * @Description Redis 工具类
 * @Date: Created in 15:48 2018/11/7
 * @Modify By:
 **/
public class JedisConnectionPool implements Serializable{
    private final static Logger logger = LoggerFactory.getLogger(JedisConnectionPool.class);

    protected static RoundRobinJedisPool jedisPool = null;


    private static void init(JedisConfig jedisConfig) {
        try {
            jedisPool = RoundRobinJedisPool.create().
                    curatorClient(jedisConfig.getZkConnectHost(), jedisConfig.getZkSessionTimeout())
                    .zkProxyDir(jedisConfig.getZkProxyDir())
                    .poolConfig(getJedisPoolConfig(jedisConfig))
                    .connectionTimeoutMs(jedisConfig.getJedis_connection_timeout_ms())
                    .soTimeoutMs(jedisConfig.getJedis_sotimeout_ms())
                    .password(jedisConfig.getSessionAuth())
                    .database(jedisConfig.getDatabase())
                    .build();
        } catch (Exception e) {
            logger.error("jedisPool init failed!");
        }

        logger.info("jedisPool init sucessful!");
    }

    /**
     * jedis连接池配置
     * @return
     */
    private static JedisPoolConfig getJedisPoolConfig(JedisConfig jedisConfig) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(jedisConfig.getMaxTotal());
        config.setMaxIdle(jedisConfig.getMaxIdle());
        config.setMinIdle(jedisConfig.getMinIdle());
        config.setMaxWaitMillis(jedisConfig.getMaxWaitMillis());
        config.setSoftMinEvictableIdleTimeMillis(jedisConfig.getSoftMinEvictableIdleTimeMillis());
        //新增
        //在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        //在return给pool时，是否提前进行validate操作
        config.setTestOnReturn(true);

        return config;
    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis(JedisConfig jedisConfig) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            logger.error("JedisConnectionPool异常："+e.getMessage());
            logger.error("JedisConnectionPool连接池断开，尝试进行重连！！！");
            try {
                init(jedisConfig);
                jedis = jedisPool.getResource();
            } catch (Exception e2){
                logger.error("JedisConnectionPool尝试进行重连失败！！！");
                e2.printStackTrace();
            }
        }
        return jedis;
    }

}
