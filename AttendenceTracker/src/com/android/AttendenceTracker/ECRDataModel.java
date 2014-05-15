package com.android.AttendenceTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.format.Time;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



/**
 * Collection of utility functions used in this package.
 */
public class ECRDataModel {
	
	public static final String HISTORY = "HISTORY";
	public static final String INPUTTYPE = "INPUTTYPE";
	public static final int ECRPREFNUM = 1;
	public static final String ECRPREF = "ECRPREF";
	public static final String CERCOURSEID = "COURSEID";
	public static final String SPNNERTEXT = "SPENNERTEXT";
	public static final String IDNUMBER = "IDNUMBER";
    private static final String TAG = "ECRUtil";
    private static final File ECR = new File("/mnt/sdcard/ECR/");

    private static ECRDataModel mECRDataModel;
    private static ArrayList<String> mHistoryListArrayAdapter;
    private String mEventName;
    private String mEventID;
    private String mRoomID;
    private String mAttendanceID;
    private String mScanType;

    public static ECRDataModel getECRDataModel(){
    	if(mECRDataModel == null){
    		mECRDataModel = new ECRDataModel();
    		mHistoryListArrayAdapter = new ArrayList<String>();
    	}
    	return mECRDataModel;
    }
    
    private ECRDataModel() {
    	
    }

    public void appendStrToArray(String str){
    	mHistoryListArrayAdapter.add(str);
    }
    
    public void removeStrFromArray(int index){
    	mHistoryListArrayAdapter.remove(index);
    }
    
    public ArrayList<String> getList(){
    	return mHistoryListArrayAdapter;
    }
    
    public void removeAll(){
    	mHistoryListArrayAdapter.clear();
    	mEventName = null;
    	mEventID = null;
    	mRoomID = null;
    	mAttendanceID = null;
    }
    
    
    public void saveEventName(String str){
    	mEventName = str;
    }
    
    public void saveEventID(String str){
    	mEventID = str;
    }
    public void saveRoomID(String str){
    	mRoomID = str;
    }
    public void saveAttendance(String str){
    	mAttendanceID = str;
    }
    public void saveScanType(String str){
    	mScanType = str;
    }
    
    public String getEventName(){
    	return mEventName;
    }
    
    public String getEventID(){
    	return mEventID;
    }
    public String getRoomID(){
    	return mRoomID;
    }
    public String getAttendance(){
    	return mAttendanceID;
    } 
    public String getScanType(){
    	return mScanType;
    }    
}
