package com.example.smartcity.bean;

import java.util.List;

/**
 * @PackageName: com.example.smartcity.bean
 * @ClassName: RegisterResponse
 * @Author: winwa
 * @Date: 2022/12/17 9:14
 * @Description:
 **/
public class RegisterResponse {

    private int total;
    private int code;
    private String msg;
    private List<?> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
