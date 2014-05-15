package com.android.AttendenceTracker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RFKeyBoardEvent extends Activity {
	private static final String TAG = "RFKeyBoardEvent";
	String mEventTitle;
	LinearLayout mRFKBReaderLayout;
	LinearLayout mRFReaderLayout;
	LinearLayout mManualReaderLayout;
	EditText mRfEditText;
	Button mRfButton;
	Button mKbdButton;
	private TextWatcher mAutoTextWatcher;
	private ECRDataModel mECRDataModel;
	String mScanType;
	
	ArrayAdapter mHistoryAdapter; 
	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Log.e("RFKbd History","onCreate");
	        setContentView(R.layout.rf_keyboard);
	        
	        mECRDataModel = ECRDataModel.getECRDataModel();
	        mScanType = "00"; //By default manual scan type.
	        
	        mRfEditText = (EditText) findViewById(R.id.rfEditText);
	        Button continueButton= (Button)findViewById(R.id.rfKbdCntBtn);
	        continueButton.setOnClickListener(mOnClicklistener);
	        
	        mRfButton= (Button)findViewById(R.id.rfreader);
	        mRfButton.setOnClickListener(mOnClicklistener);
	        
	        mKbdButton = (Button)findViewById(R.id.manualreader);
	        mKbdButton.setOnClickListener(mOnClicklistener);
	        
	        initList();
	        rfTextListener();
	        mRfEditText.addTextChangedListener(mAutoTextWatcher);
	        mRfEditText.setClickable(true);
	        mRfEditText.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.e("vinod","Edit text click");
				}
			});
	    }
	    
	    @Override
	    protected void onPause() {
	    	// TODO Auto-generated method stub
	    	super.onPause();
	    	Log.e("RFKbd History","onPause");
	    }
	    
	    @Override
	    protected void onStop() {
	    	// TODO Auto-generated method stub
	    	super.onStop();
	    	Log.e("RFKbd History","onStop");
	    }
	    
	    protected void onDestroy() {
	    	super.onDestroy();
	    	mRfEditText.removeTextChangedListener(mAutoTextWatcher);
	    	Log.e("RFKbd History","onDestroy");
	    };
	    
	    private void enableRFText(){
			mRfEditText.setVisibility(View.VISIBLE);
			mRfEditText.setFocusable(true);
//			mRfEditText.setPressed(true);
//			mRfEditText.performClick();
			mRfEditText.requestFocus();
			//KeyEvent.KEYCODE_PAGE_UP
		//	mRfEditText.dispatchKeyEvent (KeyEvent.KEYCODE_PAGE_UP);
			//mRfEditText..clearComposingText();
			//MotionEvent.obtain(1, 1,1, x, y, metaState)
			//mRfEditText.dispatchTouchEvent(MotionEvent.obtain(1, 1,1, 1, 1, 1));
			
			//mRfEditText.addTextChangedListener(mAutoTextWatcher);
	    }
	    private void disableRFText(){
			mRfEditText.removeTextChangedListener(mAutoTextWatcher);
			mRfEditText.setVisibility(View.GONE);
	    }
	    
	    OnClickListener mOnClicklistener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.rfKbdCntBtn:
				{
					disableRFText();
					mRfButton.setVisibility(View.VISIBLE);
					mKbdButton.setVisibility(View.VISIBLE);
					mECRDataModel.saveScanType(mScanType);
					
					Intent intent = new Intent();
					intent.setClass(RFKeyBoardEvent.this, DataSyncOptionsActivity.class);
					startActivity(intent);
					finish();
				}
					break;
				case R.id.rfreader:{
					enableRFText();
					mRfButton.setVisibility(View.GONE);
					mKbdButton.setVisibility(View.VISIBLE);
					mScanType = "01";
					mECRDataModel.saveScanType(mScanType);
				}
					break;
				case R.id.manualreader:{
					disableRFText();
					mKbdButton.setVisibility(View.GONE);
					mRfButton.setVisibility(View.VISIBLE);
					mScanType = "00";
					mECRDataModel.saveScanType(mScanType);
					
					Intent intent = new Intent();
					intent.setClass(RFKeyBoardEvent.this, RFKeyBoardDataActivity.class);
					startActivity(intent);
					finish();
				}
					break;
					default:break;
				}
			}
	    
	    };
	    
	    private void initList(){
	    	Log.d(TAG, "initList start");
	    	
	    	mHistoryAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
	        ListView historyList = (ListView) findViewById(R.id.history_list);	        
	        historyList.setAdapter(mHistoryAdapter);
	        mHistoryAdapter.notifyDataSetChanged();
	        updateListItems();
	    }
	    
	   private void rfTextListener(){
		   mAutoTextWatcher  = new TextWatcher() {
				
				public void onTextChanged(CharSequence st, int start, int before, int count) {
					// TODO Auto-generated method stub
		               Log.i(TAG, "NFC values after read ###");
		               String s = st.toString();   
		                
		                int index = s.indexOf('\n');
		                if(index > 0){
		                	Log.i(TAG, "s.contains(\r)");
		                
		                StringBuilder sb = new StringBuilder(126);
		                sb.append(mECRDataModel.getScanType());
		                sb.append(",");
		                sb.append(s);
		                sb.append(",");
		                sb.append(mECRDataModel.getEventName());
		                sb.append(",");
		                sb.append(mECRDataModel.getEventID());
		                sb.append(",");
		                sb.append(mECRDataModel.getRoomID());
		                sb.append(",");
		                sb.append(mECRDataModel.getAttendance());
		                String str = ECRUtil.appendDataToString(sb.toString());
		                mECRDataModel.appendStrToArray(str);
		                
		                //mECRDataModel.appendStrToArray(s);
		                mRfEditText.setText("");
		                
		                updateListItems();
		               // restartActivity();
		               // mHistoryAdapter.notifyDataSetChanged();

		                }
					
				}
				
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}


				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}
			};
			
			
	   }

	    private void updateListItems(){
	    	//initList();
	    	Log.d(TAG, "updateListItems");
	    	ArrayList<String> history = mECRDataModel.getList();
	        
	    
	        // If there are paired devices, add each one to the ArrayAdapter
	        if (history.size() > 0) {
	        	mHistoryAdapter.clear();
	        	Log.d(TAG, "updateListItems history.size() > 0");
	            for (int i =0; i<history.size(); i++) {
	            	mHistoryAdapter.add(history.get(i));
	            }
	        }
	        else{
	        	Log.d(TAG, "updateListItems history.size() ==  0");
	        	mHistoryAdapter.add("Empty List.. To Auto scan select RF button");
	        }
	        mHistoryAdapter.notifyDataSetChanged();
	    }
}
