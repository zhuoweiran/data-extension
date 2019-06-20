package cn.csg.msg.producer.bean;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

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
    private ValueType valueType;
    private boolean status;

    public void put(String key, String value){
        this.setKey(key);
        this.setValue(value);
    }
}
