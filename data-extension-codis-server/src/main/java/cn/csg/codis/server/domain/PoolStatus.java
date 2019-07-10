package cn.csg.codis.server.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

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
