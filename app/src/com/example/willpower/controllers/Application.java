package com.example.willpower.controllers;

import com.example.willpower.models.User;
import com.example.willpower.models.friendLoc;
import com.example.willpower.models.userFriend;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();

	// Initialize the Parse SDK.
	Parse.initialize(this, "k67gag0IGiefqnYZHySJmvEiwpEwpi6c1uk5ExUl", "nmRcR7jOVAamqxtL9TmuWA0uBzZoJcJGNYFYVZxz"); 

	// Specify an Activity to handle all pushes by default.
	
    ParseObject.registerSubclass(friendLoc.class);
    ParseObject.registerSubclass(userFriend.class);
    ParseObject.registerSubclass(User.class);
	PushService.setDefaultPushCallback(this, MainActivity.class);
  }
}