package mytest;

import cn.csg.jobschedule.constants.EventConstants;
import cn.csg.jobschedule.constants.EventTypeAndTag;
import cn.csg.jobschedule.constants.SymbolsConstants;
import cn.csg.jobschedule.util.CommonUtil;
import cn.csg.jobschedule.util.DeviceUtils;
import cn.csg.jobschedule.util.JedisClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeviceTest {

    public static void main(String[] args) throws Exception{

        //初始化参数
        EventTypeAndTag.init();

        //资产GUID
        String guid = "00000102-b9a9f6c8-b781-494f-adc2-20cbaf85c4e7";
        //添加资产信息
        JedisClient jedisClient = new JedisClient();
        String deviceBean = jedisClient.hget(Integer.parseInt("0"), "deviceInfoGuidbd", guid);
        System.out.println("deviceBean===========>>>>>"+deviceBean);
        Map<String, Object> map = new HashMap();
        deviceStrAddToMap(map,  deviceBean);
        System.out.println("map====>"+map);

        //获取corpId
        int idx = guid.indexOf(SymbolsConstants.HorizontalBar);
        String corpId = guid.substring(0, idx);
        map.put("corpId", corpId);

        //添加组织机构信息
        String corporationBean = jedisClient.hget(EventTypeAndTag.REDISDBINDEX, EventTypeAndTag.ZZCORPORATIONKEY, corpId);
        System.out.println("corporationBean====>"+corporationBean);
        DeviceUtils.corpAddToMap(map, corporationBean);
        System.out.println("map====>"+map);


        //构建数据格式
//        String DcdGuid = "";
//        String DevGuid = "";
//        String Sid = "";
//        String Timestamp = "";
//        String AppName = "";
//        String EventType = "01";
//        String FunClassTag = "Comm-expl";
//        String DiscoverTime = "";
//        String ExplType = "";
//        String ClientIp = "";
//        String ServerIp = "";
//        String Threshold = "";
//        String Cycle = "";
//        String Partition = "";
//
//        //guid即是DevGuid，也是资产ID
//        String alarm = "{\"Header\":{\"DcdGuid\":\"0000-a2a60498-07f9-4101-ae4b-24feb863413f \",\"DevGuid\":\"\",\"Sid\":\"xxxxx\",\"Timestamp\":\"123213232111\"},\"Data\":{\"AppName\":\"\",\"EventType \":\"01\",\"FunClassTag\":\" Comm-expl\",\"DiscoverTime\":\"2019-02-03 12:12:12\",\"Details\":{\"ExplType\":\" 1\", \"ClientIp\":\"10.50.33.33\",\"ServerIp\":\"10.50.33.34\",\"Threshold\":\"30\",\"Cycle\":\"1\",\"Partition\":\"II\",}}}";
//        System.out.println(JSON.parseObject(alarm));
//        System.out.println("00000102-0edde6c5-18e9-4dd6-847c-f7ddf9dcbe22".equals("00000102-0edde6c5-18e9-4dd6-847c-f7ddf9dcbe22"));
    }

    /**
     * 将已知资产信息数据重新组装入ES
     * @param map
     * @param deviceBean
     */
    public static void deviceStrAddToMap(Map<String, Object> map, String deviceBean) {
        JSONObject jsonObject = JSONObject.parseObject(deviceBean);
        map.put("deviceName", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_NAME)));
        map.put("ip", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_IP)));
        map.put("securityPartition", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_AREA)));
        map.put("speciality", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_BELONGEDMAJOR)));
        map.put("deviceType", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_DEVICECODE)));
        map.put("os", CommonUtil.changeNull(jsonObject.get(EventConstants.EVENT_OS)));
    }
}
