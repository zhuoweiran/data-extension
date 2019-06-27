package cn.csg.msg.producer.config;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 类{@code CoidsDataSource}初始化codis连接池
 *
 * <p>创建一个codis连接池</p>
 * <pre class="code">
 * 使用方法
 * &#64;Autowired
 * private JedisResourcePool jedisResourcePool;
 * 获取一个jedis连接
 * Jedis jedis = jedisResourcePool.getResource();
 * 回收jedis连接
 * jedis.close();
 *
 * </pre>
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 *
 */
@Configuration
public class CoidsDataSource {
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

        JedisResourcePool jedisResourcePool = RoundRobinJedisPool.create().
                curatorClient(zkAddr, zkSessionTimeout)
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
