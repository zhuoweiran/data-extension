package cn.csg.msg.producer.controller;

import cn.csg.msg.producer.service.MsgJobService;
import cn.csg.msg.producer.service.MsgRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private MsgJobService msgJobService;
    @Autowired
    private MsgRulesService msgRulesService;

    @RequestMapping("/test")
    public String test(ModelMap map) {
        map.addAttribute("host","http://blog.inverseli.com");
        return "test";
    }

    @RequestMapping("/index")
    public String index(ModelMap map) {
        map.addAttribute("tasks",msgJobService.list());
        map.addAttribute("rules",msgRulesService.list());
        return "index";
    }
    @RequestMapping("/rule/{jobId}")
    public String rule(@PathVariable(name = "jobId") String jobId, ModelMap map) {
        map.addAttribute("rules",msgRulesService.findAllByJobId(jobId));
        map.addAttribute("jobId",jobId);
        return "rule";
    }

}
