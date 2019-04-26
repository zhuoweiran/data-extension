package cn.csg.jobschedule.service;

import cn.csg.common.vo.ExplodeVo;
import cn.csg.jobschedule.dao.ExplodeDao;
import cn.csg.jobschedule.dao.RuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExplodeService {
    @Autowired
    private ExplodeDao explodeDao;

    @Autowired
    private RuleDao ruleDao;


    public List<ExplodeVo> findAll(){
        return explodeDao.findAll();
    }

    public ExplodeVo save(ExplodeVo explodeVo){
        explodeDao.save(explodeVo);
        ruleDao.saveAll(explodeVo.getRuleVos());

        return explodeVo;
    }

}
