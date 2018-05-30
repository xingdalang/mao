package com.dl.mao.common;

import java.io.Serializable;

/**
 * @author xingguanghui
 * @create 2018年5月30日 09:31:20
 **/
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NO_PERMISSION = 2;
    private String msg = "success";
    private int code = SUCCESS;
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static int getSUCCESS() {
        return SUCCESS;
    }

    public static int getFAIL() {
        return FAIL;
    }

    public static int getNoPermission() {
        return NO_PERMISSION;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}