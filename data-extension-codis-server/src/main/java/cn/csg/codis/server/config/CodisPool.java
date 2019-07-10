package cn.csg.codis.server.config;

import cn.csg.codis.server.pool.RoundRobinJedisPoolEx;
import io.codis.jodis.JedisResourcePool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 类{@code CodisPool}初始化一个jedis连接池
 *
 * @author Alex Han
 * @version 1.0
 */
@Configuration
public class CodisPool {

    @Value("${jedis.zk.connect.host}")
    private String zkAddr;

    @Value("${jedis.database}")
    private int database;

    @Value("${jedis.zk.proxy.dir}")
    private String zkProxyDir;

    @Value("${jedis.session.auth}")
    private String password;

    @Value("${jedis.zk.session.timeout}")
    private int zkSessionTimeout;

    @Value("${jedis.maxTotal}")
    private int maxTotal;

    @Value("${jedis.connection.timeout}")
    private int codisConnectionTimeout;

    @Value("${jedis.sotimeout}")
    private int sotimeout;

    @Value("${jedis.maxIdle}")
    private int maxIdle;

    @Value("${jedis.minIdle}")
    private int minIdle;

    @Value("${jedis.maxWaitMillis}")
    private long maxWaitMillis;

    @Value("${jedis.softMinEvictableIdleTimeMillis}")
    private long softMinEvictableIdleTimeMillis;

    /**
     * 获取一个jedis连接池
     * @return JedisResourcePool
     */
    @Bean
    public JedisResourcePool getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
        //在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        //在return给pool时，是否提前进行validate操作
        config.setTestOnReturn(true);

        RoundRobinJedisPoolEx jedisResourcePool = RoundRobinJedisPoolEx.create()
                .curatorClient(zkAddr, zkSessionTimeout)
                .zkProxyDir(zkProxyDir)
                .poolConfig(config)
                .connectionTimeoutMs(codisConnectionTimeout)
                .soTimeoutMs(sotimeout)
                .password(password)
                .database(database)
                .build();

        return jedisResourcePool;
    }
}
