package cn.csg.jobschedule.service;


import cn.csg.jobschedule.dao.YarnClusterDao;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.codehaus.jackson.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YarnClusterService {
    @Autowired
    private YarnClusterDao yarnClusterDao;

    public JSONObject requestClusterInfo() throws JsonParseException {
        return JSON.parseObject(yarnClusterDao.requestClusterInfo());
    }

    public JSONObject requestClusterMetrics() throws JsonParseException {
        return JSON.parseObject(yarnClusterDao.requestClusterMetrics());
    }
}
