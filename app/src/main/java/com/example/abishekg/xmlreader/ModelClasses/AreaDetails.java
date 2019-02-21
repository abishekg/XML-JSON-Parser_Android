package com.example.abishekg.xmlreader.ModelClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abishek.g on 06-Feb-18.
 */

/**
 * Holds the parameters of Diff
 */
public class AreaDetails implements Serializable{

    String name;
    int areaId;
    private List<MachineDetails> machineDetailsList = new ArrayList<>();



    public AreaDetails(String name, int areaId) {
        this.name = name;
        this.areaId = areaId;
        machineDetailsList = new ArrayList<>();
    }

    public AreaDetails(String name, int areaId, List<MachineDetails> machineDetailsList) {
        this.name = name;
        this.areaId = areaId;
        this.machineDetailsList = machineDetailsList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public List<MachineDetails> getMachineDetailsList() {
        return machineDetailsList;
    }

    public void setMachineDetailsList(List<MachineDetails> machineDetailsList) {
        this.machineDetailsList = machineDetailsList;
    }

    public void addMachineDetails(MachineDetails machineDetails){
        this.machineDetailsList.add(machineDetails);
    }

    @Override
    public String toString() {
        return "AreaDetails{" +
                "name='" + name + '\'' +
                ", areaId=" + areaId +
                ", machineDetailsList=" + machineDetailsList +
                '}';
    }
}
