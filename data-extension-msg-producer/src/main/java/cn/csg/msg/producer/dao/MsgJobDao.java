package cn.csg.msg.producer.dao;

import cn.csg.msg.producer.bean.MsgJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsgJobDao extends JpaRepository<MsgJob, String> {
    List<MsgJob> findAllByStatus(boolean status);
}
