package cn.csg.esclient.controller;

import cn.csg.common.vo.Corporation;
import cn.csg.esclient.dao.CorporationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CorporationDao corporationDao;

    @GetMapping("/crop/{id}")
    public Corporation getCorporationByCropId(@PathVariable("id") String cropId){
        return corporationDao.findByCropId(cropId);
    }
}
