package com.orient.profresh;

import android.net.Uri;



public class FoodMetaData {

	public static final String QUERY_CREATE_TABLE="CREATE TABLE 'Food' ('Name' TEXT PRIMARY KEY  NOT NULL , 'Category' TEXT, 'Description' TEXT, 'VitaminA' TEXT, 'VitaminC' TEXT, 'Minerals' TEXT, 'FreshNormal' TEXT, 'FreshPro' TEXT)";
	public static final String KEY_ROWID = "rowid";
	public static final String KEY_NAME = "Name";
	public static final String KEY_CAT= "Category";
	public static final String KEY_DESC = "Description";
	public static final String KEY_ITEMS = "Items";
	
	public static final String KEY_FRESHTIME_NORMAL = "FreshNormal";
	public static final String KEY_FRESHTIME_PROFRESH = "FreshPro";
	public static final String KEY_STATUS="status";
	
	public static final String KEY_IMAGE="image";
	
	public static final String DATABASE_TABLE = "Food";

	public static final String DB_PATH = "/data/data/com.orient.profresh/databases/";
	public static final String DB_NAME = "orprofreshfoods.sqlite";
	
	public static final String DATABASE_NAME="orprofreshfoods.sqlite";
	public static final int DATABASE_VERSION=1;
	
	
	
	public static final String AUTHORITY= "com.orient.profresh.FoodContentProvider";
	
	public static final Uri CONTENT_URI_CATEGORIES=Uri.parse("content://"+AUTHORITY+"/categories");
	public static final Uri CONTENT_URI_ITEMS=Uri.parse("content://"+AUTHORITY+"/items");
	public static final Uri CONTENT_URI_SEARCH=Uri.parse("content://"+AUTHORITY+"/search");
	public static final Uri CONTENT_UPDATE_STATUS=Uri.parse("content://"+AUTHORITY+"/update");
	public static final Uri CONTENT_CHECK_STATUS=Uri.parse("content://"+AUTHORITY+"/check");
	
	
	public static final String PATH="Food";
	public static final String PATH_CATEGORIES="categories";
	public static final String PATH_ITEMS="items";
	public static final String PATH_SEARCH="search";
	public static final String PATH_UPDATE_STATUS="update";
	public static final String PATH_CHECK_STATUS="check";
	
	public static final int INDICATOR_FOOD_CATEGORIES=1;
	public static final int INDICATOR_FOOD_ITEMS=2;
	public static final int INDICATOR_FOOD_SEARCH=3;
	public static final int INDICATOR_UPDATE_STATUS=4;
	public static final int INDICATOR_CHECK_STATUS=5;
	
	
	
	public static final class ItemsTableMetaData{
		public static final String KEY_ROWID = "rowid";
		public static final String DATABASE_TABLE_NAME="vitamin";
		public static final String KEY_ITEM="item";
		public static final String KEY_DETAILS="details";
		public static final int INDICATOR_DETAILS=6;
		
		public static final Uri CONTENT_DETAILS=Uri.parse("content://"+AUTHORITY+"/details");
		public static final String PATH_DETAILS="details";
		
		private ItemsTableMetaData(){
		}
	}
	
	public static final class IconTableMetaData{
		public static final String KEY_ROWID="rowid";
		public static final String DATABASE_TABLE_NAME="icons";
		public static final String KEY_CAT="category";
		public static final String KEY_ICON="icon";
		public static final int INDICATOR_ICONS=7;
		
		public static final Uri CONTENT_ICONS=Uri.parse("content://"+AUTHORITY+"/icons");
		public static final String PATH_ICONS="icons";
		
	}
	private FoodMetaData(){
		
	}

}
