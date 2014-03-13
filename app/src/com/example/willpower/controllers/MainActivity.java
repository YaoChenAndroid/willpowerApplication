package com.example.willpower.controllers;

import com.example.willpower.controllers.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ImageButton ib1,ib2,ib3,ib4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupUI();
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
		View.OnTouchListener onTouchListener=new View.OnTouchListener() {
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
		ib4.setOnTouchListener(onTouchListener);

		ib1.setOnClickListener(new View.OnClickListener() {
			@Override
			//Yuxin Override this method
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Button1 is clicked, please override this method to activate game1Activity", 2000).show();
			}
		});
		ib2.setOnClickListener(new View.OnClickListener() {
			@Override
			//Lai Override this method
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Button2 is clicked, please override this method to activate game2Activity", 2000).show();
			}
		});
		ib3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Button3 is clicked, please override this method to activate game3Activity", 2000).show();
			}
		});
		ib4.setOnClickListener(new View.OnClickListener() {
			@Override
			//Yao Override this method
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Button4 is clicked, please override this method to activate game4Activity", 2000).show();
			}
		});
	}
}
