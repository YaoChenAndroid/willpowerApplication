package com.example.willpower.controllers;

import com.example.willpower.controllers.R;
import com.example.willpower.models.User;
import com.example.willpower.models.friendLoc;
import com.example.willpower.models.userFriend;
import com.example.willpower.yao.controllers.loginActivity;
import com.example.willpower.yao.controllers.shareActivity;
import com.parse.Parse;
import com.parse.ParseObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final int START_BRAIN_ACTIVITY_ACTION = 1;
	public static final int START_TREE_ACTIVITY_ACTION = 2;
	
	private ImageButton ib1,ib2,ib3,ib4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupUI();
		//connect service, which only can be connenct once
        Parse.initialize(this, "k67gag0IGiefqnYZHySJmvEiwpEwpi6c1uk5ExUl", "nmRcR7jOVAamqxtL9TmuWA0uBzZoJcJGNYFYVZxz");
	    ParseObject.registerSubclass(friendLoc.class);
	    ParseObject.registerSubclass(userFriend.class);
	    ParseObject.registerSubclass(User.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setupUI(){
		ib1=(ImageButton) findViewById(R.id.ImageButtonGame1);
		ib2=(ImageButton) findViewById(R.id.ImageButtonGame2);
		ib3=(ImageButton) findViewById(R.id.ImageButtonGame3);
		ib4=(ImageButton) findViewById(R.id.ImageButtonGame4);
		/*View.OnTouchListener onTouchListener=new View.OnTouchListener() {
			//add touch effect to the imageButtons
			//refer to the solution from http://stackoverflow.com/questions/4617898/how-can-i-give-imageview-click-effect-like-a-button-in-android
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
		           switch (event.getAction()) {
	                case MotionEvent.ACTION_DOWN: {
	                    ImageView view = (ImageView) v;
	                    //overlay is black with transparency of 0x77 (119)
	                    view.getDrawable().setColorFilter(0x77000000,android.graphics.PorterDuff.Mode.SRC_ATOP);
	                    view.invalidate();
	                    break;
	                }
	                case MotionEvent.ACTION_UP:
	                case MotionEvent.ACTION_CANCEL: {
	                    ImageView view = (ImageView) v;
	                    //clear the overlay
	                    view.getDrawable().clearColorFilter();
	                    view.invalidate();
	                    break;
	                }
	            }    
                return false;
           }
		};
		ib1.setOnTouchListener(onTouchListener);
		ib2.setOnTouchListener(onTouchListener);
		ib3.setOnTouchListener(onTouchListener);
		ib4.setOnTouchListener(onTouchListener);*/
		ib1.setOnClickListener(new View.OnClickListener() {
			@Override
			//Yuxin Override this method
			public void onClick(View v) {
				//Toast.makeText(MainActivity.this, "Button1 is clicked, please override this method to activate game1Activity", 2000).show();
				Intent yuxin_brain_intent = new Intent(MainActivity.this, com.example.willpower.yuxin.controllers.ColorBrainActivity.class);
				startActivityForResult(yuxin_brain_intent, START_BRAIN_ACTIVITY_ACTION);
			}
		});
		ib2.setOnClickListener(new View.OnClickListener() {
			@Override
			//Lai Override this method
			public void onClick(View v) {
				//Toast.makeText(MainActivity.this, "Button2 is clicked, please override this method to activate game2Activity", 2000).show();
				Intent lai_tree_strategy_intent = new Intent(MainActivity.this, com.example.willpower.lai.controllers.TreeStrategyMainActivity.class);
				startActivityForResult(lai_tree_strategy_intent, START_TREE_ACTIVITY_ACTION);
			}
		});
		ib3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(MainActivity.this, "Button3 is clicked, please override this method to activate game3Activity", 2000).show();
				Intent temp = new Intent(MainActivity.this, loginActivity.class);
				startActivity(temp);
			}
		});
		ib4.setOnClickListener(new View.OnClickListener() {
			@Override
			//Yao Override this method
			public void onClick(View v) {
				//Toast.makeText(MainActivity.this, "Button4 is clicked, please override this method to activate game4Activity", 2000).show();
				Intent temp = new Intent(MainActivity.this,shareActivity.class);
				startActivity(temp);
			}
		});
	}
	
	/**
	 * Each button will request some data when user finish their games
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	}
}
