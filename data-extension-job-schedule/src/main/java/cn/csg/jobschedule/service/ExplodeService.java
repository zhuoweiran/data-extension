package cn.csg.jobschedule.service;

import cn.csg.common.enums.ExplodeType;
import cn.csg.common.vo.ExplodeVo;
import cn.csg.common.vo.RuleVo;
import cn.csg.jobschedule.dao.ExplodeDao;
import cn.csg.jobschedule.dao.RuleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExplodeService {
    private final Logger logger = LoggerFactory.getLogger(ExplodeType.class);

    @Autowired
    private ExplodeDao explodeDao;

    @Autowired
    private RuleDao ruleDao;



    public List<ExplodeVo> findAll(){
        return explodeDao.findAll();
    }

    public ExplodeVo save(ExplodeVo explodeVo){
        List<RuleVo> ruleVos = explodeVo.getRuleVos();
        logger.debug("保存ExplodeVo:{}",explodeVo);

        for(RuleVo ruleVo : ruleVos){
            ruleVo.setId(ruleDao.findFirstByKeyAndRule(ruleVo.getKey(),explodeVo.getId()));
            //修改trigger

            /*if(ruleVo.getKey().equals(RuleKeyEnum.WINDOWN.getKey())) {
                try {
                    jobTriggerService.scheduleUpdateTrigger(
                            ExplodeType.initExplodeType(explodeVo.getName()),
                            ruleVo.getValue().longValue()
                    );
                } catch (SchedulerException e) {
                    e.printStackTrace();
                    logger.error("修改trigger {} 失败",explodeVo.getName());
                }
            }*/
        }
        ruleDao.saveAll(ruleVos);
        return explodeVo;
    }
}
