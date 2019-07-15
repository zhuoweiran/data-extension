package cn.csg.codis.server.handler;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类{@code RedisCollects} 用到的redis集合名称
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */
@Component
@Data
@ConfigurationProperties(prefix = "redis.collect")
public class RedisCollects {
    private String device;
    private String devicetmp;
    private String corporation;
    private String dcdDeviceIpbd;
    private String evilIp;
    private String evilDns;
}
