package com.example.willpower.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("User")
public class User extends ParseObject{
	private final static String TAG = "User";
	public String getEmail()
	{
		return getString("Email");
	}
	public void setEmail(String value)
	{
		put("Email", value);
	}
	public String getName()
	{
		return getString("Name");
	}
	public void setName(String value)
	{
		put("Name", value);
	}
	public String getPassword()
	{
		return getString("Password");
	}
	public void setPassword(String value)
	{
		put("Password", value);
	}
	public static ParseQuery<User> getQuery()
	{
		return ParseQuery.getQuery(User.class);
	}
}
