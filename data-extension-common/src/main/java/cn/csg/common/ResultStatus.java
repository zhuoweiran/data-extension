package cn.csg.common;

import cn.csg.common.enums.StatusEnum;

public class ResultStatus {
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

    public ResultStatus(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResultStatus() {
    }

    public static ResultStatus initStatus(StatusEnum statusEnum) {
        return new ResultStatus(
                statusEnum.getStatus(),
                statusEnum.getMsg()
        );
    }
}
