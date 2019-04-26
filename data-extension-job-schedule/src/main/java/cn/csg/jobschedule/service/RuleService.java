package cn.csg.jobschedule.service;

import cn.csg.common.vo.RuleVo;
import cn.csg.jobschedule.dao.RuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {
    @Autowired
    private RuleDao ruleDao;

    RuleVo findFirstByKey(String key){
        return ruleDao.findFirstByKey(key);
    }
}
