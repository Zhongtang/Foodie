package com.yahoo.foodie.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.yahoo.group12.foodie.R;

public class FavoriteListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorite_list, menu);
		return true;
	}

}
