package com.orient.profresh;

public class Item {
	
	private int rowid;
	private String item;
	private String details;
	
	
	public Item() {}
	
	public Item(int rowid,String item,String details)
	{
		this.rowid=rowid;
		this.item=item;
		this.details=details;
	}
	
	public Item(String item,String details){
		this.item=item;
		this.details=details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	
	public String getDetails() {
		return details;
	}
	
	public String getItem() {
		return item;
	}
	
	public int getRowid() {
		return rowid;
	}
	
	

}
