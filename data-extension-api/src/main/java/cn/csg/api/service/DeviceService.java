package cn.csg.api.service;

import cn.csg.api.dao.DeviceRepository;
import cn.csg.common.vo.Device;
import cn.csg.core.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService extends BaseService {
    @Autowired
    private DeviceRepository deviceRepository;


    public Device findByCorpId(String cropId){


        return deviceRepository.findByCorpId(cropId);
    }

}
