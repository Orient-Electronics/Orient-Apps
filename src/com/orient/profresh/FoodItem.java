package com.orient.profresh;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class FoodItem {

	private int id;
	private String name;
	private String category;
	private String description;
	private ArrayList<String> items;
	//private String vit_c;
	//private String minerals;
	private String fresh_normal;
	private String fresh_pro;
	private boolean status;
	private byte[] image;
	//private byte[] icon;
	
	
	public FoodItem() {
		
	}
	
	
	public FoodItem(String name,String category,String description,String items,String fresh_normal,String fresh_pro,boolean status,byte[] image)
	{
		
		this.name=name;
		this.category=category;
		this.description=description;
		//this.items=items;
		
		this.fresh_normal=fresh_normal;
		this.fresh_pro=fresh_pro;
		this.status=status;
		
		this.image=image;
		//this.icon=icon;
		
		StringTokenizer st = new StringTokenizer(items, ";");

		this.items = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			this.items.add(s);
		}
	}
	
	public FoodItem(int id,String name,String category,String description,String items,String fresh_normal,String fresh_pro,boolean status,byte[] image)
	{
		this.id=id;
		this.name=name;
		this.category=category;
		this.description=description;
		//this.items=items;
		
		this.fresh_normal=fresh_normal;
		this.fresh_pro=fresh_pro;
		this.status=status;
		
		this.image=image;
		
		
		StringTokenizer st = new StringTokenizer(items, ";");

		this.items = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			this.items.add(s);
		}
	}
	
	public FoodItem(String name,String category,String description,String items,String fresh_normal,String fresh_pro,boolean status)
	{
		
		this.name=name;
		this.category=category;
		this.description=description;
		//this.items=items;
		
		this.fresh_normal=fresh_normal;
		this.fresh_pro=fresh_pro;
		this.status=status;
		
		StringTokenizer st = new StringTokenizer(items, ";");

		this.items = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			this.items.add(s);
		}

	
	}
	
	
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setFresh_normal(String fresh_normal) {
		this.fresh_normal = fresh_normal;
	}
	
	public void setFresh_pro(String fresh_pro) {
		this.fresh_pro = fresh_pro;
	}
	

	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setitems(String items) {
		//this.items = items;
	}
	
  
    
    
    public void setCategory(String category) {
		this.category = category;
	}
    public String getDescription() {
		return description;
	}
    
    public String getFresh_normal() {
		return fresh_normal;
	}
    
    public String getFresh_pro() {
		return fresh_pro;
	}
    
  
    
    public String getName() {
		return name;
	}
    
    public ArrayList<String> getitems() {
		return items;
	}
    
  
    
    public String getCategory() {
		return category;
	}
    
    public boolean isStatus() {
		return status;
	}
    

    
    public byte[] getImage() {
		return image;
	}

    public void setImage(byte[] image) {
		this.image = image;
	}
    
    public void setId(int id) {
		this.id = id;
	}
    
    public int getId() {
		return id;
	}
}
