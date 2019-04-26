package cn.csg.common.vo;


import jdk.nashorn.internal.objects.annotations.Property;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Explode")
@Table(name = "tb_explode")
public class ExplodeVo{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Property(name = "name")
    private String name;

    @Property(name = "ruleVos")
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Rule", referencedColumnName = "id")
    private List<RuleVo> ruleVos;

    public ExplodeVo(String id, String name, List<RuleVo> ruleVos) {
        this.id = id;
        this.name = name;
        this.ruleVos = ruleVos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RuleVo> getRuleVos() {
        return ruleVos;
    }

    public void setRuleVos(List<RuleVo> ruleVos) {
        this.ruleVos = ruleVos;
    }

    public ExplodeVo() {
    }

    public ExplodeVo(String name, List<RuleVo> ruleVos) {
        this.name = name;
        this.ruleVos = ruleVos;
    }
    public ExplodeVo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExplodeVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ruleVos=" + ruleVos +
                '}';
    }
}
