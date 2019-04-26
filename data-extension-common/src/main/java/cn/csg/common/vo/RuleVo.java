package cn.csg.common.vo;


import jdk.nashorn.internal.objects.annotations.Property;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity(name = "Rule")
@Table(name = "tb_rule")
public class RuleVo{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(name = "rule_key")
    @Property(name = "key")
    private String key;
    @Column(name = "rule_value")
    @Property(name = "value")
    private BigDecimal value;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RuleVo(String id, String key, BigDecimal value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }


    public RuleVo() {
    }

    public static RuleVo put(String key, BigDecimal value){
        RuleVo ruleVo = new RuleVo();
        ruleVo.setKey(key);
        ruleVo.setValue(value);
        return ruleVo;
    }

    @Override
    public String toString() {
        return "RuleVo{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
