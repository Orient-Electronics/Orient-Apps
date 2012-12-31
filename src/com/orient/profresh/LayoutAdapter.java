package com.orient.profresh;





import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LayoutAdapter extends BaseAdapter {

	
	private ArrayList<FoodItem> values;
	private Context context;
	public LayoutAdapter(Context context,ArrayList<FoodItem> arr) {
		super();
		this.context=context;
		this.values=arr;
	}
	

	
	
	
	public int getCount() {
		if(this.values!=null)
			return values.size();
		else 
			return 0;
	}

	
	public Object getItem(int position) {
		
		return values.get(position);
	}

	
	public long getItemId(int position) {
		
		return 0;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup layout= (ViewGroup) inflater.inflate(R.layout.item_layout, parent, false);
		
		ImageView image=(ImageView) layout.findViewById(R.id.image_food_item);
		byte[] blob=values.get(position).getImage();
		Bitmap bmp=BitmapFactory.decodeByteArray(blob,0,blob.length);
		image.setImageBitmap(bmp);
		
		TextView item_name=(TextView) layout.findViewById(R.id.food_name);
		
		item_name.setText(values.get(position).getName().toUpperCase());
		Typeface face=Typeface.createFromAsset(this.context.getAssets(), "fonts/eurof55.ttf"); 
		item_name.setTypeface(face); 
		    
		
		
		
		TextView refri=(TextView) layout.findViewById(R.id.days_ref);
		refri.setText(values.get(position).getFresh_normal() + " Days");
		refri.setTypeface(face);
		
		TextView profresh=(TextView) layout.findViewById(R.id.days_profresh);
		profresh.setText(values.get(position).getFresh_pro() + " Days");
		profresh.setTypeface(face);
	
		Button btn= (Button) layout.findViewById(R.id.button_fav);
		btn.setId(values.get(position).getId());
		//btn.setSelected(values.get(position).isStatus());
		this.setFavButtonBackground(values.get(position).isStatus(), btn);
		
		
	    btn.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
				//Log.v("Fav Button Clicked","FAV BUTTON");
				boolean flag=false;
				int id=v.getId();
				Cursor cur=context.getContentResolver().query(FoodMetaData.CONTENT_CHECK_STATUS, null, FoodMetaData.KEY_ROWID + " = "+ id, null, null);
				
				if(cur.moveToNext())
				{
					flag=cur.getInt(6)>0;
				}
				
				flag=!flag;
				setFavButtonBackground(flag, (Button) v);
				changeStatusInDB(flag,id);
				
			}
		});
	    
	    
	   
		return layout;
	}

	
	
	private void setFavButtonBackground(boolean flag,Button btn)
	{
		if(flag)
			btn.setBackgroundResource(R.drawable.star_pressed);
		else
			btn.setBackgroundResource(R.drawable.star_unpressed);
			
		
	}
	
	
	private void changeStatusInDB(boolean status,int rowid)
	{
		ContentValues values=new ContentValues();
		values.put(FoodMetaData.KEY_STATUS, status);
		
		String where=FoodMetaData.KEY_ROWID + " = " + rowid;
		
		this.context.getContentResolver().update(FoodMetaData.CONTENT_UPDATE_STATUS, values, where, null);
		//Log.e("Status of :"+ where, ""+status);
		
		for(int i=0;i<this.values.size();i++)
		{
			if(rowid==this.values.get(i).getId())
			{
				this.values.get(i).setStatus(status);
			}
		}
	}


}
