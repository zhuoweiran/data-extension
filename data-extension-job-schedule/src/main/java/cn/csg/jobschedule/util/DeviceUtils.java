package cn.csg.jobschedule.util;

import cn.csg.common.vo.CorporationBean;
import cn.csg.common.vo.DbDeviceTmpBean;
import cn.csg.common.vo.DeviceBean;
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
     * @param corporationBean
     */
    public static void corpBeanAddToMap(Map<String, Object> map, CorporationBean corporationBean) {
        if (corporationBean != null) {
            map.put("corpName", CommonUtil.changeNull(corporationBean.getCorpname()));
            map.put("firm", CommonUtil.changeNull(corporationBean.getOrgName()));
            map.put("transferUnit", CommonUtil.changeNull(corporationBean.getDgCorpName()));
//            map.put("speciality", CommonUtil.changeNull(corporationBean.getGsCorpName()));
            map.put("unit", CommonUtil.changeNull(corporationBean.getUnitAbbreviation()));
        }
    }

    /**
     * 将组织机构信息数据重新组装入ES
     * @param map
     * @param corporationBean
     */
    public static void corpAddToMap(Map<String, Object> map, String corporationBean) {
        if (corporationBean != null && corporationBean.indexOf("{") != -1) {
            JSONObject jsonObject = JSONObject.parseObject(corporationBean);
            map.put("corpName", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_CORPNAME)));
            map.put("firm", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_ORGNAME)));
            map.put("transferUnit", CommonUtil.changeNull(jsonObject.get(EventConstants.EVNET_DGCORPNAME)));
            map.put("unit", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_UNIT)));
        }
    }

    /**
     * 将已知资产信息数据重新组装入ES
     * @param map
     * @param deviceBean
     */
    public static void deviceBeanAddToMap(Map<String, Object> map, DeviceBean deviceBean) {
        map.put("deviceName", CommonUtil.changeNull(deviceBean.getName()));
        map.put("ip", CommonUtil.changeNull(deviceBean.getIp()));
        map.put("securityPartition", CommonUtil.changeNull(deviceBean.getArea()));
        map.put("speciality", CommonUtil.changeNull(deviceBean.getBelongedMajor()));
        map.put("deviceType", CommonUtil.changeNull(deviceBean.getDevicecode()));
    }

    /**
     * 将已知资产信息数据重新组装入ES
     * @param map
     * @param deviceBean
     */
    public static void deviceStrAddToMap(Map<String, Object> map, String deviceBean) {
        if(deviceBean != null && deviceBean.indexOf("{") != -1){
            JSONObject jsonObject = JSONObject.parseObject(deviceBean);
            map.put("deviceName", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_NAME)));
            map.put("ip", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_IP)));
            map.put("securityPartition", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_AREA)));
            map.put("speciality", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_BELONGEDMAJOR)));
            map.put("deviceType", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_DEVICECODE)));
            map.put("os", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_OS)));
        }
    }

    /**
     * 将未知资产信息数据重新组装入ES
     * @param map
     * @param dbDeviceTmpBean
     */
    public static void deviceTmpBeanAddToMap(Map<String, Object> map, DbDeviceTmpBean dbDeviceTmpBean) {
        if (dbDeviceTmpBean != null) {
            map.put("deviceName", CommonUtil.changeNull(dbDeviceTmpBean.getDevicename()));
            map.put("ip", CommonUtil.changeNull(dbDeviceTmpBean.getIp()));
            map.put("securityPartition", CommonUtil.changeNull(dbDeviceTmpBean.getPartition()));
            map.put("speciality", CommonUtil.changeNull(dbDeviceTmpBean.getBelongedMajor()));
            map.put("deviceType", CommonUtil.changeNull(dbDeviceTmpBean.getDevicecode()));
        }
    }

    /**
     * 将未知资产信息数据重新组装入ES
     * @param map
     * @param dbDeviceTmpBean
     */
    public static void deviceTmpAddToMap(Map<String, Object> map, String dbDeviceTmpBean) {
        if (dbDeviceTmpBean != null) {
            JSONObject jsonObject = JSONObject.parseObject(dbDeviceTmpBean);
            map.put("deviceName", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_DEVICENAME)));
            map.put("ip", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_IP)));
            map.put("securityPartition", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_PARTITION)));
            map.put("speciality", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_BELONGEDMAJOR)));
            map.put("deviceType", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_DEVICECODE)));
            map.put("os", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_OS)));
        }
    }
}
