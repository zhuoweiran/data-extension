package cn.csg.jobschedule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件
 */
@Component
public class ApplicationProperties {

    @Value("${jobs.schedule.CommunicationAlarmJobCorn}")
    private String communicationAlarmJobCorn;

    public String getCommunicationAlarmJobCorn() {
        return communicationAlarmJobCorn;
    }

    public void setCommunicationAlarmJobCorn(String communicationAlarmJobCorn) {
        this.communicationAlarmJobCorn = communicationAlarmJobCorn;
    }
}
