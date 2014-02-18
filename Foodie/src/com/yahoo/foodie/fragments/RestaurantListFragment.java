package com.yahoo.foodie.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.foodie.clients.RestaurantsAdapter;
import com.yahoo.foodie.clients.YelpClient;
import com.yahoo.foodie.clients.YelpClientApp;
import com.yahoo.foodie.models.Restaurant;
import com.yahoo.group12.foodie.R;

public class RestaurantListFragment extends Fragment {
	private RestaurantsAdapter adapter;
	
	private OnItemSelectedListener listener;

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        public void onProfileImageSelected(Restaurant rest);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_restaurant_list, container,
				false);
		setupViews(v);
		return v;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnItemSelectedListener) {
            this.listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement TweetsListFragment.OnItemSelectedListener");
        }
	}

	private void setupViews(View v) {

		ArrayList<Restaurant> rests = new ArrayList<Restaurant>();
		ListView lvRests = (ListView) v.findViewById(R.id.lvRestaurants);
		adapter = new RestaurantsAdapter(getActivity().getApplicationContext(),
				rests);
		lvRests.setAdapter(adapter);
		
	      // delegate this to adapter
        adapter.addClickListener(listener); 

		YelpClient client = YelpClientApp.getRestClient();
		client.search("food", "Sunnyvale CA", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int code, JSONObject body) {
				try {
					JSONArray restsJson = body.getJSONArray("businesses");
					ArrayList<Restaurant> rests = Restaurant
							.fromJson(restsJson);
					adapter.addAll(rests);
					Log.d("DEBUG", rests.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("DEBUG RestaurantListFragment", "Failure");
			}
		});
	}
}
