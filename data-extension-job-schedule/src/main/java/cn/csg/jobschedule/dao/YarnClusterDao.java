package cn.csg.jobschedule.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "cluster" ,url = "zdbd01:8088/ws/v1/cluster")
public interface YarnClusterDao {
    @RequestMapping(method = RequestMethod.GET ,value = "/info")
    String requestClusterInfo();

    @RequestMapping(method = RequestMethod.GET ,value = "/metrics")
    String requestClusterMetrics();
}
