package cn.csg.jobschedule.util;

import cn.csg.common.vo.*;
import cn.csg.jobschedule.constants.EventConstants;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @Author: tide
 * @Description:
 * @Date: Created in 17:58 2019/4/18
 * @Modified By:
 */
public class DeviceUtils {


    /**
     * 将组织机构信息数据重新组装入ES
     * @param map
     * @param corporation
     */
    public static void corpAddToMap(Map<String, Object> map, Corporation corporation) {
        if(corporation != null) {
            map.put("corpName", corporation.getCorpname());
            map.put("firm", corporation.getOrgName());
            map.put("transferUnit", corporation.getDgCorpName());
            map.put("unit", corporation.getUnitAbbreviation());
        }
    }


    /**
     * 将已知资产信息数据重新组装入ES
     * @param map
     * @param device
     */
    public static void deviceStrAddToMap(Map<String, Object> map, DeviceRedis device) {
        if(device != null) {
            map.put("deviceName", device.getName());
            map.put("ip", device.getIp());
            map.put("securityPartition", device.getArea());
            map.put("speciality", device.getBelongedMajor());
            map.put("deviceType", device.getDevicecode());
            map.put("os", device.getSystemVersion());
        }
    }

    /**
     * 将未知资产信息数据重新组装入ES
     * @param map
     * @param deviceTmp
     */
    public static void deviceTmpBeanAddToMap(Map<String, Object> map, DeviceTmpRedis deviceTmp) {
        if(deviceTmp != null) {
            map.put("deviceName", deviceTmp.getDevicename());
            map.put("ip", deviceTmp.getIp());
            map.put("securityPartition", deviceTmp.getPartition());
            map.put("speciality", deviceTmp.getBelongedMajor());
            map.put("deviceType", deviceTmp.getDevicecode());
        }
    }

}
