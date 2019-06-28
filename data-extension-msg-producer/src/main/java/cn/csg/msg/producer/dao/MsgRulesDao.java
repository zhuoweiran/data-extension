package cn.csg.msg.producer.dao;

import cn.csg.msg.producer.bean.MsgRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 类{@code MsgRulesDao} 任务参数查询数据库接口
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */
public interface MsgRulesDao extends JpaRepository<MsgRules, String> {
    List<MsgRules> findAllByJobId(String jobId);
}
