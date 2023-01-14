package com.example.smartcity.bean;

/**
 * @PackageName: com.example.smartcity.bean
 * @ClassName: NewsResponse
 * @Author: winwa
 * @Date: 2023/1/2 14:18
 * @Description:
 **/
public class NewsResponse {

    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
