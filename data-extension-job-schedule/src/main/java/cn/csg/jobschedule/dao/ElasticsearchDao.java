package cn.csg.jobschedule.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${feign.name}" ,url = "${feign.url}")
public interface ElasticsearchDao {

    @RequestMapping(method = RequestMethod.POST ,value = "/_search")
    JSONObject requestMyTest(JSONObject parmJsonObj);
}
