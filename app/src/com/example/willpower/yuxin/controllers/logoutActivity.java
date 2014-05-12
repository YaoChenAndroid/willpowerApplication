package com.example.willpower.yuxin.controllers;

import java.util.List;

import com.example.willpower.controllers.MainActivity;
import com.example.willpower.controllers.R;
import com.example.willpower.yao.controllers.loginActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class logoutActivity extends Activity{
	private final static String TAG = "logoutActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout_yuxin);

		Button buttonLogout = (Button)findViewById(R.id.logoutbt);
		buttonLogout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ParseUser parseUser=ParseUser.getCurrentUser();
				if(parseUser!=null) parseUser.logOut();
				Intent temp = new Intent(logoutActivity.this, loginActivity.class);
				startActivity(temp);
			}
		});
	}

}
