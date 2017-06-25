package com.usr.usrsimplebleassistent.bean;

/**
 * Created by win on 2017/6/20.
 */

public class GongjuRespones {

    private String ResultCode;
    private String HostTime;
    private String Note;

    public GongjuRespones() {
    }

    public GongjuRespones(String resultCode, String hostTime, String note) {
        ResultCode = resultCode;
        HostTime = hostTime;
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

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
