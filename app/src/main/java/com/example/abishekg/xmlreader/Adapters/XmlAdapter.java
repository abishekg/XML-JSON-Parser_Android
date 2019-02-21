package com.example.abishekg.xmlreader.Adapters;

/**
 * Created by abishek.g on 13-Feb-18.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.abishekg.xmlreader.ModelClasses.MachineDetails;
import com.example.abishekg.xmlreader.Utilities.OnXmlClickListener;
import com.example.abishekg.xmlreader.Fragments.XmlFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abishek.g on 08-Feb-18.
 */

public class XmlAdapter extends FragmentStatePagerAdapter implements Serializable {

    private static final int numItems = 0;
    private List<MachineDetails> machineDetailsList;
    Context xmlContext;
    private OnXmlClickListener onXmlClickListener;



    public XmlAdapter(FragmentManager fragmentManager, Context xmlContext, List<MachineDetails> machineDetailsArrayList, OnXmlClickListener onXmlClickListener) {
        super(fragmentManager);
        this.xmlContext = xmlContext;
        machineDetailsList = new ArrayList<>();
        this.machineDetailsList = machineDetailsArrayList;
        this.onXmlClickListener = onXmlClickListener;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new XmlFragment(onXmlClickListener);
        Bundle args = new Bundle();
        args.putSerializable("machineDetails",machineDetailsList.get(position));
        args.putInt("Pos",position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return machineDetailsList.size();
    }



}
