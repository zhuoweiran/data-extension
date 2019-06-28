package cn.csg.msg.producer.service;

import cn.csg.msg.producer.bean.MsgJob;
import cn.csg.msg.producer.dao.MsgJobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类{@code MsgJobService} 任务数据访问对象
 *
 * <p>用于任务的增删改查
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */
@Service
public class MsgJobService {
    @Autowired
    private MsgJobDao msgJobDao;

    /**
     * 查询所有任务
     * @return {@code List<MsgJob>}
     */
    public List<MsgJob> list(){
        return msgJobDao.findAll();
    }

    /**
     * 查询正在运行的任务
     * @return {@code List<MsgJob>}
     */
    public List<MsgJob> enableList(){
        return msgJobDao.findAllByStatus(true);
    }

    /**
     * 保存任务
     * @param msgJob 任务
     * @return {@code MsgJob}
     */
    public MsgJob save(MsgJob msgJob){
        return msgJobDao.save(msgJob);
    }

    /**
     * 根据任务id查询任务
     * @param id 任务id
     * @return
     */
    public MsgJob findById(String id){
        return msgJobDao.getOne(id);
    }

    /**
     * 删除任务
     * @param msgJob 任务
     */
    public void delete(MsgJob msgJob){
        msgJobDao.delete(msgJob);
    }

}
