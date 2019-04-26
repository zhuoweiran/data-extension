package cn.csg.jobschedule.dao;

import cn.csg.common.vo.ExplodeVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExplodeDao extends JpaRepository<ExplodeVo,String> {
    @Override
    List<ExplodeVo> findAll();
}
