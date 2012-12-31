package com.orient.profresh;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class FoodContentProvider extends ContentProvider {

	private SQLiteOpenHelper foodsDatabaseHelper;
	private SQLiteDatabase foodsDb;
	
	
	static final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
	 static{
		 matcher.addURI(FoodMetaData.AUTHORITY, FoodMetaData.PATH_CATEGORIES, FoodMetaData.INDICATOR_FOOD_CATEGORIES);
		 matcher.addURI(FoodMetaData.AUTHORITY, FoodMetaData.PATH_ITEMS, FoodMetaData.INDICATOR_FOOD_ITEMS);
		 matcher.addURI(FoodMetaData.AUTHORITY, FoodMetaData.PATH_SEARCH, FoodMetaData.INDICATOR_FOOD_SEARCH);
		 matcher.addURI(FoodMetaData.AUTHORITY, FoodMetaData.PATH_UPDATE_STATUS,FoodMetaData.INDICATOR_UPDATE_STATUS);
		 matcher.addURI(FoodMetaData.AUTHORITY, FoodMetaData.PATH_CHECK_STATUS,FoodMetaData.INDICATOR_CHECK_STATUS);
		 matcher.addURI(FoodMetaData.AUTHORITY, FoodMetaData.ItemsTableMetaData.PATH_DETAILS, FoodMetaData.ItemsTableMetaData.INDICATOR_DETAILS);
		 matcher.addURI(FoodMetaData.AUTHORITY, FoodMetaData.IconTableMetaData.PATH_ICONS, FoodMetaData.IconTableMetaData.INDICATOR_ICONS);
		 
	 }

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		this.foodsDatabaseHelper=new FoodDatabaseOpenHelper(this.getContext(),FoodMetaData.DATABASE_NAME,null,FoodMetaData.DATABASE_VERSION);//,null,null);

		this.foodsDb=foodsDatabaseHelper.getReadableDatabase();
		if(this.foodsDb!=null)
			return true;
		else 
			return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		Cursor cur=null;
		switch(matcher.match(uri))
		{
		
		case FoodMetaData.INDICATOR_FOOD_ITEMS:
			//Log.e("Inside query ", "Goig to return food ITEMS");
			cur=this.foodsDb.query(FoodMetaData.DATABASE_TABLE, projection, selection, null, null, null, null);
			break;
			
		case FoodMetaData.INDICATOR_FOOD_SEARCH:
			//Log.e("Inside query ", "Goig to return food search ITEMS");
			cur=this.foodsDb.query(FoodMetaData.DATABASE_TABLE, null, selection, null, null, null, null);
			break;
			
		case FoodMetaData.INDICATOR_CHECK_STATUS:
			cur=this.foodsDb.query(FoodMetaData.DATABASE_TABLE, null, selection, null, null, null, null);
			break;
			
		case FoodMetaData.ItemsTableMetaData.INDICATOR_DETAILS:
			cur=this.foodsDb.query(FoodMetaData.ItemsTableMetaData.DATABASE_TABLE_NAME, projection, selection, null, null, null, null);
			break;
			
		case FoodMetaData.IconTableMetaData.INDICATOR_ICONS:
			cur=this.foodsDb.query(FoodMetaData.IconTableMetaData.DATABASE_TABLE_NAME, projection, selection, null, null, null, null);
			break;
			
		}
		
		
		return cur;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
	
		int res=0;
		switch(matcher.match(uri))
		{
		case FoodMetaData.INDICATOR_UPDATE_STATUS:
			//Log.v("Performing UPDATE OPERATION ON DATABASE", "UPDATE STATUS");
			res=this.foodsDb.update(FoodMetaData.DATABASE_TABLE, values, selection, selectionArgs);
			break;
		}
		return res;
	}

}
