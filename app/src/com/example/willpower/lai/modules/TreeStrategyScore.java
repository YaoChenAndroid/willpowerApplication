package com.example.willpower.lai.modules;

import java.util.ArrayList;

import com.example.willpower.lai.SQLiteOpenHelper.TreeStrategyGameDatabaseHelper;

import android.content.Context;

/**
 * public interface to other modules
 * functions are used to get current user score and highest score
 * constructor needs to be passed a context
 * @author Lai Wang
 *
 */

public class TreeStrategyScore {
	
	private TreeStrategyGameDatabaseHelper db;
	
	public TreeStrategyScore(Context context) {
		db = new TreeStrategyGameDatabaseHelper(context);
	}
	
	/**
	 * public interface to get current user tree strategy game score
	 * @return current tree strategy game score, return 0 if no current record
	 */
	
	public long getCurrentScore() {
		ArrayList<Long> curr_list = db.getAllTreeGameScore();
		if (!curr_list.isEmpty() && curr_list.size() != 0) {
			return curr_list.get(0);
		} else {
			return 0;
		}
	}
	
	/**
	 * public interface to get users history highest game score
	 * @return current tree strategy game history highest score, return 0 if no current record
	 */
	
	public long getHighestScore() {
		ArrayList<Long> curr_list = db.getAllTreeGameHighestScore();
		if (!curr_list.isEmpty() && curr_list.size() != 0) {
			return curr_list.get(0);
		} else {
			return 0;
		}
	}
	
}
