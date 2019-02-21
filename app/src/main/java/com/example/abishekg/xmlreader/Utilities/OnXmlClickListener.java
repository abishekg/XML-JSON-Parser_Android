package com.example.abishekg.xmlreader.Utilities;


import com.example.abishekg.xmlreader.ModelClasses.MachineDetails;

/**
 * Created by abishek.g on 08-Feb-18.
 */

public interface  OnXmlClickListener {
    void XmlClick(int position, int id, MachineDetails machineDetails, String timeStamp);
}