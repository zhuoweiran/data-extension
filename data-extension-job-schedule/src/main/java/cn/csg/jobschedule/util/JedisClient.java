package cn.csg.jobschedule.util;

import cn.csg.jobschedule.constants.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

/***
 * @Author: xushiyong
 * @Description Redis 工具类
 * @Date: Created in 15:48 2018/11/7
 * @Modify By:
 **/
public class JedisClient implements Serializable{

    public static String THREATEVENT_TOTAL_COUNT = RedisConstants.THREATEVENT_TOTAL_COUNT;
    public static String THREATEVENT_UNHANDLE_TOTAL_COUNT = RedisConstants.THREATEVENT_UNHANDLE_TOTAL_COUNT;
    public static String THREATEVENT_UNHANDLE_URGENT_COUNT = RedisConstants.THREATEVENT_UNHANDLE_URGENT_COUNT;
    public static String METADATA_TOTAL_COUNT = RedisConstants.METADATA_TOTAL_COUNT;
    public static String METADATA_HW_COUNT = RedisConstants.METADATA_HW_COUNT;
    public static String METADATA_CORP_COUNT = RedisConstants.METADATA_CORP_COUNT;
    private final static Logger logger = LoggerFactory.getLogger(JedisClient.class);
    private static Jedis jedis = null ;

    public JedisClient(){
        if (jedis == null) {
            jedis = JedisConnectionPool.getJedis();
        }
    }

    /***
     * 设置key-value
     * @param key
     * @param value
     */
    public void set(int redisIndexDB, String key, String value) {
        jedis.select(redisIndexDB);
        jedis.set(key, value);
    }

    /***
     * 根据key获取
     * @param key
     * @return
     */
    public String get(int redisIndexDB, String key) {
        jedis.select(redisIndexDB);
        return jedis.get(key);
    }

    /**
     * 根据hash key获取值
     * @param redisIndexDB
     * @param mapName
     * @param key
     * @return
     */
    public String hget(int redisIndexDB, String mapName, String key) {
        jedis.select(redisIndexDB);
        return jedis.hget(mapName, key);
    }

    /**
     * 判断 member 元素是否集合 key 的成员。
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key, String member) {
        return jedis.sismember(key, member);
    }

    /**
     * 判断 member 元素是否集合 key 的成员。
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(int db, String key, String member) {
        jedis.select(db);
        return jedis.sismember(key, member);
    }


    /**
     * 判断集合key是否存在
     * @param redisIndexDB
     * @param key
     * @return
     */
    public boolean hasKey(int redisIndexDB, String key) {
        jedis.select(redisIndexDB);
        boolean existed =  jedis.get(key) != null;
        return existed;
    }

    /**
     * 删除集合key
     * @param redisIndexDB
     * @param key
     */
    public void delete(int redisIndexDB, String key) {
        jedis.select(redisIndexDB);
        jedis.del(key);
    }

    /**
     * 删除Hash集合key中某个k的值
     * @param redisIndexDB
     * @param key
     * @param k
     */
    public void hdel(int redisIndexDB, String key, String k) {
        jedis.select(redisIndexDB);
        jedis.hdel(key, k);
    }

    /**
     * 判断Hash集合key中是否存在某个k的值
     * @param redisIndexDB
     * @param key
     * @param k
     * @return
     */
    public boolean hExists(int redisIndexDB, String key, String k) {
            jedis.select(redisIndexDB);
            return jedis.hexists(key, k);
    }

    /**
     * 向Hash集合key中添加k，v值
     * @param redisIndexDB
     * @param key
     * @param k
     * @param v
     */
    public void hset(int redisIndexDB, String key, String k, String v) {
        jedis.select(redisIndexDB);
        jedis.hset(key, k, v);
    }

    /**
     * 根据hash key获取值
     * @param redisIndexDB
     * @param mapName
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T hgetBean(int redisIndexDB, byte[] mapName, byte[] key, Class<T> clazz) {
        T t = null;
        try {
            jedis.select(redisIndexDB);
            byte[] val = jedis.hget(mapName, key);
            if (val == null || val.length <= 0) {
                return null;
            }
            t = (T) toJavaBean(val);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return t;
    }

    /**
     * 将数组反序列化成JavaBean
     * @param serStr
     * @return
     */
    private static Object toJavaBean(byte[] serStr) {
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        Object object = jdkSerializationRedisSerializer.deserialize(serStr);
        return object;
    }



    public void incrThreateventTotalCount() {
        jedis.incr(THREATEVENT_TOTAL_COUNT);
    }

    public void incrThreateventUnhandleTotalCount() {
        jedis.incr(THREATEVENT_UNHANDLE_TOTAL_COUNT);
    }

    public void incrThreateventUnhandleUrgentCount() {
        jedis.incr(THREATEVENT_UNHANDLE_URGENT_COUNT);
    }

    public void incrMetadataTotalCount() {
        jedis.incr(METADATA_TOTAL_COUNT);
    }

    public void incrMetadataHWCount() {
        jedis.incr(METADATA_HW_COUNT);
    }

    public void incrMetadataCorpCount() {
        jedis.incr(METADATA_CORP_COUNT);
    }

    public void incr(String key) {
        jedis.incr(key);
    }


    public static void main(String[] args) {
        // new JedisConnectionPool().set(8,"a","11111");

        //System.out.println(new JedisConnectionPool().get(8,"a"));

        //new JedisConnectionPool().incrThreateventTotalCount() ;

        for(int i = 0 ;i<10000 ;i++){
            System.out.println(i);
            new JedisClient().incrThreateventTotalCount();
        }

    }

    /**
     * 新添一个值入redis set
     * @param db redis database
     * @param key set集合的key
     * @param metadataId set集合中的值
     */
    public void sadd(int db, String key, String metadataId) {
        jedis.select(db);
        jedis.sadd(key,metadataId);
    }

    /**
     * 移除一个值出set集合
     * @param db redis database
     * @param metadataIdKey set集合的key
     * @param metadataId set集合中的值
     */
    public void srem(int db, String metadataIdKey, String metadataId) {
        jedis.select(db);
        jedis.srem(metadataIdKey, metadataId);
    }
}