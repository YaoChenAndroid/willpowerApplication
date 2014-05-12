package com.example.willpower.lai.controllers;

import java.util.ArrayList;

import com.example.willpower.controllers.R;
import com.example.willpower.controllers.R.layout;
import com.example.willpower.controllers.R.menu;
import com.example.willpower.lai.SQLiteOpenHelper.TreeStrategyGameDatabaseHelper;
import com.example.willpower.lai.modules.TreeStrategyGameDataModule;
import com.parse.ParseException;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreeStrategyViewScoreActivity extends Activity {

	private TreeStrategyGameDatabaseHelper db = new TreeStrategyGameDatabaseHelper(this);
	private TreeStrategyGameDataModule module = new TreeStrategyGameDataModule(this);
	
	private LinearLayout mTreeStrategyLinearLayout;
	private ImageView mTreeStrategyCurrentScoreiv;
	private ImageView mTreeStrategyHighestScoreiv;
	private TextView mTreeStrategyCurrentScoretv;
	private TextView mTreeStrategyHighestScoretv;
	private ImageButton mTreeStrategyBackToMain;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tree_strategy_view_score_lai);
		mTreeStrategyLinearLayout = (LinearLayout)findViewById(R.id.tree_strategy_view_score_background);
		mTreeStrategyLinearLayout.setBackground(getResources().getDrawable(R.drawable.tree_strategy_viewscore_bg));
		
		mTreeStrategyCurrentScoreiv = (ImageView)findViewById(R.id.tree_strategy_view_score_currentscore_iv);
		mTreeStrategyHighestScoreiv = (ImageView)findViewById(R.id.tree_strategy_view_score_highestscore_iv);
		
		mTreeStrategyCurrentScoretv = (TextView)findViewById(R.id.tree_strategy_view_score_currentscore_tv);
		mTreeStrategyHighestScoretv = (TextView)findViewById(R.id.tree_strategy_view_score_highestscore_tv);
		
		mTreeStrategyBackToMain = (ImageButton)findViewById(R.id.tree_strategy_view_score_backtomain);
		
		mTreeStrategyBackToMain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TreeStrategyViewScoreActivity.this, TreeStrategyMainActivity.class));
			}
		});
		
		printScore();
		
	}
	
	public void printScore() {
//		ArrayList<Long> curr_score_list = db.getAllTreeGameScore();
//		ArrayList<Long> highest_score_list = db.getAllTreeGameHighestScore();
//		if (!curr_score_list.isEmpty() && curr_score_list.size() != 0) {
//			mTreeStrategyCurrentScoretv.setText(String.valueOf(curr_score_list.get(0)));
//		} else {
//			mTreeStrategyCurrentScoretv.setText("No current score");
//		}
//		if (!highest_score_list.isEmpty() && highest_score_list.size() != 0) {
//			mTreeStrategyHighestScoretv.setText(String.valueOf(highest_score_list.get(0)));
//		} else {
//			mTreeStrategyHighestScoretv.setText("No highest score");
//		}
		ArrayList<Long> curr_score;
		try {
			curr_score = module.getCurrentScoreInfo();
			mTreeStrategyCurrentScoretv.setText(String.valueOf(curr_score.get(0)));
			mTreeStrategyHighestScoretv.setText(String.valueOf(curr_score.get(1)));
		} catch (ParseException e) {
			Log.d("TreeStrategy", e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tree_strategy_view_score, menu);
		return true;
	}

}
