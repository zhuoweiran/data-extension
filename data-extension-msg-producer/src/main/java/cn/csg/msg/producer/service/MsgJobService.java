package cn.csg.msg.producer.service;

import cn.csg.msg.producer.bean.MsgJob;
import cn.csg.msg.producer.dao.MsgJobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgJobService {
    @Autowired
    private MsgJobDao msgJobDao;
    public List<MsgJob> list(){
        return msgJobDao.findAll();
    }

    public List<MsgJob> enableList(){
        return msgJobDao.findAllByStatus(true);
    }

    public MsgJob save(MsgJob msgJob){
        return msgJobDao.save(msgJob);
    }

    public MsgJob findById(String id){
        return msgJobDao.getOne(id);
    }

    public void delete(MsgJob msgJob){
        msgJobDao.delete(msgJob);
    }

}
