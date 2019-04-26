package cn.csg.common;


public class ResultData<T> {
    private T data;
    private ResultStatus status;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public ResultData(T data, ResultStatus status) {
        this.data = data;
        this.status = status;
    }
}
