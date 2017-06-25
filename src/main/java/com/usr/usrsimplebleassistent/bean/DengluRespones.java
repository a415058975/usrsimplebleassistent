package com.usr.usrsimplebleassistent.bean;

/**
 * Created by win on 2017/6/20.
 */

public class DengluRespones {

    private String ResultCode;
    private String HostTime;
    private String UserName;
    private String Note;

    public DengluRespones() {
    }

    public DengluRespones(String resultCode, String hostTime, String userName, String note) {
        ResultCode = resultCode;
        HostTime = hostTime;
        UserName = userName;
        Note = note;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getHostTime() {
        return HostTime;
    }

    public void setHostTime(String hostTime) {
        HostTime = hostTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
