package mytest;

import cn.csg.jobschedule.util.ESUtil;
import cn.csg.jobschedule.util.EsConnectionPool;
import cn.csg.jobschedule.util.IDUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {

    public static void main(String[] args) throws Exception{
        List<Integer> corpIdList = new ArrayList<Integer>();
        for(int i = 1;i<10;i++){
            corpIdList.add(i);
        }

        List<Integer> dataList = new ArrayList<Integer>();
        for(int i = 1;i<100;i++){
            dataList.add(i);
        }
        List<Integer> portList = new ArrayList<Integer>();
        for(int i = 5000;i<6000;i++){
            portList.add(i);
        }
        List<Integer> timeList = new ArrayList<Integer>();
        for(int i = 10;i<60;i++){
            timeList.add(i);
        }
        String index = "hjwindex_test";
        String type = "default";
        EsConnectionPool esConnectionPool = EsConnectionPool.getInstance("zdbd01,zdbd02,zdbd03",
                Integer.parseInt("9300"),"es_nfdw") ;
        TransportClient client = esConnectionPool.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for(int i =0;i<1000;i++){

            //获取随机数据下标
            int srcNum = (int)(Math.random()*(dataList.size()));
            int destNum = (int)(Math.random()*(dataList.size()));
            int srcPortNum = (int)(Math.random()*(portList.size()));
            int destPortNum = (int)(Math.random()*(portList.size()));
            int timeNum = (int)(Math.random()*(timeList.size()));
            int corpIdNum = (int)(Math.random()*(corpIdList.size()));

            Map<String,Object> contentMap = new HashMap<String,Object>();
            String id = IDUtil.getUUID();
            contentMap.put("id", id) ;
            contentMap.put("doubleCount", 10) ;
            contentMap.put("destIp", "227.126.65."+dataList.get(destNum));
            contentMap.put("srcIp", "172.16.140."+dataList.get(srcNum));
            contentMap.put("corpId", dataList.get(corpIdNum));
            contentMap.put("createTime", "2019-04-24T15:"+timeList.get(timeNum)+":36+08:00");
            contentMap.put("srcPort", portList.get(srcPortNum));
            contentMap.put("destPort", portList.get(destPortNum));
            System.out.println("i---------->>>>>"+i+"#"+contentMap);
            bulkRequest.add(client.prepareIndex(index, type, id).setSource(contentMap));
        }
        ESUtil.saveLastToES(bulkRequest) ;
        System.out.println("-------------ok-----------");
    }
}
