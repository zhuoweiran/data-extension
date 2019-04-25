package cn.csg.api.controller;

import cn.csg.api.service.DeviceService;
import cn.csg.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @GetMapping("/test/{cropid}")
    public Result test(@PathVariable("cropid") String cropId){
        Result result = new Result();
        result.setCode("100");
        result.setMsg("success");
        result.setBaseVo(deviceService.findByCorpId(cropId));
        return result;
    }
}
