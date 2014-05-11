package com.example.willpower.yao.controllers;


import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.willpower.controllers.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
//the main activity of social modular
public class shareActivity extends Activity{
	private final static String TAG = "shareActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social_share_yao);
		// TODO - fill in here	
		//share the message to user' facebook
		Button buttonShare = (Button) findViewById(R.id.buttonShare);
		buttonShare.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try
				{
					Intent temp = new Intent(shareActivity.this, loginFragmentActivity.class);
					startActivity(temp);
				}
				catch(Exception e)
				{
					System.out.print(e.getMessage());
				}

			}
		});
		//show the user in google map
		Button buttonGoogleMap = (Button) findViewById(R.id.buttonGoogleMap);
		buttonGoogleMap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try
				{
					if( GooglePlayServicesUtil.isGooglePlayServicesAvailable(shareActivity.this)== ConnectionResult.SUCCESS)
					{
						Intent temp = new Intent(shareActivity.this, googleMapActivity.class);
						startActivity(temp);
					}
				}
				catch(Exception e)
				{
					String temp = e.getMessage();
					Log.e("google", temp);
				}

			}
		});
		//show related twitter message
		Button buttonTwitterSuggestion = (Button) findViewById(R.id.buttonTwitterSuggestion);
		buttonTwitterSuggestion.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent temp = new Intent(shareActivity.this, twitterFriendActivity.class);
				startActivity(temp);
			}
		});
	}
}
