package com.usr.usrsimplebleassistent.bean;

/**
 * Created by win on 2017/6/19.
 */

public class WaisheRequest {

    private String PosCode;
    private String PeriFirmwareVs;
    private String MotorTemperature;
    private String MotorSpeed;
    private String ControllerTemperature;
    private String WorkCurrent ;
    private String ReservedBit ;

    public WaisheRequest() {
    }

    public WaisheRequest(String posCode, String periFirmwareVs, String motorTemperature, String motorSpeed, String controllerTemperature, String workCurrent, String reservedBit) {
        PosCode = posCode;
        PeriFirmwareVs = periFirmwareVs;
        MotorTemperature = motorTemperature;
        MotorSpeed = motorSpeed;
        ControllerTemperature = controllerTemperature;
        WorkCurrent = workCurrent;
        ReservedBit = reservedBit;
    }

    public String getPosCode() {
        return PosCode;
    }

    public void setPosCode(String posCode) {
        PosCode = posCode;
    }

    public String getPeriFirmwareVs() {
        return PeriFirmwareVs;
    }

    public void setPeriFirmwareVs(String periFirmwareVs) {
        PeriFirmwareVs = periFirmwareVs;
    }

    public String getMotorTemperature() {
        return MotorTemperature;
    }

    public void setMotorTemperature(String motorTemperature) {
        MotorTemperature = motorTemperature;
    }

    public String getMotorSpeed() {
        return MotorSpeed;
    }

    public void setMotorSpeed(String motorSpeed) {
        MotorSpeed = motorSpeed;
    }

    public String getControllerTemperature() {
        return ControllerTemperature;
    }

    public void setControllerTemperature(String controllerTemperature) {
        ControllerTemperature = controllerTemperature;
    }

    public String getWorkCurrent() {
        return WorkCurrent;
    }

    public void setWorkCurrent(String workCurrent) {
        WorkCurrent = workCurrent;
    }

    public String getReservedBit() {
        return ReservedBit;
    }

    public void setReservedBit(String reservedBit) {
        ReservedBit = reservedBit;
    }
}
