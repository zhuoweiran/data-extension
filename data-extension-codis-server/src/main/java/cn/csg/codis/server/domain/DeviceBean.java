package cn.csg.codis.server.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 类{@code DeviceBean}资产
 *
 * @author Alex Han
 * @version 1.2
 */
@Data
public class DeviceBean {
    /**
     * 设备ID
     */
    private int id;
    /**
     * 资产唯一标识
     */
    private String guid;
    /**
     * 设备IP
     */
    private String ip;
    /**
     * 区域（I区、II区）
     */
    private String area;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备归属者
     */
    private String owner;
    /**
     * 设备添加时间
     */
    private String addtime;
    /**
     * 最新修改时间
     */
    private String modifytime;
    /**
     * 设备类型code
     */
    private String devicecode;
    /**
     * 地域ID(关联4位corpid)
     */
    private String corpid;
    /**
     * 厂商名称（从t_evice_factory获取）
     */
    private String factory;
    /**
     * 硬件型号
     */
    private String model;
    /**
     * 软件版本
     */
    private String version;
    /**
     * 设备标识（备注说明）
     */
    private String remark;
    /**
     * 设备显示名称（拼接“地域_平面_安全区_设备类型_自增数字”）
     */
    private String showname;
    /**
     * 采集机IP地址
     */
    private String ipFetch;
    /**
     * 日志解析代理ID
     */
    private String agentId;
    /**
     * 接入状态（0：未接入 1：接入）
     */
    private int state;
    /**
     * 设备电压等级（子属性）
     */
    private String voltagelevel;
    /**
     * 站点类型（父属性）
     */
    private String zdlx;
    /**
     * 最近操作用户帐号
     */
    private String user;
    /**
     * 所属平面信息（一平面、二平面）
     */
    private String areapm;

    /**
     * Mac地址
     */
    private String mac;

    /**
     * 分组编号
     */
    private int groupid;
    /**
     * 是否已被同步(0或空已经被同步,1需要同步)
     */
    private boolean isneedsync;
    /**
     * 最后更新时间
     */
    private long lastupdatetime;
    /**
     * 推送标志:0为都不推,1为推本地,2为推上级,3为本地和上级都推
     */
    private int sendflag;

    /**
     * 采集机ip
     */
    private String agent_ip;

    /**
     * 所属业务系统
     */
    private String sys_belong;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 厂商唯一标识
     */
    private String station_uid;

    /**
     * 站点类型
     */
    private String sitetype;

    /**
     * 地域组织编号
     */
    private String areaorg_id;

    /**
     * 业务系统id
     */
    private String bussys_id;

    private String phyaddress;

    /**
     * 归属专业
     */
    private String belongedMajor;

    /**
     * 操作系统版本
     */
    private String systemVersion;

    @JSONField(format = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private String findtime;

    /**
     * 设备父级类型
     */
    private String parent_devicecode;

    /**
     * nmap扫描系统名
     */
    private String nmapSystem;

    /**
     * 1、主属；2、副属类型
     */
    private int stationType;

    /**
     * 装置GUID
     */
    private String deviceRegisterGuid;

    /**
     * 合规核查脚本
     */
    private String scriptID;
    //是否可疑(1已知/0可疑)
    private String suspicious;
    //是NMAP生成告警设为false
    private String mnapAlarm;
    //最后在线时间
    private long lastOnLineTime;
}
