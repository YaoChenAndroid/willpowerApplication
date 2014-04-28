package com.example.willpower.yao.controllers;

public class CurrentUserInfo {
	private static CurrentUserInfo currentUser = null;
	public String userName = "";
	public String UserId = "";
	public String UserGoal = "";
	protected CurrentUserInfo(){};
	public static synchronized CurrentUserInfo getInstance(){
		if(null == currentUser){
			currentUser = new CurrentUserInfo();
		}
		return currentUser;
	}
}
