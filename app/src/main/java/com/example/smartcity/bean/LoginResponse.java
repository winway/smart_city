package com.example.smartcity.bean;

/**
 * @PackageName: com.example.smartcity.bean
 * @ClassName: LoginResponse
 * @Author: winwa
 * @Date: 2022/12/15 9:08
 * @Description:
 **/
public class LoginResponse {

    private String msg;
    private String code;
    private String token;

    public LoginResponse(String msg, String code, String token) {
        this.msg = msg;
        this.code = code;
        this.token = token;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
