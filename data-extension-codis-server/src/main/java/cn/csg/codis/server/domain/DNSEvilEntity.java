package cn.csg.codis.server.domain;


import cn.csg.codis.server.domain.common.JedisCollectDomain;
import lombok.Builder;
import lombok.Data;


/**
 * 类{@code DNSEvilEntity}恶意域名
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */

@Data
@Builder
public class DNSEvilEntity extends JedisCollectDomain {
    private String evilDNS;
}
