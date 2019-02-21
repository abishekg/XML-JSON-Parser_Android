package com.example.abishekg.xmlreader.Utilities;
import android.content.Context;
import android.util.Xml;
import android.widget.Toast;


import com.example.abishekg.xmlreader.ModelClasses.AreaDetails;
import com.example.abishekg.xmlreader.ModelClasses.MachineDetails;
import com.example.abishekg.xmlreader.ModelClasses.ParameterDetails;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abishek.g on 06-Feb-18.
 */

public class ReadWriteXML implements Serializable {
    AreaDetails areaDetails;
    List<AreaDetails> areaDetailsArrayList = new ArrayList<>();

    File file;
    Context context;
    private static int areaId=0,machineId=0,parameterId=0,areaMachineFk=0, machineParameterFk=0;


    public ReadWriteXML(Context context, File file) {
        this.file = file;
        this.context = context;
    }

    public List<AreaDetails> readXMLParser (){
        FileInputStream fileInputStream = null;
        areaDetails = null;
        MachineDetails machineDetails = null;
        ParameterDetails parameterDetails = null;
        XmlPullParserFactory xmlPullParserFactory;
        String startTag = null;
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            fileInputStream =  new FileInputStream(file.getAbsolutePath());
            xmlPullParser.setInput(fileInputStream, null);
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if ((eventType == XmlPullParser.START_TAG)){
                    startTag = xmlPullParser.getName();
                    if(xmlPullParser.getName().equals("area")) {
                        areaId+=1;
                        areaDetails = new AreaDetails(xmlPullParser.getAttributeValue("","name"), areaId);
                    }else if(xmlPullParser.getName().equals("machine")) {
                        machineId+=1;
                        areaMachineFk = areaId;
                        machineDetails = new MachineDetails(machineId,xmlPullParser.getAttributeValue("","name"), areaMachineFk);
                    } else if(xmlPullParser.getName().equals("parameter")){
                        parameterId+=1;
                        machineParameterFk = machineId;
                        parameterDetails = new ParameterDetails(xmlPullParser.getAttributeValue("","name"),parameterId, machineParameterFk);
                    }
                }
                else if(eventType == XmlPullParser.TEXT){
                    if(startTag.equals("value")){
                        parameterDetails.setParameterValue(xmlPullParser.getText());
                    }else if(startTag.equals("unit")){
                        parameterDetails.setParameterUnit(xmlPullParser.getText());
                    }else if(startTag.equals("datatype")){
                        parameterDetails.setParameterDataType(xmlPullParser.getText());
                    }
                    startTag = "";
                }
                else if (eventType == XmlPullParser.END_TAG){
                    if(xmlPullParser.getName().equals("area")) {
                        areaDetailsArrayList.add(areaDetails);
                        areaDetails=null;
                    }else if(xmlPullParser.getName().equals("machine")) {
                        areaDetails.getMachineDetailsList().add(machineDetails);
                        parameterId =0;
                    }else if(xmlPullParser.getName().equals("parameter")){
                        machineDetails.getParameterDetailsList().add(parameterDetails);
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return areaDetailsArrayList;
    }

    public void writeXML(List<AreaDetails> areaDetailsArrayList) {
        FileOutputStream fileOutputStream;
        List<MachineDetails> machineDetails = new ArrayList<>();
        List<ParameterDetails> parameterDetails = new ArrayList<>();
        int i=0,j=0,k=0;

        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write("".getBytes());
            xmlSerializer.setOutput(stringWriter);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag("","data");
            for(i=0;i<areaDetailsArrayList.size();i++){
                xmlSerializer.startTag("","area");
                xmlSerializer.attribute("","name", areaDetailsArrayList.get(i).getName());
                machineDetails = areaDetailsArrayList.get(i).getMachineDetailsList();

                for (j=0;j<machineDetails.size();j++){
                    xmlSerializer.startTag("","machine");
                    xmlSerializer.attribute("","name", machineDetails.get(j).getMachineName());
                    xmlSerializer.attribute("","timestamp", machineDetails.get(j).getMachineTimeStamp());
                    parameterDetails = machineDetails.get(j).getParameterDetailsList();

                    for(k=0; k<parameterDetails.size(); k++){
                        xmlSerializer.startTag("","parameter");
                        xmlSerializer.attribute("","name", parameterDetails.get(k).getParameterName());

                        xmlSerializer.startTag("","datatype");
                        xmlSerializer.text(parameterDetails.get(k).getParameterDataType());
                        xmlSerializer.endTag("","datatype");

                        xmlSerializer.startTag("","value");
                        xmlSerializer.text(parameterDetails.get(k).getParameterValue());
                        xmlSerializer.endTag("","value");

                        xmlSerializer.startTag("","unit");
                        xmlSerializer.text(parameterDetails.get(k).getParameterUnit());
                        xmlSerializer.endTag("","unit");
                        xmlSerializer.endTag("", "parameter");
                    }
                    xmlSerializer.endTag("", "machine");
                }
                xmlSerializer.endTag("", "area");
            }
            xmlSerializer.endTag("","data");
            xmlSerializer.endDocument();
            String result = stringWriter.toString();
            fileOutputStream.write(result.getBytes(), 0, result.length());
            fileOutputStream.close();
            Toast.makeText(context, "Export Complete", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}