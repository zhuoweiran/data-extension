package cn.csg.jobschedule.service;

import cn.csg.jobschedule.constants.DatetimeConstants;
import cn.csg.jobschedule.dao.ElasticsearchDao;
import cn.csg.jobschedule.util.DatetimeUtil;
import cn.csg.jobschedule.util.ESUtil;
import cn.csg.jobschedule.util.EsConnectionPool;
import cn.csg.jobschedule.util.IDUtil;
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

    @Value("${escluster.communicationalarm.index}")
    private String communicationalarmIndex;

    @Value("${escluster.communicationalarm.type}")
    private String communicationalarmType;

    /**
     * 根据告警规则名称获取告警统计规则
     * @param alarmName 告警规则名称
     * @return
     * @throws Exception
     */
    public List getAlarmRule(String alarmName) throws Exception{
        String sql = "select * from tb_explode te left join tb_rule tr on te.id = tr.rule where  name = '"+alarmName+"'";
        List dataList = jdbcTemplate.queryForList(sql);
        return dataList;
    }

    /**
     * 前往ES集群获取数据
     * @param queryStr
     * @return
     */
    public JSONObject getResultByHttp(String queryStr) throws Exception{
        return elasticsearchDao.requestMyTest(JSONObject.parseObject(queryStr));
    }

    //srcIp在t分钟内发起n次访问
    public void handleSrcIpSumData(JSONObject jsonObj){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map aggregationsMap = (Map)jsonObj.get("aggregations");
                    Map corpIdCountMap = (Map)aggregationsMap.get("corpIdCount");
                    List<Map> buckets = (List)corpIdCountMap.get("buckets");
                    if(buckets != null && buckets.size() > 0){
                        String date = DatetimeUtil.getFirstDayOfWeek(new Date(), DatetimeConstants.YYYYMMDD);
                        String index = communicationalarmIndex+date;
                        EsConnectionPool esConnectionPool = EsConnectionPool.getInstance(esClusterNodes,
                                Integer.parseInt(esClusterPort),esClusterName) ;
                        TransportClient client = esConnectionPool.getClient();
                        BulkRequestBuilder bulkRequest = client.prepareBulk();
                        Date createTime =  DatetimeUtil.toDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), DatetimeConstants.YYYY_MM_DD);
                        String createDateStr = DatetimeUtil.toStr(createTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX);

                        for(Map corpIdGroupMap : buckets){

                            String conrpId = corpIdGroupMap.get("key")+"";
                            Map srcIpCountMap = (Map)corpIdGroupMap.get("srcIpCount");
                            List<Map> sumResultBuckets = (List)srcIpCountMap.get("buckets");
                            if(sumResultBuckets != null && sumResultBuckets.size() > 0){
                                for(Map sumResultMap : sumResultBuckets){
                                    String srcIp = sumResultMap.get("key")+"";
                                    Map doubleCountSumMap = (Map)sumResultMap.get("doubleCountSum");
                                    Double countData = (Double) doubleCountSumMap.get("value");

                                    JSONObject countDataToEsJson = new JSONObject();
                                    String id = IDUtil.getUUID();
                                    countDataToEsJson.put("id", id);
                                    countDataToEsJson.put("conrpId",conrpId);
                                    countDataToEsJson.put("createTime", createDateStr);
                                    countDataToEsJson.put("srcIp",srcIp);
                                    countDataToEsJson.put("countData",countData);
                                    countDataToEsJson.put("alarmType","爆发式通信对告警");
                                    countDataToEsJson.put("alarmLevel","紧急");
                                    bulkRequest.add(client.prepareIndex(index, communicationalarmType, id).setSource(countDataToEsJson));
                                }
                            }
                        }
                        ESUtil.saveLastToES(bulkRequest) ;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("处理 srcIp在t分钟内发起n次访问 异常:"+e.getMessage());
                }

            }
        }).start();
    }

    //srcIp在t分钟内访问了n个destIp
    public void handleSrcIpAndDestIpCountData(JSONObject jsonObj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map aggregationsMap = (Map) jsonObj.get("aggregations");
                    Map corpIdCountMap = (Map) aggregationsMap.get("corpIdCount");
                    List<Map> buckets = (List) corpIdCountMap.get("buckets");
                    if (buckets != null && buckets.size() > 0) {
                        String date = DatetimeUtil.getFirstDayOfWeek(new Date(), DatetimeConstants.YYYYMMDD);
                        String index = communicationalarmIndex+date;
                        EsConnectionPool esConnectionPool = EsConnectionPool.getInstance(esClusterNodes,
                                Integer.parseInt(esClusterPort),esClusterName) ;
                        TransportClient client = esConnectionPool.getClient();
                        BulkRequestBuilder bulkRequest = client.prepareBulk();
                        Date createTime =  DatetimeUtil.toDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), DatetimeConstants.YYYY_MM_DD);
                        String createDateStr = DatetimeUtil.toStr(createTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX);

                        for (Map corpIdGroupMap : buckets) {
                            String conrpId = corpIdGroupMap.get("key") + "";
                            Map srcIpCountMap = (Map) corpIdGroupMap.get("srcIpCount");
                            List<Map> srcIpCountResultList = (List) srcIpCountMap.get("buckets");
                            if (srcIpCountResultList != null && srcIpCountResultList.size() > 0) {
                                for (Map srcIpCountResultMap : srcIpCountResultList) {
                                    String srcIp = srcIpCountResultMap.get("key") + "";
                                    Integer countData = (Integer) srcIpCountResultMap.get("doc_count");
                                    Map destIpCountMap = (Map) srcIpCountResultMap.get("destIpCount");
                                    List<Map> destIpCountList = (List) destIpCountMap.get("buckets");
                                    List destIpList = new ArrayList();
                                    for (Map destIpMap : destIpCountList) {
                                        destIpList.add(destIpMap.get("key"));
                                    }
//                            System.out.println("conrpId=" + conrpId + ";srcIp=" + srcIp + ";countData=" + countData + ";destIpList=" + destIpList);
                                    JSONObject countDataToEsJson = new JSONObject();
                                    String id = IDUtil.getUUID();
                                    countDataToEsJson.put("id", id);
                                    countDataToEsJson.put("conrpId",conrpId);
                                    countDataToEsJson.put("createTime", createDateStr);
                                    countDataToEsJson.put("srcIp",srcIp);
                                    countDataToEsJson.put("countData",countData);
                                    countDataToEsJson.put("destIpList",destIpList);
                                    countDataToEsJson.put("alarmType","爆发式通信对告警");
                                    countDataToEsJson.put("alarmLevel","紧急");
                                    bulkRequest.add(client.prepareIndex(index, communicationalarmType, id).setSource(countDataToEsJson));
                                }
                            }
                        }
                        ESUtil.saveLastToES(bulkRequest) ;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("处理 srcIp在t分钟内访问了n个destIp 异常:" + e.getMessage());
                }

            }
        }).start();
    }

    //srcIp在t分钟内访问destIp的n个端口
    public void handleSrcIpAndDestPortCountData(JSONObject jsonObj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map aggregationsMap = (Map) jsonObj.get("aggregations");
                    Map corpIdCountMap = (Map) aggregationsMap.get("corpIdCount");
                    List<Map> buckets = (List) corpIdCountMap.get("buckets");
                    if (buckets != null && buckets.size() > 0) {
                        String date = DatetimeUtil.getFirstDayOfWeek(new Date(), DatetimeConstants.YYYYMMDD);
                        String index = communicationalarmIndex+date;
                        EsConnectionPool esConnectionPool = EsConnectionPool.getInstance(esClusterNodes,
                                Integer.parseInt(esClusterPort),esClusterName) ;
                        TransportClient client = esConnectionPool.getClient();
                        BulkRequestBuilder bulkRequest = client.prepareBulk();
                        Date createTime =  DatetimeUtil.toDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), DatetimeConstants.YYYY_MM_DD);
                        String createDateStr = DatetimeUtil.toStr(createTime, DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_XXX);

                        for (Map corpIdGroupMap : buckets) {
                            String conrpId = corpIdGroupMap.get("key") + "";
                            Map srcIpCountMap = (Map) corpIdGroupMap.get("srcIpCount");
                            List<Map> srcIpCountResultList = (List) srcIpCountMap.get("buckets");
                            if (srcIpCountResultList != null && srcIpCountResultList.size() > 0) {
                                for (Map srcIpCountResultMap : srcIpCountResultList) {
                                    String srcIp = srcIpCountResultMap.get("key") + "";
                                    Map destIpCountMap = (Map) srcIpCountResultMap.get("destIpCount");
                                    List<Map> destIpCountBuckets = (List) destIpCountMap.get("buckets");
                                    if(destIpCountBuckets != null && destIpCountBuckets.size() > 0){
                                        for(Map destIpResultMap : destIpCountBuckets){
                                            String destIp = destIpResultMap.get("key")+"";
                                            Integer countData = (Integer)destIpResultMap.get("doc_count");
                                            Map destPortCount = (Map)destIpResultMap.get("destPortCount");
                                            List<Map> destPortBuckets = (List)destPortCount.get("buckets");
                                            List destPortList = new ArrayList();
                                            for(Map destPortMap : destPortBuckets){
                                                destPortList.add(destPortMap.get("key"));
                                            }
//                                            System.out.println("conrpId="+conrpId+";srcIp="+srcIp+";destIp="+destIp+";countData="+countData+";destPortList="+destPortList);
                                            JSONObject countDataToEsJson = new JSONObject();
                                            String id = IDUtil.getUUID();
                                            countDataToEsJson.put("id", id);
                                            countDataToEsJson.put("conrpId",conrpId);
                                            countDataToEsJson.put("createTime", createDateStr);
                                            countDataToEsJson.put("srcIp",srcIp);
                                            countDataToEsJson.put("countData",countData);
                                            countDataToEsJson.put("destIp",destIp);
                                            countDataToEsJson.put("destPortList",destPortList);
                                            countDataToEsJson.put("alarmType","爆发式通信对告警");
                                            countDataToEsJson.put("alarmLevel","紧急");
                                            bulkRequest.add(client.prepareIndex(index, communicationalarmType, id).setSource(countDataToEsJson));
                                        }
                                    }

                                }
                            }
                        }
                        ESUtil.saveLastToES(bulkRequest) ;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("处理 srcIp在t分钟内访问destIp的n个端口 异常:" + e.getMessage());
                }
            }
        }).start();
    }


    public String getMetadata(){
//        Client transportClient = elasticsearchTemplate.getClient();
//        IndexResponse response = transportClient.prepareIndex("metadata20190418","default").setSource()
//                .execute().actionGet();
//        System.out.println("从ES中查询的数据-----》"+response.toString());
//        transportClient.close();
        //判断索引是否存在 start
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(new String[]{"metadata20190418"});
        boolean flag = elasticsearchTemplate.getClient().admin().indices().exists(inExistsRequest).actionGet().isExists();
        System.out.println("索引是否存在flag----》"+flag);
        //判断索引是否存在 end
        SearchRequestBuilder requestBuilder =elasticsearchTemplate.getClient().prepareSearch("metadata20190418").setTypes("default");
        //过滤条件 start
        //过滤条件 end
        SearchResponse response = requestBuilder.execute().actionGet();

        return response.toString();
    }

}
