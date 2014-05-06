package com.example.willpower.lai.controllers;

import com.example.willpower.controllers.R;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressLint("NewApi")
public class GameInstructionActivity extends Activity {

	private LinearLayout mTreeStrategyLinearLayout;
	private ImageView mTreeStrategyInstructionTitle;
	private ImageView mTreeStrategyInstructionCountDown;
	
	private ImageButton mTreeStrategyInstructionMsgButton;
	private ImageButton mTreeStrategyInstructionBackButton;
	
	private TextView mTreeStrategyInstructionCountDownMsg;
	private TextView mTreeStrategyInstructionMsgBox;
	
	private Handler handler = new Handler();
	private int mMsgButtonFlag = 0;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_instruction_lai);
		mTreeStrategyLinearLayout = (LinearLayout)findViewById(R.id.tree_strategy_game_instruction_background);
		
		mTreeStrategyInstructionTitle = (ImageView)findViewById(R.id.tree_strategy_instru_title);
		mTreeStrategyInstructionCountDown = (ImageView)findViewById(R.id.tree_strategy_instru_imageCountDown);
		
		mTreeStrategyInstructionCountDownMsg = (TextView)findViewById(R.id.tree_strategy_instru_msgCountDown);
		mTreeStrategyInstructionMsgBox = (TextView)findViewById(R.id.tree_strategy_instru_msgbox);
		
		mTreeStrategyInstructionMsgButton = (ImageButton)findViewById(R.id.tree_strategy_instru_msgButton);
		mTreeStrategyInstructionMsgButton.setBackground(null);
		mTreeStrategyInstructionBackButton = (ImageButton)findViewById(R.id.tree_strategy_instru_back_to_main);
		
		mTreeStrategyInstructionBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent backToMain = new Intent(GameInstructionActivity.this, TreeStrategyMainActivity.class);
				startActivity(backToMain);
			}
		});
		
		mTreeStrategyInstructionMsgButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mMsgButtonFlag == 1) {
					handler.postDelayed(newStepV_I, 0);
				} else if (mMsgButtonFlag == 2) {
					handler.postDelayed(newStepVI, 0);
				} else if (mMsgButtonFlag == 3) {
					AlertDialog.Builder builder = new AlertDialog.Builder(GameInstructionActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
					builder.setMessage("Ready to start new game?");
					builder.setCancelable(false);
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(GameInstructionActivity.this, TreeStrategyGameActivity.class));
						}
					});
					builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(GameInstructionActivity.this, TreeStrategyMainActivity.class));
						}
					});
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
				}
			}
		});
		
		handler.postDelayed(newStepI, 0);
	}
	
	private Runnable newStepI = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyLinearLayout.setBackgroundColor(Color.BLACK);
			mTreeStrategyInstructionMsgBox.setTextColor(Color.GREEN);
			mTreeStrategyInstructionMsgBox.setText("Welcome to Tree Strategy game ...");
			handler.postDelayed(newStepII, 5000);
		}
		
	};
	
	private Runnable newStepII = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyLinearLayout.setBackground(getResources().getDrawable(R.drawable.tree_strategy_game_background2));
			mTreeStrategyInstructionTitle.setBackground(getResources().getDrawable(R.drawable.tree_strategy_game_instruction_title));
			handler.postDelayed(newStepIII_I, 5000);
		}
		
	};
	
	private Runnable newStepIII_I = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setText("At beginning, you'll be given 100 acres of trees, and $200");
			handler.postDelayed(newStepIII_II, 5000);
		}
		
	};
	
	private Runnable newStepIII_II = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setText("You can do two things: Planting trees or chopping down trees.");
			handler.postDelayed(newStepIV, 5000);
		}
		
	};
	
	private Runnable newStepIV = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setText("Planting trees will cost you some money.");
			mMsgButtonFlag = 1;
			mTreeStrategyInstructionMsgButton.setBackground(getResources().getDrawable(R.drawable.tree_icon_lai));
		}
		
	};
	
	private Runnable newStepV_I = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setText("Congrats! You spend your money to plant some new trees.");
			handler.postDelayed(newStepV_II, 5000);
		}
		
	};
	
	private Runnable newStepV_II = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setText("Also, you can chop down trees to earn some immediate money.");
			mMsgButtonFlag = 2;
			mTreeStrategyInstructionMsgButton.setBackground(getResources().getDrawable(R.drawable.axe_icon2_lai));
		}
		
	};
	
	private Runnable newStepVI = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setText("Oops! Now you earn some money by chopping down trees.");
			mMsgButtonFlag = 0;
			mTreeStrategyInstructionMsgButton.setBackground(null);
			handler.postDelayed(newStepVII, 5000);
		}
		
	};
	
	private Runnable newStepVII = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setText("Now you can see a countdown timer. In this game, your resource will change"
					+ " for each period.");
			mTreeStrategyInstructionCountDown.setBackground(getResources().getDrawable(R.drawable.tree_strategy_countdown_title));
			mGameCountDown.start();
		}
		
	};
	
	private CountDownTimer mGameCountDown = new CountDownTimer(5 * 1000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			mTreeStrategyInstructionCountDownMsg.setText("  " + millisUntilFinished / 1000);
		}

		@Override
		public void onFinish() {
			handler.postDelayed(newStepVIII_I, 0);
		}
		
	};
	
	private Runnable newStepVIII_I = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setText("You have to pay for $1/acre to maintain your trees.");
			handler.postDelayed(newStepVIII_II, 5000);
		}
		
	};
	
	private Runnable newStepVIII_II = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setText("If you have enough money to pay for maintainence, then your acres of trees"
					+ " will increase by 10% per period");
			handler.postDelayed(newStepVIII_III, 5000);
		}
		
	};
	
	private Runnable newStepVIII_III = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setTextColor(Color.RED);
			mTreeStrategyInstructionMsgBox.setText("If you have not enough money to pay for maintainence, then your acres of trees"
					+ " will decrease by 10% per period");
			handler.postDelayed(newStepVIV, 5000);
		}
		
	};
	
	private Runnable newStepVIV = new Runnable() {

		@Override
		public void run() {
			mTreeStrategyInstructionMsgBox.setTextColor(Color.GREEN);
			mTreeStrategyInstructionMsgBox.setText("Okay, enjoy and good luck!");
			mMsgButtonFlag = 3;
			mTreeStrategyInstructionMsgButton.setBackground(getResources().getDrawable(R.drawable.tree_strategy_intru_start));
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_instruction, menu);
		return true;
	}
	
	@Override
	public void onStop() {
		super.onStop();
		handler.removeCallbacks(newStepI);
		handler.removeCallbacks(newStepII);
		handler.removeCallbacks(newStepIII_I);
		handler.removeCallbacks(newStepIII_II);
		handler.removeCallbacks(newStepVI);
		handler.removeCallbacks(newStepV_I);
		handler.removeCallbacks(newStepV_II);
		handler.removeCallbacks(newStepVI);
		handler.removeCallbacks(newStepVII);
		handler.removeCallbacks(newStepVIII_I);
		handler.removeCallbacks(newStepVIII_II);
		handler.removeCallbacks(newStepVIII_III);
		handler.removeCallbacks(newStepVIV);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(newStepI);
		handler.removeCallbacks(newStepII);
		handler.removeCallbacks(newStepIII_I);
		handler.removeCallbacks(newStepIII_II);
		handler.removeCallbacks(newStepVI);
		handler.removeCallbacks(newStepV_I);
		handler.removeCallbacks(newStepV_II);
		handler.removeCallbacks(newStepVI);
		handler.removeCallbacks(newStepVII);
		handler.removeCallbacks(newStepVIII_I);
		handler.removeCallbacks(newStepVIII_II);
		handler.removeCallbacks(newStepVIII_III);
		handler.removeCallbacks(newStepVIV);
	}

}
