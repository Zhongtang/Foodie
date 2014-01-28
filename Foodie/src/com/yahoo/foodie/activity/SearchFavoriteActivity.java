package com.yahoo.foodie.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.yahoo.foodie.models.SearchFilter;
import com.yahoo.foodie.persistence.FoodiePreference;
import com.yahoo.group12.foodie.R;

public class SearchFavoriteActivity extends Activity {

	private EditText etSearchTerm;
	private EditText etLocation;
	private Spinner spDistance;
	private Spinner spSortBy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_favorite);
		
		setupViews();
		
		SearchFilter filter = FoodiePreference.get(this);
		if (filter != null) {
			Log.d("DEBUG", "sort by: " + filter.getSortBy());
			Log.d("DEBUG", "search term: " + filter.getQueryTerm());
			Log.d("DEBUG", "location: " + filter.getLocation());
		} else {
			Log.d("DEBUG", "filter is null");
		}
	}

	private void setupViews() {
		etSearchTerm = (EditText)findViewById(R.id.etQuery);
		etLocation = (EditText) findViewById(R.id.etLocation);
		spDistance = (Spinner) findViewById(R.id.spDistance);
		spSortBy = (Spinner) findViewById(R.id.spSortBy);	
		
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
		
		String queryTerm = etSearchTerm.getText().toString();
		String location = etLocation.getText().toString();
		String sortBy = spSortBy.getSelectedItem().toString();
		String distanceStr = spDistance.getSelectedItem().toString();
		float distance; // this may need to be double for precision
		if ("Auto".equals(distanceStr)) {
			distance = 0.5f; //? what does auto means here?
		}
		try {
			distance = Float.parseFloat(distanceStr);
		} catch (NumberFormatException ne) {
			distance = 0.5f;
		}
		
		
		SearchFilter filterPref = new SearchFilter.Builder(queryTerm)
											   .setLocation(location)
								               .setSortBy(sortBy)
								               .setDistance(distance)
								               .build();
		new FoodiePreference(this, filterPref).save();
	}

}
