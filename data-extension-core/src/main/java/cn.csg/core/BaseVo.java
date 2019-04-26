package cn.csg.core;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class BaseVo implements Serializable {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
