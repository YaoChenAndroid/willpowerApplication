package com.example.willpower.yao.controllers;
//used in user system to record current user information
// there are only one current user at one time
public class CurrentUserInfo {
	private static CurrentUserInfo currentUser = null;
	public String userName = "";
	public String UserId = "";
	public String UserGoal = "";
	protected CurrentUserInfo(){};
	//single modle to make sure only one current user
	public static synchronized CurrentUserInfo getInstance(){
		if(null == currentUser){
			currentUser = new CurrentUserInfo();
		}
		return currentUser;
	}
}
