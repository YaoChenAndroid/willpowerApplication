package com.example.willpower.lai.controllers;

import com.example.willpower.controllers.R;
import com.example.willpower.controllers.R.layout;
import com.example.willpower.controllers.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TreeStrategySPResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tree_strategy_spresult_lai);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tree_strategy_spresult, menu);
		return true;
	}

}
