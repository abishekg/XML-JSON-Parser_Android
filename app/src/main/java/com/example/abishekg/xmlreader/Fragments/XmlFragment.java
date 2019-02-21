package com.example.abishekg.xmlreader.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.abishekg.xmlreader.ModelClasses.MachineDetails;
import com.example.abishekg.xmlreader.R;
import com.example.abishekg.xmlreader.Utilities.OnXmlClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@SuppressLint("ValidFragment")
public class XmlFragment extends Fragment   {
    private MachineDetails machineDetails;
    private OnXmlClickListener delegate;

    private int pos;

    @SuppressLint("ValidFragment")
    public XmlFragment(OnXmlClickListener onXmlClickListener){
        this.delegate  = onXmlClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable("machineDetails")!= null) {
                machineDetails = (MachineDetails) getArguments().getSerializable("machineDetails");
            }
            if (getArguments().getSerializable("Pos")!= null){
                pos = getArguments().getInt("Pos");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xml, container, false);
        LinearLayout linearLayout;
        TextInputLayout textInputLayout;
        TextView textView;
        EditText editText;
        Button submit;
        final List<EditText> allEditTexts = new ArrayList<>();
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutFragment);
        textView = new TextView(getContext());
        textView.setText(machineDetails.getMachineName());
        textView.setTextSize(20);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setLines(1);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(textView);
        for(int i=0;i<machineDetails.getParameterDetailsList().size();i++) {
            textInputLayout = new TextInputLayout(getContext());
            textInputLayout.setHintAnimationEnabled(true);
            editText = new EditText(this.getContext());
            if(machineDetails.getParameterDetailsList().get(i).getParameterDataType().equals("numeric"))
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            else if(machineDetails.getParameterDetailsList().get(i).getParameterDataType().equals("text"))
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
            else
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            editText.setHint(machineDetails.getParameterDetailsList().get(i).getParameterName()+" ("+ machineDetails.getParameterDetailsList().get(i).getParameterUnit()+")");
            editText.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            allEditTexts.add(editText);
            textInputLayout.addView(editText);
            linearLayout.addView(textInputLayout);
        }
        submit = new Button(getContext());
        submit.setText("Save");
        submit.setTextSize(17);
        submit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        submit.setGravity(View.TEXT_ALIGNMENT_CENTER);
        submit.setGravity(Gravity.CENTER);
        linearLayout.addView(submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String timeStamp = simpleDateFormat.format(c.getTime());
                for(int i=0;i<machineDetails.getParameterDetailsList().size();i++){
                    machineDetails.getParameterDetailsList().get(i).setParameterValue(allEditTexts.get(i).getText().toString());
                    machineDetails.getParameterDetailsList().get(i).setExportState("N");
                    machineDetails.getParameterDetailsList().get(i).setParameterTimeStamp(timeStamp);
                }
                machineDetails.setMachineTimeStamp(timeStamp);
                delegate.XmlClick(pos,machineDetails.getMachineId(),machineDetails,timeStamp);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}