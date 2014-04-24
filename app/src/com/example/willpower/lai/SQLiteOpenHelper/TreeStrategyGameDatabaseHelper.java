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
	private static final String DATABASE_NAME = "Willpower_TreeStrategyGame";
	
	private static final String TABLE_TREESTRATEGY = "treeStrategy";
	private static final String COLUMN_TREESTRATEGY_ID = "_id";
	private static final String COLUMN_COSTPERACRE = "costPerAcre";
	private static final String COLUMN_CURRENTACRESINCREASERATE = "currentAcresIncreaseRate";
	private static final String COLUMN_CURRENTACRES = "currentAcres";
	private static final String COLUMN_CURRENTCREDITS = "currentCredits";
	private static final String COLUMN_CURRENTMAINTAINPERACRE = "currentMaintainPerAcre";
	private static final String COLUMN_CURRENTVALUEPERACRE = "currentValuePerAcre";
	
	
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
		db.execSQL(create_table);
		Log.d("TreeStrategy", "(Initialize)Start database model.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String drop_table = "drop table if exists " + TABLE_TREESTRATEGY;
		db.execSQL(drop_table);
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
	
	/**
	 * public API to insert to the table
	 */
	public void insertTreeGameObject(TreeGameObject tgo) {
		insertTreeBasic(tgo);
		Log.d("TreeStrategy", "(Action)Inert new tree object.");
	}
	
	/**
	 * public API to remove all items in table
	 */
	public void clearTreeTables() {
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete(TABLE_TREESTRATEGY, null, null);
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
	 * here we give a public API, which is used to update current item.
	 */
	public void saveTreeGame(TreeGameObject tgo) {
		clearTreeTables();
		insertTreeGameObject(tgo);
		Log.d("TreeStrategy", "(Action)Save current game status.");
	}

}
