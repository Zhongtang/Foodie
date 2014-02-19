package com.yahoo.foodie.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.yahoo.group12.foodie.R;

public class RootActivity extends FragmentActivity {
	int onStartCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onStartCount = 1;
		if (savedInstanceState == null) // 1st time
		{
			this.overridePendingTransition(R.anim.anim_slide_in_left,
					R.anim.anim_slide_out_left);
		} else // already created so reverse animation
		{
			onStartCount = 2;
		}
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.display_action_bar);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (onStartCount > 1) {
			this.overridePendingTransition(R.anim.anim_slide_in_right,
					R.anim.anim_slide_out_right);

		} else if (onStartCount == 1) {
			onStartCount++;
		}
	}

	public void onSearch(MenuItem mi) {
		Intent i = new Intent(this, SearchFavoriteActivity.class);
		startActivity(i);
	}
}
