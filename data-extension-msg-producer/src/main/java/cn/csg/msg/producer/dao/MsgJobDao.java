package cn.csg.msg.producer.dao;

import cn.csg.msg.producer.bean.MsgJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 类{@code MsgJobDao} 任务查询数据库接口
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */
public interface MsgJobDao extends JpaRepository<MsgJob, String> {
    List<MsgJob> findAllByStatus(boolean status);
}
