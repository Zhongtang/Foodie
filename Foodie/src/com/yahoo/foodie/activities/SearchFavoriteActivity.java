package com.yahoo.foodie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseInstallation;
import com.parse.PushService;

import com.yahoo.foodie.R;
import com.yahoo.foodie.fragments.SearchFavoriteFragment;

public class SearchFavoriteActivity extends RootActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_favorite);
		Parse.initialize(this, "8XfMtfEQT44xBf8qS8iucwai3JTfZOmg2EF4Yr4w", "uANkznUoVmJVgzn484HYWHTOrC97bxIR8aPQ1CeR");
		// For test:
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
		PushService.setDefaultPushCallback(this, SearchFavoriteActivity.class);
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
