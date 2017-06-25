package com.usr.usrsimplebleassistent.bean;

/**
 * Created by win on 2017/6/20.
 */

public class ZhuceRespones {

    private String ResultCode;
    private String HostTime;
    private String UserId;
    private String Note;

    public ZhuceRespones() {
    }

    public ZhuceRespones(String resultCode, String hostTime, String userId, String note) {
        ResultCode = resultCode;
        HostTime = hostTime;
        UserId = userId;
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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
