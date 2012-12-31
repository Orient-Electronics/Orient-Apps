package com.orient.profresh;

import android.graphics.drawable.Drawable;

public class Product {

	private String category;
	private Drawable icon;
	
	public Product() {
		
	}
	
	public Product(String category,Drawable icon){
		this.category=category;
		this.icon=icon;
	}

	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
	public String getCategory() {
		return category;
	}
	
	public Drawable getIcon() {
		return icon;
	}
	
	
}
