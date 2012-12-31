package com.orient.profresh;

import java.util.ArrayList;
import java.util.List;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class FoodListActivity extends ActivityGroup implements
		OnItemClickListener, OnClickListener {

	private ArrayList<CategoryButton> topMenuKeys;
	private LinearLayout mylayout;
	private View popup_layout;
	private PopupWindow popup_window;
	private LayoutAdapter adapter;
	private CustomGalleryView gallery;
	private ArrayList<FoodItem> arr;
	private ArrayList<Item> details;
	private String[] categories = null;
	private LinearLayout menu_categories = null;
	private ImageButton search;
	private boolean flag = false;
	private View searchPopupLayout;
	private PopupWindow searchPopup;
	private EditText searchTextBox;
	
	private boolean searchFlag=false;
	//private CategoryButton searchBtn;
	
	@Override
	protected void onPause() {
		super.onPause();
		this.flag = true;
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		if (this.flag) {
			this.initializeActivity();

		}
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.setContentView(R.layout.food_gallery);
		this.mylayout = (LinearLayout) this.findViewById(R.id.gallery_layout);
		this.search = (ImageButton) this.findViewById(R.id.button_topMenu_search);
		this.search.setOnClickListener(this);
		this.menu_categories = (LinearLayout) this
				.findViewById(R.id.menu_categories);
		this.categories = this.getAllCategories();
		//this.searchBtn=(CategoryButton) this.findViewById(R.id.button_topMenu_search);
		//this.searchBtn.setResources("search");
		this.initializeActivity();

	}

	private void initializeActivity() {

		if (this.getIntent().getExtras().get(MainTabs.FLAG_FAVOURITE) != null) {
			arr = null;
			// this.category=MainTabs.CATEGORY_SELECTED;
			arr = this.getFavouriteItems();

		} else {
			arr = this.getAllItems();
		}
		this.setTopMenu();
		this.setAdapter();
		this.details = this.getItemDetails();
	}

	private void setAdapter() {

		if (this.arr == null || this.arr.size() < 1) {

			Typeface face = Typeface.createFromAsset(this.getAssets(),
					"fonts/EurostileTOT-Regular.otf");

			this.findViewById(R.id.item_notfound).setVisibility(View.VISIBLE);
			this.findViewById(R.id.mygallery).setVisibility(View.INVISIBLE);

			TextView not_found = (TextView) this
					.findViewById(R.id.item_notfound);
			not_found.setTypeface(face);
			return;
		} else {
			this.findViewById(R.id.item_notfound).setVisibility(View.GONE);
			this.findViewById(R.id.mygallery).setVisibility(View.VISIBLE);
			adapter = new LayoutAdapter(this, arr);
			gallery = (CustomGalleryView) findViewById(R.id.mygallery);
			gallery.setAdapter(adapter);
			gallery.setOnItemClickListener(this);
		}

		flag = true;
	}

	
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Intent intent = new Intent();
		// intent.setClass(this, ViewFood.class);
		intent.putExtra(FoodMetaData.KEY_NAME, arr.get(arg2).getName());
		intent.putExtra(FoodMetaData.KEY_CAT, arr.get(arg2).getCategory());
		intent.putExtra(FoodMetaData.KEY_DESC, arr.get(arg2).getDescription());
		intent.putExtra(FoodMetaData.KEY_ITEMS, arr.get(arg2).getitems());
		// intent.putExtra(FoodMetaData.KEY_VITAMIN_C,
		// arr.get(arg2).getVit_c());
		// intent.putExtra(FoodMetaData.KEY_MINERALS,
		// arr.get(arg2).getMinerals());
		intent.putExtra(FoodMetaData.KEY_FRESHTIME_NORMAL, arr.get(arg2)
				.getFresh_normal());
		intent.putExtra(FoodMetaData.KEY_FRESHTIME_PROFRESH, arr.get(arg2)
				.getFresh_pro());

		// this.startActivity(intent);
		this.initiatePopupWindow(20, 0, 15, arg2);

	}

	public ArrayList<FoodItem> getFavouriteItems() {

		Cursor cur;

		String[] projection = { FoodMetaData.KEY_ROWID, FoodMetaData.KEY_NAME,
				FoodMetaData.KEY_CAT, FoodMetaData.KEY_DESC,
				FoodMetaData.KEY_ITEMS, FoodMetaData.KEY_FRESHTIME_NORMAL,
				FoodMetaData.KEY_FRESHTIME_PROFRESH, FoodMetaData.KEY_STATUS,
				FoodMetaData.KEY_IMAGE };

		cur = this.getContentResolver()
				.query(FoodMetaData.CONTENT_URI_ITEMS,
						projection,
						FoodMetaData.KEY_STATUS + " = 1 AND "
								+ FoodMetaData.KEY_CAT + " = '"
								+ MainTabs.CATEGORY_SELECTED + "'", null, null);

		/*
		 * if (cur == null) { cur = this.getContentResolver().query(
		 * FoodMetaData.CONTENT_URI_ITEMS, projection, null, null, null); }
		 */

		ArrayList<FoodItem> data = this.getArrayListofFoodItems(cur);
		return data;
	}

	public ArrayList<FoodItem> getAllItems() {

		Cursor cur = null;

		String[] projection = { FoodMetaData.KEY_ROWID, FoodMetaData.KEY_NAME,
				FoodMetaData.KEY_CAT, FoodMetaData.KEY_DESC,
				FoodMetaData.KEY_ITEMS, FoodMetaData.KEY_FRESHTIME_NORMAL,
				FoodMetaData.KEY_FRESHTIME_PROFRESH, FoodMetaData.KEY_STATUS,
				FoodMetaData.KEY_IMAGE };

		if (this.getIntent().getExtras().get(MainTabs.FLAG_FAVOURITE) != null
				&& MainTabs.CATEGORY_SELECTED != null) {
			cur = this.getContentResolver().query(
					FoodMetaData.CONTENT_URI_ITEMS,
					projection,
					FoodMetaData.KEY_STATUS + " = 1 AND "
							+ FoodMetaData.KEY_CAT + " = '"
							+ MainTabs.CATEGORY_SELECTED + "'", null, null);
		} else if (MainTabs.CATEGORY_SELECTED != null) {
			cur = this.getContentResolver().query(
					FoodMetaData.CONTENT_URI_ITEMS,
					projection,
					FoodMetaData.KEY_CAT + "='" + MainTabs.CATEGORY_SELECTED
							+ "'", null, null);
		} else {
			cur = this.getContentResolver().query(
					FoodMetaData.CONTENT_URI_ITEMS, projection, null, null,
					null);
		}

		if (cur == null)
			return null;

		ArrayList<FoodItem> data = this.getArrayListofFoodItems(cur);
		return data;
	}

	public String[] getAllCategories() {

		List<String> cats = new ArrayList<String>();
		Cursor cursor = this.getContentResolver().query(
				FoodMetaData.IconTableMetaData.CONTENT_ICONS, null, null, null,
				null);

		while (cursor.moveToNext())
			cats.add(new String(cursor.getString(0)));

		String[] catarr = new String[cats.size()];
		for (int i = 0; i < cats.size(); i++) {
			catarr[i] = cats.get(i);
		}

		return catarr;

	}

	public ArrayList<Item> getItemDetails() {

		Cursor cur;

		String[] projection = { FoodMetaData.ItemsTableMetaData.KEY_ROWID,
				FoodMetaData.ItemsTableMetaData.KEY_ITEM,
				FoodMetaData.ItemsTableMetaData.KEY_DETAILS };
		cur = this.getContentResolver().query(
				FoodMetaData.ItemsTableMetaData.CONTENT_DETAILS, projection,
				null, null, null);
		if (cur == null)
			return null;

		ArrayList<Item> data = new ArrayList<Item>();
		while (cur.moveToNext()) {

			data.add(new Item(cur.getInt(0), cur.getString(1), cur.getString(2)));

		}
		return data;
	}

	public void setTopMenu() {
		if (this.categories == null || this.menu_categories == null)
			return;

		if (this.menu_categories.getChildCount() > 0)
			this.menu_categories.removeAllViews();

		this.topMenuKeys = new ArrayList<CategoryButton>();

		for (int i = 0; i < this.categories.length; i++) {
			CategoryButton btn = new CategoryButton(this);
			btn.setId(i);
			btn.setAdjustViewBounds(true);
			btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			btn.setResources(this.categories[i]);
		
			this.menu_categories.addView(btn);

			btn.setCategory(this.categories[i]);
			
			btn.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {

					int id = v.getId();

					for (int i = 0; i < topMenuKeys.size(); i++) {
						if (topMenuKeys.get(i).isClicked()) {

							topMenuKeys.get(i).toggle();
						}

						if (topMenuKeys.get(i).getId() == id) {
							// category=topMenuKeys.get(i).getCategory();
							MainTabs.CATEGORY_SELECTED = topMenuKeys.get(i)
									.getCategory();
							topMenuKeys.get(i).toggle();
							arr = getAllItems();
							setAdapter();
						}

					}
				}
			});

			this.topMenuKeys.add(btn);

			if (MainTabs.CATEGORY_SELECTED != null) {
				if (MainTabs.CATEGORY_SELECTED.equals(this.categories[i])) {
					btn.toggle();
				}
			}
		}

	}

	public void replaceContentView(String id, Intent newIntent) {
		// Obtain the view of 'Called' activity using its Intent 'newIntent'
		View view = getLocalActivityManager().startActivity(id,
				newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView();

		// set the above view to the content of 'Calling' activity.
		this.setContentView(view);
	}

	public ArrayList<FoodItem> doMySearch(String query) {

		String whereClause = null;// FoodMetaData.KEY_NAME + " LIKE " + "'" +
									// query+ "%'";

		if (this.getIntent().getExtras().getString(MainTabs.FLAG_FAVOURITE) != null) {
			whereClause = FoodMetaData.KEY_NAME + " LIKE " + "'" + query
					+ "%' AND " + FoodMetaData.KEY_STATUS + " = 1 ";
		} else {
			whereClause = FoodMetaData.KEY_NAME + " LIKE " + "'" + query + "%'";
		}

		Cursor cur = this.getContentResolver().query(
				FoodMetaData.CONTENT_URI_SEARCH, null, whereClause, null, null);

		ArrayList<FoodItem> data = this.getArrayListofFoodItems(cur);
		return data;
	}

	private void initiatePopupWindow(int paddingFromParent, int x, int y,
			int position) {

		try {
			LayoutInflater inflater = (LayoutInflater) FoodListActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			this.popup_layout = inflater.inflate(R.layout.view_item,
					(ViewGroup) findViewById(R.id.popup_layout));

			this.popup_window = new PopupWindow(this.popup_layout,
					this.mylayout.getWidth() - paddingFromParent,
					this.mylayout.getHeight() - paddingFromParent, true);

			Typeface desc_face = Typeface.createFromAsset(this.getAssets(),
					"fonts/eurof55.ttf");
			Typeface heading_face = Typeface.createFromAsset(this.getAssets(),
					"fonts/CALIBRI.TTF");

			TextView item_name = (TextView) this.popup_layout
					.findViewById(R.id.item_name);
			item_name.setText(this.arr.get(position).getName());
			Typeface face = Typeface.createFromAsset(this.getAssets(),
					"fonts/eurof55.ttf");
			item_name.setTypeface(face);

			TextView cat_name = (TextView) this.popup_layout
					.findViewById(R.id.category_name);
			cat_name.setTextSize(28);
			cat_name.setTypeface(heading_face);
			cat_name.setText(this.arr.get(position).getCategory());

			TextView desc = (TextView) this.popup_layout
					.findViewById(R.id.category_description);
			desc.setTypeface(desc_face);
			desc.setText(this.arr.get(position).getDescription());

			TextView refri = (TextView) this.popup_layout
					.findViewById(R.id.refri_days);
			refri.setText(this.arr.get(position).getFresh_normal() + " Days");
			refri.setTypeface(desc_face);

			TextView pro = (TextView) this.popup_layout
					.findViewById(R.id.pro_days);
			pro.setText(this.arr.get(position).getFresh_pro() + " Days");
			pro.setTypeface(desc_face);

			LinearLayout layout = (LinearLayout) this.popup_layout
					.findViewById(R.id.vitamins_layout);

			ArrayList<String> vits = this.arr.get(position).getitems();
			for (int i = 0; i < vits.size(); i++) {
				Item item = this.findDetails(vits.get(i));
				if (item != null) {
					TextView heading = new TextView(this);
					heading.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					heading.setTextColor(Color.parseColor("#0c4da2"));
					heading.setTypeface(heading_face);
					heading.setTextSize(28);
					heading.setPadding(10, 10, 0, 0);
					heading.setText(item.getItem());
					layout.addView(heading);

					TextView details_view = new TextView(this);
					details_view.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					details_view.setTextColor(Color.parseColor("#565350"));
					details_view.setTypeface(desc_face);
					details_view.setText(item.getDetails());
					details_view.setPadding(10, 10, 0, 0);
					// details_view.setTextSize(23);
					layout.addView(details_view);
				}

			}

			this.popup_window.setBackgroundDrawable(new BitmapDrawable());
			this.popup_window.setOutsideTouchable(true);
			this.popup_window.showAtLocation(this.popup_layout, Gravity.CENTER,
					x, y);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initiateSearchPopupWindow(int paddingFromParent, int x, int y) {
		
		search.setBackgroundResource(R.drawable.search_pressed);
		try {
			
			LayoutInflater inflater = (LayoutInflater) FoodListActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			this.searchPopupLayout = inflater.inflate(R.layout.search,
					(ViewGroup) findViewById(R.id.linearLayout_popup_search));

			this.searchPopup = new PopupWindow(this.searchPopupLayout,
					this.mylayout.getWidth() - paddingFromParent, 100, true);

			this.searchPopup.setBackgroundDrawable(new BitmapDrawable());
			this.searchPopup.setOutsideTouchable(true);
			this.searchPopup.showAtLocation(this.searchPopupLayout,
					Gravity.TOP, x, y);

			this.searchTextBox = (EditText) this.searchPopupLayout
					.findViewById(R.id.edit_text_searchBox);

			this.searchPopup.setOnDismissListener(new OnDismissListener(){

				
				public void onDismiss() {
					search.setBackgroundResource(R.drawable.search_unpressed);					
				}
				
			});
			
			
			this.searchTextBox.addTextChangedListener(new TextWatcher() {
				
				public void afterTextChanged(Editable arg0) {
				}

				
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					arr = doMySearch(s.toString());
					setAdapter();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			//Log.e("Spreadsheet Activity Popup exception", e.toString());
		}
	}

	public Item findDetails(String name) {
		if (this.details == null)
			return null;

		for (int i = 0; i < this.details.size(); i++) {
			if (this.details.get(i).getItem().equals(name))
				return this.details.get(i);
		}
		return null;
	}

	
	public void onClick(View view) {
		if (view.getId() == R.id.button_topMenu_search) {
			this.initiateSearchPopupWindow(0, 0, 50);
			
		}
	}
	
	

	private ArrayList<FoodItem> getArrayListofFoodItems(Cursor cur) {
		ArrayList<FoodItem> data = new ArrayList<FoodItem>();
		while (cur.moveToNext()) {

			data.add(new FoodItem(cur.getInt(0), cur.getString(1), cur
					.getString(2), cur.getString(3), cur.getString(4), cur
					.getString(5), cur.getString(6), cur.getInt(7) > 0, cur
					.getBlob(8)));

			//Log.v(cur.getString(1), "Item FAV");
		}
		return data;
	}

	
	@Override
	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
	};
	
	
	

}
