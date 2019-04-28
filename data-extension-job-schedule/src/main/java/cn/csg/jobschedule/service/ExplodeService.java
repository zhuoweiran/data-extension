package cn.csg.jobschedule.service;

import cn.csg.common.enums.ExplodeType;
import cn.csg.common.enums.RuleKeyEnum;
import cn.csg.common.vo.ExplodeVo;
import cn.csg.common.vo.RuleVo;
import cn.csg.jobschedule.dao.ExplodeDao;
import cn.csg.jobschedule.dao.RuleDao;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExplodeService {
    @Autowired
    private ExplodeDao explodeDao;

    @Autowired
    private RuleDao ruleDao;

    @Autowired
    private JobTriggerService jobTriggerService;


    public List<ExplodeVo> findAll(){
        return explodeDao.findAll();
    }

    public ExplodeVo save(ExplodeVo explodeVo){
        List<RuleVo> ruleVos = explodeVo.getRuleVos();

        for(RuleVo ruleVo : ruleVos){
            ruleVo.setId(ruleDao.findFirstByKeyAndRule(ruleVo.getKey(),explodeVo.getId()));
            //修改trigger
            if(ruleVo.getKey().equals(RuleKeyEnum.WINDOWN.getKey())) {
                try {
                    jobTriggerService.scheduleUpdateTrigger(
                            ExplodeType.initExplodeType(explodeVo.getName()),
                            ruleVo.getValue().longValue()
                    );
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
        ruleDao.saveAll(ruleVos);
        return explodeVo;
    }
}
