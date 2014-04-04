package com.example.willpower.lai.controllers;

import com.example.willpower.controllers.R;
import com.example.willpower.controllers.R.layout;
import com.example.willpower.controllers.R.menu;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreeStrategyMainActivity extends Activity {

	private LinearLayout mTreeStrategyLinearLayout;
	private TextView mTreeStrategyTitle;
	private TextView mTreeStrategyAuthor;
	private Button mTreeStrategyNewGame;
	private Button mTreeStrategyContinue;
	private Button mTreeStrategyViewScore;
	private Button mTreeStrategyBackToMain;
	
	public static final int START_TREE_ACTIVITY_ACTION = 2;
	
	public static final int START_TREE_STRATEGY_NEW_GAME = 21;
	public static final int START_TREE_STRATEGY_CONTINUE = 22;
	public static final int START_TREE_STRATEGY_VIEW_SCORE = 23;
	
	private Handler mHandler = new Handler();
	private int mBlink_interval = 1000;
	private boolean mBlink_flag = true;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tree_strategy_main_lai);
		mTreeStrategyLinearLayout = (LinearLayout)findViewById(R.id.tree_strategy_background);
		mTreeStrategyLinearLayout.setBackground(getResources().getDrawable(R.drawable.tree_strategy_background));
		
		mTreeStrategyTitle = (TextView)findViewById(R.id.tree_strategy_title);
		mTreeStrategyAuthor = (TextView)findViewById(R.id.tree_strategy_author);
		
		mHandler.postDelayed(titleBlink, 0);
		
		mTreeStrategyNewGame = (Button)findViewById(R.id.tree_strategy_new_game);
		mTreeStrategyContinue = (Button)findViewById(R.id.tree_strategy_continue);
		mTreeStrategyViewScore = (Button)findViewById(R.id.tree_strategy_view_score);
		mTreeStrategyBackToMain = (Button)findViewById(R.id.tree_strategy_back_to_main);
		
		mTreeStrategyNewGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newGame = new Intent(TreeStrategyMainActivity.this, TreeStrategyGameActivity.class);
				startActivityForResult(newGame, START_TREE_STRATEGY_NEW_GAME);
			}
			
		});
		mTreeStrategyContinue.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent continueGame = new Intent(TreeStrategyMainActivity.this, TreeStrategyGameActivity.class);
				startActivityForResult(continueGame, START_TREE_STRATEGY_CONTINUE);
			}
			
		});
		mTreeStrategyViewScore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent viewScore = new Intent(TreeStrategyMainActivity.this, TreeStrategyViewScoreActivity.class);
				startActivityForResult(viewScore, START_TREE_STRATEGY_VIEW_SCORE);
			}
			
		});
		mTreeStrategyBackToMain.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent backToMain = new Intent(TreeStrategyMainActivity.this, com.example.willpower.controllers.MainActivity.class);
				startActivity(backToMain);
			}
			
		});
	}
	
	private Runnable titleBlink = new Runnable() {

		@Override
		public void run() {
			if (mBlink_flag) {
				mTreeStrategyTitle.setText("Tree Strategy\n\n");
				mTreeStrategyAuthor.setText("Lai Wang, 0488605");
				mBlink_flag = false;
			} else {
				mTreeStrategyTitle.setText(" \n\n");
				mTreeStrategyAuthor.setText(" ");
				mBlink_flag = true;
			}
			mHandler.postDelayed(this, mBlink_interval);
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tree_strategy_main, menu);
		return true;
	}

}
