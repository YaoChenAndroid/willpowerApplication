package com.example.willpower.controllers;

import com.example.willpower.controllers.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ImageButton ib1,ib2,ib3,ib4;
	private void setupUI(){
		ib1=(ImageButton) findViewById(R.id.ImageButtonGame1);
		ib2=(ImageButton) findViewById(R.id.ImageButtonGame2);
		ib3=(ImageButton) findViewById(R.id.ImageButtonGame3);
		ib4=(ImageButton) findViewById(R.id.ImageButtonGame4);
		ib1.setOnClickListener(new View.OnClickListener() {
			@Override
			//Yuxin Override this method
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Button1 is clicked, please override this method to activate game1Activity", 3000);
			}
		});
		ib2.setOnClickListener(new View.OnClickListener() {
			@Override
			//Lai Override this method
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Button2 is clicked, please override this method to activate game2Activity", 3000);
			}
		});
		ib3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Button3 is clicked, please override this method to activate game3Activity", 3000);
			}
		});
		ib4.setOnClickListener(new View.OnClickListener() {
			@Override
			//Yao Override this method
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Button4 is clicked, please override this method to activate game4Activity", 3000);
			}
		});
	}
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

}
