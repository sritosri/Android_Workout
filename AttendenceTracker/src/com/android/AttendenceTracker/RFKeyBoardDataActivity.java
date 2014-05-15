package com.android.AttendenceTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class RFKeyBoardDataActivity extends Activity {
	private ECRDataModel mECRDataModel;
	private EditText mFirstName;
	private EditText mLastName;
	private EditText mBadgeID;
	private EditText mEntityName;
	private EditText mOthers;
	private Button mKbdCntBtn;

	
	/** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.kbd_input);
	        mECRDataModel = ECRDataModel.getECRDataModel();
	    	mFirstName = (EditText)findViewById(R.id.kbdFirstName);
	    	mLastName =(EditText)findViewById(R.id.kbdLastName);
	    	mBadgeID = (EditText)findViewById(R.id.kbdBadgeID);
	    	mEntityName = (EditText)findViewById(R.id.kbdEntityName);
	    	mOthers = (EditText)findViewById(R.id.kbdOthers);
	    	mKbdCntBtn = (Button)findViewById(R.id.kbdInputcontinuebtn);
	    	mKbdCntBtn.setOnClickListener(mOnClicklistener);
	    	
	    }

	    OnClickListener mOnClicklistener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.kbdInputcontinuebtn:
				{
					Intent intent = new Intent();
					intent.setClass(RFKeyBoardDataActivity.this, RFKeyBoardEvent.class);
					saveAttendenceTrackerData();
					startActivity(intent);
					finish();
			     }
			}
			}
	      };
	      
	      public void saveAttendenceTrackerData(){
	      	
	    	  
		    if( (mFirstName.getText().toString().length() >1) &&
		    		(mLastName.getText().toString().length() >1) &&
		    		(mBadgeID.getText().toString().length() >1) &&
		    		(mEntityName.getText().toString().length() >1) ){
		    	    	  
	       mECRDataModel = ECRDataModel.getECRDataModel();
	        
           StringBuilder sb = new StringBuilder(126);
           sb.append(mECRDataModel.getScanType());
           sb.append(",");
           sb.append(mBadgeID.getText().toString());
           sb.append(",");
           sb.append(mFirstName.getText().toString()+" "+mLastName.getText().toString());
           sb.append(",");
           sb.append(mEntityName.getText().toString());
           sb.append(",");
           String str = ECRUtil.appendDataToString(sb.toString());
           mECRDataModel.appendStrToArray(str);
           
//	      	mECRDataModel.appendStrToArray(mFirstName.getText().toString());
//	      	mECRDataModel.appendStrToArray(mLastName.getText().toString());
//	      	mECRDataModel.appendStrToArray(mBadgeID.getText().toString());
//	      	mECRDataModel.appendStrToArray(mEntityName.getText().toString());
//	      	mECRDataModel.appendStrToArray(mOthers.getText().toString());
	      }else{
	    	  Toast.makeText(this," Please fill all fields",Toast.LENGTH_SHORT).show();
	      }
	 }
	      
}
