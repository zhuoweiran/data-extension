package cn.csg.jobschedule.service;

import cn.csg.common.vo.DeviceRedis;
import cn.csg.common.vo.DeviceTmpRedis;
import cn.csg.common.vo.ESSecurityPolicy;
import cn.csg.jobschedule.dao.DcdRedisDao;
import cn.csg.jobschedule.dao.DeviceRedisDao;
import cn.csg.jobschedule.dao.DeviceTmpRedisDao;
import cn.csg.jobschedule.dao.ElasticsearchDao;
import cn.csg.jobschedule.service.common.ESQueryAndKafkaProducerService;
import cn.csg.jobschedule.util.DatetimeUtil;
import cn.csg.jobschedule.util.IDUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 相同源IP访问同一IP端口数
 */
@Service
public class SrcIpAndDestPortCountQueryService extends ESQueryAndKafkaProducerService {
    private final Logger logger = LoggerFactory.getLogger(SrcIpAndDestPortCountQueryService.class);

    @Autowired
    private ElasticsearchDao elasticsearchDao;

    @Autowired
    private DeviceRedisDao deviceRedisDao;

    @Autowired
    private DeviceTmpRedisDao deviceTmpRedisDao;

    @Autowired
    private DcdRedisDao dcdRedisDao;

    @Override
    public void exec(String query, int windowMins, int target) {
        JSONObject jsonResult = elasticsearchDao.getResultsetByQueryJson(JSONObject.parseObject(query));
        logger.info("即将运行[{}分钟相同源IP访问同一IP端口数>={}],查询语句为:\n{}", windowMins, target, JSONObject.parseObject(query).toJSONString());
        logger.debug("即将运行[{}分钟相同源IP访问同一IP端口数>={}],查询结果为:\n{}", windowMins, target, jsonResult.toJSONString());
        Map aggregationsMap = (Map) jsonResult.get("aggregations");
        Map deviceGUIDCountMap = (Map) aggregationsMap.get("deviceGUIDCount");
        List<Map> buckets = (List) deviceGUIDCountMap.get("buckets");

        if(buckets == null || buckets.size() == 0){
            logger.info("[相同源IP访问同一IP端口数]未查询到符合条件的数据");
        }

        if (buckets != null && buckets.size() > 0) {//aggregations.deviceGUIDCount.buckets
            Date createTime = new Date();
            String createDateStr = DatetimeUtil.formatDate(createTime,"yyyy-MM-dd'T'HH:mm:ssXXX");
            String discoverTime = DatetimeUtil.formatDate(createTime,"yyyy-MM-dd HH:mm:ss");

            List<ESSecurityPolicy> result = Lists.newArrayList();

            for (Map deviceGUIDGroupMap : buckets) {//aggregations.deviceGUIDCount.buckets
                String deviceGUID = deviceGUIDGroupMap.get("key") + "";

                Map srcIpCountMap = (Map) deviceGUIDGroupMap.get("srcIpCount");
                List<Map> sumResultBuckets = (List) srcIpCountMap.get("buckets");
                if (sumResultBuckets != null && sumResultBuckets.size() > 0) {//aggregations.deviceGUIDCount.buckets.srcIpCount.buckets
                    for (Map srcIpCountResultMap : sumResultBuckets) {
                        String srcIp = srcIpCountResultMap.get("key") + "";

                        if(dcdRedisDao.isDcd(srcIp)){//如果源ip是装置，就跳过
                            logger.info("[相同源IP访问同一IP端口数]查询源ip[{}]为装置，忽略这一条",srcIp);
                            continue;
                        }

                        int idx = deviceGUID.indexOf("-");
                        Map destIpCountMap = (Map) srcIpCountResultMap.get("destIpCount");
                        List<Map> destIpCountBuckets = (List) destIpCountMap.get("buckets");
                        if (destIpCountBuckets != null && destIpCountBuckets.size() > 0) {
                            for (Map destIpResultMap : destIpCountBuckets) {
                                String destIp = destIpResultMap.get("key") + "";
                                Map destPortCount = (Map) destIpResultMap.get("destPortCount");
                                List<Map> destPortBuckets = (List) destPortCount.get("buckets");

                                if(destPortBuckets.size() >= target){

                                    DeviceRedis device = deviceRedisDao.findDeviceByGuid(deviceGUID);
                                    String corpId = deviceGUID.substring(0, idx);

                                    //考虑加入缓存
                                    String securityPartition = "";

                                    //去掉查询redis的部分
                                    if (device == null) {
                                        //添加未知资产信息
                                        DeviceTmpRedis deviceTmp = deviceTmpRedisDao.findDeviceByGuid(deviceGUID);
                                        if(deviceTmp != null) {
                                            securityPartition = deviceTmp.getPartition();
                                        }else {
                                            // 如果已知未知都没有这个资产，就先特殊处理
                                            logger.warn("未能查询到资产guid []", deviceGUID);
                                        }

                                    } else {
                                        //添加已知资产信息
                                        securityPartition = device.getArea();
                                    }

                                    ESSecurityPolicy esSecurityPolicy = ESSecurityPolicy.builder()
                                            .id(IDUtil.getUUID())
                                            .corpId(corpId)
                                            .devGuid(deviceGUID)
                                            .processTime(createDateStr)
                                            .timestamp(new Date().getTime() + "")
                                            .discoverTime(createDateStr)
                                            .sessionStartTime(createDateStr)
                                            .srcIp(srcIp)
                                            .destIp("")
                                            .build();
                                    putData(esSecurityPolicy);

                                    //保存到ES
                                    result.add(esSecurityPolicy);
                                    //转发至告警

                                    String message = "{\"Header\":{\"DcdGuid\":\""
                                            + deviceGUID
                                            + "\",\"DevGuid\":\""
                                            + deviceGUID
                                            + "\",\"Sid\":\""
                                            + esSecurityPolicy.getId()
                                            + "\",\"Timestamp\":\""
                                            + new Date().getTime()
                                            + "\"},\"Data\":{\"AppName\":\"\",\"EventType \":\"01\",\"FunClassTag\":\"Comm-expl\",\"DiscoverTime\":\""
                                            + discoverTime
                                            + "\",\"Details\":{\"ExplType\":\"3\", \"ClientIp\":\""
                                            + srcIp +"\",\"ServerIp\":\"\",\"Threshold\":\""
                                            + windowMins + "\",\"Cycle\":\"" + target + "\",\"Partition\":\""
                                            + securityPartition
                                            + "\"}}}";
                                    sendMessage(message);
                                }
                            }
                        }
                    }
                }
            }
            saveResult(result);
        }
    }
}
