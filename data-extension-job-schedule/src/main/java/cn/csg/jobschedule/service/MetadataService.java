package cn.csg.jobschedule.service;

import cn.csg.jobschedule.config.JedisConfig;
import cn.csg.jobschedule.constants.DatetimeConstants;
import cn.csg.jobschedule.constants.SymbolsConstants;
import cn.csg.jobschedule.dao.ElasticsearchDao;
import cn.csg.jobschedule.util.*;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MetadataService {

    private final static Logger logger = LoggerFactory.getLogger(MetadataService.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchDao elasticsearchDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${escluster.cluster}")
    private String esClusterName;

    @Value("${escluster.nodes}")
    private String esClusterNodes;

    @Value("${escluster.port}")
    private String esClusterPort;

    @Value("${escluster.commpair_alarm.index}")
    private String commpairAlarmIndex;

    @Value("${escluster.type}")
    private String type;

    @Value("${escluster.security_policy.index}")
    private String securityPolicyIndex;

    /**
     * redis的数据库
     */
    @Value("${jedis.database}")
    private int REDISDBINDEX ;

    /**
     * 主站已知资产KEY
     */
    @Value("${redis.key.event.zz.device}")
    private  String ZZDEVICEKEY;

    /**
     * 未知资产key
     */
    @Value("${redis.key.event.zz.devicetmp}")
    private String ZZDEVICETMPKEY ;

    /**
     * 主站组织架构key
     */
    @Value("${redis.key.event.zz.corporation}")
    private String ZZCORPORATIONKEY ;

    /**
     * 告警topic
     */
    @Value("${kafka.producer.alarms.topic}")
    private String ALARMSTOPIC ;

    @Value("${jedis.zk.connect.host}")
    private String zkConnectHost;
    @Value("${jedis.zk.session.timeout}")
    private int zkSessionTimeout;
    @Value("${jedis.zk.proxy.dir}")
    private String zkProxyDir;
    @Value("${jedis.session.auth}")
    private String sessionAuth;
    @Value("${jedis.connection.timeout}")
    private int jedis_connection_timeout_ms;
    @Value("${jedis.sotimeout}")
    private int jedis_sotimeout_ms;

    @Value("${jedis.maxTotal}")
    private int maxTotal;
    @Value("${jedis.maxIdle}")
    private int maxIdle;
    @Value("${jedis.minIdle}")
    private int minIdle;
    @Value("${jedis.maxWaitMillis}")
    private int maxWaitMillis;
    @Value("${jedis.softMinEvictableIdleTimeMillis}")
    private int softMinEvictableIdleTimeMillis;

    /**
     * 根据告警规则名称获取告警统计规则
     *
     * @param alarmName 告警规则名称
     * @return
     * @throws Exception
     */
    public List getAlarmRule(String alarmName) throws Exception {
        String sql = "select * from tb_explode te left join tb_rule tr on te.id = tr.rule where  name = '" + alarmName + "'";
        List dataList = jdbcTemplate.queryForList(sql);
        return dataList;
    }

    /**
     * 前往ES集群获取数据
     *
     * @param queryStr
     * @return
     */
    public JSONObject getResultByHttp(String queryStr) throws Exception {

        if (isIndexExisys(commpairAlarmIndex)) {
            return elasticsearchDao.requestMyTest(JSONObject.parseObject(queryStr));
        }
        return null;
    }

    //srcIp在t分钟内发起n次访问
    public void handleSrcIpSumData(Long thresholdValue, Long cycle, JSONObject jsonObj) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        try {

            Map aggregationsMap = (Map) jsonObj.get("aggregations");
            Map deviceGUIDCountMap = (Map) aggregationsMap.get("deviceGUIDCount");
            List<Map> buckets = (List) deviceGUIDCountMap.get("buckets");
            if (buckets != null && buckets.size() > 0) {
                String date = DatetimeUtil.getFirstDayOfWeek(new Date(), DatetimeConstants.YYYYMMDD);
                String index = securityPolicyIndex + date;
                EsConnectionPool esConnectionPool = EsConnectionPool.getInstance(esClusterNodes,
                        Integer.parseInt(esClusterPort), esClusterName);
                TransportClient client = esConnectionPool.getClient();
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                Date createTime = DatetimeUtil.toDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), DatetimeConstants.YYYY_MM_DD);
                String createDateStr = DatetimeUtil.toStr(createTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX);

                for (Map deviceGUIDGroupMap : buckets) {

                    String deviceGUID = deviceGUIDGroupMap.get("key") + "";
                    Map srcIpCountMap = (Map) deviceGUIDGroupMap.get("srcIpCount");
                    List<Map> sumResultBuckets = (List) srcIpCountMap.get("buckets");
                    if (sumResultBuckets != null && sumResultBuckets.size() > 0) {
                        for (Map sumResultMap : sumResultBuckets) {
                            String srcIp = sumResultMap.get("key") + "";
                            Map<String, Object> map = new HashMap();

                            //获取corpId
                            int idx = deviceGUID.indexOf(SymbolsConstants.HorizontalBar);
                            String corpId = deviceGUID.substring(0, idx);
                            map.put("corpId", corpId);

                            try {
                                //添加资产信息
                                JedisConfig jedisConfig = new JedisConfig(REDISDBINDEX,zkConnectHost,zkSessionTimeout,zkProxyDir,sessionAuth,
                                        jedis_connection_timeout_ms,jedis_sotimeout_ms,
                                        maxTotal,maxIdle,minIdle,maxWaitMillis,softMinEvictableIdleTimeMillis);
                                JedisClient jedisClient = new JedisClient(jedisConfig);
                                String deviceBean = jedisClient.hget(REDISDBINDEX, ZZDEVICEKEY, deviceGUID);
                                if (deviceBean == null) {
                                    //添加未知资产信息
                                    String dbDeviceTmpBean = jedisClient.hget(REDISDBINDEX, ZZDEVICETMPKEY, deviceGUID);
                                    DeviceUtils.deviceTmpAddToMap(map, dbDeviceTmpBean);
                                } else {
                                    //添加已知资产信息
                                    DeviceUtils.deviceStrAddToMap(map, deviceBean);
                                }
                                //添加组织机构信息
                                String corporationBean = jedisClient.hget(REDISDBINDEX, ZZCORPORATIONKEY, corpId);
                                DeviceUtils.corpAddToMap(map, corporationBean);
                            } catch (Exception e) {
                                logger.error("从redis中获取数据异常：" + e.getMessage());
//                                e.printStackTrace();
                            }

                            String id = IDUtil.getUUID();
                            map.put("id", id);
                            map.put("devGuid", "");
                            map.put("dcdGuid", deviceGUID);
                            map.put("processTime", createDateStr);
                            map.put("timestamp", "" + new Date().getTime());
                            map.put("discoverTime", createDateStr);
                            map.put("sessionStartTime", createDateStr);
                            map.put("srcIp", srcIp);
                            map.put("destIp", "");
                            putDataMap(map);

                            logger.info("srcIp在t分钟内发起n次访问 map========>" + map);
                            bulkRequest.add(client.prepareIndex(index, type, id).setSource(map));
                            //转发至告警
                            String alarm = "{\"Header\":{\"DcdGuid\":\"" + deviceGUID + "\",\"DevGuid\":\"" + deviceGUID + "\",\"Sid\":\"" + id + "\",\"Timestamp\":\"" + Long.valueOf(map.get("timestamp") + "") + "\"},\"Data\":{\"AppName\":\"\",\"EventType \":\"01\",\"FunClassTag\":\"Comm-expl\",\"DiscoverTime\":\"" + map.get("processTime") + "\",\"Details\":{\"ExplType\":\"1\", \"ClientIp\":\"\",\"ServerIp\":\"\",\"Threshold\":\"" + thresholdValue + "\",\"Cycle\":\"" + cycle + "\",\"Partition\":\"" + map.get("securityPartition") + "\"}}}";
                            BigdataProducerUtil producer = BigdataProducerUtil.getInstance(ResourceUtil.load("EventCommonJob.properties"));
                            producer.send(ALARMSTOPIC, alarm);
                            producer.flush();
                        }
                    }
                }
                ESUtil.saveLastToES(bulkRequest);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理 srcIp在t分钟内发起n次访问 异常:" + e.getMessage());
        }

//            }
//        }).start();
    }

    //srcIp在t分钟内访问了n个destIp
    public void handleSrcIpAndDestIpCountData(Long thresholdValue, Long cycle, JSONObject jsonObj) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        try {
            Map aggregationsMap = (Map) jsonObj.get("aggregations");
            Map deviceGUIDMap = (Map) aggregationsMap.get("deviceGUIDCount");
            List<Map> buckets = (List) deviceGUIDMap.get("buckets");
            if (buckets != null && buckets.size() > 0) {
                String date = DatetimeUtil.getFirstDayOfWeek(new Date(), DatetimeConstants.YYYYMMDD);
                String index = securityPolicyIndex + date;
                EsConnectionPool esConnectionPool = EsConnectionPool.getInstance(esClusterNodes,
                        Integer.parseInt(esClusterPort), esClusterName);
                TransportClient client = esConnectionPool.getClient();
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                Date createTime = DatetimeUtil.toDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), DatetimeConstants.YYYY_MM_DD);
                String createDateStr = DatetimeUtil.toStr(createTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX);

                for (Map deviceGUIDGroupMap : buckets) {
                    String deviceGUID = deviceGUIDGroupMap.get("key") + "";
                    Map srcIpCountMap = (Map) deviceGUIDGroupMap.get("srcIpCount");
                    List<Map> srcIpCountResultList = (List) srcIpCountMap.get("buckets");
                    if (srcIpCountResultList != null && srcIpCountResultList.size() > 0) {
                        for (Map srcIpCountResultMap : srcIpCountResultList) {
                            String srcIp = srcIpCountResultMap.get("key") + "";
                            Integer countData = (Integer) srcIpCountResultMap.get("doc_count");
                            Map destIpCountMap = (Map) srcIpCountResultMap.get("destIpCount");
                            List<Map> destIpCountList = (List) destIpCountMap.get("buckets");
                            Map<String, Object> map = new HashMap();

                            //获取corpId
                            int idx = deviceGUID.indexOf(SymbolsConstants.HorizontalBar);
                            String corpId = deviceGUID.substring(0, idx);
                            map.put("corpId", corpId);
                            try {
                                //添加资产信息
                                JedisConfig jedisConfig = new JedisConfig(REDISDBINDEX,zkConnectHost,zkSessionTimeout,zkProxyDir,sessionAuth,
                                        jedis_connection_timeout_ms,jedis_sotimeout_ms,
                                        maxTotal,maxIdle,minIdle,maxWaitMillis,softMinEvictableIdleTimeMillis);
                                JedisClient jedisClient = new JedisClient(jedisConfig);
                                String deviceBean = jedisClient.hget(REDISDBINDEX, ZZDEVICEKEY, deviceGUID);
                                if (deviceBean == null) {
                                    //添加未知资产信息
                                    String dbDeviceTmpBean = jedisClient.hget(REDISDBINDEX, ZZDEVICETMPKEY, deviceGUID);
                                    DeviceUtils.deviceTmpAddToMap(map, dbDeviceTmpBean);
                                } else {
                                    //添加已知资产信息
                                    DeviceUtils.deviceStrAddToMap(map, deviceBean);
                                }
                                //添加组织机构信息
                                String corporationBean = jedisClient.hget(REDISDBINDEX, ZZCORPORATIONKEY, corpId);
                                DeviceUtils.corpAddToMap(map, corporationBean);
                            } catch (Exception e) {
                                logger.error("从redis中获取数据异常：" + e.getMessage());
//                                e.printStackTrace();
                            }
                            String id = IDUtil.getUUID();
                            map.put("id", id);
                            map.put("devGuid", "");
                            map.put("dcdGuid", deviceGUID);
                            map.put("processTime", createDateStr);
                            map.put("timestamp", "" + new Date().getTime());
                            map.put("discoverTime", createDateStr);
                            map.put("sessionStartTime", createDateStr);
                            map.put("srcIp", srcIp);
                            map.put("destIp", "");
                            putDataMap(map);
                            logger.info("srcIp在t分钟内访问了n个destIp map************>" + map);
                            bulkRequest.add(client.prepareIndex(index, type, id).setSource(map));

                            //转发至告警
                            String alarm = "{\"Header\":{\"DcdGuid\":\"" + deviceGUID + "\",\"DevGuid\":\"" + deviceGUID + "\",\"Sid\":\"" + id + "\",\"Timestamp\":\"" + Long.valueOf(map.get("timestamp") + "") + "\"},\"Data\":{\"AppName\":\"\",\"EventType \":\"01\",\"FunClassTag\":\"Comm-expl\",\"DiscoverTime\":\"" + map.get("processTime") + "\",\"Details\":{\"ExplType\":\"2\", \"ClientIp\":\"\",\"ServerIp\":\"\",\"Threshold\":\"" + thresholdValue + "\",\"Cycle\":\"" + cycle + "\",\"Partition\":\"" + map.get("securityPartition") + "\"}}}";
                            BigdataProducerUtil producer = BigdataProducerUtil.getInstance(ResourceUtil.load("EventCommonJob.properties"));
                            producer.send(ALARMSTOPIC, alarm);
                            producer.flush();
                        }
                    }
                }
                ESUtil.saveLastToES(bulkRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理 srcIp在t分钟内访问了n个destIp 异常:" + e.getMessage());
        }

//            }
//        }).start();
    }

    //srcIp在t分钟内访问destIp的n个端口
    public void handleSrcIpAndDestPortCountData(Long thresholdValue, Long cycle, JSONObject jsonObj) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        try {
            Map aggregationsMap = (Map) jsonObj.get("aggregations");
            Map deviceGUIDCountMap = (Map) aggregationsMap.get("deviceGUIDCount");
            List<Map> buckets = (List) deviceGUIDCountMap.get("buckets");
            if (buckets != null && buckets.size() > 0) {
                String date = DatetimeUtil.getFirstDayOfWeek(new Date(), DatetimeConstants.YYYYMMDD);
                String index = securityPolicyIndex + date;
                EsConnectionPool esConnectionPool = EsConnectionPool.getInstance(esClusterNodes,
                        Integer.parseInt(esClusterPort), esClusterName);
                TransportClient client = esConnectionPool.getClient();
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                Date createTime = DatetimeUtil.toDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), DatetimeConstants.YYYY_MM_DD);
                String createDateStr = DatetimeUtil.toStr(createTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX);

                for (Map deviceGUIDGroupMap : buckets) {
                    String deviceGUID = deviceGUIDGroupMap.get("key") + "";
                    Map srcIpCountMap = (Map) deviceGUIDGroupMap.get("srcIpCount");
                    List<Map> srcIpCountResultList = (List) srcIpCountMap.get("buckets");
                    if (srcIpCountResultList != null && srcIpCountResultList.size() > 0) {
                        for (Map srcIpCountResultMap : srcIpCountResultList) {
                            String srcIp = srcIpCountResultMap.get("key") + "";
                            Map destIpCountMap = (Map) srcIpCountResultMap.get("destIpCount");
                            List<Map> destIpCountBuckets = (List) destIpCountMap.get("buckets");
                            if (destIpCountBuckets != null && destIpCountBuckets.size() > 0) {
                                for (Map destIpResultMap : destIpCountBuckets) {
                                    String destIp = destIpResultMap.get("key") + "";
                                    Map destPortCount = (Map) destIpResultMap.get("destPortCount");
                                    List<Map> destPortBuckets = (List) destPortCount.get("buckets");
                                    if (destPortBuckets.size() >= thresholdValue) {
                                        Map<String, Object> map = new HashMap();
                                        //获取corpId
                                        int idx = deviceGUID.indexOf(SymbolsConstants.HorizontalBar);
                                        String corpId = deviceGUID.substring(0, idx);
                                        map.put("corpId", corpId);

                                        try {
                                            //添加资产信息
                                            JedisConfig jedisConfig = new JedisConfig(REDISDBINDEX,zkConnectHost,zkSessionTimeout,zkProxyDir,sessionAuth,
                                                    jedis_connection_timeout_ms,jedis_sotimeout_ms,
                                                    maxTotal,maxIdle,minIdle,maxWaitMillis,softMinEvictableIdleTimeMillis);
                                            JedisClient jedisClient = new JedisClient(jedisConfig);
                                            String deviceBean = jedisClient.hget(REDISDBINDEX, ZZDEVICEKEY, deviceGUID);
                                            if (deviceBean == null) {
                                                //添加未知资产信息
                                                String dbDeviceTmpBean = jedisClient.hget(REDISDBINDEX,ZZDEVICETMPKEY, deviceGUID);
                                                DeviceUtils.deviceTmpAddToMap(map, dbDeviceTmpBean);
                                            } else {
                                                //添加已知资产信息
                                                DeviceUtils.deviceStrAddToMap(map, deviceBean);
                                            }
                                            //添加组织机构信息
                                            String corporationBean = jedisClient.hget(REDISDBINDEX, ZZCORPORATIONKEY, corpId);
                                            DeviceUtils.corpAddToMap(map, corporationBean);
                                        } catch (Exception e) {
                                            logger.error("从redis中获取数据异常：" + e.getMessage());
//                                            e.printStackTrace();
                                        }
                                        String id = IDUtil.getUUID();
                                        map.put("id", id);
                                        map.put("dcdGuid", deviceGUID);
                                        map.put("devGuid", "");
                                        map.put("processTime", createDateStr);
                                        map.put("timestamp", "" + new Date().getTime());
                                        map.put("discoverTime", createDateStr);
                                        map.put("sessionStartTime", createDateStr);
                                        map.put("srcIp", srcIp);
                                        map.put("destIp", destIp);
                                        putDataMap(map);
                                        logger.info("srcIp在t分钟内访问同一destIp的n个端口 map-------------->"+map);
                                        bulkRequest.add(client.prepareIndex(index, type, id).setSource(map));
                                        //转发到告警
                                        String alarm = "{\"Header\":{\"DcdGuid\":\"" + deviceGUID + "\",\"DevGuid\":\"" + deviceGUID + "\",\"Sid\":\"" + id + "\",\"Timestamp\":\"" + Long.valueOf(map.get("timestamp") + "") + "\"},\"Data\":{\"AppName\":\"\",\"EventType \":\"01\",\"FunClassTag\":\"Comm-expl\",\"DiscoverTime\":\"" + map.get("processTime") + "\",\"Details\":{\"ExplType\":\"3\", \"ClientIp\":\"" + srcIp + "\",\"ServerIp\":\"" + destIp + "\",\"Threshold\":\"" + thresholdValue + "\",\"Cycle\":\"" + cycle + "\",\"Partition\":\"" + map.get("securityPartition") + "\"}}}";
                                        BigdataProducerUtil producer = BigdataProducerUtil.getInstance(ResourceUtil.load("EventCommonJob.properties"));
                                        producer.send(ALARMSTOPIC, alarm);
                                        producer.flush();
                                    }
                                }
                            }
                        }
                    }
                }
                ESUtil.saveLastToES(bulkRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理 srcIp在t分钟内访问同一destIp的n个端口 异常:" + e.getMessage());
        }
//            }
//        }).start();
    }

    /**
     * 判断ES索引是否存在
     *
     * @param index 索引名称
     * @return true 存在；false 不存在
     * @throws Exception
     */
    private boolean isIndexExisys(String index) throws Exception {
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(new String[]{index});
        return elasticsearchTemplate.getClient().admin().indices().exists(inExistsRequest).actionGet().isExists();

    }

    public String getMetadata() {
//        Client transportClient = elasticsearchTemplate.getClient();
//        IndexResponse response = transportClient.prepareIndex("metadata20190418","default").setSource()
//                .execute().actionGet();
//        transportClient.close();
        //判断索引是否存在 start
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(new String[]{"metadata20190418"});
        boolean flag = elasticsearchTemplate.getClient().admin().indices().exists(inExistsRequest).actionGet().isExists();
        //判断索引是否存在 end
        SearchRequestBuilder requestBuilder = elasticsearchTemplate.getClient().prepareSearch("metadata20190418").setTypes("default");
        //过滤条件 start
        //过滤条件 end
        SearchResponse response = requestBuilder.execute().actionGet();

        return response.toString();
    }

    public static void putDataMap(Map map) throws Exception {
        map.put("severity", "");
        map.put("appName", "");
        map.put("srcDeviceGUID", "");
        map.put("destCorpId", "");
        map.put("srcMac", "");
        map.put("destDeviceGUID", "");
        map.put("srcPort", "");
        map.put("source", "");
        map.put("detailType", "");
        map.put("protocol", "");
        map.put("destPort", "");
        map.put("tag", "");
        map.put("destMac", "");
        map.put("alarmType", "0");
        map.put("eventCode", "01");
        map.put("status", 0);
    }

}
