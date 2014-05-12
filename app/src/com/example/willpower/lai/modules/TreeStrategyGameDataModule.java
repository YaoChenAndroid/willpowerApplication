package com.example.willpower.lai.modules;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.willpower.lai.SQLiteOpenHelper.TreeStrategyGameDatabaseHelper;
import com.example.willpower.lai.models.TreeGameObject;
import com.parse.ParseUser;

/**
 * Control data of tree strategy game, when connecting to cloud server, then upload current game and
 * current highest score on server. If not, then just save them into native database
 * @author Jerry
 *
 */

public class TreeStrategyGameDataModule {
	
	private TreeStrategyGameDatabaseHelper db;
	
	public TreeStrategyGameDataModule(Context context) {
		db = new TreeStrategyGameDatabaseHelper(context);
	}
	
	/**
	 * public interface!
	 * save current game and current score, and upload current game state and highest score on server if possible
	 * @param gameObject
	 * @param score
	 * @throws JSONException
	 */
	
	public void saveCurrentGame(TreeGameObject gameObject, long score) throws JSONException {
		ParseUser curr_user = ParseUser.getCurrentUser();
		if (curr_user != null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("CostPerAcre", gameObject.getCostPerAcre());
			jsonObject.put("CurrentAcres", gameObject.getCurrentAcres());
			jsonObject.put("CurrentAcresIncreaseRate", gameObject.getCurrentAcresIncreaseRate());
			jsonObject.put("CurrentCredits", gameObject.getCurrentCredits());
			jsonObject.put("CurrentMaintainPerAcre", gameObject.getCurrentMaintainPerAcre());
			jsonObject.put("CurrentValuePerAcre", gameObject.getCurrentValuePerAcre());
			String curr_state = jsonObject.toString();
			curr_user.put("TreeSavedGame", curr_state);
			curr_user.put("TreeCurrentScore", score);
			Long curr_highest_score = curr_user.getLong("TreeHighest");
			if (curr_highest_score != null && curr_highest_score < score) {
				curr_user.put("TreeHighest", score);
			} else {
				curr_user.put("TreeHighest", score);
			}
		} else {
			db.saveTreeGame(gameObject, score);
		}
	}
	
	/**
	 * Load current game and get game object
	 * @return regular TreeGameObject to load current game, null if no records
	 * @throws JSONException
	 */
	
	public TreeGameObject loadCurrentGame() throws JSONException {
		ParseUser curr_user = ParseUser.getCurrentUser();
		if (curr_user != null) {
			String curr_savedGame = curr_user.getString("TreeSavedGame");
			if (curr_savedGame != null) {
				JSONObject jsonObject = new JSONObject(curr_savedGame);
				return new TreeGameObject(jsonObject.getLong("CostPerAcre"), jsonObject.getDouble("CurrentAcresIncreaseRate"), 
						jsonObject.getLong("CurrentAcres"), jsonObject.getLong("CurrentCredits"), 
						jsonObject.getLong("CurrentMaintainPerAcre"), jsonObject.getLong("CurrentValuePerAcre"));
			} else {
				ArrayList<TreeGameObject> tgo_list = db.getAllTreeGameObject();
				if (tgo_list != null && tgo_list.size() != 0) {
					return tgo_list.get(0);
				} else {
					return null;
				}
			}
		} else {
			ArrayList<TreeGameObject> tgo_list = db.getAllTreeGameObject();
			if (tgo_list != null && tgo_list.size() != 0) {
				return tgo_list.get(0);
			} else {
				return null;
			}
		}
	}
	
	/**
	 * return current score and highest score, and store into a list
	 * @return the list that store current score and highest score, if no records then return null
	 */
	
	public ArrayList<Long> getCurrentScoreInfo() {
		ParseUser curr_user = ParseUser.getCurrentUser();
		ArrayList<Long> result = new ArrayList<Long>();
		if (curr_user != null) {
			long curr_score = curr_user.getLong("TreeCurrentScore");
			long highest_score = curr_user.getLong("TreeHighestScore");
			result.add(curr_score);
			result.add(highest_score);
		} else {
			ArrayList<Long> curr_score_fromdb = db.getAllTreeGameScore();
			ArrayList<Long> highest_score_fromdb = db.getAllTreeGameHighestScore();
			if (curr_score_fromdb != null && curr_score_fromdb.size() != 0) {
				result.add(curr_score_fromdb.get(0));
			} else {
				result.add(0l);
			}
			if (highest_score_fromdb != null && highest_score_fromdb.size() != 0) {
				result.add(highest_score_fromdb.get(0));
			} else {
				result.add(0l);
			}
		}
		return result;
	}
	
	/**
	 * public interface to clear all current items except highest score
	 */
	
	public void clearRecords() {
		ParseUser curr_user = ParseUser.getCurrentUser();
		if (curr_user != null) {
			curr_user.put("TreeCurrentScore", 0);
			curr_user.put("TreeSavedGame", null);
		}
		db.clearTreeTables();
	}
	
	/**
	 * public interface to return whether can load a game
	 * @return true if there exists an object
	 * @throws JSONException 
	 */
	public boolean canLoadGame() throws JSONException {
		TreeGameObject tgo = loadCurrentGame();
		if (tgo != null) {
			return true;
		}
		return false;
	}
	
}