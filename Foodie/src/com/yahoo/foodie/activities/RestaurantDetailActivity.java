package com.yahoo.foodie.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.yahoo.foodie.fragments.RestaurantDetailFragment;
import com.yahoo.foodie.models.Restaurant;
import com.yahoo.group12.foodie.R;

public class RestaurantDetailActivity extends FragmentActivity {
	private Restaurant restaurant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_detail);
		this.restaurant = (Restaurant) getIntent().getSerializableExtra(
				"restaurant");
		// View pf = findViewById(R.id.frgmtRestaurantDetail);
		// pf.setTag(this.restaurant);
		RestaurantDetailFragment restaurantDetailFragment = (RestaurantDetailFragment) getSupportFragmentManager()
				.findFragmentById(R.id.frgmtRestaurantDetail);
		restaurantDetailFragment.setRestaurant(this.restaurant);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_detail, menu);
		return true;
	}
}
