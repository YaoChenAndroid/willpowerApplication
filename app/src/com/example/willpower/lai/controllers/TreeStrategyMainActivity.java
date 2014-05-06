package com.example.willpower.lai.controllers;

import java.util.ArrayList;

import com.example.willpower.controllers.R;
import com.example.willpower.lai.SQLiteOpenHelper.TreeStrategyGameDatabaseHelper;
import com.example.willpower.lai.models.TreeGameObject;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
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
	private ImageButton mTreeStrategyInstruction;
	
	private TreeStrategyGameDatabaseHelper db = new TreeStrategyGameDatabaseHelper(TreeStrategyMainActivity.this);
	
	public static final int START_TREE_ACTIVITY_ACTION = 2;
	
	public static final int START_TREE_STRATEGY_NEW_GAME = 21;
	public static final int START_TREE_STRATEGY_CONTINUE = 22;
	public static final int START_TREE_STRATEGY_VIEW_SCORE = 23;
	public static final int START_TREE_STRATEGY_INSTRUCTION = 24;
	
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
		mTreeStrategyInstruction = (ImageButton)findViewById(R.id.tree_strategy_go_to_instruction);
		
		ArrayList<TreeGameObject> log = db.getAllTreeGameObject();
		Log.d("TreeStrategy", "(Variable Value)check whether have saved game: " + log.size());
		if (log.size() == 0) {
			mTreeStrategyContinue.setBackground(getResources().getDrawable(R.drawable.tree_strategy_continue_disable_icon));
			mTreeStrategyContinue.setClickable(false);
		} else {
			mTreeStrategyContinue.setBackground(getResources().getDrawable(R.drawable.tree_strategy_continue_icon));
			mTreeStrategyContinue.setClickable(true);
			mTreeStrategyContinue.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d("TreeStrategy", "(LoadModule)Continue game model.");
					Intent continueGame = new Intent(TreeStrategyMainActivity.this, TreeStrategyGameActivity.class);
					startActivityForResult(continueGame, START_TREE_STRATEGY_CONTINUE);
				}
				
			});
		}
		
		mTreeStrategyNewGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("TreeStrategy", "(LoadModule)Start new game model.");
				ArrayList<TreeGameObject> log = db.getAllTreeGameObject();
				if (log.size() == 0) {
					Intent newGame = new Intent(TreeStrategyMainActivity.this, TreeStrategyGameActivity.class);
					startActivityForResult(newGame, START_TREE_STRATEGY_NEW_GAME);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(TreeStrategyMainActivity.this, android.R.style.Theme_Translucent);
					builder.setIcon(R.drawable.tree_dialog_icon);
					builder.setMessage("Start a new game will lost your previous record, are you sure you want to start a new game?");
					builder.setCancelable(false);
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							db.clearTreeTables();
							Intent newGame = new Intent(TreeStrategyMainActivity.this, TreeStrategyGameActivity.class);
							startActivityForResult(newGame, START_TREE_STRATEGY_NEW_GAME);
						}
					});
					builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					AlertDialog alertDialog = builder.create();
					alertDialog.getWindow().setGravity(Gravity.CENTER);
					alertDialog.show();
				}
			}
			
		});
		
		mTreeStrategyViewScore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("TreeStrategy", "(LoadModule)View score model.");
				Intent viewScore = new Intent(TreeStrategyMainActivity.this, TreeStrategyViewScoreActivity.class);
				startActivityForResult(viewScore, START_TREE_STRATEGY_VIEW_SCORE);
			}
			
		});
		
		mTreeStrategyBackToMain.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("TreeStrategy", "(LoadModule)Back to main model.");
				Intent backToMain = new Intent(TreeStrategyMainActivity.this, com.example.willpower.controllers.MainActivity.class);
				startActivity(backToMain);
			}
			
		});
		
		mTreeStrategyInstruction.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("TreeStrategy", "(LoadModule)Go to instruction model.");
				Intent gotoInstruction = new Intent(TreeStrategyMainActivity.this, GameInstructionActivity.class);
				startActivityForResult(gotoInstruction, START_TREE_STRATEGY_INSTRUCTION);
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
