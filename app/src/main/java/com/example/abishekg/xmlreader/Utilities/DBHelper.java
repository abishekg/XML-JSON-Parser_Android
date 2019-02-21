package com.example.abishekg.xmlreader.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.abishekg.xmlreader.ModelClasses.MachineDetails;
import com.example.abishekg.xmlreader.ModelClasses.AreaDetails;
import com.example.abishekg.xmlreader.ModelClasses.ParameterDetails;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by abishek.g on 12-Feb-18.
 */

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase sd;
    public DBHelper(Context context) {
        super(context, "androidsqlite.db", null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sd=db;
        createDB();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sd=db;
        dropDB();
        createDB();
    }


    public  void createDB() {
        String areaQuery = "CREATE TABLE IF NOT EXISTS "+ ApplicationConstants.areaTable+ "(" + ApplicationConstants.areaId + " INTEGER PRIMARY KEY , "+ApplicationConstants.areaName+" VARCHAR(100))";
        String machineQuery = "CREATE TABLE IF NOT EXISTS "+ApplicationConstants.machineTable+ "(" + ApplicationConstants.machineId + " INTEGER PRIMARY KEY, "+ApplicationConstants.machineNumber+" INTEGER, "+ApplicationConstants.machineName+" VARCHAR(100), "+ApplicationConstants.machineVersion + " INTEGER, "+ApplicationConstants.machineTimeStamp + " VARCHAR(100), "+ApplicationConstants.areaMachineFk + " INTEGER references " +ApplicationConstants.areaTable+"("+ApplicationConstants.areaId +"))";
        String parameterQuery = "CREATE TABLE IF NOT EXISTS "+ApplicationConstants.parameterTable+ "(" + ApplicationConstants.parameterId + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ApplicationConstants.parameterNumber+" INTEGER, "+ApplicationConstants.parameterName+" VARCHAR(100), "+ApplicationConstants.parameterDatatype + " VARCHAR(100), "+ApplicationConstants.parameterValue + " VARCHAR(100),"+ApplicationConstants.parameterUnit + " VARCHAR(100),"+ApplicationConstants.parameterVersion + " INTEGER, "+ApplicationConstants.parameterTimeStamp + " VARCHAR(100), "+ApplicationConstants.exportState + " VARCHAR(100), "+ApplicationConstants.machineParameterFk + " INTEGER references " +ApplicationConstants.machineTable+"("+ApplicationConstants.machineId +"))";
        sd.execSQL(areaQuery);
        sd.execSQL(machineQuery);
        sd.execSQL(parameterQuery);
    }

    private void dropDB() {
        String areaTableDrop = "DROP TABLE "+ApplicationConstants.areaTable +";";
        String machineTableDrop = "DROP TABLE "+ApplicationConstants.machineTable +";";
        String parameterTableDrop = "DROP TABLE "+ApplicationConstants.parameterTable +";";
        sd.execSQL(areaTableDrop);
        sd.execSQL(machineTableDrop);
        sd.execSQL(parameterTableDrop);

    }

    public AreaDetails AreaDB(@NonNull AreaDetails areaDetails) {
        AreaDetails result = null;
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(ApplicationConstants.areaId,areaDetails.getAreaId());
            contentValues.put(ApplicationConstants.areaName, areaDetails.getName());
            sd = getWritableDatabase();
            if (sd.insert(ApplicationConstants.areaTable, null, contentValues) != -1) {
                result = areaDetails;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sd.close();
        }

        return result;
    }

    public MachineDetails MachineDB(@NonNull MachineDetails machineDetails) {
        MachineDetails result = null;
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(ApplicationConstants.machineNumber, machineDetails.getMachineNumber());
            contentValues.put(ApplicationConstants.machineName, machineDetails.getMachineName());
            contentValues.put(ApplicationConstants.areaMachineFk, machineDetails.getAreaMachinefk());
            contentValues.put(ApplicationConstants.machineTimeStamp, machineDetails.getMachineTimeStamp());
            contentValues.put(ApplicationConstants.machineVersion, machineDetails.getMachineVersionNumber());
            sd = getWritableDatabase();
            if (sd.insert(ApplicationConstants.machineTable, ApplicationConstants.machineId, contentValues) != -1) {
                result = machineDetails;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sd.close();
        }
        return result;
    }

    public ParameterDetails ParameterDB(@NonNull ParameterDetails parameterDetails) {
        ParameterDetails result = null;
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(ApplicationConstants.parameterNumber, parameterDetails.getParameterNumber());
            contentValues.put(ApplicationConstants.parameterName, parameterDetails.getParameterName());
            contentValues.put(ApplicationConstants.parameterDatatype, parameterDetails.getParameterDataType());
            contentValues.put(ApplicationConstants.parameterValue, parameterDetails.getParameterValue());
            contentValues.put(ApplicationConstants.parameterUnit, parameterDetails.getParameterUnit());
            contentValues.put(ApplicationConstants.parameterVersion, parameterDetails.getParameterVersion());
            contentValues.put(ApplicationConstants.parameterTimeStamp, parameterDetails.getParameterTimeStamp());
            contentValues.put(ApplicationConstants.machineParameterFk, parameterDetails.getMachineParameterFk());
            contentValues.put(ApplicationConstants.exportState, parameterDetails.getExportState());
            sd = getWritableDatabase();
            if (sd.insert(ApplicationConstants.parameterTable, ApplicationConstants.parameterId, contentValues) != -1) {
                result = parameterDetails;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sd.close();
        }
        return result;
    }

//    public int getMachineVersionNumber(@NonNull int machineId) {
//        int machineVersion=0;
//        try {
//            sd = this.getReadableDatabase();
//            String query = "SELECT " + ApplicationConstants.machineVersion + " FROM " + ApplicationConstants.machineTable + " WHERE " + ApplicationConstants.machineId + " = " + machineId;
//            Cursor c = sd.rawQuery(query, null);
//            if (c.moveToFirst()) {
//                machineVersion = c.getInt(0);
//            }
//            c.close();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            sd.close();
//        }
//        return machineVersion;
//    }

//    public List<ParameterDetails> getParameterDetailsList(@NonNull int paramVersion,@NonNull int machineIdFk){
//        List<ParameterDetails> parameterDetailsList = new ArrayList<>();
//        try {
//            sd = this.getReadableDatabase();
//            String query = "SELECT * FROM " + ApplicationConstants.parameterTable + " WHERE " + ApplicationConstants.parameterVersion + " = " + paramVersion + " AND " + ApplicationConstants.machineParameterFk +" = " + machineIdFk;
//            Cursor c = sd.rawQuery(query, null);
//            if (c.moveToFirst()) {
//                do {
//                    parameterDetailsList.add(new ParameterDetails(
//                            c.getInt(0),
//                            c.getInt(1),
//                            c.getString(2),
//                            c.getString(3),
//                            c.getString(4),
//                            c.getString(5),
//                            c.getInt(6),
//                            c.getString(7),
//                            c.getInt(8)
//                            ));
//                }
//                while (c.moveToNext());
//            }
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            sd.close();
//        }
//        return parameterDetailsList;
//    }

    public List<MachineDetails> getMachineDetailsList(@NonNull int areaMachineFk) {
        List<MachineDetails> machineDetailsList = new ArrayList<>();
        try{
            sd=this.getReadableDatabase();
            String query = "SELECT * FROM " + ApplicationConstants.machineTable + " WHERE " + ApplicationConstants.areaMachineFk + " = " + areaMachineFk;
            Cursor c = sd.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    machineDetailsList.add(new MachineDetails(
                            c.getInt(0),
                            c.getInt(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getString(4),
                            c.getInt(5)
                            ));
                }
                while (c.moveToNext());
            }
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            sd.close();
        }
        return machineDetailsList;
    }

    public List<AreaDetails> getAreaDetailsList() {
        List<AreaDetails> areaDetailsList = new ArrayList<>();
        try{
            sd=this.getReadableDatabase();
            String query = "SELECT * FROM " + ApplicationConstants.areaTable;
            Cursor c = sd.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    areaDetailsList.add(new AreaDetails(
                            c.getString(1),
                            c.getInt(0)
                    ));
                }
                while (c.moveToNext());
            }
                c.close();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            sd.close();
        }
        return areaDetailsList;
    }

//    public ParameterDetails getParameterDetails(@NonNull int machineFk, @NonNull int versionNumber, @NonNull int parameterNumber) {
//        ParameterDetails parameterDetails = null;
//        try{
//            sd=this.getReadableDatabase();
//            String query = "SELECT * FROM " + ApplicationConstants.parameterTable + " WHERE " + ApplicationConstants.machineParameterFk + " = " + machineFk+ " AND " + ApplicationConstants.parameterVersion +" = " + versionNumber+ " AND " + ApplicationConstants.parameterNumber +" = " + parameterNumber;
//            Cursor c = sd.rawQuery(query, null);
//            if(c.moveToFirst()) {
//                parameterDetails = new ParameterDetails(
//                        c.getInt(0),
//                        c.getInt(1),
//                        c.getString(2),
//                        c.getString(3),
//                        c.getString(4),
//                        c.getString(5),
//                        c.getInt(6),
//                        c.getInt(7)
//                );
//            }c.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        } finally {
//            sd.close();
//        }
//        return parameterDetails;
//    }


    public void updateMachineVersion(@NonNull String versionColumn, @NonNull int versionNumber,@NonNull String name, @NonNull int machineNumber) {
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(versionColumn, versionNumber);
            sd = getWritableDatabase();
            sd.update(ApplicationConstants.machineTable, contentValues, ApplicationConstants.machineName + "='" + name+"' AND " + ApplicationConstants.machineNumber + "="+ machineNumber, null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sd.close();
        }
    }

    public void updateMachineTimeStamp(@NonNull String versionColumn, @NonNull String timeStamp,@NonNull String name, @NonNull int machineNumber) {
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(versionColumn, timeStamp);
            sd = getWritableDatabase();
            sd.update(ApplicationConstants.machineTable, contentValues, ApplicationConstants.machineName + "='" + name+"' AND " + ApplicationConstants.machineNumber + "="+ machineNumber, null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sd.close();
        }
    }
    public void updateParameterExportState(@NonNull String versionColumn, @NonNull String exportState,
//                                           @NonNull int parameterVersion ,
                                           @NonNull int machineFK) {
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(versionColumn, exportState);
            sd = getWritableDatabase();
            sd.update(ApplicationConstants.parameterTable, contentValues,ApplicationConstants.machineParameterFk + "="+ machineFK, null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sd.close();
        }
    }

    public int getMaxMachineVersionNumber(int machineNumber, int areaMachineFk) {
        int versionNumber=0;
        try {
            sd = this.getReadableDatabase();
            String query = "SELECT MAX(" + ApplicationConstants.machineVersion + ") FROM " + ApplicationConstants.machineTable  + " WHERE " + ApplicationConstants.machineNumber + " = " + machineNumber +" AND "+ ApplicationConstants.areaMachineFk + " = " + areaMachineFk;
            Cursor c = sd.rawQuery(query, null);
            if (c.moveToFirst()) {
                versionNumber = c.getInt(0);
            }
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            sd.close();
        }
        return versionNumber;
    }

    public int isMachinePresent(int machineNumber, int areaMachineFk){
        int count=0;
        try {
            sd = this.getReadableDatabase();
            String query = "SELECT COUNT(" + ApplicationConstants.machineName + ") FROM " + ApplicationConstants.machineTable  + " WHERE " + ApplicationConstants.machineNumber + " = " + machineNumber +" AND "+ ApplicationConstants.areaMachineFk + " = " + areaMachineFk;
            Cursor c = sd.rawQuery(query, null);
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            sd.close();
        }
        return count;
    }

    public List<List<ParameterDetails>> getParameterListList(int machineNumber, int machineVersionNumber){
        List<List<ParameterDetails>> parameterListList = new ArrayList<>();
        List<ParameterDetails> parameterList = new ArrayList<>();
        for(int i=1;i<=machineVersionNumber;i++) {
            try {
                sd = this.getReadableDatabase();
                String query = "SELECT * FROM " + ApplicationConstants.parameterTable + " WHERE " + ApplicationConstants.parameterVersion + " = " + i + " AND " + ApplicationConstants.machineParameterFk +" = " + machineNumber;
                Cursor c = sd.rawQuery(query, null);
                if (c.moveToFirst()) {
                    parameterList = new ArrayList<>();
                    do {
                            parameterList.add(new ParameterDetails(
                                    c.getInt(0),
                                    c.getInt(1),
                                    c.getString(2),
                                    c.getString(3),
                                    c.getString(4),
                                    c.getString(5),
                                    c.getInt(6),
                                    c.getString(7),
                                    c.getInt(8)
                            ));

                    }
                    while (c.moveToNext());
                    parameterListList.add(parameterList);
                }
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sd.close();
            }
        }
        return parameterListList;
    }


//    public void updateMachineVersion(@NonNull String versionColumn, @NonNull int versionNumber,@NonNull String name, @NonNull int machineNumber) {
//        ContentValues contentValues = new ContentValues();
//
//        try {
//            contentValues.put(versionColumn, versionNumber);
//            sd = getWritableDatabase();
//            sd.update(ApplicationConstants.machineTable, contentValues, ApplicationConstants.machineName + "='" + name+"' AND " + ApplicationConstants.machineNumber + "="+ machineNumber, null);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            sd.close();
//        }
//    }

    public List<List<ParameterDetails>> getRecentParameterListList(int machineNumber, int machineVersionNumber){
        List<List<ParameterDetails>> parameterListList = new ArrayList<>();
        List<ParameterDetails> parameterList = new ArrayList<>();
            try {
                sd = this.getReadableDatabase();
                String query = "SELECT * FROM " + ApplicationConstants.parameterTable + " WHERE " + ApplicationConstants.machineParameterFk +" = " + machineNumber+ " AND " + ApplicationConstants.exportState +" = 'N'";
                Cursor c = sd.rawQuery(query, null);
                if (c.moveToFirst()) {
                    parameterList = new ArrayList<>();
                    do {
                        parameterList.add(new ParameterDetails(
                                c.getInt(0),
                                c.getInt(1),
                                c.getString(2),
                                c.getString(3),
                                c.getString(4),
                                c.getString(5),
                                c.getInt(6),
                                c.getString(7),
                                c.getInt(8)
                        ));

                    }
                    while (c.moveToNext());
                    parameterListList.add(parameterList);
                }
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sd.close();
            }
        return parameterListList;
    }

}
