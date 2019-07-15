package cn.csg.codis.server.domain;


import cn.csg.codis.server.domain.common.JedisCollectDomain;
import lombok.Builder;
import lombok.Data;


/**
 * 类{@code IPEvilEntity}恶意IP
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */
@Data
@Builder
public class IPEvilEntity extends JedisCollectDomain {
    private String evilIp;
}
