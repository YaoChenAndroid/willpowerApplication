package com.example.willpower.yao.controllers;
import com.example.willpower.controllers.R;

import java.util.Arrays;  

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;  
import android.support.v4.app.Fragment;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Session;  
import com.facebook.SessionState;  
import com.facebook.UiLifecycleHelper;  
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.Callback;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.facebook.widget.LoginButton;  

public class MainFragment extends Fragment {  
	private final static String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;  
    
    private Button sendRequestButton;
    private boolean overflag = false;
    //the call back function when the user's facebook status change.
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
        //must be login button. used to open facebook share dialog in the sendRequestDialog()
        uiHelper = new UiLifecycleHelper(getActivity(), callback);  
        uiHelper.onCreate(savedInstanceState);  

    }  
  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.activity_social_facebook_login_yao, container, false);  
        
        //set the permission of willpower in user's Facebook
        LoginButton authButton = (LoginButton) view  
                .findViewById(R.id.login_button);  
        authButton.setFragment(this);  
//        authButton.setReadPermissions(Arrays  
//                .asList("email","user_likes", "user_status"));
        //need the publish permission to publish photo to timeline
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
    //if the users' facebook state changed
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        sendRequestButton.setVisibility(View.VISIBLE);
	    } else if (state.isClosed()) {
	        sendRequestButton.setVisibility(View.INVISIBLE);
	    }
	}
	//share the message to user' facebook
	private void sendRequestDialog() {

		if(FacebookDialog.canPresentShareDialog(this.getActivity(), FacebookDialog.ShareDialogFeature.SHARE_DIALOG))
		{
			//if the Facebook application has been installed in user's device
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this.getActivity())
	        .setLink("http://developer.android.com/intl/zh-cn/index.html")
	        .setDescription("This is SDK test")
	        .setName("name")
	        .setCaption("Caption")
	        .setPicture("https://lh4.googleusercontent.com/-iJyrkSyGc6U/AAAAAAAAAAI/AAAAAAAAAqE/6Ctt86bBArw/photo.jpg")
	        .build();
			uiHelper.trackPendingDialogCall(shareDialog.present());	
			Toast.makeText(this.getActivity(), "Your achivement has shared with your friends!",
					   Toast.LENGTH_LONG).show();
			overflag = true;
		}
		else
		{
			//if the facebook app did not installed in user's device
			WebView myWebView = new WebView(getActivity());
			myWebView.getSettings().setUserAgentString("Mozilla/5.0 (compatible; MSIE 10.0; Windows Phone 8.0; Trident/6.0; IEMobile/10.0; ARM; Touch; NOKIA; Lumia 920)");
			myWebView.getSettings().setLoadWithOverviewMode(true);
			myWebView.getSettings().setUseWideViewPort(true);
			myWebView.getSettings().setSupportZoom(false);
			//myWebView.getSettings().setJavaScriptEnabled(true);

			myWebView.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// TODO Auto-generated method stub
					view.loadUrl(url);
					return false;
				}
			});

			myWebView.loadUrl("https://www.facebook.com/dialog/feed?app_id=510119485760833&display=popup&caption=An%20example%20caption&link=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2Fdialogs%2F&redirect_uri=http://willpower.parseapp.com/");
			
		    AlertDialog.Builder dialog = new AlertDialog.Builder(this.getActivity());
		    dialog.setView(myWebView);
		    dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {

		            public void onClick(DialogInterface dialog, int which) {

		                dialog.dismiss();
		            }
		        });
		    dialog.show();
			Toast.makeText(this.getActivity(), "Loading...",Toast.LENGTH_LONG).show();
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
        if(overflag)
        {
        	finish();
        	overflag = false;
        }
        	
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
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//must implement, if not the login button is not correct
		uiHelper.onActivityResult(requestCode, resultCode, data, new Callback(){

			@Override
			public void onComplete(PendingCall pendingCall, Bundle data) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(PendingCall pendingCall, Exception error,
					Bundle data) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}  