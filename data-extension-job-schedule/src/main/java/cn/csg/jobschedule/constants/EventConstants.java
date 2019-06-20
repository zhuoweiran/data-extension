package cn.csg.jobschedule.constants;

/**
 * @Author: tide
 * @Description: Event事件公共常量名称
 * @Date: Created in 16:50 2019/4/22
 * @Modified By:
 */
public interface EventConstants {

    /**
     * Event事件的报文开始
     */
    String EVENT_PREFIX = "<?begn?>\u0000\u0001";

    /**
     * Event事件的报文结尾
     */
    String EVENT_SUBIFX = "<?endn?>";

    /**
     * Event事件的报文的Header信息
     */
    String EVNET_HEADER = "Header";

    /**
     * Event事件的报文的Data信息
     */
    String EVENT_DATA = "Data";

    /**
     * 装置guid
     */
     String EVENT_DCDGUID = "DcdGuid";

    /**
     * 资产guid
     */
    String EVENT_DEVGUID= "DevGuid";

    /**
     * 报文唯一标识
     */
    String EVENT_SID = "Sid";

    /**
     * 请求时间，时间戳为13位
     */
    String EVENT_TIMESTAMP = "Timestamp";

    /**
     * 应用名称
     */
    String EVENT_APPNAME = "AppName";

    /**
     * 事件类型
     */
    String EVENT_EVENTTYPE = "EventType";

    /**
     * 子类型
     */
    String EVENT_FUNCLASSTAG= "FunClassTag";

    /**
     * 发现时间
     */
    String EVENT_DISCOVERTIME = "DiscoverTime";

    /**
     * 明细信息
     */
    String EVENT_DETAILS = "Details";

    /**
     * 厂站名称
     */
    String EVENT_CORPNAME = "corpname";

    /**
     * 公司
     */
    String EVENT_ORGNAME = "orgName";

    /**
     * 单位
     */
    String EVENT_UNIT = "unitAbbreviation";

    /**
     * 调管单位
     */
    String EVNET_DGCORPNAME = "dgCorpName";

    /**
     * 设备名称
     */
    String EVENT_NAME = "name";

    /**
     * 安全分区
     */
    String EVENT_AREA = "area";

    /**
     * 归属专业
     */
    String EVENT_BELONGEDMAJOR = "belongedMajor";

    /**
     * 设备子类型
     */
    String EVENT_DEVICECODE = "devicecode";

    /**
     * 未知资产：设备名称
     */
    String EVENT_DEVICENAME = "devicename";

    /**
     * 未知资产：安全分区
     */
    String EVENT_PARTITION = "partition";

    /**
     * 资产：ip
     */
    String EVENT_IP = "ip";

    /**
     * 操作系统
     */
    String EVENT_OS = "systemVersion";
}
