package com.orient.profresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CategoryButton extends ImageView {

	private String category;
	private boolean clicked = false;
	private int res_clicked;
	private int res_released;

	public void setResources(String category) {

		if (category.equals("Fruites")) {
			this.res_clicked = R.drawable.fruits_pressed;
			this.res_released = R.drawable.fruits_unpressed;
		} else if (category.equals("Meat")) {
			this.res_clicked = R.drawable.meat_pressed;
			this.res_released = R.drawable.meat_unpressed;
		} else if (category.equals("Vegetables")) {
			this.res_clicked = R.drawable.vegetables_pressed;
			this.res_released = R.drawable.vegetables_unpressed;
		} else if (category.equals("Cheese")) {
			this.res_clicked = R.drawable.cheese_pressed;
			this.res_released = R.drawable.cheese_unpressed;
		} else if (category.equals("Dairy Products")) {
			this.res_clicked = R.drawable.milkdairy_pressed;
			this.res_released = R.drawable.milkdairy_unpressed;
		} else if(category.equals("search")) {
			this.res_clicked = R.drawable.search_pressed;
			this.res_released = R.drawable.search_unpressed;
		}

		// this.setBackgroundResource(this.res_released);

		this.setImageResource(this.res_released);
	}

	public void setRes_released(int res_released) {
		this.res_released = res_released;
	}

	public int getRes_clicked() {
		return res_clicked;
	}

	public int getRes_released() {
		return res_released;
	}

	public CategoryButton(Context context) {
		super(context);
	}

	public CategoryButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public String getCategory() {
		return category;
	}

	public boolean isClicked() {
		return clicked;
	}

	public boolean toggle() {

		if (this.clicked == true) {
			this.clicked = false;
			this.setImageResource(this.res_released);

		} else {
			this.clicked = true;
			this.setImageResource(this.res_clicked);
		}
		return this.clicked;
	}

}
