package cn.csg.jobschedule.util;

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

//    protected static Jedis jedis = null ;

    private static Properties props = ResourceUtil.load("redis.properties");

    /**
     * Jedis初始化
     */
    static {
        init();
    }

    private static void init() {
        String zkConnectHost = props.getProperty("jedis.zk.connect.host") ;
        int zkSessionTimeout = Integer.valueOf(props.getProperty("jedis.zk.session.timeout"));
        String zkProxyDir = props.getProperty("jedis.zk.proxy.dir");
        String sessionAuth = props.getProperty("jedis.session.auth");
        int database = Integer.valueOf(props.getProperty("jedis.database"));
        int jedis_connection_timeout_ms = Integer.valueOf(props.getProperty("jedis.connection.timeout"));
        int jedis_sotimeout_ms = Integer.valueOf(props.getProperty("jedis.sotimeout"));

        try {
            jedisPool = RoundRobinJedisPool.create().
                    curatorClient(zkConnectHost, zkSessionTimeout)
                    .zkProxyDir(zkProxyDir)
                    .poolConfig(getJedisPoolConfig(props))
                    .connectionTimeoutMs(jedis_connection_timeout_ms)
                    .soTimeoutMs(jedis_sotimeout_ms)
                    .password(sessionAuth)
                    .database(database)
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
    private static JedisPoolConfig getJedisPoolConfig(Properties props) {
        int maxTotal = Integer.valueOf(props.getProperty("jedis.maxTotal"));
        int maxIdle = Integer.valueOf(props.getProperty("jedis.maxIdle"));
        int minIdle = Integer.valueOf(props.getProperty("jedis.minIdle"));
        int maxWaitMillis = Integer.valueOf(props.getProperty("jedis.maxWaitMillis"));
        int softMinEvictableIdleTimeMillis = Integer.valueOf(props.getProperty("jedis.softMinEvictableIdleTimeMillis"));


        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
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
    public synchronized static Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            logger.error("JedisConnectionPool异常："+e.getMessage());
            logger.error("JedisConnectionPool连接池断开，尝试进行重连！！！");
            try {
                init();
                jedis = jedisPool.getResource();
            } catch (Exception e2){
                logger.error("JedisConnectionPool尝试进行重连失败！！！");
                e2.printStackTrace();
            }
        }
        return jedis;
    }

}
