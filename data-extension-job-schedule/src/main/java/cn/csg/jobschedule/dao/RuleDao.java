package cn.csg.jobschedule.dao;

import cn.csg.common.vo.RuleVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleDao extends JpaRepository<RuleVo,String> {
    RuleVo findFirstByKey(String key);
}
