package com.yahoo.foodie.activity;

import com.yahoo.group12.foodie.R;
import com.yahoo.group12.foodie.R.layout;
import com.yahoo.group12.foodie.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RestaurantListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_list, menu);
		return true;
	}

}
