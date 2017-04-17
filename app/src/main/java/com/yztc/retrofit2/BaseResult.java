package com.yztc.retrofit2;

/**
 * Created by wanggang on 2017/1/13.
 */

public class BaseResult<T> {


    /**
     * status : 1
     * result : JSON已经处理好了
     */

    private int status;
    private T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "status=" + status +
                ", result='" + result + '\'' +
                '}';
    }
}
