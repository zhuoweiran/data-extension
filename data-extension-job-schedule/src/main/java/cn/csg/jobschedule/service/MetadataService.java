package cn.csg.jobschedule.service;

import cn.csg.jobschedule.dao.ElasticsearchDao;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

@Service
public class MetadataService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchDao elasticsearchDao;

    public JSONObject getMetadataByHttp(String queryStr){
        return elasticsearchDao.requestMyTest(JSONObject.parseObject(queryStr));
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
