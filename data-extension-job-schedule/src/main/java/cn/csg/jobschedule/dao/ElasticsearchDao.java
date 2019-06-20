package cn.csg.jobschedule.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${feign.name}" ,url = "${feign.url}")
public interface ElasticsearchDao {

    @RequestMapping(method = RequestMethod.POST ,value = "/commpair_alarm/_search")
    JSONObject getResultsetByQueryJson(JSONObject parmJsonObj);

    @RequestMapping(method = RequestMethod.PUT ,value = "/{indexTail}/default/{id}")
    JSONObject save(
            @PathVariable(name = "indexTail") String indexTail,
            @PathVariable(name = "id") String id,
            JSONObject parmJsonObj);
}
