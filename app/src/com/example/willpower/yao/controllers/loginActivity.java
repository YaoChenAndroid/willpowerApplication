package com.example.willpower.yao.controllers;

import java.util.List;

import com.example.willpower.controllers.MainActivity;
import com.example.willpower.controllers.R;
import com.example.willpower.models.User;
import com.example.willpower.yuxin.controllers.logoutActivity;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
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

public class loginActivity extends Activity{
	private final static String TAG = "loginActivity";
	private Animation shakeAction;
	private String emailStr;
	private String passwordStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_yao);
        this.shakeAction = new TranslateAnimation(-3,3,0,0);
        this.shakeAction.setDuration(100);
        this.shakeAction.setRepeatCount(5);
		Button buttonLogin = (Button)findViewById(R.id.buttonLogin);
		Button buttonReg = (Button)findViewById(R.id.buttonRegActivity);
		buttonLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*try {
					if(valid())
					{
			            ParseQuery<User> userQuery = User.getQuery();   
			            userQuery.whereEqualTo("Email", emailStr);
			            userQuery.whereEqualTo("Password", passwordStr);
			            
			            List<User> userList;
						userList = userQuery.find();
			            if(userList.size() != 0)
			            {
					        CurrentUserInfo temp = CurrentUserInfo.getInstance();
					        User user = userList.get(0);
					        temp.userName = user.getName();
					        temp.UserId = user.getObjectId();
					        temp.UserGoal = user.getGoal();
			            	finish();
			            }
			            else{
			            	Toast.makeText(arg0.getContext(), getResources().getString(R.string.yao_login_i_email_password_error), Toast.LENGTH_SHORT).show();
			            }
							
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, e.getMessage());
				}*/
				valid();
				ParseUser.logInInBackground(emailStr, passwordStr, new LogInCallback() {
					
					@Override
					public void done(ParseUser user, ParseException e) {
						// TODO Auto-generated method stub
						if(user!=null){
							ParseQuery<ParseObject> query = ParseQuery.getQuery("GameData");
							query.whereEqualTo("userObjectId", user.getObjectId());
							query.findInBackground(new FindCallback<ParseObject>() {

								@Override
								public void done(List<ParseObject> arg0, ParseException arg1) {
									// TODO Auto-generated method stub
									CurrentUserInfo.getInstance().UserGoal=arg0.get(0).getString("Goal");
								}
								
							});
							Toast.makeText(loginActivity.this, "welcome back! "+emailStr, Toast.LENGTH_LONG).show();
							Intent temp = new Intent(loginActivity.this, MainActivity.class);
							startActivity(temp);
						
						}else{
							Toast.makeText(loginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}
				});
				
			}
		});
		buttonReg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startReg();


			}
			
		});
	}
	//check the textbox validation
    private boolean valid()// throws ParseException
    {
		EditText emailAddress = (EditText)findViewById(R.id.editTextLoginEmail);
		emailStr = emailAddress.getText().toString().trim();
		EditText password = (EditText)findViewById(R.id.editTextLoginPassword);
		passwordStr= password.getText().toString().trim();
		
		/*
        if(emailStr.length() == 0)
        {
        	setRequired(emailAddress, getString(R.string.yao_login_i_empty));
        	return false;
        }

        if(passwordStr.length() == 0)
        {
            this.setRequired(password, this.getString(R.string.yao_login_i_empty) );
            return false;
        }*/
        return true;
    }
    //show the animation of error message
    protected void setRequired(View view,String... error){
        view.startAnimation(shakeAction);
        view.setFocusable(true);
        view.requestFocus();
        view.setFocusableInTouchMode(true);
        if(view instanceof EditText){
            ((EditText) view).setError(error[0]);
        }
    }
    //start register activity
	private void startReg()
	{
		Intent intent = new Intent(this, registerActivity.class);
		startActivityForResult(intent, 100);
	}
	@Override
	//after register, finish current activity
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		if(requestCode == 100)
		{
			if(resultCode == this.RESULT_OK)
				finish();
		}
		
	}
}
