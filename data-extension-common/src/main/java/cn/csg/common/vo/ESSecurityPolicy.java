package cn.csg.common.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ESSecurityPolicy {
    private String destIp;
    private String source;
    private String processTime;
    private String detailType;
    private String firm;
    private String protocol;
    private String devGuid;
    private String id;
    private String tag;
    private String destMac;
    private String timestamp;
    private String severity;
    private String srcIp;
    private String corpId;
    private String appName;
    private String discoverTime;
    private String srcDeviceGUID;
    private String sessionStartTime;
    private String corpName;
    private String dcdGuid;
    private String eventCode;
    private String transferUnit;
    private String unit;
    private String alarmType;
    private String destCorpId;
    private String srcMac;
    private String destDeviceGUID;
    private int status;
    private String srcPort;
    private String destPort;
}
