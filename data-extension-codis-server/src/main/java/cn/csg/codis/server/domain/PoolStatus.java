package cn.csg.codis.server.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

/**
 * 类{@code PoolStatus}资源池状态
 *
 * @author Alex Han
 * @version 1.2
 */
@Data
@Builder
public class PoolStatus {
    @JSONField(name = "addr")
    private String addr;
    @JSONField(name = "active_num")
    private int activeNum;
    @JSONField(name = "idle_num")
    private int idleNum;
    @JSONField(name = "waiter_num")
    private int waitersNum;
}
