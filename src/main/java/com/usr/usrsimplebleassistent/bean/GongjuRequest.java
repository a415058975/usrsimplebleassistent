package com.usr.usrsimplebleassistent.bean;

/**
 * Created by win on 2017/6/19.
 */

public class GongjuRequest {

    private String PosCode;
    private String BatteryID;
    private String ChargerStatus;

    public GongjuRequest() {
    }

    public GongjuRequest(String posCode, String batteryID, String chargerStatus) {
        PosCode = posCode;
        BatteryID = batteryID;
        ChargerStatus = chargerStatus;
    }

    public String getPosCode() {
        return PosCode;
    }

    public void setPosCode(String posCode) {
        PosCode = posCode;
    }

    public String getBatteryID() {
        return BatteryID;
    }

    public void setBatteryID(String batteryID) {
        BatteryID = batteryID;
    }

    public String getChargerStatus() {
        return ChargerStatus;
    }

    public void setChargerStatus(String chargerStatus) {
        ChargerStatus = chargerStatus;
    }
}
