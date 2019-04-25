package cn.csg.jobschedule.util;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/***
 * @Author: xushiyong
 * @Description
 * @Date: Created in 21:05 2018/11/6
 * @Modify By:
 **/
public class ESUtil implements Serializable{
    private static final long serialVersionUID = -489219421303458595L;
    private final static Logger logger = LoggerFactory.getLogger(ESUtil.class);
    private static final long bulkActions=2000 ;

    public static void saveRddToES(String index, String type, String id, Map<String, Object> esMap, TransportClient client, BulkRequestBuilder bulkRequest, long count){
        bulkRequest.add(client.prepareIndex(index, type, id).setSource(esMap));
        if (count % bulkActions == 0) {
            if (bulkRequest.numberOfActions() > 0) {
                BulkResponse bulkResponse = bulkRequest.get();
                if (bulkResponse.hasFailures()) {
                    logger.error("error in doing bulk request: " + bulkResponse.buildFailureMessage());
                }
            }
            bulkRequest = client.prepareBulk();
        }
        count += 1;
    }

    /**
     * 批量入库缓存，并根据阀值触发入库操作
     * @param client
     * @param bulkRequest
     * @param count
     * @param props
     * @param logger
     * @return
     */
    public static BulkRequestBuilder getBulkRequestBuilder(TransportClient client, BulkRequestBuilder bulkRequest, long count, Properties props, Logger logger) {
        if (count % Integer.parseInt(props.getProperty("es.bulkActions")) == 0) {
            if (bulkRequest.numberOfActions() > 0) {
                BulkResponse bulkResponse = bulkRequest.get();
                if (bulkResponse.hasFailures()) {
                    logger.error("error in doing bulk request: " + bulkResponse.buildFailureMessage());
                }
            }
            bulkRequest = client.prepareBulk();
        }
        return bulkRequest;
    }

    /**
     * 保存到ES
     * @param bulkRequest
     */
    public static void saveLastToES(BulkRequestBuilder bulkRequest){
        if (bulkRequest.numberOfActions() > 0) {
            BulkResponse bulkResponse = bulkRequest.get();
            if (bulkResponse.hasFailures()) {
                logger.error("error in doing bulk request: " + bulkResponse.buildFailureMessage());
            }
        }
    }


}
