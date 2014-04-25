package com.example.willpower.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Friends")
public class userFriend extends ParseObject{
	private final static String TAG = "userFriend";
	public String getUserID()
	{
		return getString("UserID");
	}
	public void setUserID(String UserID)
	{
		put("UserID", UserID);
	}
	public String getFriendID()
	{
		return getString("FriendID");
	}
	public void setFriendID(String FriendID)
	{
		put("FriendID", FriendID);
	}
	public static ParseQuery<userFriend> getQuery()
	{
		return ParseQuery.getQuery(userFriend.class);
	}

}
