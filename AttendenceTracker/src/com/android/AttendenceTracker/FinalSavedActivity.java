package com.android.AttendenceTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FinalSavedActivity extends Activity {
   String mEventTitle;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_saved_screen);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mEventTitle=  new String("Final Saved Activity");
        if(mEventTitle == null || mEventTitle.length()==0)
        	mEventTitle = "Event";
        TextView eventTitle = (TextView)findViewById(R.id.event_title);
       	eventTitle.setText(mEventTitle + " Attendance Tracker ");
       	
       	Button extBtn = (Button) findViewById(R.id.appExitButton);
       	extBtn.setOnClickListener(mOnClicklistener);
    }
    
    OnClickListener mOnClicklistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {

			switch(v.getId()){
			case R.id.appExitButton:
			{
				finish();
			}
			break;
			default:break;
			}
		}
    };    
}