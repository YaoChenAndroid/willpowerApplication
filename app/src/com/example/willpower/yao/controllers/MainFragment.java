package com.example.willpower.yao.controllers;
import com.example.willpower.controllers.R;

import java.util.Arrays;  

import android.content.Intent;  
import android.os.Bundle;  
import android.support.v4.app.Fragment;  
import android.util.Log;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.Button;
import android.widget.Toast;
  
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;  
import com.facebook.SessionState;  
import com.facebook.UiLifecycleHelper;  
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;  
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
  
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
        authButton.setReadPermissions(Arrays  
                .asList("email","user_likes", "user_status"));
        
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
		/*FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this.getActivity())
        .setLink("http://developer.android.com/intl/zh-cn/index.html")
        .setDescription("This is SDK test")
        .setName("name")
        .setCaption("Caption")
        .setPicture("https://lh4.googleusercontent.com/-iJyrkSyGc6U/AAAAAAAAAAI/AAAAAAAAAqE/6Ctt86bBArw/photo.jpg")
        .build();
		//uiHelper.trackPendingDialogCall(shareDialog.present());*/
		publishFeedDialog();
	}
	
	private void publishFeedDialog() {
	    Bundle params = new Bundle();
	    params.putString("name", "Facebook SDK for Android");
	    params.putString("caption", "Build great social apps and get more installs.");
	    params.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
	    params.putString("link", "https://developers.facebook.com/android");
	    params.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

	    WebDialog feedDialog = (
	        new WebDialog.FeedDialogBuilder(getActivity(),
	            Session.getActiveSession(),
	            params).setOnCompleteListener(new OnCompleteListener() {

	            @Override
	            public void onComplete(Bundle values,
	                FacebookException error) {
	                if (error == null) {
	                    // When the story is posted, echo the success
	                    // and the post Id.
	                    final String postId = values.getString("post_id");
	                    if (postId != null) {
	                        Toast.makeText(getActivity(),
	                            "Posted story, id: "+postId,
	                            Toast.LENGTH_SHORT).show();
	                    } else {
	                        // User clicked the Cancel button
	                        Toast.makeText(getActivity().getApplicationContext(), 
	                            "Publish cancelled", 
	                            Toast.LENGTH_SHORT).show();
	                    }
	                } else if (error instanceof FacebookOperationCanceledException) {
	                    // User clicked the "x" button
	                    Toast.makeText(getActivity().getApplicationContext(), 
	                        "Publish cancelled", 
	                        Toast.LENGTH_SHORT).show();
	                } else {
	                    // Generic, ex: network error
	                    Toast.makeText(getActivity().getApplicationContext(), 
	                        "Error posting story", 
	                        Toast.LENGTH_SHORT).show();
	                }
	            }

	        }).build());

	    		/*WebDialog feedDialog = (
	    		        new WebDialog.FeedDialogBuilder(getActivity(),
	    		            Session.getActiveSession(),
	    		            params))
	    		        .setOnCompleteListener(new OnCompleteListener(){

							@Override
							public void onComplete(Bundle values,
									FacebookException error) {
								// TODO Auto-generated method stub
								
							}})
	    		        .build();
	    		feedDialog.setTitle("willpower");*/
	    		feedDialog.show();
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
//        super.onActivityResult(requestCode, resultCode, data);  
//        uiHelper.onActivityResult(requestCode, resultCode, data);  
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