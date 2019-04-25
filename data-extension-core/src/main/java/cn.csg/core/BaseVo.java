package cn.csg.core;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class BaseVo implements Serializable {
    @Id
    private String id;
}
