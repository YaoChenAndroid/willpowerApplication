package com.example.willpower.yao.controllers;
import com.example.willpower.controllers.R;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;  

import android.content.Intent;  
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;  
import android.support.v4.app.Fragment;  
import android.util.Log;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;  
import com.facebook.SessionState;  
import com.facebook.UiLifecycleHelper;  
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;  

public class MainFragment extends Fragment {  
    private UiLifecycleHelper uiHelper;  
    
    private Button sendRequestButton;
    private Session.StatusCallback callback = new Session.StatusCallback() {  
        @Override  
        public void call(Session session, SessionState state,  
                Exception exception) {  
            onSessionStateChange(session, state, exception);  
        }  
    };  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        uiHelper = new UiLifecycleHelper(getActivity(), callback);  
        uiHelper.onCreate(savedInstanceState);  

    }  
  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.activity_social_facebook_login_yao, container, false);  
  
        LoginButton authButton = (LoginButton) view  
                .findViewById(R.id.login_button);  
        authButton.setFragment(this);  
//        authButton.setReadPermissions(Arrays  
//                .asList("email","user_likes", "user_status"));
        authButton.setPublishPermissions(Arrays  
                .asList("publish_actions", "publish_stream"));
        sendRequestButton = (Button) view.findViewById(R.id.sendRequestButton);
        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestDialog(); 

            }
        });
        return view;  
    }  
    
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        sendRequestButton.setVisibility(View.VISIBLE);
//	        sendRequestDialog();
	    } else if (state.isClosed()) {
	        sendRequestButton.setVisibility(View.INVISIBLE);
	    }
	}
	
	private void sendRequestDialog() {

		if(FacebookDialog.canPresentShareDialog(this.getActivity(), FacebookDialog.ShareDialogFeature.SHARE_DIALOG))
		{
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this.getActivity())
	        .setLink("http://developer.android.com/intl/zh-cn/index.html")
	        .setDescription("This is SDK test")
	        .setName("name")
	        .setCaption("Caption")
	        .setPicture("https://lh4.googleusercontent.com/-iJyrkSyGc6U/AAAAAAAAAAI/AAAAAAAAAqE/6Ctt86bBArw/photo.jpg")
	        .build();
			uiHelper.trackPendingDialogCall(shareDialog.present());			
		}
		else
		{
			try
			{

				Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
			    ByteArrayOutputStream stream = new ByteArrayOutputStream();
			    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
				com.facebook.Request request = com.facebook.Request.newUploadPhotoRequest(Session.getActiveSession(), bmp, new Request.Callback() {

					@Override
					public void onCompleted(Response response) {
						// TODO Auto-generated method stub
						
					}});

				Bundle params = request.getParameters();
				params.putString("name", "I finally upload the message and phone to the timeline by the facebook sdk");
				request.setParameters(params);
				Request.executeBatchAsync(request);
			}catch(Exception e){
				Log.e("shareDialog", e.getMessage());
			}
			Toast.makeText(this.getActivity(), "Your achivement has shared with your friends!",
					   Toast.LENGTH_LONG).show();
		}

}
      
    @Override  
    public void onResume() {  
        super.onResume();  
  
        // For scenarios where the main activity is launched and user  
        // session is not null, the session state change notification  
        // may not be triggered. Trigger it if it's open/closed.  
        Session session = Session.getActiveSession();  
        if (session != null && (session.isOpened() || session.isClosed())) {  
            onSessionStateChange(session, session.getState(), null);  
        }  
  
        uiHelper.onResume();  
    }  
  
    @Override  
    public void onActivityResult(int requestCode, int resultCode, Intent data) {  

	        super.onActivityResult(requestCode, resultCode, data);
	
	        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	            @Override
	            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	                Log.e("Activity", String.format("Error: %s", error.toString()));
	            }
	
	            @Override
	            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	                Log.i("Activity", "Success!");
	                
	                finish();
	            }
	        });
    }  


    public void finish()
    {
    	this.getActivity().finish();
    }
  
    @Override  
    public void onPause() {  
        super.onPause();  
        uiHelper.onPause();  
    }  
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        uiHelper.onDestroy();  
    }  
  
    @Override  
    public void onSaveInstanceState(Bundle outState) {  
        super.onSaveInstanceState(outState);  
        uiHelper.onSaveInstanceState(outState);  
    }  
}  