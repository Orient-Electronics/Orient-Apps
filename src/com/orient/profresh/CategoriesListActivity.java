package com.orient.profresh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class CategoriesListActivity extends ActivityGroup implements
		OnItemClickListener {

	private Cursor cursor;
	private ListView list;
	private ArrayList<Category> cats;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.setContentView(R.layout.foods);
		this.list = (ListView) this.findViewById(R.id.myFoodsList);
		this.list.setDivider(null);
		this.list.setDividerHeight(0);
		this.list.setCacheColorHint(0);
		this.list.setPadding(25, 0, 0, 0);

		cats = this.getItemsWithUniqueCategory();
		if (cats != null)
			this.list.setAdapter(new CategoriesAdapter(this, cats));
		else
			Toast.makeText(this, "Categories are empty", Toast.LENGTH_LONG)
					.show();

		this.list.setOnItemClickListener(this);

	}

	@SuppressLint("ParserError")
	private String[] fillData(String whereClause) throws SQLException,
			IOException {

		List<String> cats = new ArrayList<String>();
		cursor = this.getContentResolver().query(
				FoodMetaData.CONTENT_URI_CATEGORIES, null, whereClause, null,
				null);

		while (cursor.moveToNext())
			cats.add(new String(cursor.getString(0)));

		String[] catarr = new String[cats.size()];
		for (int i = 0; i < cats.size(); i++) {
			catarr[i] = cats.get(i);
		}

		cursor.close();
		return catarr;
	}

	public void replaceContentView(String id, Intent newIntent) {

		View view = this
				.getLocalActivityManager()
				.startActivity(id,
						newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView();

		this.setContentView(view);

	}

	
	@Override
	protected void onResume() {
		super.onResume();

	}

	private ArrayList<Category> getItemsWithUniqueCategory() {

		Cursor cur = this.getContentResolver().query(
				FoodMetaData.IconTableMetaData.CONTENT_ICONS, null, null, null,
				null);

		if (cur == null)
			return null;

		ArrayList<Category> data = new ArrayList<Category>();
		while (cur.moveToNext()) {

			Category item = new Category();
			item.setCategory(cur.getString(0));
			item.setIcon(cur.getBlob(1));
			data.add(item);
		}

		cur.close();
		return data;
	}

	
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {

		Intent intent = new Intent();
		intent.setClass(CategoriesListActivity.this, FoodListActivity.class);
		intent.putExtra(FoodMetaData.KEY_CAT, cats.get(position).getCategory());
		MainTabs.CATEGORY_SELECTED = cats.get(position).getCategory();
		replaceContentView("FoodsList", intent);

	}
}
