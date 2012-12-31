package com.orient.profresh;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
	private final Context context;
	private final ArrayList<Product> values;

	public ProductAdapter(Context context, ArrayList<Product> values) {
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
		

		icon.setBackgroundDrawable(values.get(position).getIcon());

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
