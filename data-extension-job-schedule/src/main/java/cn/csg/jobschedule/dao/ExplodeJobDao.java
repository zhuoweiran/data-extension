package cn.csg.jobschedule.dao;

import cn.csg.common.vo.ExplodeJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExplodeJobDao extends JpaRepository<ExplodeJob, String> {
    ExplodeJob findFirstByExplodeId(String explodeId);
}
