package com.android.AttendenceTracker;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
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

public class emailSendActivity extends Activity {

//	LinearLayout mSaveToFileLayout;
//	LinearLayout mSendToEmailLayout;
//	LinearLayout mRemoteSyncLayout;
//	private Button mEmailButton;
	private ECRDataModel mECRDataModel;
//	private boolean mIsFileSaved;
//	private boolean mIsEmailSelected;
//	String mEventTitle;
//	String mDataFileName;
	private Button mSendMail;
	
	private String mFileName;
	private Context mContext;
	
	private static final String TAG = "emailSendActivity.java";
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.email_layout);
	        mContext =this;
	        
	        mSendMail = (Button) findViewById(R.id.emailSendBtn); 
	        mSendMail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i(TAG, " EMail Send Button Clicked");
					sendEmail();
				}
			});
	        
	        Intent intent = getIntent();
	        mFileName = intent.getStringExtra("dataFileName");
	        if(mFileName == null || mFileName.length()==0){
	    		Log.e("emailSendActivity.java","Email activity completed");
	    		Toast.makeText(mContext,"Invalid saved file name",Toast.LENGTH_SHORT).show();
	    		finish();
	        }

	        
	    }


	   
	 private void sendEmail(){
		 
		 Log.e("Send Email "," attachment file name = "+mFileName);

		 EditText emailID = (EditText) findViewById(R.id.email_id);
		 EditText emailSubject = (EditText) findViewById(R.id.email_subject);
//		 EditText emailSenderName = (EditText) findViewById(R.id.email_senderName);
		 
		 
		 
		 String id = emailID.getText().toString();
		 String subject = emailSubject.getText().toString();
//		 String senderName = emailSenderName.getText().toString();
		 //mDataFileName = "file:/"+mDataFileName;
		 	 
		 //Uri emailUri = Uri.parse(mDataFileName );
		 //emailIntent.putExtra(Intent.EXTRA_STREAM, emailUri);
		 
		 Log.e("Send Email ","id ="+id);
		 Log.e("Send Email ","subject ="+subject);
		 if(id.length() == 0){
			 Toast.makeText(mContext,"Invalid email ID",Toast.LENGTH_SHORT).show();
			 return;
		 }
		 
		 
		 String fileDirectory = "file://"+Environment.getExternalStorageDirectory().getAbsolutePath()+"/ECR2/"+mFileName;
		Uri emailUri = Uri.parse(fileDirectory );
		//loadAttachmentInfo(emailUri);
		
		// String fileDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ECR2/";
		// Uri uri = Uri.fromFile(new File(fileDirectory, mDataFileName));
		 Log.e("Send Attachment = ","uri ="+emailUri);
		 Intent i = new Intent(Intent.ACTION_SEND);
		 i.putExtra(android.content.Intent.EXTRA_EMAIL, 
                 new String[]{id});
		 i.putExtra(Intent.EXTRA_SUBJECT, subject);
//		 i.putExtra(Intent.EXTRA_TEXT, senderName);
		 i.putExtra(Intent.EXTRA_STREAM, emailUri);
		 i.setType("message/rfc822");
		 try{
		 Log.e("Send Email ","before emil intent ");
		 startActivityForResult(Intent.createChooser(i, "Send mail"),99);
		 Log.e("Send Email ","after email intent");
		 }catch (ActivityNotFoundException e){
			 Toast.makeText(this,"Email Client not configured on your device.",Toast.LENGTH_SHORT).show();
		 }
	 }
	 
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("Send Email "," Email sent finish email send activity");
		finish();
	}
	 
	 
	  
	    private static final String[] ATTACHMENT_META_NAME_PROJECTION = {
	        OpenableColumns.DISPLAY_NAME
	    };
	    private static final int ATTACHMENT_META_NAME_COLUMN_DISPLAY_NAME = 0;

	    private static final String[] ATTACHMENT_META_SIZE_PROJECTION = {
	        OpenableColumns.SIZE
	    };
	    
	    private static final int ATTACHMENT_META_SIZE_COLUMN_SIZE = 0;
	    public static final int MAX_ATTACHMENT_UPLOAD_SIZE = (5 * 1024 * 1024); 
	    
	   private void loadAttachmentInfo(Uri uri) {
	        long size = -1;
	        String name = null;
	        ContentResolver contentResolver = getContentResolver();
 
	        // Load name & size independently, because not all providers support both
	        Cursor metadataCursor = contentResolver.query(uri, ATTACHMENT_META_NAME_PROJECTION,
	                null, null, null);
	        if (metadataCursor != null) {
	            try {
	                if (metadataCursor.moveToFirst()) {
	                    name = metadataCursor.getString(ATTACHMENT_META_NAME_COLUMN_DISPLAY_NAME);
	                }
	            } finally {
	                metadataCursor.close();
	            }
	        }
	        metadataCursor = contentResolver.query(uri, ATTACHMENT_META_SIZE_PROJECTION,
	                null, null, null);
	        if (metadataCursor != null) {
	            try {
	                if (metadataCursor.moveToFirst()) {
	                    size = metadataCursor.getLong(ATTACHMENT_META_SIZE_COLUMN_SIZE);
	                }
	            } finally {
	                metadataCursor.close();
	            }
	        }

	        // When the name or size are not provided, we need to generate them locally.
	        if (name == null) {
	            name = uri.getLastPathSegment();
	        }
	        if (size < 0) {
	            // if the URI is a file: URI, ask file system for its size
	            if ("file".equalsIgnoreCase(uri.getScheme())) {
	                String path = uri.getPath();
	               // path = "/mnt/sdcard/icon.png";
	                if (path != null) {
	                    File file = new File(path);
	                    size = file.length();  // Returns 0 for file not found
	                }
	            }
 
	            if (size <= 0) {
	                // The size was not measurable;  This attachment is not safe to use.
	                // Quick hack to force a relevant error into the UI
	                // TODO: A proper announcement of the problem
	                size = MAX_ATTACHMENT_UPLOAD_SIZE + 1;
	                Log.e("Max upload size reached ", "size = "+size);
	            }else{
	            	Log.e("proper upload size  ", "size = "+size);
	            }
	        }

	        String contentType = contentResolver.getType(uri);
	        if (contentType == null) {
	            contentType = "";
	        }

	    }
}
