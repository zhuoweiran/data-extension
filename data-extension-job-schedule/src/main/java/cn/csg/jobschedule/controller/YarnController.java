package cn.csg.jobschedule.controller;

import cn.csg.jobschedule.service.YarnClusterService;
import com.alibaba.fastjson.JSONObject;
import org.codehaus.jackson.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/info")
public class YarnController {
    @Autowired
    private YarnClusterService yarnService;

    @GetMapping(value = "/getInfo")
    public JSONObject getInfo(){

        try{
            return yarnService.requestClusterInfo();
        }catch (JsonParseException e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/getMetrics" )
    public JSONObject getMetrics(){
        try{
            return yarnService.requestClusterMetrics();
        }catch (JsonParseException e){
            e.printStackTrace();
            return null;
        }
    }
}
