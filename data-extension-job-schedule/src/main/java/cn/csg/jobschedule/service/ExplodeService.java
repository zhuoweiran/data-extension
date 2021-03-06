package cn.csg.jobschedule.service;

import cn.csg.common.enums.ExplodeType;
import cn.csg.common.vo.ExplodeVo;
import cn.csg.common.vo.RuleVo;
import cn.csg.jobschedule.dao.ExplodeDao;
import cn.csg.jobschedule.dao.RuleDao;
import org.quartz.SchedulerException;
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

    @Autowired
    private ExplodeJobService jobService;



    public List<ExplodeVo> findAll(){
        return explodeDao.findAll();
    }

    public ExplodeVo save(ExplodeVo explodeVo)  {
        List<RuleVo> ruleVos = explodeVo.getRuleVos();
        logger.debug("保存ExplodeVo:{}",explodeVo);

        for(RuleVo ruleVo : ruleVos){
            ruleVo.setId(ruleDao.findFirstByKeyAndRule(ruleVo.getKey(),explodeVo.getId()));
        }

        ruleDao.saveAll(ruleVos);
        try {
            jobService.updateJob(explodeVo);
        } catch (SchedulerException e) {
            logger.error("更新调度失败[{}],[job_{}]", explodeVo.getName(), explodeVo.getId());
            e.printStackTrace();
        }
        return explodeVo;
    }

    public ExplodeVo findById(String id){
        return explodeDao.getOne(id);
    }
}
