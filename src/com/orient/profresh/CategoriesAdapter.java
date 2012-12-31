package com.orient.profresh;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesAdapter extends BaseAdapter {
	private final Context context;
	private final ArrayList<Category> values;

	public CategoriesAdapter(Context context, ArrayList<Category> values) {
		super();
		this.context = context;
		this.values = values;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ViewGroup rowView = (ViewGroup) inflater.inflate(R.layout.cat_row,
				parent, false);
		TextView cat_name = (TextView) rowView.findViewById(R.id.category_name);

		Typeface face = Typeface.createFromAsset(this.context.getAssets(),
				"fonts/EurostileTOT-Regular.otf");
		cat_name.setTypeface(face);

		ImageView icon = (ImageView) rowView.findViewById(R.id.category_icon);

		cat_name.setText(values.get(position).getCategory());

		byte[] blob = values.get(position).getIcon();
		Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
		icon.setImageBitmap(bmp);

		return rowView;
	}

	
	public int getCount() {
		if (this.values != null)
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
}
