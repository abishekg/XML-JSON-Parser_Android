package com.example.abishekg.xmlreader.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.abishekg.xmlreader.Adapters.XmlAdapter;
import com.example.abishekg.xmlreader.Fragments.XmlFragment;
import com.example.abishekg.xmlreader.ModelClasses.AreaDetails;
import com.example.abishekg.xmlreader.ModelClasses.MachineDetails;
import com.example.abishekg.xmlreader.R;
import com.example.abishekg.xmlreader.Utilities.ApplicationConstants;
import com.example.abishekg.xmlreader.Utilities.DBHelper;
import com.example.abishekg.xmlreader.Utilities.OnXmlClickListener;
import com.example.abishekg.xmlreader.Utilities.ReadWriteXML;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MachineViewPagerActivity extends AppCompatActivity implements OnXmlClickListener {
    DBHelper dbHelper;
    List<AreaDetails> areaDetailsArrayList;
    XmlAdapter xmlAdapter;
    OnXmlClickListener onXmlClickListener;
    ViewPager viewPager;
    Button previousButton, nextButton;
    TextView areaName;
    int index = 0, areaLastIndex,versionNumber=0,machinePage=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmlconfig);
        dbHelper = new DBHelper(getBaseContext());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        previousButton = (Button) findViewById(R.id.previous);
        nextButton = (Button) findViewById(R.id.next);
        areaName = (TextView) findViewById(R.id.name);
        onXmlClickListener = (OnXmlClickListener) this;
        areaDetailsArrayList = new ArrayList<>();
        Intent intent = getIntent();
        areaDetailsArrayList = (List<AreaDetails>) intent.getExtras().getSerializable("AreaList");
        index = intent.getExtras().getInt("groupPosition");
        machinePage = intent.getExtras().getInt("childPosition");
        for(int i = 1;i <= areaDetailsArrayList.size();i++){
            dbHelper.AreaDB(areaDetailsArrayList.get(i-1));
        }
        areaLastIndex = (areaDetailsArrayList.size()) - 1;
        xmlAdapter = new XmlAdapter(getSupportFragmentManager(),this, areaDetailsArrayList.get(index).getMachineDetailsList(),onXmlClickListener);
        viewPager.setAdapter(xmlAdapter);
        viewPager.setCurrentItem(machinePage,true);
        updateAreaName(index);
        invalidateIndex(index);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index>=1 && index<=areaLastIndex){
                    index-=1;
                    updateAreaName(index);
                    invalidateIndex(index);
                    xmlAdapter = new XmlAdapter(getSupportFragmentManager(),MachineViewPagerActivity.this, areaDetailsArrayList.get(index).getMachineDetailsList(),onXmlClickListener);
                    viewPager.setAdapter(xmlAdapter);
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index>=0 && index<= areaLastIndex-1){
                    index+=1;
                    updateAreaName(index);
                    invalidateIndex(index);
                    xmlAdapter = new XmlAdapter(getSupportFragmentManager(),MachineViewPagerActivity.this, areaDetailsArrayList.get(index).getMachineDetailsList(),onXmlClickListener);
                    viewPager.setAdapter(xmlAdapter);
                }
            }
        });
    }

    public void updateAreaName(int index){
        areaName.setText(areaDetailsArrayList.get(index).getName());
    }

    public void invalidateIndex(int index){
        if(index ==0){
            previousButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }
        else if(index == (areaDetailsArrayList.size())-1){
            nextButton.setVisibility(View.INVISIBLE);
            previousButton.setVisibility(View.VISIBLE);
        }
        else {
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void XmlClick(int position, int id, MachineDetails machineDetails, String timeStamp) {
        versionNumber=dbHelper.getMaxMachineVersionNumber(machineDetails.getMachineNumber(),machineDetails.getAreaMachinefk());
        versionNumber++;
        for(int i=0;i<areaDetailsArrayList.get(index).getMachineDetailsList().get(position).getParameterDetailsList().size();i++){
            areaDetailsArrayList.get(index).getMachineDetailsList().get(position).getParameterDetailsList().get(i).setParameterValue(machineDetails.getParameterDetailsList().get(i).getParameterValue());
            areaDetailsArrayList.get(index).getMachineDetailsList().get(position).getParameterDetailsList().get(i).setParameterVersion(versionNumber);
            areaDetailsArrayList.get(index).getMachineDetailsList().get(position).getParameterDetailsList().get(i).setParameterTimeStamp(machineDetails.getMachineTimeStamp());
            areaDetailsArrayList.get(index).getMachineDetailsList().get(position).setMachineVersionNumber(versionNumber);
            areaDetailsArrayList.get(index).getMachineDetailsList().get(position).setMachineTimeStamp(machineDetails.getMachineTimeStamp());
        }
        if(dbHelper.isMachinePresent(machineDetails.getMachineNumber(),machineDetails.getAreaMachinefk())!=0){
            dbHelper.updateMachineVersion(ApplicationConstants.machineVersion, versionNumber, machineDetails.getMachineName(), machineDetails.getMachineNumber());
            dbHelper.updateMachineTimeStamp(ApplicationConstants.machineTimeStamp, machineDetails.getMachineTimeStamp(), machineDetails.getMachineName(), machineDetails.getMachineNumber());
            for (int i=0;i<areaDetailsArrayList.get(index).getMachineDetailsList().get(position).getParameterDetailsList().size();i++){
                dbHelper.ParameterDB(areaDetailsArrayList.get(index).getMachineDetailsList().get(position).getParameterDetailsList().get(i));
            }
        }
        else
        {
            dbHelper.AreaDB(areaDetailsArrayList.get(index));
            dbHelper.MachineDB(areaDetailsArrayList.get(index).getMachineDetailsList().get(position));
            for (int i=0;i<areaDetailsArrayList.get(index).getMachineDetailsList().get(position).getParameterDetailsList().size();i++){
                dbHelper.ParameterDB(areaDetailsArrayList.get(index).getMachineDetailsList().get(position).getParameterDetailsList().get(i));
            }
        }
    }

    public void backPress(View view){
        onBackPressed();
    }
}