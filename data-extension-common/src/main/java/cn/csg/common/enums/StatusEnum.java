package cn.csg.common.enums;

public enum StatusEnum {
    SUCCESS(0,"获取成功"),
    ERROR(-1,"获取失败"),
    UPDATE(0,"更新成功");
    private Integer status;
    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    StatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
