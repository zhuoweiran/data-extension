package cn.csg.codis.server.controller;

import cn.csg.codis.server.handler.CodisHandler;
import cn.csg.common.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


/**
 * 类{@code CodisController}控制器
 * <p>提供restful服务控制
 *
 * @author Alex Han
 * @version 1.2
 */
@RestController
@RequestMapping("/codis")
public class CodisController {

    @Autowired
    private CodisHandler codisHandler;

    /**
     * 通过guid查询device
     * HttpMethod: Post
     * URL: /coids/find/{collect}
     *
     * @param guid 唯一id的key
     * @param collect 类型
     * @return ResultData
     */
    @PostMapping("/find/{collect}")
    public Mono<ResultData> findByGuid(
            @PathVariable(name = "collect") String collect,
            @RequestParam(name = "key") String guid){

        return codisHandler.findByGuid(collect, guid);
    }

    /**
     * 更新多个device
     * HttpMethod: Post
     * URL: /codis/update
     *
     * @param collect 集合名称
     * @param listStr device list json string
     * @return ResultData
     */
    @PostMapping("/update/{collect}")
    public Mono<ResultData> update(
            @PathVariable(name = "collect") String collect,
            @RequestParam(name = "list") String listStr
    ){
        return codisHandler.updateDevices(collect ,listStr);
    }

    /**
     * 删除一个key
     * @param collect 集合名称
     * @param key key
     * @return ResultData
     */
    @PostMapping("/delete/{collect}")
    public Mono<ResultData> deleteBykey(
            @PathVariable(name = "collect") String collect,
            @RequestParam(name = "key") String key
    ){
        return codisHandler.deleteByGuid(collect ,key);
    }

    /**
     * 查看连接池状态
     * HttpMethod: Get
     * URL: /codis/status
     *
     * @return ResultData
     */
    @GetMapping("/status")
    public Mono<ResultData> showStatus(){
        return codisHandler.showStatus();
    }

}
