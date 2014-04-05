package com.example.willpower.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Posts")
public class friendLoc extends ParseObject{

	public String getText()
	{
		return getString("text");
	}
	public void setText(String value)
	{
		put("text", value);
	}
	public ParseGeoPoint getLocation()
	{
		return getParseGeoPoint("location");
	}
	public void setLocation(ParseGeoPoint value)
	{
		put("location", value);
	}
	public static ParseQuery<friendLoc> getQuery()
	{
		return ParseQuery.getQuery(friendLoc.class);
	}
}
