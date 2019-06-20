package cn.csg.common.vo;


import cn.csg.common.enums.ExplodeType;
import cn.csg.common.enums.RuleKeyEnum;
import jdk.nashorn.internal.objects.annotations.Property;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "Explode")
@Table(name = "tb_explode")
@Proxy(lazy = false)
public class ExplodeVo{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Property(name = "name")
    private String name;

    @Property(name = "ruleVos")
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
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
        if(StringUtils.isNotEmpty(name)){
            if(name.equals(ExplodeType.SRC_IP_SUM.getExplode())){
                this.name = ExplodeType.SRC_IP_SUM.getName();
            }
            if(name.equals(ExplodeType.SRC_IP_AND_DEST_IP_COUNT.getExplode())){
                this.name = ExplodeType.SRC_IP_AND_DEST_IP_COUNT.getName();
            }
            if(name.equals(ExplodeType.SRC_IP_AND_DEST_PORT_COUNT.getExplode())){
                this.name = ExplodeType.SRC_IP_AND_DEST_PORT_COUNT.getName();
            }
        }
        return name;
    }
    public ExplodeType getExplodeType(){
        return ExplodeType.initExplodeType(this.name);
    }

    public void setName(String name) {
        if(StringUtils.isNotEmpty(name)){
            if(name.equals(ExplodeType.SRC_IP_SUM.getName())){
                this.name = ExplodeType.SRC_IP_SUM.getExplode();
            }
            if(name.equals(ExplodeType.SRC_IP_AND_DEST_IP_COUNT.getName())){
                this.name = ExplodeType.SRC_IP_AND_DEST_IP_COUNT.getExplode();
            }
            if(name.equals(ExplodeType.SRC_IP_AND_DEST_PORT_COUNT.getName())){
                this.name = ExplodeType.SRC_IP_AND_DEST_PORT_COUNT.getExplode();
            }
        }
    }

    public BigDecimal getValByName(String name){
        BigDecimal val = null;
        if(name == null)
            return val;
        if(! name.equals(RuleKeyEnum.WINDOWN.getKey()) && ! name.equals(RuleKeyEnum.TARGET.getKey()))
            return val;
        for(RuleVo rule : ruleVos){
            if(rule.getKey().equals(name)) {
                val = rule.getValue();
            }
        }
        return val;
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

    @Override
    public String toString() {
        return "ExplodeVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ruleVos=" + ruleVos +
                '}';
    }
}
