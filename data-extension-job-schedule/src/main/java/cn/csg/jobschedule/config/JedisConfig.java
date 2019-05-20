package cn.csg.jobschedule.config;


/**
 * redis连接参数
 */
public class JedisConfig {

    private int database ;
    private String zkConnectHost;
    private int zkSessionTimeout;
    private String zkProxyDir;
    private String sessionAuth;
    private int jedis_connection_timeout_ms;
    private int jedis_sotimeout_ms;

    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int maxWaitMillis;
    private int softMinEvictableIdleTimeMillis;

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getZkConnectHost() {
        return zkConnectHost;
    }

    public void setZkConnectHost(String zkConnectHost) {
        this.zkConnectHost = zkConnectHost;
    }

    public int getZkSessionTimeout() {
        return zkSessionTimeout;
    }

    public void setZkSessionTimeout(int zkSessionTimeout) {
        this.zkSessionTimeout = zkSessionTimeout;
    }

    public String getZkProxyDir() {
        return zkProxyDir;
    }

    public void setZkProxyDir(String zkProxyDir) {
        this.zkProxyDir = zkProxyDir;
    }

    public String getSessionAuth() {
        return sessionAuth;
    }

    public void setSessionAuth(String sessionAuth) {
        this.sessionAuth = sessionAuth;
    }

    public int getJedis_connection_timeout_ms() {
        return jedis_connection_timeout_ms;
    }

    public void setJedis_connection_timeout_ms(int jedis_connection_timeout_ms) {
        this.jedis_connection_timeout_ms = jedis_connection_timeout_ms;
    }

    public int getJedis_sotimeout_ms() {
        return jedis_sotimeout_ms;
    }

    public void setJedis_sotimeout_ms(int jedis_sotimeout_ms) {
        this.jedis_sotimeout_ms = jedis_sotimeout_ms;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public int getSoftMinEvictableIdleTimeMillis() {
        return softMinEvictableIdleTimeMillis;
    }

    public void setSoftMinEvictableIdleTimeMillis(int softMinEvictableIdleTimeMillis) {
        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }

    public JedisConfig(int database, String zkConnectHost, int zkSessionTimeout, String zkProxyDir, String sessionAuth, int jedis_connection_timeout_ms, int jedis_sotimeout_ms, int maxTotal, int maxIdle, int minIdle, int maxWaitMillis, int softMinEvictableIdleTimeMillis) {
        this.database = database;
        this.zkConnectHost = zkConnectHost;
        this.zkSessionTimeout = zkSessionTimeout;
        this.zkProxyDir = zkProxyDir;
        this.sessionAuth = sessionAuth;
        this.jedis_connection_timeout_ms = jedis_connection_timeout_ms;
        this.jedis_sotimeout_ms = jedis_sotimeout_ms;
        this.maxTotal = maxTotal;
        this.maxIdle = maxIdle;
        this.minIdle = minIdle;
        this.maxWaitMillis = maxWaitMillis;
        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }
}
