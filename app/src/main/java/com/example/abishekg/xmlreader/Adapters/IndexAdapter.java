package com.example.abishekg.xmlreader.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abishekg.xmlreader.Activities.MachineViewPagerActivity;
import com.example.abishekg.xmlreader.ModelClasses.AreaDetails;
import com.example.abishekg.xmlreader.ModelClasses.MachineDetails;
import com.example.abishekg.xmlreader.R;
import com.example.abishekg.xmlreader.Utilities.OnIndexClickListener;

import java.io.Serializable;
import java.util.List;

/**
 * Created by abishek.g on 15-Feb-18.
 */

public class IndexAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<AreaDetails> areaDetailsArrayList;
    OnIndexClickListener onIndexClickListener;

    public IndexAdapter(Context context, List<AreaDetails> areaDetailsArrayList, OnIndexClickListener onIndexClickListener) {
        this.context = context;
        this.areaDetailsArrayList = areaDetailsArrayList;
        this.onIndexClickListener = onIndexClickListener;
    }

    @Override
    public int getGroupCount() {
        return areaDetailsArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return areaDetailsArrayList.get(groupPosition).getMachineDetailsList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return areaDetailsArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return areaDetailsArrayList.get(groupPosition).getMachineDetailsList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        AreaDetails areaDetails = (AreaDetails) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandablelistview_layout, parent, false);
        }
        TextView areaName = (TextView) convertView.findViewById(R.id.textView);
        areaName.setText(areaDetails.getName());
        areaName.setTypeface(null,Typeface.NORMAL);
        areaName.setTextSize(25);
        areaName.setPadding(80,0,0,0);
        if(isExpanded){
            areaName.setTypeface(null, Typeface.BOLD);
            areaName.setTextSize(30);
        }
        if(!isExpanded){
            areaName.setTypeface(null,Typeface.NORMAL);
            areaName.setTextSize(25);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final MachineDetails machineDetails = (MachineDetails) getChild(groupPosition, childPosition);
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandablelistview_layout, parent, false);
        }
        TextView machineName = (TextView) convertView.findViewById(R.id.textView);
        machineName.setText(machineDetails.getMachineName());
        machineName.setPadding(150,0,0,0);
        machineName.setTextSize(20);
        machineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,machineDetails.getMachineName() , Toast.LENGTH_SHORT).show();
//                onIndexClickListener.IndexClick(groupPosition,childPosition,  areaDetailsArrayList);
                Intent intent = new Intent(context, MachineViewPagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("AreaList", (Serializable) areaDetailsArrayList);
                bundle.putInt("groupPosition", groupPosition);
                bundle.putInt("childPosition", childPosition);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
