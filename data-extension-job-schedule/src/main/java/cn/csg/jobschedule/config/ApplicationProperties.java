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

    @Value("${jobs.schedule.CommunicationAlarmJob,srcIpSumInterval}")
    private String srcIpSumInterval;

    @Value("${jobs.schedule.CommunicationAlarmJob,srcIpAndDestIpCountInterval}")
    private String srcIpAndDestIpCountInterval;

    @Value("${jobs.schedule.CommunicationAlarmJob,srcIpAndDestPortCountInterval}")
    private String srcIpAndDestPortCountInterval;

    public String getCommunicationAlarmJobCorn() {
        return communicationAlarmJobCorn;
    }

    public void setCommunicationAlarmJobCorn(String communicationAlarmJobCorn) {
        this.communicationAlarmJobCorn = communicationAlarmJobCorn;
    }

    public String getSrcIpSumInterval() {
        return srcIpSumInterval;
    }

    public void setSrcIpSumInterval(String srcIpSumInterval) {
        this.srcIpSumInterval = srcIpSumInterval;
    }

    public String getSrcIpAndDestIpCountInterval() {
        return srcIpAndDestIpCountInterval;
    }

    public void setSrcIpAndDestIpCountInterval(String srcIpAndDestIpCountInterval) {
        this.srcIpAndDestIpCountInterval = srcIpAndDestIpCountInterval;
    }

    public String getSrcIpAndDestPortCountInterval() {
        return srcIpAndDestPortCountInterval;
    }

    public void setSrcIpAndDestPortCountInterval(String srcIpAndDestPortCountInterval) {
        this.srcIpAndDestPortCountInterval = srcIpAndDestPortCountInterval;
    }
}
