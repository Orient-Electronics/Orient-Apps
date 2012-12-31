package com.orient.profresh;


import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TableRow.LayoutParams;

public class MainTabs extends TabActivity {

	public static String FLAG_FAVOURITE="FAVOURITE";
	public static String CATEGORY_SELECTED = "Meat";
	public static TabHost tabHost;
	Resources res;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		res = getResources(); // Resource object to get Drawables
		tabHost = getTabHost(); // The activity TabHost

		LayoutParams logoParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

		logoParams.setMargins(30, 50, 40, 40);

		addTabs();

	}

	private void addTabs() {
		TabHost.TabSpec spec;

		Intent intentCategories = null; // Reusable Intent for each tab
		intentCategories = new Intent().setClass(this,
				CategoriesListActivity.class);
		intentCategories.putExtra("tab", "0");

		Intent intentSearch = null; // Reusable Intent for each tab
		intentSearch = new Intent().setClass(this, FoodListActivity.class);
	
		Intent intentProducts=null;
		intentProducts=new Intent().setClass(this, ProductsListActivity.class);
		
		intentSearch.putExtra("tab", "1");
		intentSearch.putExtra(FLAG_FAVOURITE, "1");
		Intent intentAbout = null;
		intentAbout = new Intent().setClass(this, OrientProFreshActivity.class);
		intentAbout.putExtra("tab", "2");
		
		spec = tabHost.newTabSpec("Categories")
				.setIndicator("",res.getDrawable(R.drawable.ic_tab_cat)).setContent(intentCategories);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("Favourite")
				.setIndicator("", res.getDrawable(R.drawable.ic_tab_fav))
				.setContent(intentSearch);
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("About Profresh")
				.setIndicator("", res.getDrawable(R.drawable.ic_tab_prod))
				.setContent(intentProducts);
		
		tabHost.addTab(spec);
		
		spec = tabHost.newTabSpec("Products")
				.setIndicator("", res.getDrawable(R.drawable.ic_tab_pf))
				.setContent(intentAbout);
		
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
		this.setTabColor(tabHost);
	}

	public void setTabColor(TabHost tabhost) {
		for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
			tabhost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#05326e")); // unselected
		}
		tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#05326e")); // selected
	}

	
	@Override
	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	};
}
