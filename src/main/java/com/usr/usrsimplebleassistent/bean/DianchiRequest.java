package com.usr.usrsimplebleassistent.bean;

import java.io.Serializable;

/**
 * Created by win on 2017/6/19.
 */

public class DianchiRequest implements Serializable {
    private String PosCode;
    private String BatteryID;
    private String BatteryString;
    private String BatteryStatus;
    private String TotalVoltage ;
    private String SocElePercentage;
    private String Electricity;
    private String Residuallife;
    private String MaxTemperature ;
    private String MonomerVoltage;
    private String SensorTemperature ;
    private String BatteryLockStatus;
    private String CumulativeNum;
    private String BatteryPackVs;

    public DianchiRequest() {
    }

    public DianchiRequest(String posCode, String batteryID, String batteryString, String batteryStatus, String totalVoltage, String socElePercentage, String electricity, String residuallife, String maxTemperature, String monomerVoltage, String sensorTemperature, String batteryLockStatus, String cumulativeNum, String batteryPackVs) {
        PosCode = posCode;
        BatteryID = batteryID;
        BatteryString = batteryString;
        BatteryStatus = batteryStatus;
        TotalVoltage = totalVoltage;
        SocElePercentage = socElePercentage;
        Electricity = electricity;
        Residuallife = residuallife;
        MaxTemperature = maxTemperature;
        MonomerVoltage = monomerVoltage;
        SensorTemperature = sensorTemperature;
        BatteryLockStatus = batteryLockStatus;
        CumulativeNum = cumulativeNum;
        BatteryPackVs = batteryPackVs;
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

    public String getBatteryString() {
        return BatteryString;
    }

    public void setBatteryString(String batteryString) {
        BatteryString = batteryString;
    }

    public String getBatteryStatus() {
        return BatteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        BatteryStatus = batteryStatus;
    }

    public String getTotalVoltage() {
        return TotalVoltage;
    }

    public void setTotalVoltage(String totalVoltage) {
        TotalVoltage = totalVoltage;
    }

    public String getSocElePercentage() {
        return SocElePercentage;
    }

    public void setSocElePercentage(String socElePercentage) {
        SocElePercentage = socElePercentage;
    }

    public String getElectricity() {
        return Electricity;
    }

    public void setElectricity(String electricity) {
        Electricity = electricity;
    }

    public String getResiduallife() {
        return Residuallife;
    }

    public void setResiduallife(String residuallife) {
        Residuallife = residuallife;
    }

    public String getMaxTemperature() {
        return MaxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        MaxTemperature = maxTemperature;
    }

    public String getMonomerVoltage() {
        return MonomerVoltage;
    }

    public void setMonomerVoltage(String monomerVoltage) {
        MonomerVoltage = monomerVoltage;
    }

    public String getSensorTemperature() {
        return SensorTemperature;
    }

    public void setSensorTemperature(String sensorTemperature) {
        SensorTemperature = sensorTemperature;
    }

    public String getBatteryLockStatus() {
        return BatteryLockStatus;
    }

    public void setBatteryLockStatus(String batteryLockStatus) {
        BatteryLockStatus = batteryLockStatus;
    }

    public String getCumulativeNum() {
        return CumulativeNum;
    }

    public void setCumulativeNum(String cumulativeNum) {
        CumulativeNum = cumulativeNum;
    }

    public String getBatteryPackVs() {
        return BatteryPackVs;
    }

    public void setBatteryPackVs(String batteryPackVs) {
        BatteryPackVs = batteryPackVs;
    }
}
