package cn.csg.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum  ExplodeType {
    SRC_IP_SUM("srcIpSum","相同源IP发起访问数"),
    SRC_IP_AND_DEST_IP_COUNT("srcIpAndDestIpCount","相同源IP访问不同IP数"),
    SRC_IP_AND_DEST_PORT_COUNT("srcIpAndDestPortCount","相同源IP访问同一IP端口数");

    public final static Map<String,ExplodeType> EXPLODE_MAP = new HashMap<String,ExplodeType>(){{
        put(SRC_IP_SUM.getExplode(),SRC_IP_SUM);
        put(SRC_IP_AND_DEST_IP_COUNT.getExplode(),SRC_IP_AND_DEST_IP_COUNT);
        put(SRC_IP_AND_DEST_PORT_COUNT.getExplode(),SRC_IP_AND_DEST_PORT_COUNT);
    }};
    private String explode;
    private String name;

    ExplodeType(String explode, String name) {
        this.explode = explode;
        this.name = name;
    }

    public static ExplodeType initExplodeType(String explode) {
        if(explode.equals("srcIpSum")){
            return SRC_IP_SUM;
        }else if(explode.equals("srcIpAndDestIpCount")){
            return SRC_IP_AND_DEST_IP_COUNT;
        }else if(explode.equals("srcIpAndDestPortCount")){
            return SRC_IP_AND_DEST_PORT_COUNT;
        }else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getExplode() {
        return explode;
    }
}
