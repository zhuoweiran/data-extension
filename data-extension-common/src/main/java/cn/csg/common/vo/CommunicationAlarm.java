package cn.csg.common.vo;

import lombok.Data;

@Data
public class CommunicationAlarm {

    private String id;
    private String srcIp;
    private String alarmType = "爆发式通信对告警";
    private int countData;
    private String deviceGUID;
    private String sessionStartTime;
    private String alarmLevel = "紧急";
}
