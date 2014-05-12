package com.example.willpower.yao.controllers;

import java.util.List;

import com.example.willpower.controllers.R;
import com.example.willpower.models.User;
import com.example.willpower.models.friendLoc;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

public class registerActivity extends Activity{
	private final static String TAG = "registerActivity";
	private Animation shakeAction;
	private String emailStr;
	private String userNameStr;
	private String passwordStr;
	private String goals;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_yao);
        this.shakeAction = new TranslateAnimation(-3,3,0,0);
        this.shakeAction.setDuration(100);
        this.shakeAction.setRepeatCount(5);
		Button buttonReg = (Button)findViewById(R.id.buttonReg);
		Button buttonCancel = (Button)findViewById(R.id.buttonCancle);
		buttonReg.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					if(valid())
					{
						saveUser();
						finish();
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		});
		buttonCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg){
				CancelMessage();
				finish();
			}
		});
		
	}
	private void CancelMessage(){
		Intent intent = new Intent();
		setResult(this.RESULT_CANCELED,intent);
	}
	//save register information to the web server
	private void saveUser() {
		// TODO Auto-generated method stub
        final User user = new User();
        user.setEmail(emailStr);
        user.setName(userNameStr);
        user.setPassword(passwordStr);
        user.setGoal(goals);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        user.setACL(acl);
        //Updata the location data
        user.saveInBackground(new SaveCallback(){
			@Override
			public void done(ParseException arg0) {
				// TODO Auto-generated method stub
		        CurrentUserInfo temp = CurrentUserInfo.getInstance();
		        temp.userName = userNameStr;
		        temp.UserId = user.getObjectId();
		        temp.UserGoal = goals;
			}
        	
        });
        ParseUser parseUser=new ParseUser();
        parseUser.setUsername(userNameStr);
        parseUser.setPassword(passwordStr);
        
        parseUser.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(ParseException arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        Intent intent = new Intent();
        setResult(this.RESULT_OK, intent);
        
	}
	//check whether the textbox is empty and the input validation
    private boolean valid() throws ParseException
    {
    	EditText emailAddress = (EditText)findViewById(R.id.editTextEmail);
		EditText userName = (EditText)findViewById(R.id.editTextName);
		EditText goal = (EditText)findViewById(R.id.editTextGOals);
		EditText password = (EditText)findViewById(R.id.editTextPassword);
		EditText passwordComfirm = (EditText)findViewById(R.id.editTextPasswordComfirm);
		
		emailStr = emailAddress.getText().toString().trim();
        if(emailStr.length() == 0)
        {
        	setRequired(emailAddress, getString(R.string.yao_login_i_empty));
        	return false;
        }
        else{
            ParseQuery<User> userQuery = User.getQuery();   
            userQuery.whereEqualTo("Email", emailStr);
            List<User> userList = userQuery.find();
            if(userList.size() != 0)
            {
            	setRequired(emailAddress, getString(R.string.yao_login_i_email_registed));
            	return false;
            }
        }
        userNameStr = userName.getText().toString().trim();
        if(userNameStr.length() == 0)
        {
            this.setRequired(userName, this.getString(R.string.yao_login_i_empty) );
            return false;
        }
        goals = goal.getText().toString().trim();
        if(goals.length() == 0)
        {
            this.setRequired(goal, this.getString(R.string.yao_login_i_empty) );
            return false;
        }
        passwordStr= password.getText().toString().trim();
        if(passwordStr.length() == 0)
        {
            this.setRequired(password, this.getString(R.string.yao_login_i_empty) );
            return false;
        }
        String passwordConfirm = passwordComfirm.getText().toString().trim();
        if(passwordConfirm.length() == 0)
        {
            this.setRequired(passwordComfirm, this.getString(R.string.yao_login_i_empty) );
            return false;
        }
        if(!passwordStr.equals(passwordConfirm))
        {
            this.setRequired(passwordComfirm, this.getString(R.string.yao_login_i_passwordConfirm) );
            return false;
        }
        return true;
    }
    //show the anamition of error information
    protected void setRequired(View view,String... error){
        view.startAnimation(shakeAction);
        view.setFocusable(true);
        view.requestFocus();
        view.setFocusableInTouchMode(true);
        if(view instanceof EditText){
            ((EditText) view).setError(error[0]);
        }
    }
}
