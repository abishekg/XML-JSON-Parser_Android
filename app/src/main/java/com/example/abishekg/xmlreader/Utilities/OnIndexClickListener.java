package com.example.abishekg.xmlreader.Utilities;

import com.example.abishekg.xmlreader.ModelClasses.AreaDetails;
import com.example.abishekg.xmlreader.ModelClasses.MachineDetails;

import java.util.List;

/**
 * Created by abishek.g on 16-Feb-18.
 */

public interface OnIndexClickListener {
    void IndexClick(int groupPosiiton, int childPosition, List<AreaDetails> areaDetailsList);
}
