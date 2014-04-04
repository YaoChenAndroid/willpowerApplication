package com.example.willpower.yao.controllers;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.willpower.controllers.R;
public class shareActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social_share_yao);
		// TODO - fill in here	
		Button buttonShare = (Button) findViewById(R.id.buttonShare);
		buttonShare.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});


	}
}
