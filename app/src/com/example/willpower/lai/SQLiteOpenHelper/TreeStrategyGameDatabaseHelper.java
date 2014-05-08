package com.example.willpower.lai.SQLiteOpenHelper;

import java.util.ArrayList;

import com.example.willpower.lai.models.TreeGameObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TreeStrategyGameDatabaseHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Willpower_TreeStrategyGameDatabase";
	
	private static final String TABLE_TREESTRATEGY = "treeStrategy";
	private static final String COLUMN_TREESTRATEGY_ID = "_id";
	private static final String COLUMN_COSTPERACRE = "costPerAcre";
	private static final String COLUMN_CURRENTACRESINCREASERATE = "currentAcresIncreaseRate";
	private static final String COLUMN_CURRENTACRES = "currentAcres";
	private static final String COLUMN_CURRENTCREDITS = "currentCredits";
	private static final String COLUMN_CURRENTMAINTAINPERACRE = "currentMaintainPerAcre";
	private static final String COLUMN_CURRENTVALUEPERACRE = "currentValuePerAcre";
	
	private static final String TABLE_TREESTRATEGY_SCORE = "treeStrategyScore";
	private static final String COLUMN_TREESTRATEGY_SCORE_ID = "score_id";
	private static final String COLUMN_TREESTRATEGY_SCORE = "score";
	
	private static final String TABLE_TREESTRATEGY_HIGHESTSCORE = "treeStrategyHighestScore";
	private static final String COLUMN_TREESTRATEGY_HIGHESTSCORE_ID = "highestscore_id";
	private static final String COLUMN_TREESTRATEGY_HIGHESTSCORE = "score";
	
	
	public TreeStrategyGameDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create_table = "create table " + TABLE_TREESTRATEGY + "("
				+ COLUMN_TREESTRATEGY_ID + " integer primary key autoincrement, "
				+ COLUMN_COSTPERACRE + " bigint, " + COLUMN_CURRENTACRESINCREASERATE + " double, "
				+ COLUMN_CURRENTACRES + " bigint, " + COLUMN_CURRENTCREDITS + " bigint, "
				+ COLUMN_CURRENTMAINTAINPERACRE + " bigint, " + COLUMN_CURRENTVALUEPERACRE + " bigint)";
		String create_score = "create table " + TABLE_TREESTRATEGY_SCORE + "("
				+ COLUMN_TREESTRATEGY_SCORE_ID + " integer primary key autoincrement, "
				+ COLUMN_TREESTRATEGY_SCORE + " bigint)";
		String create_highest = "create table " + TABLE_TREESTRATEGY_HIGHESTSCORE + "("
				+ COLUMN_TREESTRATEGY_HIGHESTSCORE_ID + " integer primary key autoincrement, "
				+ COLUMN_TREESTRATEGY_HIGHESTSCORE + " bigint)";
		db.execSQL(create_table);
		db.execSQL(create_score);
		db.execSQL(create_highest);
		Log.d("TreeStrategy", "(Initialize)Start database model.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String drop_table = "drop table if exists " + TABLE_TREESTRATEGY;
		String drop_score = "drop table if exists " + TABLE_TREESTRATEGY_SCORE;
		String drop_highestscore = "drop table if exists " + TABLE_TREESTRATEGY_HIGHESTSCORE;
		db.execSQL(drop_table);
		db.execSQL(drop_score);
		db.execSQL(drop_highestscore);
		onCreate(db);
	}
	
	public long insertTreeBasic(TreeGameObject tgo) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_COSTPERACRE, tgo.getCostPerAcre());
		cv.put(COLUMN_CURRENTACRESINCREASERATE, tgo.getCurrentAcresIncreaseRate());
		cv.put(COLUMN_CURRENTACRES, tgo.getCurrentAcres());
		cv.put(COLUMN_CURRENTCREDITS, tgo.getCurrentCredits());
		cv.put(COLUMN_CURRENTMAINTAINPERACRE, tgo.getCurrentMaintainPerAcre());
		cv.put(COLUMN_CURRENTVALUEPERACRE, tgo.getCurrentValuePerAcre());
		return getWritableDatabase().insert(TABLE_TREESTRATEGY, null, cv);
	}
	
	public long insertCurrentScore(long score) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_TREESTRATEGY_SCORE, score);
		return getWritableDatabase().insert(TABLE_TREESTRATEGY_SCORE, null, cv);
	}
	
	public long insertHighestScore(long score) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_TREESTRATEGY_HIGHESTSCORE, score);
		return getWritableDatabase().insert(TABLE_TREESTRATEGY_HIGHESTSCORE, null, cv);
	}
	
	/**
	 * public API to insert to the table
	 */
	public void insertTreeGameObject(TreeGameObject tgo) {
		insertTreeBasic(tgo);
		Log.d("TreeStrategy", "(Action)Inert new tree object.");
	}
	
	/**
	 * public API to insert new score
	 */
	public void insertTreeGameScore(long score) {
		// Here we also update current highest score
		insertCurrentScore(score);
		ArrayList<Long> curr_highest_score_List = getAllTreeGameHighestScore();
		if (!curr_highest_score_List.isEmpty() && curr_highest_score_List.size() != 0) {
			long curr_highest_score = curr_highest_score_List.get(0);
			if (score > curr_highest_score) {
				SQLiteDatabase db = this.getReadableDatabase();
				db.delete(TABLE_TREESTRATEGY_HIGHESTSCORE, null, null);
				insertHighestScore(score);
			}
		}
		
		Log.d("TreeStrategy", "(Action)Inert new tree score.");
	}
	
	/**
	 * public API to remove all items in table
	 */
	public void clearTreeTables() {
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete(TABLE_TREESTRATEGY, null, null);
		db.delete(TABLE_TREESTRATEGY_SCORE, null, null);
		Log.d("TreeStrategy", "(Action)Clear all items in table.");
	}
	
	/**
	 * public API to get the TreeGameObject from the table
	 * here we suppose users only save the last game, so here there should only have one item
	 */
	public ArrayList<TreeGameObject> getAllTreeGameObject() {
		ArrayList<TreeGameObject> tgoList = new ArrayList<TreeGameObject>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String select = "select * from " + TABLE_TREESTRATEGY;
		Cursor c = db.rawQuery(select, null);
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			long costPerAcre = c.getLong(1);
			double currentAcreIncreaseRate = c.getDouble(2);
			long currentAcres = c.getLong(3);
			long currentCredits = c.getLong(4);
			long currentMaintainPerAcre = c.getLong(5);
			long currentValuePerAcre = c.getLong(6);
			
			TreeGameObject tgo = new TreeGameObject(costPerAcre, currentAcreIncreaseRate,
					currentAcres, currentCredits, currentMaintainPerAcre, currentValuePerAcre);
			tgoList.add(tgo);
		}
		
		Log.d("TreeStrategy", "(Action)Return all current saved items.");
		return tgoList;
	}
	
	/**
	 * public API to get the score from the table
	 * here we suppose users only save the last game, so here there should only have one item
	 */
	public ArrayList<Long> getAllTreeGameScore() {
		ArrayList<Long> tgs = new ArrayList<Long>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String select = "select * from " + TABLE_TREESTRATEGY_SCORE;
		Cursor c = db.rawQuery(select, null);
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			long curr_score = c.getLong(1);
			tgs.add(curr_score);
		}
		
		Log.d("TreeStrategy", "(Action)Return all current saved items.");
		return tgs;
	}
	
	/**
	 * public API to get the highest score from the table
	 * here we suppose users only save the last game, so here there should only have one item
	 */
	public ArrayList<Long> getAllTreeGameHighestScore() {
		ArrayList<Long> tghs = new ArrayList<Long>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String select = "select * from " + TABLE_TREESTRATEGY_HIGHESTSCORE;
		Cursor c = db.rawQuery(select, null);
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
			long curr_highest_score = c.getLong(1);
			tghs.add(curr_highest_score);
		}
		
		Log.d("TreeStrategy", "(Action)Return all current saved items.");
		return tghs;
	}
	
	/**
	 * here we give a public API, which is used to update current item.
	 */
	public void saveTreeGame(TreeGameObject tgo, long score) {
		clearTreeTables();
		insertTreeGameObject(tgo);
		insertTreeGameScore(score);
		Log.d("TreeStrategy", "(Action)Save current game status.");
	}

}
