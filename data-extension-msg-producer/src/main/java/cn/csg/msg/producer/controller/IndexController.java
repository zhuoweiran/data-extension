package cn.csg.msg.producer.controller;

import cn.csg.msg.producer.service.MsgJobService;
import cn.csg.msg.producer.service.MsgRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>类{@code IndexController} 页面跳转控制
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */
@Controller
public class IndexController {
    @Autowired
    private MsgJobService msgJobService;
    @Autowired
    private MsgRulesService msgRulesService;

    /**
     * 跳转到/test
     * @param map 返回任务列表和参数列表
     * @return 跳转到test页面
     */
    @RequestMapping("/test")
    public String test(ModelMap map) {
        map.addAttribute("host","http://blog.inverseli.com");
        return "test";
    }

    /**
     * 跳转到/index
     * @param map 返回任务列表和参数列表
     * @return 跳转到/index
     */
    @RequestMapping({"/","/index"})
    public String index(ModelMap map) {
        map.addAttribute("tasks",msgJobService.list());
        map.addAttribute("rules",msgRulesService.list());
        return "index";
    }

    /**
     * 跳转到/rule
     * @param jobId 任务id
     * @param map 返回任务列表和参数列表
     * @return 跳转到/rule
     */
    @RequestMapping("/rule/{jobId}")
    public String rule(@PathVariable(name = "jobId") String jobId, ModelMap map) {
        map.addAttribute("rules",msgRulesService.findAllByJobId(jobId));
        map.addAttribute("jobId",jobId);
        return "rule";
    }

}
