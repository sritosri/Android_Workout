package com.android.AttendenceTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;



/**
 * Collection of utility functions used in this package.
 */
public class ECRUtil {
	
	public static final String HISTORY = "HISTORY";
	public static final String INPUTTYPE = "INPUTTYPE";
	public static final int ECRPREFNUM = 1;
	public static final String ECRPREF = "ECRPREF";
	public static final String CERCOURSEID = "COURSEID";
	public static final String SPNNERTEXT = "SPENNERTEXT";
	public static final String IDNUMBER = "IDNUMBER";
    private static final String TAG = "ECRUtil";
    private static final String mString = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ECR2/";
    //private static final File ECR = new File("/mnt/sdcard/ECR/");
    private static final File ECR = new File(mString);
    private ECRUtil() {
    }


    public static String createFile(String course, String badge, String id) {
    	Time systemTime = new Time();
    	systemTime.setToNow();
    	int monthday = systemTime.monthDay;
    	int month = systemTime.month;
        int hour = systemTime.hour;
        int minute = systemTime.minute;
    	
    	StringBuffer sb = new StringBuffer(255);
        sb.append(course);
        sb.append("_");
        if(monthday < 10)
        sb.append("0");	
        sb.append(systemTime.monthDay);
        if(month <10)
        sb.append("0");	
        sb.append((systemTime.month+1));
        sb.append(systemTime.year);
        sb.append("_");
        if(hour < 10)
        sb.append("0");
        sb.append(systemTime.hour);
        if(minute < 10)
        sb.append("0");
        sb.append(systemTime.minute);
        sb.append(".txt");
        course = sb.toString();
        
    	StringBuffer sb2 = new StringBuffer(255);
        sb2.append(id);
        sb2.append("  - ");
        sb2.append(badge);
        id = sb2.toString();
        		
        
        
    	OutputStream out = null;
        if (!ECR.isDirectory() && !ECR.mkdirs()) {
            throw new AssertionError("Couldn't create " + ECR + ".");
        }
        
        try{
            out = new FileOutputStream(new File(ECR,course));
            //if(data.length() >0){
            out.write(id.getBytes());	
            //}
           // else{
            	// nothing to write
            //}
            
            
        }catch(FileNotFoundException e){
        	Log.e("FILE Open error",e.toString());
        	
        }catch(IOException ioe){
        	Log.e("FILE Open error2",ioe.toString());
        }finally{
        	try{
        	out.close();
        	}catch(IOException ioe){
        		Log.e(TAG,"IO Exception while closing the file");
        	}
        }
        return course;
    }

    public static String createFile(String course, String history) {
    	
    	Time systemTime = new Time();
    	systemTime.setToNow();
    	int monthday = systemTime.monthDay;
    	int month = systemTime.month;
        int hour = systemTime.hour;
        int minute = systemTime.minute;
        
    	StringBuffer sb = new StringBuffer(255);
        sb.append(course);
        sb.append("_");
        if(monthday < 10)
        sb.append("0");	
        sb.append(systemTime.monthDay);
        if(month <10)
        sb.append("0");	
        sb.append((systemTime.month+1));
        sb.append(systemTime.year);
        sb.append("_");
        if(hour < 10)
        sb.append("0");
        sb.append(systemTime.hour);
        if(minute < 10)
        sb.append("0");
        sb.append(systemTime.minute);
        sb.append(".txt");
        course = sb.toString();
    	OutputStream out = null;
        if (!ECR.isDirectory() && !ECR.mkdirs()) {
        	//Toast.makeText(mContext,"Couldn't create ECR directory IO Error", Toast.LENGTH_SHORT).show();
        	throw new AssertionError("Couldn't create " + ECR + ".");
        }
        
        try{
            out = new FileOutputStream(new File(ECR,course));
            //if(data.length() >0){
            out.write(history.getBytes());	
            //}
           // else{
            	// nothing to write
            //}
            
            
        }catch(FileNotFoundException e){
        	Log.e("FILE Open error",e.toString());
        	
        }catch(IOException ioe){
        	Log.e("FILE Open error2",ioe.toString());
        }finally{
        	try{
        	out.close();
        	}catch(IOException ioe){
        		Log.e(TAG,"IO Exception while closing the file");
        	}
        }
       // course = mString+course;
        return course;
    }
    public static void addHistoryID(StringBuilder historySb, String newID,String type){
    	//if( (historySb != null) && (newID !=null) ) 
    	{
        	        	
    		historySb.append(type);
    		historySb.append(",");
    		historySb.append(newID);
    		historySb.append(",");
        	
        	Time systemTime = new Time();
        	systemTime.setToNow();
        	int monthday = systemTime.monthDay;
        	int month = systemTime.month;
            int hour = systemTime.hour;
            int minute = systemTime.minute;
            int sec = systemTime.second;
        	
        	StringBuffer dataTime = new StringBuffer(126);
            
        	dataTime.append(systemTime.year);
        	dataTime.append("/");
        	 
            if(month <10)
            dataTime.append("0");	
            dataTime.append((systemTime.month+1)); 
            dataTime.append("/");
            if(monthday < 10)
            dataTime.append("0");	
            dataTime.append(systemTime.monthDay);        
            dataTime.append(",");
            if(hour < 10)
            dataTime.append("0");
            dataTime.append(systemTime.hour);
            dataTime.append(":");
            if(minute < 10)
            dataTime.append("0");
            dataTime.append(systemTime.minute);
            dataTime.append(":");
            if(sec < 10)
            dataTime.append("0");
            dataTime.append(systemTime.second);
            
            historySb.append(dataTime);
            historySb.append("\n");
            historySb.append("\n");
    	}
    }
    
    public static String formatHistoryID(String newID,String type){
    	//if( (historySb != null) && (newID !=null) ) 
    	{
            int index = newID.indexOf('\n');
            Log.i(TAG, " \n character index = "+index);
            
//            if(index > 0)
//            	{
//            	Log.i(TAG, "readMessage.contains(\n)");
//            	Log.i(TAG, "replace str = "+newID.replace("\n",""));
//            	newID = newID.replace("\n","");
//            }
            Log.i(TAG, "newID = "+newID);
            
        	StringBuilder sb = new StringBuilder(126);    	
        	sb.append(type);
        	sb.append(",");
        	sb.append(newID);
        	sb.append(",");
        	
        	Time systemTime = new Time();
        	systemTime.setToNow();
        	int monthday = systemTime.monthDay;
        	int month = systemTime.month;
            int hour = systemTime.hour;
            int minute = systemTime.minute;
            int sec = systemTime.second;
        	
        	StringBuffer dataTime = new StringBuffer(126);
            
        	dataTime.append(systemTime.year);
        	dataTime.append("/");
        	 
            if(month <10)
            dataTime.append("0");	
            dataTime.append((systemTime.month+1)); 
            dataTime.append("/");
            if(monthday < 10)
            dataTime.append("0");	
            dataTime.append(systemTime.monthDay);        
            dataTime.append(",");
            if(hour < 10)
            dataTime.append("0");
            dataTime.append(systemTime.hour);
            dataTime.append(":");
            if(minute < 10)
            dataTime.append("0");
            dataTime.append(systemTime.minute);
            dataTime.append(":");
            if(sec < 10)
            dataTime.append("0");
            dataTime.append(systemTime.second);
            
            sb.append(dataTime);
            sb.append("\r");
            //newID = sb.toString();
            return sb.toString(); 
    	}
    }
    
    public static String appendDataToString(String newID){
    	//if( (historySb != null) && (newID !=null) ) 
    	{
            int index = newID.indexOf('\n');
            Log.i(TAG, " \n character index = "+index);
            
//            if(index > 0)
//            	{
//            	Log.i(TAG, "readMessage.contains(\n)");
//            	Log.i(TAG, "replace str = "+newID.replace("\n",""));
//            	newID = newID.replace("\n","");
//            }
            Log.i(TAG, "newID = "+newID);
            
        	StringBuilder sb = new StringBuilder(126);    	
        	sb.append(newID);
        	sb.append(",");
        	
        	Time systemTime = new Time();
        	systemTime.setToNow();
        	int monthday = systemTime.monthDay;
        	int month = systemTime.month;
            int hour = systemTime.hour;
            int minute = systemTime.minute;
            int sec = systemTime.second;
        	
        	StringBuffer dataTime = new StringBuffer(126);
            
        	dataTime.append(systemTime.year);
        	dataTime.append("/");
        	 
            if(month <10)
            dataTime.append("0");	
            dataTime.append((systemTime.month+1)); 
            dataTime.append("/");
            if(monthday < 10)
            dataTime.append("0");	
            dataTime.append(systemTime.monthDay);        
            dataTime.append(",");
            if(hour < 10)
            dataTime.append("0");
            dataTime.append(systemTime.hour);
            dataTime.append(":");
            if(minute < 10)
            dataTime.append("0");
            dataTime.append(systemTime.minute);
            dataTime.append(":");
            if(sec < 10)
            dataTime.append("0");
            dataTime.append(systemTime.second);
            
            sb.append(dataTime);
            sb.append("\r");
            //newID = sb.toString();
            return sb.toString(); 
    	}
    }    
    public static void updateFile(String data){
    	
    }
}
