package cn.csg.codis.server.controller;

import cn.csg.codis.server.domain.DeviceBean;
import cn.csg.codis.server.handler.CodisHandler;
import cn.csg.common.ResultData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 类{@code CodisController}控制器
 * <p>提供restful服务控制
 *
 * @author Alex Han
 * @version 1.0
 */
@RestController
@RequestMapping("/codis")
public class CodisController {

    @Autowired
    private CodisHandler codisHandler;


    /**
     * 通过guid查询device
     * HttpMethod: Get
     * URL: /coids/find/{guid}
     *
     * @param guid 唯一id的key
     * @return Mono<DeviceBean>
     */
    @GetMapping("/find/{guid}")
    public Mono<ResultData> findDeviceByGuid(@PathVariable(name = "guid") String guid){

        return codisHandler.findByGuid(guid);
    }

    /**
     * 更新多个device
     * HttpMethod: Post
     * URL: /codis/update
     *
     * @param listStr device list json string
     * @return Mono<ResultData>
     */
    @PostMapping("/update")
    public Mono<ResultData> updateDevices(
            @RequestParam(name = "list") String listStr
    ){
        List<DeviceBean> list = null;
        if(listStr.startsWith("[")) {
            list = JSONArray.parseArray(listStr, DeviceBean.class);
            return codisHandler.updateDevices(list);
        }else if(listStr.startsWith("{")){
            list = Arrays.asList(JSONObject.parseObject(listStr, DeviceBean.class));
        }
        return codisHandler.updateDevices(list);
    }

    /**
     * 查看连接池状态
     * HttpMethod: Get
     * URL: /codis/status
     *
     * @return Mono<ResultData>
     */
    @GetMapping("/status")
    public Mono<ResultData> showStatus(){
        return codisHandler.showStatus();
    }
}
