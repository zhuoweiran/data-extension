package cn.csg.jobschedule.job;

import cn.csg.jobschedule.service.MetadataService;
import com.alibaba.fastjson.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hjw on 2019-04-22
 * 爆发式通信对告警统计
 */
public class CommunicationAlarmJob extends QuartzJobBean {

    private final static Logger logger = LoggerFactory.getLogger(CommunicationAlarmJob.class);

    @Autowired
    public MetadataService metadataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //srcIp在t分钟内发起n次访问 start
        String srcIpSumQueryStr = getSrcIpSumQueryStr();
        JSONObject srcIpSumJson = metadataService.getResultByHttp(srcIpSumQueryStr);
        metadataService.handleSrcIpSumData(srcIpSumJson);
        //srcIp在t分钟内发起n次访问 end

        //srcIp在t分钟内访问了n个destIp start
        String srcIpAndDestIpCountQueryStr = getSrcIpAndDestIpCountQueryStr();
        JSONObject srcIpAndDestIpCountJson = metadataService.getResultByHttp(srcIpAndDestIpCountQueryStr);
        metadataService.handleSrcIpAndDestIpCountData(srcIpAndDestIpCountJson);
        //srcIp在t分钟内访问了n个destIp end

        //srcIp在t分钟内访问destIp的n个端口 start
        String srcIpAndDestPortCountQueryStr = getSrcIpAndDestPortCountQueryStr();
        JSONObject srcIpAndDestPortCountJson = metadataService.getResultByHttp(srcIpAndDestPortCountQueryStr);
        metadataService.handleSrcIpAndDestPortCountData(srcIpAndDestPortCountJson);
        //srcIp在t分钟内访问destIp的n个端口 end
    }

    /**
     * (1)组装 srcIp在t分钟内发起n次访问 统计条件
     * @return
     */
    private String getSrcIpSumQueryStr(){
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
                "                                \"script\": \"params.filterKey >= 10\"\n" +
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
                "                \"gte\": \"2019-04-24T15:00:00+08:00\",\n" +
                "                \"lt\": \"2019-04-24T16:00:00+08:00\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"size\": 0\n" +
                "}";
        return queryStr;
    }

    /**
     * (2)组装 srcIp在t分钟内访问了n个destIp 统计条件
     * @return
     */
    private String getSrcIpAndDestIpCountQueryStr(){
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
                "                                    \"source\": \"params.srcIpCount > 0\"\n" +
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
                "                \"gte\": \"2019-04-24T15:00:00+08:00\",\n" +
                "                \"lt\": \"2019-04-24T16:00:00+08:00\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"size\": 0\n" +
                "}";
        return queryStr;
    }

    /**
     * (3)组装 srcIp在t分钟内访问destIp的n个端口 查询条件
     * @return
     */
    private String getSrcIpAndDestPortCountQueryStr(){
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
                "                                            \"source\": \"params.destIpCount > 0\"\n" +
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
                "                \"gte\": \"2019-04-24T15:00:00+08:00\",\n" +
                "                \"lt\": \"2019-04-24T16:00:00+08:00\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"size\": 0\n" +
                "}";

        return queryStr;
    }
}
