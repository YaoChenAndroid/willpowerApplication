package com.example.willpower.models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.willpower.controllers.R;
import com.parse.Parse;
import com.parse.ParseObject;

public class dataExchange{
	public static final int DATA_TYPE_MAP = 0;
	public final String[] mapKey = {"Name", "PointX", "PointY"};
	private Context curCon;
	public dataExchange(Context source)
	{
		curCon = source;
		Parse.initialize(source, "k67gag0IGiefqnYZHySJmvEiwpEwpi6c1uk5ExUl", "nmRcR7jOVAamqxtL9TmuWA0uBzZoJcJGNYFYVZxz");
	}
	public void saveData(String[] data, int type)
	{
		switch(type)
		{
			case DATA_TYPE_MAP:
				saveMap(data);
				break;
		}
	}
	private void saveMap(String[] data) {
		// TODO Auto-generated method stub
		if(curCon == null)
			return;
		if(data == null || data.length < 3)
		{
			Log.e(curCon.getString(R.string.yao_social_error_map_data) ,curCon.getString(R.string.yao_social_error_map_data_message));
			return;
		}
		ParseObject testObject = new ParseObject("MapPoint");
		for(int i = 0, nLen = data.length; i < nLen; i++)
		{
			testObject.put(mapKey[i], data[i]);
		}		
		testObject.saveInBackground();
			
	}
}
