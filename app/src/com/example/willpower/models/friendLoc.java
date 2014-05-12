package com.example.willpower.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Location")
public class friendLoc extends ParseObject{
	private final static String TAG = "friendLoc";
	public String getText()
	{
		return getString("name");
	}
	public void setText(String value)
	{
		put("name", value);
	}
	public String getUserID()
	{
		return getString("UserID");
	}
	public void setUserID(String value)
	{
		put("UserID", value);
	}
	public ParseGeoPoint getLocation()
	{
		return getParseGeoPoint("location");
	}
	public void setLocation(ParseGeoPoint value)
	{
		put("location", value);
	}
	public String getUserGoal()
	{
		return getString("UserGoal");
	}
	public void setUserGoal(String goal)
	{
		put("UserGoal", goal);
	}
	public static ParseQuery<friendLoc> getQuery()
	{
		return ParseQuery.getQuery(friendLoc.class);
	}
}
