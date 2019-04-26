package cn.csg.jobschedule.service;

import cn.csg.common.vo.ExplodeVo;
import cn.csg.jobschedule.dao.ExplodeDao;
import cn.csg.jobschedule.dao.RuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        ruleDao.saveAll(
            explodeVo.getRuleVos().stream().map(
                    ruleVo -> {
                        ruleVo.setId(ruleDao.findFirstByKey(ruleVo.getKey()).getId());
                        return ruleVo;
                    }
            ).collect(Collectors.toList())
        );

        return explodeVo;
    }

}
