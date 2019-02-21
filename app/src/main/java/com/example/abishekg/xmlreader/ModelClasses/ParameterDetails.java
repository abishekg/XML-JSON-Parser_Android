package com.example.abishekg.xmlreader.ModelClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abishek.g on 06-Feb-18.
 */

public class ParameterDetails implements Serializable {

    int parameterId;
    int parameterNumber;
    String parameterName;
    String parameterDataType;
    String parameterValue;
    String parameterUnit;
    int parameterVersion;
    String parameterTimeStamp;
    int machineParameterFk;
    String exportState;

    public String getExportState() {
        return exportState;
    }

    public void setExportState(String exportState) {
        this.exportState = exportState;
    }

    public ParameterDetails(int parameterId, int parameterNumber, String parameterName, String parameterDataType, String parameterValue, String parameterUnit, int parameterVersion, String parameterTimeStamp, int machineParameterFk) {
        this.parameterId = parameterId;
        this.parameterTimeStamp = parameterTimeStamp;
        this.parameterNumber = parameterNumber;
        this.parameterName = parameterName;
        this.parameterDataType = parameterDataType;
        this.parameterValue = parameterValue;
        this.parameterUnit = parameterUnit;
        this.parameterVersion = parameterVersion;
        this.machineParameterFk = machineParameterFk;
    }

    public String getParameterTimeStamp() {
        return parameterTimeStamp;
    }

    public void setParameterTimeStamp(String parameterTimeStamp) {
        this.parameterTimeStamp = "";
        this.parameterTimeStamp = parameterTimeStamp;
    }

    public int getParameterNumber() {
        return parameterNumber;
    }

    public void setParameterNumber(int parameterNumber) {
        this.parameterNumber = parameterNumber;
    }

    public ParameterDetails(String parameterName, int parameterNumber, int machineParameterFk) {
        this.parameterName = parameterName;
        this.parameterNumber = parameterNumber;
        this.machineParameterFk = machineParameterFk;
    }


    public int getMachineParameterFk() {

        return machineParameterFk;
    }

    public void setMachineParameterFk(int machineParameterFk) {
        this.machineParameterFk = machineParameterFk;
    }

    public int getParameterVersion() {

        return parameterVersion;
    }

    public void setParameterVersion(int parameterVersion) {
        this.parameterVersion = parameterVersion;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterUnit() {
        return parameterUnit;
    }

    public void setParameterUnit(String parameterUnit) {
        this.parameterUnit = parameterUnit;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public int getParameterId() {
        return parameterId;
    }

    public void setParameterId(int parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterDataType() {
        return parameterDataType;
    }

    public void setParameterDataType(String parameterDataType) {
        this.parameterDataType = parameterDataType;
    }

    @Override
    public String toString() {
        return "ParameterDetails{" +
                "parameterId=" + parameterId +
                ", parameterNumber=" + parameterNumber +
                ", parameterName='" + parameterName + '\'' +
                ", parameterDataType='" + parameterDataType + '\'' +
                ", parameterValue='" + parameterValue + '\'' +
                ", parameterUnit='" + parameterUnit + '\'' +
                ", parameterVersion=" + parameterVersion +
                ", parameterTimeStamp='" + parameterTimeStamp + '\'' +
                ", machineParameterFk=" + machineParameterFk +
                ", exportState='" + exportState + '\'' +
                '}';
    }

}
