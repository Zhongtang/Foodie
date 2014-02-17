package com.yahoo.foodie.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.yahoo.foodie.fragments.RestaurantListFragment;
import com.yahoo.group12.foodie.R;

public class RestaurantListActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		loadRestaurantList();
	}

	private void loadRestaurantList() {
		FragmentTransaction fts = getSupportFragmentManager()
				.beginTransaction();
		RestaurantListFragment restaurantListFragment = new RestaurantListFragment();
		fts.replace(R.id.frame_restaurant_list_container,
				restaurantListFragment);
		fts.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_list, menu);
		return true;
	}

}
