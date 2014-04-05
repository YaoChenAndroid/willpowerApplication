package com.example.willpower.lai.controllers;

import com.example.willpower.controllers.R;
import com.example.willpower.controllers.R.layout;
import com.example.willpower.controllers.R.menu;
import com.example.willpower.lai.models.TreeGameObject;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TreeStrategyGameActivity extends Activity implements OnClickListener{

	private TreeGameObject mGameObject; // Currrent game object
	private double mCurrentIncreaseRate = 0.1; // Current increase rate
	private int mStartAcres = 100; // Start tree acres
	private int mStartCredits = 200; // Start users credits
	private int mMaintainPerAcre = 1; // start maintain cost
	private int mValuePerAcre = 20; // value when cutting down the trees
	private int mCostPerAcre = 30; // costs to plant new trees
	
	private Handler treeHandler = new Handler();
	
	private LinearLayout mTreeStrategyLinearLayout;
	private Button mCut_new_tree_lai;
	private Button mPlant_new_tree_lai;
	private Button mView_current_score_lai;
	private Button mSave_current_game_lai;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tree_strategy_game_lai);
		mTreeStrategyLinearLayout = (LinearLayout)findViewById(R.id.tree_strategy_game_background);
		mTreeStrategyLinearLayout.setBackground(getResources().getDrawable(R.drawable.tree_strategy_background));
		
		mCut_new_tree_lai = (Button)findViewById(R.id.cut_new_tree_lai);
		mPlant_new_tree_lai = (Button) findViewById(R.id.plant_new_tree_lai);
		mView_current_score_lai = (Button) findViewById(R.id.view_current_score_lai);
		mSave_current_game_lai = (Button) findViewById(R.id.save_current_game_lai);
		mCut_new_tree_lai.setOnClickListener(this);
		mPlant_new_tree_lai.setOnClickListener(this);
		mView_current_score_lai.setOnClickListener(this);
		mSave_current_game_lai.setOnClickListener(this);
	}

	/**
	 * Init game object
	 */
	public void initGameObject() {
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tree_strategy_game, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		if (viewId == R.id.cut_new_tree_lai) {
			
		} else if (viewId == R.id.plant_new_tree_lai) {
			
		} else if (viewId == R.id.view_current_score_lai) {
			
		} else if (viewId == R.id.save_current_game_lai) {
			
		}
	}

	/**
	 * toast, makeText function
	 */
	public void toastAnnounce(String text, int duration) {
		Toast.makeText(this, text, duration).show();
	}
}
