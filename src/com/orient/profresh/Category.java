package com.orient.profresh;

public class Category {

	private String category;
	private byte[] icon;
	
	public Category() {
		
	}
	
	public Category(String category,byte[] icon){
		this.category=category;
		this.icon=icon;
	}

	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	
	public String getCategory() {
		return category;
	}
	
	public byte[] getIcon() {
		return icon;
	}
	
	
}
