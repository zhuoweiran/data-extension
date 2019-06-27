package cn.csg.msg.producer.bean;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * 类{@code MsgJob}消息任务JavaBean.
 *
 * <p>定义一个消息任务，用于序列化于存储中</p>
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 *
 */
@Data
@Entity
@Table(name = "t_msg_job")
@Proxy(lazy=false) // 去除懒加载
public class MsgJob {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(length = 3000)
    private String template;
    private Date lastSuccessTime;
    private String name;
    private boolean status;
    private String topic;
    @Column(name = "job_window")
    private int window;
    @Enumerated(EnumType.STRING)
    private MsgType msgType;
}
