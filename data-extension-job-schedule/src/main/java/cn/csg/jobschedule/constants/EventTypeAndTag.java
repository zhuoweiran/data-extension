package cn.csg.jobschedule.constants;


import cn.csg.jobschedule.util.ResourceUtil;

import java.util.Properties;

/**
 * @Author: tide
 * @Description:
 * @Date: Created in 15:45 2019/5/6
 * @Modified By:
 */
public class EventTypeAndTag {

    private static Properties props;

    /**
     * 告警topic
     */
    public static String ALARMSTOPIC ;

    /**
     * redis的数据库
     */
    public static int REDISDBINDEX ;

    /**
     * 大数据资产上下线KEY
     */
    public static String ASSETREDISKEY ;

    /**
     * 主站组织机构KEY
     */
    public static String ZZCORPORATIONKEY ;

    /**
     * 主站已知资产KEY
     */
    public static String ZZDEVICEKEY ;

    /**
     * 主站未知资产KEY
     */
    public static String ZZDEVICETMPKEY ;

    /**
     * 资产上下线的类型
     */
    public static String EVENT_DEVICEONOFF ;

    //未知资产在线
    public static String EVENT_UDON ;

    //未知资产离线
    public static String EVENT_UDOF ;

    //已知资产在线
    public static String EVENT_DON ;

    //已知资产离线
    public static String EVENT_DOF ;

    /**
     * USB拔插类型
     */
    public static String EVENT_USBPLUGGED ;

    //USB插入
    public static String EVENT_USBON ;

    //USB拔出
    public static String EVENT_USBOFF ;

    /**
     * 登录类型
     */
    public static String EVENT_LOGINOUT ;

    //登录成功
    public static String EVENT_LOGINOUT_IN ;

    //退出登录
    public static String EVENT_LOGINOUT_OUT ;

    //登录失败
    public static String EVENT_LOGINOUT_FLD ;

    //登录操作
    public static String EVENT_LOGINOUT_OPTS ;

    /**
     * 资产发现
     */
    public static String EVENT_ASSETDISCOVER ;

    //发现未知资产
    public static String EVENT_ASSETDISCOVER_TAG ;

    /**
     * 自身监视
     */
    public static String EVENT_DCDALARMS ;

    //采集装置自身告警
    public static String EVENT_DCDALARMS_TAG ;

    //关键文件变更
    public static String EVENT_KEYFILECHAN ;

    public static String EVENT_KEYFILECHAN_TAG ;
    /**
     * 网络行为
     */
    public static String EVENT_NETWORK ;

    //网口状态-插入/拔出
    public static String EVENT_MESHPORT_TAG ;

    /**
     * 威胁日志
     */
    public static String EVENT_THREATLOG_TAG;

    /**
     * 隧道建立错误
     */
//    public static String EVENT_TUNNEL = props.getProperty("EVENT_TUNNEL").trim();

    /**
     * 隧道建立错误
     */
    public static String EVENT_TUNNEL_TAG ;

    /**
     * 资源占用
     */
    public static String EVENT_RESOURCES_TAG;

    /**
     * 参数初始化
     */
    public static void init() {
        props = ResourceUtil.load("EventCommonJob.properties");
        /**
         * 告警topic
         */
        ALARMSTOPIC = props.getProperty("kafka.producer.alarms.topic").trim();

        /**
         * redis的数据库
         */
        REDISDBINDEX = Integer.parseInt(props.getProperty("jedis.database").trim());

        /**
         * 大数据资产上下线KEY
         */
        ASSETREDISKEY = props.getProperty("redis.key.event.assert.status").trim();

        /**
         * 主站组织机构KEY
         */
        ZZCORPORATIONKEY = props.getProperty("redis.key.event.zz.corporation").trim();

        /**
         * 主站已知资产KEY
         */
        ZZDEVICEKEY = props.getProperty("redis.key.event.zz.device").trim();

        /**
         * 主站未知资产KEY
         */
        ZZDEVICETMPKEY = props.getProperty("redis.key.event.zz.devicetmp").trim();

        /**
         * 资产上下线的类型
         */
        EVENT_DEVICEONOFF = props.getProperty("EVENT_DEVICEONOFF").trim();

        //未知资产在线
        EVENT_UDON = props.getProperty("EVENT_UDON").trim();

        //未知资产离线
        EVENT_UDOF = props.getProperty("EVENT_UDOF").trim();

        //已知资产在线
        EVENT_DON = props.getProperty("EVENT_DON").trim();

        //已知资产离线
        EVENT_DOF = props.getProperty("EVENT_DOF").trim();

        /**
         * USB拔插类型
         */
        EVENT_USBPLUGGED = props.getProperty("EVENT_USBPLUGGED").trim();

        //USB插入
        EVENT_USBON = props.getProperty("EVENT_USERPLUGGED_ON").trim();

        //USB拔出
        EVENT_USBOFF = props.getProperty("EVENT_USERPLUGGED_OFF").trim();

        /**
         * 登录类型
         */
        EVENT_LOGINOUT = props.getProperty("EVENT_LOGINOUT").trim();

        //登录成功
        EVENT_LOGINOUT_IN = props.getProperty("EVENT_LONGINOUT_IN").trim();

        //退出登录
        EVENT_LOGINOUT_OUT = props.getProperty("EVENT_LONGINOUT_OUT").trim();

        //登录失败
        EVENT_LOGINOUT_FLD = props.getProperty("EVENT_LONGINOUT_FLD").trim();

        //登录操作
        EVENT_LOGINOUT_OPTS = props.getProperty("EVENT_LONGINOUT_OPTS").trim();

        /**
         * 资产发现
         */
        EVENT_ASSETDISCOVER = props.getProperty("EVENT_ASSETDISCOVER").trim();

        //发现未知资产
        EVENT_ASSETDISCOVER_TAG = props.getProperty("TAG_ASSETDISCOVER").trim();

        /**
         * 自身监视
         */
        EVENT_DCDALARMS = props.getProperty("EVENT_DCDALARMS").trim();

        //采集装置自身告警
        EVENT_DCDALARMS_TAG = props.getProperty("TAG_DCDALARMS").trim();

        //关键文件变更
        EVENT_KEYFILECHAN = props.getProperty("EVENT_KEYFILECHAN").trim();

         EVENT_KEYFILECHAN_TAG = props.getProperty("EVENT_KEYFILECHAN_TAG").trim();
        /**
         * 网络行为
         */
        EVENT_NETWORK = props.getProperty("EVENT_NETWORK").trim();

        //网口状态-插入/拔出
        EVENT_MESHPORT_TAG = props.getProperty("EVENT_MESHPORT_TAG").trim();

        /**
         * 威胁日志
         */
        EVENT_THREATLOG_TAG = props.getProperty("EVENT_THREATLOG_TAG").trim();

        /**
         * 隧道建立错误
         */
//    public static String EVENT_TUNNEL = props.getProperty("EVENT_TUNNEL").trim();

        /**
         * 隧道建立错误
         */
        EVENT_TUNNEL_TAG = props.getProperty("EVENT_TUNNEL_TAG").trim();

        /**
         * 资源占用
         */
        EVENT_RESOURCES_TAG = props.getProperty("EVENT_RESOURCES_TAG").trim();
    }

}
