package cn.csg.msg.producer.bean;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
/**
 * 类{@code MsgRules}消息模板的参数
 *
 * <p>消息任务中的模板{@link MsgJob}的参数，当模板被渲染时，可以使用这些参数.</p>
 * @see <a href="https://freemarker.apache.org/docs/index.html">Freemarker2.3.28模版</a>
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 *
 */
@Data
@Entity
@Table(name = "t_msg_rules")
@Proxy(lazy=false) // 去除懒加载
public class MsgRules {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    private String jobId;
    private String name;
    @Column(name = "rule_key")
    private String key;
    @Column(name = "rule_value")
    private String value;
    @Enumerated(EnumType.STRING)
    private ValueType valueType;
    private boolean status;

    public void put(String key, String value){
        this.setKey(key);
        this.setValue(value);
    }
}
