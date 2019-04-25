package cn.csg.api.dao;


import cn.csg.common.vo.Device;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface DeviceRepository extends ElasticsearchRepository<Device, String> {
    Device findByCorpId(String corpId);
}
