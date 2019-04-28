package cn.csg.jobschedule.job;

import cn.csg.jobschedule.constants.DatetimeConstants;
import cn.csg.jobschedule.service.MetadataService;
import cn.csg.jobschedule.util.DatetimeUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hjw on 2019-04-22
 * 爆发式通信对告警统计
 */
@Component
@EnableScheduling
public class CommunicationAlarmJob {

    private final static Logger logger = LoggerFactory.getLogger(CommunicationAlarmJob.class);

    @Autowired
    public MetadataService metadataService;


    /**
     * 1.srcIp在t分钟内发起n次访问
     */
    public void executeSrcIpSumTask() {
        try {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"-------------srcIp在t分钟内发起n次访问----------");
            List dataList = metadataService.getAlarmRule("srcIpSum");
            if (dataList != null && dataList.size() > 0) {
                Map<String, Long> ruleValueMap = getRuleValue(dataList);
                Map<String, Date> ranTimeMap = getRangTime(ruleValueMap.get("rangValue"));
                String srcIpSumQueryStr = getSrcIpSumQueryStr(ruleValueMap.get("thresholdValue"), ranTimeMap.get("startDelayTime"), ranTimeMap.get("endDelayDate"));
                JSONObject srcIpSumJson = metadataService.getResultByHttp(srcIpSumQueryStr);
                metadataService.handleSrcIpSumData(srcIpSumJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("srcIp在t分钟内发起n次访问执行异常：" + e.getMessage());
        }
    }

    /**
     * 2.srcIp在t分钟内访问了n个destIp
     */
    public void executeSrcIpAndDestIpCountTask() {
        try {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"-------------srcIp在t分钟内访问了n个destIp----------");
            List dataList = metadataService.getAlarmRule("srcIpAndDestIpCount");
            if (dataList != null && dataList.size() > 0) {
                Map<String, Long> ruleValueMap = getRuleValue(dataList);
                Map<String, Date> ranTimeMap = getRangTime(ruleValueMap.get("rangValue"));
                String srcIpAndDestIpCountQueryStr = getSrcIpAndDestIpCountQueryStr(ruleValueMap.get("thresholdValue"), ranTimeMap.get("startDelayTime"), ranTimeMap.get("endDelayDate"));
                JSONObject srcIpAndDestIpCountJson = metadataService.getResultByHttp(srcIpAndDestIpCountQueryStr);
                metadataService.handleSrcIpAndDestIpCountData(srcIpAndDestIpCountJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("srcIp在t分钟内发起n次访问执行异常：" + e.getMessage());
        }
    }

    /**
     * 3.srcIp在t分钟内访问destIp的n个端口
     */
    public void executeSrcIpAndDestPortCountTask() {
        try {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"-------------srcIp在t分钟内访问destIp的n个端口----------");
            List dataList = metadataService.getAlarmRule("srcIpAndDestPortCount");
            if (dataList != null && dataList.size() > 0) {
                Map<String, Long> ruleValueMap = getRuleValue(dataList);
                Map<String, Date> ranTimeMap = getRangTime(ruleValueMap.get("rangValue"));
                String srcIpAndDestPortCountQueryStr = getSrcIpAndDestPortCountQueryStr(ruleValueMap.get("thresholdValue"), ranTimeMap.get("startDelayTime"), ranTimeMap.get("endDelayDate"));
                JSONObject srcIpAndDestPortCountJson = metadataService.getResultByHttp(srcIpAndDestPortCountQueryStr);
                metadataService.handleSrcIpAndDestPortCountData(srcIpAndDestPortCountJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("srcIp在t分钟内发起n次访问执行异常：" + e.getMessage());
        }
    }


    /**
     * (1)组装 srcIp在t分钟内发起n次访问 统计条件
     *
     * @param thresholdValue 统计阈值
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @return
     */
    private String getSrcIpSumQueryStr(Long thresholdValue, Date startTime, Date endTime) throws Exception {
        String queryStr = "{\n" +
                "    \"aggs\": {\n" +
                "        \"corpIdCount\": {\n" +
                "            \"aggs\": {\n" +
                "                \"srcIpCount\": {\n" +
                "                    \"aggs\": {\n" +
                "                        \"doubleCountSum\": {\n" +
                "                            \"sum\": {\n" +
                "                                \"field\": \"doubleCount\"\n" +
                "                            }\n" +
                "                        },\n" +
                "                        \"doubleCount_filter\": {\n" +
                "                            \"bucket_selector\": {\n" +
                "                                \"buckets_path\": {\n" +
                "                                    \"filterKey\": \"doubleCountSum\"\n" +
                "                                },\n" +
                "                                \"script\": \"params.filterKey >= " + thresholdValue + "\"\n" +
                "                            }\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"terms\": {\n" +
                "                        \"field\": \"srcIp\"\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"terms\": {\n" +
                "                \"field\": \"corpId\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"query\": {\n" +
                "        \"range\": {\n" +
                "            \"createTime\": {\n" +
                "                \"gte\": \"" + DatetimeUtil.toStr(startTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX) + "\",\n" +
                "                \"lt\": \"" + DatetimeUtil.toStr(endTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX) + "\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"size\": 0\n" +
                "}";
        return queryStr;
    }

    /**
     * (2)组装 srcIp在t分钟内访问了n个destIp 统计条件
     *
     * @param thresholdValue 阈值
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @return
     */
    private String getSrcIpAndDestIpCountQueryStr(Long thresholdValue, Date startTime, Date endTime) throws Exception {
        String queryStr = "{\n" +
                "    \"aggs\": {\n" +
                "        \"corpIdCount\": {\n" +
                "            \"aggs\": {\n" +
                "                \"srcIpCount\": {\n" +
                "                    \"aggs\": {\n" +
                "                        \"destIpCount\": {\n" +
                "                            \"terms\": {\n" +
                "                                \"field\": \"destIp\"\n" +
                "                            }\n" +
                "                        },\n" +
                "                        \"having\": {\n" +
                "                            \"bucket_selector\": {\n" +
                "                                \"buckets_path\": {\n" +
                "                                    \"srcIpCount\": \"_count\"\n" +
                "                                },\n" +
                "                                \"script\": {\n" +
                "                                    \"source\": \"params.srcIpCount >= " + thresholdValue + "\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"terms\": {\n" +
                "                        \"field\": \"srcIp\"\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"terms\": {\n" +
                "                \"field\": \"corpId\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"query\": {\n" +
                "        \"range\": {\n" +
                "            \"createTime\": {\n" +
                "                \"gte\": \"" + DatetimeUtil.toStr(startTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX) + "\",\n" +
                "                \"lt\": \"" + DatetimeUtil.toStr(endTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX) + "\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"size\": 0\n" +
                "}";
        return queryStr;
    }

    /**
     * (3)组装 srcIp在t分钟内访问destIp的n个端口 查询条件
     *
     * @param thresholdValue 阈值
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @return
     */
    private String getSrcIpAndDestPortCountQueryStr(Long thresholdValue, Date startTime, Date endTime) throws Exception {
        String queryStr = "{\n" +
                "    \"aggs\": {\n" +
                "        \"corpIdCount\": {\n" +
                "            \"aggs\": {\n" +
                "                \"srcIpCount\": {\n" +
                "                    \"aggs\": {\n" +
                "                        \"destIpCount\": {\n" +
                "                            \"aggs\": {\n" +
                "                                \"destPortCount\": {\n" +
                "                                    \"terms\": {\n" +
                "                                        \"field\": \"destPort\"\n" +
                "                                    }\n" +
                "                                },\n" +
                "                                \"having\": {\n" +
                "                                    \"bucket_selector\": {\n" +
                "                                        \"buckets_path\": {\n" +
                "                                            \"destIpCount\": \"_count\"\n" +
                "                                        },\n" +
                "                                        \"script\": {\n" +
                "                                            \"source\": \"params.destIpCount >= " + thresholdValue + "\"\n" +
                "                                        }\n" +
                "                                    }\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"terms\": {\n" +
                "                                \"field\": \"destIp\"\n" +
                "                            }\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"terms\": {\n" +
                "                        \"field\": \"srcIp\"\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"terms\": {\n" +
                "                \"field\": \"corpId\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"query\": {\n" +
                "        \"range\": {\n" +
                "            \"createTime\": {\n" +
                "                \"gte\": \"" + DatetimeUtil.toStr(startTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX) + "\",\n" +
                "                \"lt\": \"" + DatetimeUtil.toStr(endTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX) + "\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"size\": 0\n" +
                "}";

        return queryStr;
    }

    /**
     * 将时间范围转换为开始、结束时间
     *
     * @param rangTime 查询时间范围，单位：秒
     * @return
     */
    private static Map<String, Date> getRangTime(Long rangTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long time = rangTime * 1000;
        long endTime = System.currentTimeMillis();
        long startTime = endTime - time;
        Date startDate = new Date();
        startDate.setTime(startTime);
        String startTimeStr = sdf.format(startDate) + ":00";
        String endTimeStr = sdf.format(endTime) + ":00";
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startDelayTime = sd.parse(startTimeStr).getTime();
        long endDelayTime = sd.parse(endTimeStr).getTime();
        //延迟时间
        long delayTime = 1 * 1000 * 60;
        startDelayTime = startDelayTime - delayTime;
        endDelayTime = endDelayTime - delayTime;
        Date startDelayDate = new Date();
        startDelayDate.setTime(startDelayTime);
        Date endDelayDate = new Date();
        endDelayDate.setTime(endDelayTime);
        Map<String, Date> ranTimeMap = new HashMap<String, Date>();
        ranTimeMap.put("startDelayTime", startDelayDate);
        ranTimeMap.put("endDelayDate", endDelayDate);
        return ranTimeMap;
    }

    /**
     * 获取规则值
     *
     * @param dataList
     * @return
     */
    private Map<String, Long> getRuleValue(List dataList) {
        Map<String, Long> ruleValueMap = new HashMap<String, Long>();
        Iterator it = dataList.iterator();
        Long rangValue = 0L;
        Long thresholdValue = 0L;
        while (it.hasNext()) {
            Map dataMap = (Map) it.next();
            if ("window".equals(dataMap.get("rule_key") + "")) {
                rangValue = Double.valueOf(dataMap.get("rule_value") + "").longValue() * 1000L;
            }
            if ("target".equals(dataMap.get("rule_key") + "")) {
                thresholdValue = Double.valueOf(dataMap.get("rule_value") + "").longValue();
            }
        }
        ruleValueMap.put("rangValue", rangValue);
        ruleValueMap.put("thresholdValue", thresholdValue);
        return ruleValueMap;
    }
}
