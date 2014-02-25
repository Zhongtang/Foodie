package com.yahoo.foodie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.yahoo.foodie.R;
import com.yahoo.foodie.clients.ParseClient;
import com.yahoo.foodie.fragments.RestaurantListFragment;
import com.yahoo.foodie.fragments.SearchFavoriteFragment;
import com.yahoo.foodie.models.Restaurant;
import com.yahoo.foodie.models.SearchFilter;
import com.yahoo.foodie.persistence.FoodiePreference;

public class RestaurantListActivity extends RootActivity implements
		RestaurantListFragment.OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		
	    SearchFilter filter = FoodiePreference.get(getApplicationContext());
		FragmentTransaction fts = getSupportFragmentManager()
                .beginTransaction();
        if (filter != null && null != filter.getQueryTerm() && !filter.getQueryTerm().isEmpty()) {
            Log.d("DEBUG", "filter: " + filter.toString());
            RestaurantListFragment restaurantListFragment = new RestaurantListFragment();
            fts.replace(R.id.frame_restaurant_list_container,
                    restaurantListFragment);
        } else {
            Log.d("DEBUG SearchFavoriteFragment", "filter is null, loading search favorite fragment");
            SearchFavoriteFragment searchFavFragment = new SearchFavoriteFragment();
            fts.replace(R.id.frame_restaurant_list_container,
                    searchFavFragment);
        }      
        fts.commit();
     
        // user session log in
        ParseClient pc = ParseClient.getInstance(getApplicationContext());
        pc.userLogIn("stone", "iamstone");
	}

	@Override
	public void onRestaurantSelected(Restaurant rest) {
		Intent i = new Intent(getApplicationContext(),
				RestaurantDetailActivity.class);
		i.putExtra("restaurant", rest);
		startActivity(i);
	}
}
