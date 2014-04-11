package com.teamtreehouse.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class RibbitApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		  Parse.initialize(this, "BgSuc0RvWGTmzdiyN2OCNdtqCzTdJ2mwUSx9Eevj", "nYAAwbRNBqE1XhQWs9vyWUmleFkUVspHKgxE6vkY");
		  
		  ParseObject testObject = new ParseObject("TestObject");
		  testObject.put("foo", "bar");
		  testObject.saveInBackground();	
	}

}
