package com.yahoo.foodie.activity;

import android.app.ActionBar;
import android.app.SearchableInfo;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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
		initActionBar();
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_favorite, menu);
		return true;
	}
	
	private void initActionBar() {
		// Change the ActionBar background color to white
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.WHITE)); 
		// Change the ActionBar font color to black
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
		    TextView title = (TextView) findViewById(actionBarTitleId);
		    if (title != null) {
		        title.setTextColor(Color.BLACK);
		    }
		}
	}
	
	public void onSubmitSearch(MenuItem mi) {
		SearchFavoriteFragment prefs = (SearchFavoriteFragment) getSupportFragmentManager().findFragmentById(R.id.fgmtSearchOptions);
		prefs.onSavePreference();
		Intent i = new Intent(SearchFavoriteActivity.this, RestaurantListActivity.class);
		startActivity(i);
	}

}
