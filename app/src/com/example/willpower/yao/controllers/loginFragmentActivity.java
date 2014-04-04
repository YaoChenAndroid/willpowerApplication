package com.example.willpower.yao.controllers;
import java.io.EOFException;
import java.security.MessageDigest;

import com.example.willpower.controllers.R;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;

public class loginFragmentActivity extends FragmentActivity {
    private MainFragment mainFragment;  
    

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 

        if (savedInstanceState == null) {  
            // Add the fragment on initial activity setup  
            mainFragment = new MainFragment();  
            getSupportFragmentManager()  
            .beginTransaction()  
            .add(android.R.id.content, mainFragment)  
            .commit();  
       } else {  
          // Or set the fragment from restored state info  
           mainFragment = (MainFragment) getSupportFragmentManager()  
           .findFragmentById(android.R.id.content);  
       }  

	}

	   @Override  
	    public boolean onCreateOptionsMenu(Menu menu) {  
	        // Inflate the menu; this adds items to the action bar if it is present.  
	        getMenuInflater().inflate(R.menu.main, menu);  
	        return true;  
	    } 
}
