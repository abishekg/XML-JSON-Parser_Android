package com.example.abishekg.xmlreader.ModelClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abishek.g on 06-Feb-18.
 */

public class MachineDetails implements Serializable{
    int machineId;
    int machineNumber;
    String machineName;



    @Override
    public String toString() {
        return "MachineDetails{" +
                "machineId=" + machineId +
                ", machineNumber=" + machineNumber +
                ", machineName='" + machineName + '\'' +
                ", machineTimeStamp='" + machineTimeStamp + '\'' +
                ", machineVersionNumber=" + machineVersionNumber +
                ", areaMachinefk=" + areaMachinefk +
                ", parameterDetailsList=" + parameterDetailsList +
                '}';
    }

    public MachineDetails(int machineId, int machineNumber, String machineName,  int machineVersionNumber,String machineTimeStamp, int areaMachinefk, List<ParameterDetails> parameterDetailsList) {
        this.machineId = machineId;
        this.machineNumber = machineNumber;
        this.machineName = machineName;
        this.machineTimeStamp = machineTimeStamp;
        this.machineVersionNumber = machineVersionNumber;
        this.areaMachinefk = areaMachinefk;
        this.parameterDetailsList = parameterDetailsList;
    }

    public String getMachineTimeStamp() {

        return machineTimeStamp;
    }

    public void setMachineTimeStamp(String machineTimeStamp) {
        this.machineTimeStamp = machineTimeStamp;
    }

    String machineTimeStamp;

    public MachineDetails(int machineId, int machineNumber, String machineName, int machineVersionNumber, String machineTimeStamp, int areaMachinefk) {
        this.machineId = machineId;
        this.machineNumber = machineNumber;
        this.machineName = machineName;
        this.machineVersionNumber = machineVersionNumber;
        this.machineTimeStamp = machineTimeStamp;
        this.areaMachinefk = areaMachinefk;
    }

    int machineVersionNumber;
    int areaMachinefk;
    List<ParameterDetails> parameterDetailsList = new ArrayList<>();

    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }

    public MachineDetails(int machineId, String machineName,  int machineVersionNumber, int areaMachinefk) {
        this.machineName = machineName;
        this.areaMachinefk = areaMachinefk;
        this.machineId = machineId;
        this.machineVersionNumber = machineVersionNumber;
    }

    public MachineDetails(int machineNumber, String machineName, int areaMachinefk) {
        this.machineName = machineName;
        this.areaMachinefk = areaMachinefk;
        this.machineNumber = machineNumber;
    }

    public int getAreaMachinefk() {
        return areaMachinefk;
    }

    public void setAreaMachinefk(int areaMachinefk) {
        this.areaMachinefk = areaMachinefk;
    }

    public int getMachineVersionNumber() {
        return machineVersionNumber;
    }

    public void setMachineVersionNumber(int machineVersionNumber) {
        this.machineVersionNumber = machineVersionNumber;
    }

    public MachineDetails(String machineName) {
        this.machineName = machineName;
    }



    public MachineDetails(int machineId, String machineName) {
        this.machineName = machineName;
        this.machineId = machineId;
        parameterDetailsList = new ArrayList<>();
    }

    public MachineDetails(MachineDetails MD) {
        this.machineName = MD.getMachineName();
        this.machineId = MD.getMachineId();
        this.machineNumber = MD.getMachineNumber();
        this.machineVersionNumber = MD.getMachineVersionNumber();
        this.areaMachinefk = MD.getAreaMachinefk();
        this.machineTimeStamp = MD.getMachineTimeStamp();
        parameterDetailsList = new ArrayList<>();
    }

    public MachineDetails(int machineId, String machineName, List<ParameterDetails> parameterDetailsList) {
        this.machineName = machineName;
        this.machineId = machineId;
        this.parameterDetailsList = parameterDetailsList;
    }

    public String getMachineName() {

        return machineName;
    }

    public void addParaMDetails(ParameterDetails parameterDetails){
        this.parameterDetailsList.add(parameterDetails);
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public List<ParameterDetails> getParameterDetailsList() {
        return parameterDetailsList;
    }

    public void setParameterDetailsList(List<ParameterDetails> parameterDetailsList) {
        this.parameterDetailsList = parameterDetailsList;
    }

}

