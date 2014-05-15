package com.android.AttendenceTracker;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DataSyncOptionsActivity extends Activity {

//	LinearLayout mSaveToFileLayout;
//	LinearLayout mSendToEmailLayout;
//	LinearLayout mRemoteSyncLayout;
//	private Button mEmailButton;
	private ECRDataModel mECRDataModel;
	private boolean mIsFileSaved;
//	private boolean mIsEmailSelected;
//	String mEventTitle;
	String mDataFileName;
//	private Button mSendMail;
	
	private static final String TAG = "DataSyncOptionsActivity.java";
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.email_remote_sync_layout);
	           
	        
	       	Button emailButton =  (Button) findViewById(R.id.emailSelection);
	       	emailButton.setOnClickListener(mOnClicklistener); 
	        
	        
	        Button remoteSyncButton= (Button)findViewById(R.id.remotesyncSelection);
	        remoteSyncButton.setOnClickListener(mOnClicklistener); 

	        
	        Button syncContinueButton= (Button)findViewById(R.id.syncContinuebtn);
	        syncContinueButton.setOnClickListener(mOnClicklistener);
	        
	        Button saveFileExitButton= (Button)findViewById(R.id.saveFileExit);
	        saveFileExitButton.setOnClickListener(mOnClicklistener);
	        
	        
	    }
	    
	    
	    OnClickListener mOnClicklistener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e(TAG,"onClick id = "+v.getId());
				switch(v.getId()){
				
				case R.id.emailSelection:{
					saveDataToFile();
					launchEmailSendActivity();
				}
				break;
				case R.id.remotesyncSelection:{
					Toast.makeText(DataSyncOptionsActivity.this,"This option is not avaliable in this version",Toast.LENGTH_SHORT).show();
				}
					break;
				case R.id.syncContinuebtn:
				{
					//saveDataToFile();
					continueButton();
				}
				break;	
				case R.id.saveFileExit:
				{
					saveDataToFile();
					continueButton();
				}
				break;	
				default:
				break;
				}
			}
	    
	    };
	    
	    private void saveDataToFile(){
	    	mECRDataModel = ECRDataModel.getECRDataModel();
	    	//Save data to file.
            // if( history !=null) //course != null && badge != null && id != null)
            if(!mIsFileSaved)
	    	{
           	StringBuilder sb = new StringBuilder(512);
           	ArrayList<String> list = mECRDataModel.getList();
           	
//           	sb.append(mECRDataModel.getScanType());
//           	sb.append(",");
//           	 
           	
           	for(int i=0; i< list.size(); i++){
           		String str = list.get(i);
           		int len = str.length();
           		 int index = str.indexOf('\n');
           		 Log.i(TAG, " \n character index = "+index);
           		                
           		 if(index > 0 && index < len ){
           		   Log.i(TAG, "str.contains(\n)");
           		   Log.i(TAG, "replace str = "+str.replace("\n",""));
           		   str = str.replace("\n","");
           		     }
           		 sb.append(str);
           		
           		//sb.append(list.get(i));
           	}
        	//mECRDataModel.getEventName();
        	//mECRDataModel.getEventID();
        	//mECRDataModel.getRoomID();
        	//mECRDataModel.getAttendance();
        	
           //	ECRUtil.createFile(mECRDataModel.getEventName(),mECRDataModel.getEventID(),mECRDataModel.getRoomID());
           	mDataFileName =	ECRUtil.createFile(mECRDataModel.getEventID(),sb.toString());
           	mECRDataModel.removeAll();
           	
           	//set file save flag.
           	mIsFileSaved = true;
            }else{
            	Toast.makeText(this," Data already saved to file.",Toast.LENGTH_SHORT).show();
            }
	    }
	   

	 
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(TAG, " Email sent ");

		continueButton();
		finish();
	}
	 
	  private void continueButton(){
			Intent intent = new Intent();
			intent.setClass(this, FinalSavedActivity.class);
			startActivity(intent);
			finish();
	  }
	  
	   
	   private void launchEmailSendActivity(){
			Intent intent = new Intent();
			intent.setClass(this, emailSendActivity.class);
			intent.putExtra("dataFileName", mDataFileName);
			startActivityForResult(intent,101);
	   }
}
