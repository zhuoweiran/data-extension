package cn.csg.jobschedule.job;

import cn.csg.jobschedule.service.MetadataService;
import com.alibaba.fastjson.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hjw on 2019-04-22
 * 爆发式通信对告警统计
 */
@Component
@EnableScheduling
public class CommunicationAlarmJob{

    private final static Logger logger = LoggerFactory.getLogger(CommunicationAlarmJob.class);

    @Autowired
    public MetadataService metadataService;

    /**
     * 1.srcIp在t分钟内发起n次访问
     */
    public void executeSrcIpSumTask(){
//        System.out.println("-------------srcIp在t分钟内发起n次访问 task---"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        //TODO 获取数据库中的统计策略，更改ES查询条件
        //srcIp在t分钟内发起n次访问 start
        String srcIpSumQueryStr = getSrcIpSumQueryStr(10,new Date(),new Date());
        JSONObject srcIpSumJson = metadataService.getResultByHttp(srcIpSumQueryStr);
        metadataService.handleSrcIpSumData(srcIpSumJson);
        //srcIp在t分钟内发起n次访问 end
    }

    /**
     * 2.srcIp在t分钟内访问了n个destIp
     */
    public void executeSrcIpAndDestIpCountTask(){
//        System.out.println("-------------srcIp在t分钟内访问了n个destIp task---"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            String srcIpAndDestIpCountQueryStr = getSrcIpAndDestIpCountQueryStr(10,new Date(),new Date());
            JSONObject srcIpAndDestIpCountJson = metadataService.getResultByHttp(srcIpAndDestIpCountQueryStr);
            metadataService.handleSrcIpAndDestIpCountData(srcIpAndDestIpCountJson);
    }

    /**
     * 3.srcIp在t分钟内访问destIp的n个端口
     */
    public void executeSrcIpAndDestPortCountTask(){
//        System.out.println("-------------srcIp在t分钟内访问destIp的n个端口 task---"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String srcIpAndDestPortCountQueryStr = getSrcIpAndDestPortCountQueryStr(10,new Date(),new Date());
        JSONObject srcIpAndDestPortCountJson = metadataService.getResultByHttp(srcIpAndDestPortCountQueryStr);
        metadataService.handleSrcIpAndDestPortCountData(srcIpAndDestPortCountJson);
    }


    /**
     * (1)组装 srcIp在t分钟内发起n次访问 统计条件
     * @return
     */
    private String getSrcIpSumQueryStr(Integer value,Date startTime,Date endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = sdf.format(startTime);
        String[] startDateArr = startStr.split(" ");
        String startTimeStr = startDateArr[0]+"T"+startDateArr[1]+"+08:00";

        String endStr = sdf.format(endTime);
        String[] endDateArr = endStr.split(" ");
        String endTimeStr = endDateArr[0]+"T"+endDateArr[1]+"+08:00";

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
                "                                \"script\": \"params.filterKey >= "+value+"\"\n" +
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
                "                \"gte\": \""+startTimeStr+"\",\n" +
                "                \"lt\": \""+endTimeStr+"\"\n" +
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
    private String getSrcIpAndDestIpCountQueryStr(Integer value,Date startTime,Date endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = sdf.format(startTime);
        String[] startDateArr = startStr.split(" ");
        String startTimeStr = startDateArr[0]+"T"+startDateArr[1]+"+08:00";

        String endStr = sdf.format(endTime);
        String[] endDateArr = endStr.split(" ");
        String endTimeStr = endDateArr[0]+"T"+endDateArr[1]+"+08:00";

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
                "                                    \"source\": \"params.srcIpCount > "+value+"\"\n" +
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
                "                \"gte\": \""+startTimeStr+"\",\n" +
                "                \"lt\": \""+endTimeStr+"\"\n" +
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
    private String getSrcIpAndDestPortCountQueryStr(Integer value,Date startTime,Date endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = sdf.format(startTime);
        String[] startDateArr = startStr.split(" ");
        String startTimeStr = startDateArr[0]+"T"+startDateArr[1]+"+08:00";

        String endStr = sdf.format(endTime);
        String[] endDateArr = endStr.split(" ");
        String endTimeStr = endDateArr[0]+"T"+endDateArr[1]+"+08:00";

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
                "                                            \"source\": \"params.destIpCount > "+value+"\"\n" +
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
                "                \"gte\": \""+startTimeStr+"\",\n" +
                "                \"lt\": \""+endTimeStr+"\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"size\": 0\n" +
                "}";

        return queryStr;
    }
}
