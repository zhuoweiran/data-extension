package cn.csg.common.vo;

import jdk.nashorn.internal.objects.annotations.Property;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "ExploderJob")
@Table(name = "tb_explode_job")
public class ExplodeJob {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Property(name = "explode_id")
    private String explodeId;

    @Property(name = "query")
    @Column(length = 3000)
    private String query;

    private Date lastSuccessTime;

    public Date getLastSuccessTime() {
        return lastSuccessTime;
    }

    public void setLastSuccessTime(Date lastSuccessTime) {
        this.lastSuccessTime = lastSuccessTime;
    }

    public ExplodeJob() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExplodeId() {
        return explodeId;
    }

    public void setExplodeId(String explodeId) {
        this.explodeId = explodeId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
