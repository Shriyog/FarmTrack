package com.example.farmtrack;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

	public class SplashActivity extends Activity {

	    /** Duration of wait **/
	    private final int SPLASH_DISPLAY_LENGTH = 1500;

	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle icicle) {
	        super.onCreate(icicle);
	        /** Hiding Title bar of this activity screen */
	        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	 
	        /** Making this activity, full screen */
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.activity_splash);
            SharedPreferences sp = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);	
            final String str = sp.getString("ph_no", "none");
	        
	        /* New Handler to start the Menu-Activity 
	         * and close this Splash-Screen after some seconds.*/
	        new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	                /* Create an Intent that will start the Menu-Activity. */
	            	
	        		

	        		if(str.equals("none")){
	        			Intent launchIntent = new Intent(getApplicationContext(), NewStartActivity.class);
	        		    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
//	        		    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY );
	        		    startActivity(launchIntent);
	        		    finish();
	        		}
	        		else{
	            	Intent launchIntent = new Intent(getApplicationContext(), MainActivity.class);
	    		    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
	    		    startActivity(launchIntent);
	        		}
	            }
	        }, SPLASH_DISPLAY_LENGTH);
	    }
	}