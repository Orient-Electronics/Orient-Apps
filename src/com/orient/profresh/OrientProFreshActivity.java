package com.orient.profresh;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.widget.ScrollView;

public class OrientProFreshActivity extends Activity {

	private ScrollView view;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
       super.onCreate(savedInstanceState);
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       setContentView(R.layout.about);
       view=(ScrollView) this.findViewById(R.id.scroll_about);
       view.scrollTo(0,view.getTop());  
    }
    
  
}