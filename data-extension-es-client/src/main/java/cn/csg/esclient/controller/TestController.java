package cn.csg.esclient.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {


    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("host","http://blog.inverseli.com");
        return "index";
    }
}
