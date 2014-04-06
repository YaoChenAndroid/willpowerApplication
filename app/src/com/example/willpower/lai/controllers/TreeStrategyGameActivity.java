package com.example.willpower.lai.controllers;

import java.util.concurrent.locks.ReentrantLock;

import com.example.willpower.controllers.R;
import com.example.willpower.lai.SQLiteOpenHelper.TreeStrategyGameDatabaseHelper;
import com.example.willpower.lai.models.TreeGameObject;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TreeStrategyGameActivity extends Activity implements OnClickListener{

//	private TreeGameObject mGameObject; // Current game object
	private TreeStrategyGameDatabaseHelper helper = new TreeStrategyGameDatabaseHelper(this);
	
	private double mCurrentIncreaseRate = 0.1; // Current increase rate
	private long mCurrentAcres = 100; // Start tree acres
	private long mCurrentCredits = 200; // Start users credits
	private long mCurrentMaintainPerAcre = 1; // start maintain cost
	private long mCurrentValuePerAcre = 15; // value when cutting down the trees
	private long mCostPerAcre = 30; // costs to plant new trees
	
	private boolean notEnough = false; // use flag to record the status of increase or decrease rate
	
	private Handler treeHandler = new Handler();
	private int period_time = 60 * 1000;
	private int printResult_interval = 50;
	private ReentrantLock treeMainLock = new ReentrantLock();
	
	private LinearLayout mTreeStrategyLinearLayout;
	private TextView mTreeGameMessage;
	private Button mCut_new_tree_lai;
	private Button mPlant_new_tree_lai;
	private Button mView_current_score_lai;
	private Button mSave_current_game_lai;
	
//	private View plantNewTreeView;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tree_strategy_game_lai);
		mTreeStrategyLinearLayout = (LinearLayout)findViewById(R.id.tree_strategy_game_background);
		mTreeStrategyLinearLayout.setBackground(getResources().getDrawable(R.drawable.tree_strategy_background));
		
		mTreeGameMessage = (TextView) findViewById(R.id.TreeGameMessage);
		
		mCut_new_tree_lai = (Button)findViewById(R.id.cut_new_tree_lai);
		mPlant_new_tree_lai = (Button) findViewById(R.id.plant_new_tree_lai);
		mView_current_score_lai = (Button) findViewById(R.id.view_current_score_lai);
		mSave_current_game_lai = (Button) findViewById(R.id.save_current_game_lai);
		mCut_new_tree_lai.setOnClickListener(this);
		mPlant_new_tree_lai.setOnClickListener(this);
		mView_current_score_lai.setOnClickListener(this);
		mSave_current_game_lai.setOnClickListener(this);
		
		treeHandler.postDelayed(printResult, 0);
		treeHandler.postDelayed(gameDevelopThread, period_time);
	}

	/**
	 * Init game object
	 */
	public TreeGameObject createGameObject() {
		return new TreeGameObject(mCostPerAcre, mCurrentIncreaseRate, mCurrentAcres,
				mCurrentCredits, mCurrentMaintainPerAcre, mCurrentValuePerAcre);
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
			toastAnnounce("cut new trees");
			cutTreesDialog();
		} else if (viewId == R.id.plant_new_tree_lai) {
			toastAnnounce("plant new trees");
			plantNewTreeDialog();
		} else if (viewId == R.id.view_current_score_lai) {
			toastAnnounce("view current score");
		} else if (viewId == R.id.save_current_game_lai) {
			toastAnnounce("save current game");
		}
	}
	
	/**
	 * new runnable to handle the data
	 * when game starts, acres will increase per period time
	 * however if user current credits can't afford to maintain trees
	 * acres will stop to increase, and then decrease per period time by the same
	 * rate until current credits can afford to maintain trees or game over.
	 */
	private Runnable gameDevelopThread = new Runnable() {

		@Override
		public void run() {
			treeMainLock.lock();
			try {
				long totalMaintain = mCurrentAcres * mCurrentMaintainPerAcre;
				if (totalMaintain > mCurrentCredits) {
					if (!notEnough) {
						notEnough = true;
						toastAnnounce("Our credits are not enough!");
					} else {
						if (mCurrentAcres == 0) {
							toastAnnounce("Game Over");
							treeHandler.removeCallbacks(this);
						}
						mCurrentAcres = (long) (mCurrentAcres * (1 - mCurrentIncreaseRate));
					}
				} else {
					notEnough = false;
					if (totalMaintain == mCurrentCredits) {
						toastAnnounce("Our credits are running low!");
					}
					mCurrentCredits = mCurrentCredits - totalMaintain;
					mCurrentAcres = (long) (mCurrentAcres * (1 + mCurrentIncreaseRate));
				}
			} finally {
				treeMainLock.unlock();
			}
			treeHandler.postDelayed(this, period_time);
		}
		
	};
	
	/**
	 * new runnable to print out all current data
	 */
	private Runnable printResult = new Runnable() {

		@Override
		public void run() {
			String result = "Current Acres: " + mCurrentAcres + "\n"
					+ "Current Credits: " + mCurrentCredits;
			mTreeGameMessage.setText(result);
			treeHandler.postDelayed(this, printResult_interval);
		}
		
	};
	
	/**
	 * private function to update current acres, it should focus on the lock
	 * @param mAcres, here param is the value to change
	 * @param isPlantNew, check whether user plants new trees or cuts down trees
	 */
	private void updateAcres(long mAcres, boolean isPlantNew) {
		while (true) {
			if (treeMainLock.isLocked()) {
				continue;
			} else {
				treeMainLock.lock();
				if (isPlantNew) {
					try {
						long currentCost = mCostPerAcre * mAcres;
						if (currentCost <= mCurrentCredits) {
							mCurrentCredits = mCurrentCredits - currentCost;
							mCurrentAcres = mCurrentAcres + mAcres;
						}
					} finally {
						treeMainLock.unlock();
					}
					break;
				} else {
					try {
						long profit = mCurrentValuePerAcre * mAcres;
						if (mAcres <= mCurrentAcres) {
							mCurrentCredits = mCurrentCredits + profit;
							mCurrentAcres = mCurrentAcres - mAcres;
						}
					} finally {
						treeMainLock.unlock();
					}
					break;
				}
			}
		}
	}

	/**
	 * a dialog to input the information about planting new trees
	 */
	public void plantNewTreeDialog() {
		LayoutInflater inflater = (LayoutInflater) TreeStrategyGameActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View plantNewTreeView = inflater.inflate(R.layout.activity_tree_strategy_game_plant_new_trees_lai, null);
		//treeHandler.postDelayed(plantNewTreeRunnable, 0);
		final EditText newAcresEditText = (EditText) plantNewTreeView.findViewById(R.id.tree_plant_new_tree_new_acres_input_lai);
		final EditText creditsEditText = (EditText) plantNewTreeView.findViewById(R.id.tree_plant_new_tree_credits_cost_lai);
		final TextView creditsMsg = (TextView) plantNewTreeView.findViewById(R.id.tree_plant_new_tree_credits_msgresult_lai);
		creditsEditText.setKeyListener(null);
		AlertDialog.Builder builder = new AlertDialog.Builder(TreeStrategyGameActivity.this);
		builder.setTitle("Plant new trees");
		builder.setView(plantNewTreeView);
		builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				long newAcres = Long.parseLong(newAcresEditText.getText().toString());
				updateAcres(newAcres, true);
			}
			
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//treeHandler.removeCallbacks(plantNewTreeRunnable);
				dialog.cancel();
			}
		});
		final AlertDialog treeDialog = builder.create();
		treeDialog.setCancelable(false);
		//treeDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
		
		treeDialog.show();
		
		newAcresEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					try {
						long newAcres = Long.parseLong(newAcresEditText.getText().toString());
						long creditsCost = newAcres * mCostPerAcre;
						if (creditsCost > mCurrentCredits) {
							creditsEditText.setText(String.valueOf(creditsCost));
							creditsEditText.setTextColor(Color.parseColor("#FF0000"));
							creditsMsg.setText("Not Enough credits");
							creditsMsg.setTextColor(Color.parseColor("#FF0000"));
						} else {
							creditsEditText.setText(String.valueOf(creditsCost));
							creditsEditText.setTextColor(Color.parseColor("#000000"));
							creditsMsg.setText("");
							treeDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
						}
					} catch (Exception e) {
						//do nothing
					}
				} else {
					treeDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
				}
			}
			
		});
	}
	
	/**
	 * a dialog to input the information about cutting trees
	 */
	public void cutTreesDialog() {
		LayoutInflater inflater = (LayoutInflater) TreeStrategyGameActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View cutDownTreesView = inflater.inflate(R.layout.activity_tree_strategy_game_cut_trees_lai, null);
		//treeHandler.postDelayed(plantNewTreeRunnable, 0);
		final EditText newAcresEditText = (EditText) cutDownTreesView.findViewById(R.id.tree_cut_down_trees_new_acres_input_lai);
		//final EditText creditsEditText = (EditText) cutDownTreesView.findViewById(R.id.tree_cut_down_trees_credits_cost_lai);
		final TextView cutDownTreesMsg = (TextView) cutDownTreesView.findViewById(R.id.tree_cut_down_trees_msgresult_lai);
		//creditsEditText.setKeyListener(null);
		AlertDialog.Builder builder = new AlertDialog.Builder(TreeStrategyGameActivity.this);
		builder.setTitle("Cut down trees");
		builder.setView(cutDownTreesView);
		builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				long newAcres = Long.parseLong(newAcresEditText.getText().toString());
				updateAcres(newAcres, false);
			}
			
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//treeHandler.removeCallbacks(plantNewTreeRunnable);
				dialog.cancel();
			}
		});
		final AlertDialog treeDialog = builder.create();
		treeDialog.setCancelable(false);
		//treeDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
		
		treeDialog.show();
		
		newAcresEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					try {
						long newAcres = Long.parseLong(newAcresEditText.getText().toString());
						if (newAcres > mCurrentAcres) {
							cutDownTreesMsg.setText("Not Enough trees");
							cutDownTreesMsg.setTextColor(Color.parseColor("#FF0000"));
							treeDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
						} else {
							cutDownTreesMsg.setText("");
							treeDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
						}
					} catch (Exception e) {
						//do nothing
					}
				} else {
					treeDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
				}
			}
			
		});
	}
	
	/**
	 * toast, makeText function
	 */
	public void toastAnnounce(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//helper.saveTreeGame(createGameObject());
	}
	
	@Override
	public void onStop() {
		super.onStop();
		//helper.saveTreeGame(createGameObject());
		treeHandler.removeCallbacks(printResult);
		treeHandler.removeCallbacks(gameDevelopThread);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
