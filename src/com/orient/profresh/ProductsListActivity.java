package com.orient.profresh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ProductsListActivity extends Activity {

	private PopupWindow alertDialog;
	private LinearLayout mylayout;
	private View popup_layout;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private ArrayList<Product> cats;
	
	ProgressDialog pd;
	static final int Dialog_id = 1;
	
	 private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.products);
		showDialog(Dialog_id);
		
		
		String readProd = readProducts();
		// Log.i("Test", readTwitterFeed);

		
		
		if(readProd.length()>1)
		{
		ListView listView = (ListView) findViewById(R.id.mylist);
		listView.setDivider(null);
		listView.setDividerHeight(0);
		listView.setCacheColorHint(0);
		listView.setPadding(25, 0, 0, 0);
		

		String list[] = null;
		cats = new ArrayList<Product>();

		try {
			JSONObject jsonObj = new JSONObject(readProd);

			if (jsonObj.getInt("count") > 0) {
				list = new String[jsonObj.getInt("count")];

				jsonArray = new JSONArray(jsonObj.getString("items"));
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);

					list[i] = jsonObject.getString("title");
					
					//Category item = new Category();
					Product item = new Product();
					item.setCategory(jsonObject.getString("title"));
					
					JSONObject jsonImage = jsonObject.getJSONObject("image");
					JSONArray thumbnail = jsonImage.getJSONArray("thumbnail");

					String thumbUrl = thumbnail.getString(0);
					String baseUrl = "http://www.orient.com.pk";

					String productImgUrl = baseUrl + thumbUrl;
					
					
					//item.setIcon(this.getResources().getDrawable(R.id.category_icon));
					
					String url = productImgUrl;
					InputStream URLcontent = null;
					try {
						URLcontent = (InputStream) new URL(url).getContent();
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Drawable image = Drawable.createFromStream(URLcontent,
							productImgUrl);
					item.setIcon(image);
					
					cats.add(item);
				    Log.i("Test", jsonObject.getString("title"));

					// Log.i("Test", jsonObject.length());
				}
			} else {
				list = new String[1];
				list[0] = "No Item Found";
			}

			//
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		removeDialog(Dialog_id);
		
		
		/* Reference code for popup window
		 * 
		 * LayoutInflater inflater = (LayoutInflater) FoodListActivity.this
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
				.findViewById(R.id.item_name);*/
		
	/*	
	 * 
	 * Latest test
		mContext = this;
		AlertDialog.Builder builder;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		this.popup_layout = inflater.inflate(R.layout.aggregat,
				(ViewGroup) findViewById(R.id.popup_layout));
		this.alertDialog = new PopupWindow(this.popup_layout,
				this.mylayout.getWidth() - 7,
				this.mylayout.getHeight() - 8, true);*/
		
		
	//	this.alertDialog.setBackgroundDrawable(new BitmapDrawable());
		//this.alertDialog.setOutsideTouchable(true);
	//	this.alertDialog.showAtLocation(layout, Gravity.CENTER,
			//	20, 0);
		
		//builder = new AlertDialog.Builder(mContext);
		//builder.setView(layout);

		//alertDialog =  builder.create();
		
	/*	alertDialog = new Dialog(mContext, android.R.style.Widget_ListView_White);

		alertDialog.setContentView(R.layout.product_layout);*/
	

		
		
		//TextView text = (TextView)findViewById(R.id.category_name);
		//Typeface face = Typeface.createFromAsset(this.getAssets(),
		//		"fonts/EurostileTOT-Regular.otf");
		
		//text.setTypeface(face);
		
		//ImageView img = (ImageView)findViewById(R.id.category_icon);
		//img.setImageResource(R.drawable.refrigerator);

		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				//R.layout.cat_row, R.id.category_name, list);
		
		listView.setAdapter(new ProductAdapter(this, cats));

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
				try {
					jsonObject = jsonArray.getJSONObject(position);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Intent intent;
				try {
					intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(jsonObject
									.getString("permalink")));
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*
				 * New popup testing
				
				alertDialog.setBackgroundDrawable(new BitmapDrawable());
				alertDialog.setOutsideTouchable(true);
				alertDialog.showAtLocation(popup_layout, Gravity.CENTER,
						20, 0);

				*/
				
				/*New Activity testing
				 * 
				 * Intent intentProductItem=null;
				intentProductItem=new Intent().setClass(mContext, ProductItem.class);
				Bundle bun = new Bundle();
				bun.putString("JSONArray", jsonArray.toString());
				
				//intentProductItem.putExtras(bun);
				
				//mContext.startActivity(intentProductItem);
*/				
				// Toast.makeText(getApplicationContext(),
				// "Click ListItem Number " + position, Toast.LENGTH_LONG)
				// .show();

			//	alertDialog.show();
			//	alertDialog.setCanceledOnTouchOutside(true);
				
				
				// Typeface font =
				// Typeface.createFromAsset(getApplicationContext().getAssets(),"Calibri.ttf");
				//Typeface face = Typeface.createFromAsset(getApplicationContext().getAssets(),
						//"fonts/CALIBRI.TTF");

				/*TextView model = (TextView) alertDialog
						.findViewById(R.id.modelName);
				 model.setTypeface(face);

				TextView brand = (TextView) alertDialog
						.findViewById(R.id.brandName);
				brand.setTypeface(face);
				
				TextView category = (TextView) alertDialog
						.findViewById(R.id.categoryProd);
				category.setTypeface(face);

				TextView type = (TextView) alertDialog
						.findViewById(R.id.typeProd);
				type.setTypeface(face);

				TextView desc = (TextView) alertDialog
						.findViewById(R.id.descProd);
				desc.setTypeface(face);
				desc.setPadding(10, 10, 10, 10);

				ImageView productImg = (ImageView) alertDialog
						.findViewById(R.id.imageView1);

				TextView urlLink = (TextView) alertDialog
						.findViewById(R.id.urlProd);
				urlLink.setTypeface(face);

				String imageURL = null;

				String productImgUrl = "";

				try {
					jsonObject = jsonArray.getJSONObject(position);

					model.setText(jsonObject.getString("model"));
					brand.setText(jsonObject.getString("brand"));
					category.setText(jsonObject.getString("category"));
					type.setText(jsonObject.getString("type"));
					desc.setText(jsonObject.getString("description"));

					Log.i("Test", jsonObject.getString("description"));

					// desc.setText("asclkalscknalscknalskcnalcknalscknalscknalsck aslck alskc aksc ,as clkas clka sclk asclka cslka sclka sclka sclaksc laksc laskc lasck alskcanlscknals c oiasckm lkna aslckn lknac aslckn lknasc lknzx lkxn ");
					urlLink.setText(jsonObject.getString("permalink"));

					urlLink.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							Intent intent;
							try {
								intent = new Intent(Intent.ACTION_VIEW, Uri
										.parse(jsonObject
												.getString("permalink")));
								startActivity(intent);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});

					JSONObject jsonImage = jsonObject.getJSONObject("image");
					JSONArray thumbnail = jsonImage.getJSONArray("thumbnail");

					String thumbUrl = thumbnail.getString(0);
					String baseUrl = "http://www.orient.com.pk";

					productImgUrl = baseUrl + thumbUrl;
					Log.i("Test", productImgUrl);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String url = productImgUrl;
				InputStream URLcontent = null;
				try {
					URLcontent = (InputStream) new URL(url).getContent();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Drawable image = Drawable.createFromStream(URLcontent,
						productImgUrl);

				productImg.setBackgroundDrawable(image);
				// productImg.set
*/
			}
		});
		}
		else
		{
			Log.i("Error", "No internet connection");
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error");
			alertDialog.setCanceledOnTouchOutside(true);
			alertDialog.setMessage("Error connecting to network. Please check your internet connection.");
			alertDialog.show();
		}

	}

	public String readProducts() {
		StringBuilder builder = null;
		
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://www.orient.com.pk/api/?action=products&brand=orient&category=refrigerator");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				builder = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(ProductsListActivity.class.toString(),
						"Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		removeDialog(Dialog_id);
		if(builder!=null)
		return builder.toString();
		else
			return "";
	};
	
	@Override
	protected Dialog onCreateDialog(int id){
	       switch(id){
	          case Dialog_id :
	            ProgressDialog pd = new ProgressDialog(this);
	            pd.setTitle("Loading Data");
	            pd.setCancelable(true);
	            return pd;
	            
	       }
	         return null;
	}
	
	public void writeJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("name", "Jack Hack");
			object.put("score", new Integer(200));
			object.put("current", new Double(152.32));
			object.put("nickname", "Hacker");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(object);
	}

	public static Drawable LoadImageFromWebOperations(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			return null;
		}
	}

}
