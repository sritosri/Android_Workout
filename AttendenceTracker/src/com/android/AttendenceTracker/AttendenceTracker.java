package com.android.AttendenceTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AttendenceTracker extends Activity {
	
	private ECRDataModel mECRDataModel;
	EditText mEditorText;
	EditText mEventName;
	EditText mEventID;
	EditText mRoomID;
	EditText mAttendance;
	private static final String mString = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ECR2/";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_tracker);
        
        Log.e("mString ==","mString = "+mString);
        mECRDataModel = ECRDataModel.getECRDataModel();
        mEventName = (EditText)findViewById(R.id.editeventname);
        mEventID = (EditText)findViewById(R.id.editEventID);
        mRoomID = (EditText) findViewById(R.id.editRoomID);
        mAttendance = (EditText) findViewById(R.id.attendanceID);
        
        Button exitButton= (Button)findViewById(R.id.exitBtn);
        exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
        Button continueButton= (Button)findViewById(R.id.MaincontinueBtn);
        continueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveAttendenceTrackerData();
			}
		});
}
    
    public void saveAttendenceTrackerData(){
    	
    	
    	if ( (mEventName.getText().length() > 1) &&
    			(mEventID.getText().length() > 1) &&
    			(mRoomID.getText().length() > 1) &&
    			(mAttendance.getText().length() > 1) ){
    	
        mECRDataModel = ECRDataModel.getECRDataModel();
        
    	mECRDataModel.saveEventName(mEventName.getText().toString());
    	mECRDataModel.saveEventID(mEventID.getText().toString());
    	mECRDataModel.saveRoomID(mRoomID.getText().toString());
    	mECRDataModel.saveAttendance(mAttendance.getText().toString());
    	
    	//Finish the current activity.
    	onContinueButtonClick();
    	
    }else{
    	Toast.makeText(this," Please fill all fields",Toast.LENGTH_SHORT).show();
    }
   }
    	
    private void onContinueButtonClick(){
		Intent intent = new Intent();
		intent.setClass(AttendenceTracker.this, RFKeyBoardEvent.class);
		startActivity(intent);
		finish();
    }
}    