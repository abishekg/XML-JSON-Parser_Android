package com.example.abishekg.xmlreader.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abishekg.xmlreader.Adapters.IndexAdapter;
import com.example.abishekg.xmlreader.ModelClasses.AreaDetails;
import com.example.abishekg.xmlreader.ModelClasses.MachineDetails;
import com.example.abishekg.xmlreader.ModelClasses.ParameterDetails;
import com.example.abishekg.xmlreader.R;
import com.example.abishekg.xmlreader.Utilities.ApplicationConstants;
import com.example.abishekg.xmlreader.Utilities.DBHelper;
import com.example.abishekg.xmlreader.Utilities.OnIndexClickListener;
import com.example.abishekg.xmlreader.Utilities.ReadWriteXML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class IndexActivity extends AppCompatActivity implements OnIndexClickListener {

    ReadWriteXML readWriteXML;
    List<AreaDetails> areaDetailsArrayList;
    AreaDetails areaDetails;
    MachineDetails machineDetails;
    ParameterDetails parameterDetails;
    File pathFile;
    DBHelper dbHelper;
    IndexAdapter indexAdapter;
    ExpandableListView expandableListView;
    OnIndexClickListener onIndexClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        pathFile = new File(Environment.getExternalStorageDirectory(),"random.xml");
        readWriteXML = new ReadWriteXML(IndexActivity.this, pathFile);
        areaDetailsArrayList = new ArrayList<>();
        areaDetailsArrayList = readWriteXML.readXMLParser();
        onIndexClickListener = (OnIndexClickListener) this;
        indexAdapter = new IndexAdapter(this, areaDetailsArrayList,onIndexClickListener);
        expandableListView = (ExpandableListView) findViewById(R.id.expandablelistview_activity);
        expandableListView.setFocusable(true);
        expandableListView.setAdapter(indexAdapter);
    }

    @Override
    public void IndexClick(int groupPosiiton, int childPosition, List<AreaDetails> areaDetailsList) {

    }

    public void webExport(View view){
        dbHelper = new DBHelper(getBaseContext());
        areaDetails = null;
        machineDetails = null;
        parameterDetails = null;
        areaDetailsArrayList = new ArrayList<>();
        pathFile = new File(Environment.getExternalStorageDirectory(),"newFile.xml");
        try {
            if(!pathFile.exists())
                pathFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readWriteXML = new ReadWriteXML(this, pathFile);
        areaDetailsArrayList=dbHelper.getAreaDetailsList();
        final Dialog dialog = new Dialog(IndexActivity.this);
        dialog.setContentView(R.layout.alertdialog_layout);
        LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.linearLayout_alertdialog);
        dialog.setTitle("Export Menu");
        dialog.setCanceledOnTouchOutside(true);
        Button exportAllButton = new Button(getBaseContext());
        Button exportFromLastButton = new Button(getBaseContext());
        exportAllButton.setText("Export All");
        exportFromLastButton.setText("Export only Recent Data");
        ll.addView(exportAllButton);
        ll.addView(exportFromLastButton);
        exportAllButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        exportFromLastButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        exportAllButton.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        exportFromLastButton.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        exportAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int areaIndex=0;areaIndex<areaDetailsArrayList.size();areaIndex++){
                    List<MachineDetails> mdList = dbHelper.getMachineDetailsList(areaDetailsArrayList.get(areaIndex).getAreaId());
                    for(int machineIndex = 0; machineIndex<mdList.size(); machineIndex++) {
                        List<List<ParameterDetails>> parameterListList = dbHelper.getParameterListList(
                                mdList.get(machineIndex).getMachineNumber(),
                                mdList.get(machineIndex).getMachineVersionNumber()
                        );
                        MachineDetails md;
                        for (List<ParameterDetails> lpList : parameterListList) {
                            md = new MachineDetails(mdList.get(machineIndex));
                            md.setParameterDetailsList(lpList);
                            dbHelper.updateParameterExportState(
                                    ApplicationConstants.exportState, "Y", md.getMachineNumber());
                            areaDetailsArrayList.get(areaIndex).addMachineDetails(md);
                        }
                    }
                }
                readWriteXML.writeXML(areaDetailsArrayList);
                dialog.hide();
            }
        });
        exportFromLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int areaIndex=0;areaIndex<areaDetailsArrayList.size();areaIndex++){
                    List<MachineDetails> machineDetailsList = dbHelper.getMachineDetailsList(areaDetailsArrayList.get(areaIndex).getAreaId());
                    for(int machineIndex = 0; machineIndex<machineDetailsList.size(); machineIndex++){
                        List<List<ParameterDetails>> parameterList = dbHelper.getRecentParameterListList(
                                machineDetailsList.get(machineIndex).getMachineNumber(),
                                machineDetailsList.get(machineIndex).getMachineVersionNumber()
                        );
                        MachineDetails md;
                        int i=0;
                        for(List<ParameterDetails> lpList:parameterList) {
                            md = new MachineDetails(machineDetailsList.get(machineIndex));
                            md.setParameterDetailsList(lpList);
                            dbHelper.updateParameterExportState(
                                    ApplicationConstants.exportState,
                                    "Y",
                                    md.getMachineNumber()
                            );
                            areaDetailsArrayList.get(areaIndex).addMachineDetails(md);
                        }
                    }
                }
                readWriteXML.writeXML(areaDetailsArrayList);
                dialog.hide();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
}