package com.example.willpower.lai.controllers;

import java.util.ArrayList;

import com.example.willpower.controllers.R;
import com.example.willpower.lai.SQLiteOpenHelper.TreeStrategyGameDatabaseHelper;
import com.example.willpower.lai.models.TreeGameObject;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TreeStrategyMainActivity extends Activity {

	private LinearLayout mTreeStrategyLinearLayout;
	private ImageView mTreeStrategyTitle;
	private ImageButton mTreeStrategyNewGame;
	private ImageButton mTreeStrategyContinue;
	private ImageButton mTreeStrategyViewScore;
	private ImageButton mTreeStrategyBackToMain;
	
	private TreeStrategyGameDatabaseHelper db = new TreeStrategyGameDatabaseHelper(TreeStrategyMainActivity.this);
	
	public static final int START_TREE_ACTIVITY_ACTION = 2;
	
	public static final int START_TREE_STRATEGY_NEW_GAME = 21;
	public static final int START_TREE_STRATEGY_CONTINUE = 22;
	public static final int START_TREE_STRATEGY_VIEW_SCORE = 23;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tree_strategy_main_lai);
		mTreeStrategyLinearLayout = (LinearLayout)findViewById(R.id.tree_strategy_background);
		mTreeStrategyLinearLayout.setBackground(getResources().getDrawable(R.drawable.tree_strategy_main_background));
		
		mTreeStrategyTitle = (ImageView)findViewById(R.id.tree_strategy_title);
		mTreeStrategyTitle.setImageResource(R.drawable.tree_strategy_title2);
		
		mTreeStrategyNewGame = (ImageButton)findViewById(R.id.tree_strategy_new_game);
		mTreeStrategyContinue = (ImageButton)findViewById(R.id.tree_strategy_continue);
		mTreeStrategyViewScore = (ImageButton)findViewById(R.id.tree_strategy_view_score);
		mTreeStrategyBackToMain = (ImageButton)findViewById(R.id.tree_strategy_back_to_main);
		
		ArrayList<TreeGameObject> log = db.getAllTreeGameObject();
		if (log.size() == 0) {
			mTreeStrategyContinue.setClickable(false);
		}
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tree_strategy_main, menu);
		return true;
	}

}
