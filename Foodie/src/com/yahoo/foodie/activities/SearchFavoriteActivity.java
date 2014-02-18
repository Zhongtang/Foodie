package com.yahoo.foodie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.yahoo.foodie.fragments.SearchFavoriteFragment;
import com.yahoo.group12.foodie.R;

public class SearchFavoriteActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_favorite);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_favorite, menu);
		return true;
	}

	public void onSubmitSearch(MenuItem mi) {
		SearchFavoriteFragment prefs = (SearchFavoriteFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fgmtSearchOptions);
		prefs.onSavePreference();
		Intent i = new Intent(SearchFavoriteActivity.this,
				RestaurantListActivity.class);
		startActivity(i);
	}

}
