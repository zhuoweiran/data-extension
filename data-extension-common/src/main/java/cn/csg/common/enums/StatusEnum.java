package cn.csg.common.enums;

public enum StatusEnum {
    SUCCESS(0,"获取成功"),
    ERROR(-1,"获取失败"),
    UPDATE(0,"更新成功"),
    UPDATE_FAIL(-1,"更新失败"),
    DELETE(0,"删除成功"),
    DELETE_FAIL(-1,"删除失败"),
    ADD(0,"添加成功"),
    ADD_FAIL(0,"添加失败"),
    ;

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
