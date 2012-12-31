package com.orient.profresh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

public class Splash extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 4000; // time to display the splash screen in ms
	private Context me;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		 me=this;
	    // thread for displaying the SplashScreen
	    Thread splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                int waited = 0;
	                while(_active && (waited < _splashTime)) {
	                    sleep(100);
	                    if(_active) {
	                        waited += 100;
	                    }
	                }
	            } catch(InterruptedException e) {
	                // do nothing
	            } finally {
	                finish();
	                //startActivity(new Intent("com.obs.BotomtabActivity"));
	                Intent intentinfo = new Intent().setClass(me, MainTabs.class);
	                me.startActivity(intentinfo);
	                //stop();
	            }
	        }
	    };
	    splashTread.start();
	}
	
	

}
