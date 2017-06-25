package com.usr.usrsimplebleassistent.bean;

/**
 * Created by win on 2017/6/20.
 */

public class DengluRequest {

    private String UserName;
    private String UserPwd;

    public DengluRequest() {
    }

    public DengluRequest(String userName, String userPwd) {
        UserName = userName;
        UserPwd = userPwd;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPwd() {
        return UserPwd;
    }

    public void setUserPwd(String userPwd) {
        UserPwd = userPwd;
    }
}
