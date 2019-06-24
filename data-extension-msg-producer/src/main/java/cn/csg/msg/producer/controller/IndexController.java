package cn.csg.msg.producer.controller;

import cn.csg.msg.producer.service.MsgJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private MsgJobService msgJobService;

    @RequestMapping("/test")
    public String test(ModelMap map) {
        map.addAttribute("host","http://blog.inverseli.com");
        return "test";
    }

    @RequestMapping("/index")
    public String index(ModelMap map) {
        map.addAttribute("tasks",msgJobService.list());
        return "index";
    }
}
