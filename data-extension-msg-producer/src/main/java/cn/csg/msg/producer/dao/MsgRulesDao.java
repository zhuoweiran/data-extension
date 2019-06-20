package cn.csg.msg.producer.dao;

import cn.csg.msg.producer.bean.MsgRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MsgRulesDao extends JpaRepository<MsgRules, String> {
    List<MsgRules> findAllByJobId(String jobId);
}
