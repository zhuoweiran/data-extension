package cn.csg.jobschedule.service;

import cn.csg.common.vo.DeviceRedis;
import cn.csg.common.vo.DeviceTmpRedis;
import cn.csg.common.vo.ESSecurityPolicy;
import cn.csg.jobschedule.dao.*;
import cn.csg.jobschedule.service.common.ESQueryAndKafkaProducerService;
import cn.csg.jobschedule.util.*;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 相同源IP访问不同IP数
 */
@Service
public class SrcIpAndDestIpCountQueryService extends ESQueryAndKafkaProducerService {
    private final Logger logger = LoggerFactory.getLogger(SrcIpAndDestIpCountQueryService.class);
//    @Autowired
//    private CorporationDao corporationDao;
    @Autowired
    private DeviceRedisDao deviceRedisDao;

    @Autowired
    private DeviceTmpRedisDao deviceTmpRedisDao;

    @Autowired
    private ElasticsearchDao elasticsearchDao;

    //爆发式通讯对存储到es的index
    @Value("${escluster.security_policy.index}")
    private String securityPolicyIndex;

    @Autowired
    private DcdRedisDao dcdRedisDao;


    @Override
    public void exec(String query, int windowMins, int target) {
        JSONObject jsonResult = elasticsearchDao.getResultsetByQueryJson(JSONObject.parseObject(query));
        logger.info("即将运行[{}分钟内相同源IP访问不同IP数>={}],查询语句为:\n{}", windowMins, target, JSONObject.parseObject(query).toJSONString());
        logger.debug("即将运行[{}分钟内相同源IP访问不同IP数>={}],查询结果为:\n{}", windowMins, target, jsonResult.toJSONString());
        Map aggregationsMap = (Map) jsonResult.get("aggregations");
        Map deviceGUIDCountMap = (Map) aggregationsMap.get("deviceGUIDCount");
        List<Map> buckets = (List) deviceGUIDCountMap.get("buckets");
        if(buckets == null || buckets.size() == 0){
            logger.info("[相同源IP访问不同IP数]未查询到符合条件的数据");
        }

        if (buckets != null && buckets.size() > 0) {//aggregations.deviceGUIDCount.buckets
            Date createTime = new Date();
            String createDateStr = DatetimeUtil.formatDate(createTime,"yyyy-MM-dd'T'HH:mm:ssXXX");
            String discoverTime = DatetimeUtil.formatDate(createTime,"yyyy-MM-dd HH:mm:ss");

            List<ESSecurityPolicy> result = Lists.newArrayList();

            for(Map deviceGUIDGroupMap : buckets){//aggregations.deviceGUIDCount.buckets
                String deviceGUID = deviceGUIDGroupMap.get("key") + "";
                Map srcIpCountMap = (Map) deviceGUIDGroupMap.get("srcIpCount");
                List<Map> sumResultBuckets = (List) srcIpCountMap.get("buckets");
                if (sumResultBuckets != null && sumResultBuckets.size() > 0) {//aggregations.deviceGUIDCount.buckets.srcIpCount.buckets
                    for(Map sumResultMap : sumResultBuckets){
                        String srcIp = sumResultMap.get("key").toString();

                        if(dcdRedisDao.isDcd(srcIp)){//如果源ip是装置，就跳过
                            logger.info("[相同源IP访问不同IP数]查询源ip[{}]为装置，忽略这一条",srcIp);
                            continue;
                        }
                        logger.info("[相同源IP访问不同IP数]查询源ip[{}]不是装置",srcIp);
                        //获取corpId
                        int idx = deviceGUID.indexOf("-");
                        String corpId = deviceGUID.substring(0, idx);
                        DeviceRedis device = deviceRedisDao.findDeviceByGuid(deviceGUID);

                        String securityPartition="";
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
                        //添加组织机构信息
//                        Corporation corporation = corporationDao.findByCropId(corpId);
//                        DeviceUtils.corpAddToMap(map, corporation);

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
                                + "\",\"Details\":{\"ExplType\":\"2\", \"ClientIp\":\""
                                + srcIp +"\",\"ServerIp\":\"\",\"Threshold\":\""
                                + windowMins + "\",\"Cycle\":\"" + target + "\",\"Partition\":\""
                                + securityPartition
                                + "\"}}}";
                        sendMessage(message);
                    }
                }
            }
            saveResult(result);
        }
    }
}
