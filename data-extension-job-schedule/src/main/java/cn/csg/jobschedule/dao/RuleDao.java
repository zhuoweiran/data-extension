package cn.csg.jobschedule.dao;

import cn.csg.common.vo.RuleVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RuleDao extends JpaRepository<RuleVo,String> {
    @Query(value = "select id from tb_rule where rule_key=?1 and rule=?2" , nativeQuery = true)
    String findFirstByKeyAndRule(String key,String rule);
}
