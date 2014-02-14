package com.yahoo.foodie.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.foodie.app.RestaurantsAdapter;
import com.yahoo.foodie.app.YelpClient;
import com.yahoo.foodie.app.YelpClientApp;
import com.yahoo.foodie.models.Restaurant;
import com.yahoo.group12.foodie.R;

public class RestaurantListActivity extends Activity {
    private  RestaurantsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		
		ArrayList<Restaurant> rests = new ArrayList<Restaurant>();
        ListView lvRests = (ListView)findViewById(R.id.lvRestaurants);
        adapter = new RestaurantsAdapter(getApplicationContext(), rests);       
        lvRests.setAdapter(adapter);
        
        YelpClient client = YelpClientApp.getRestClient();
        client.search("food", "Sunnyvale CA", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, JSONObject body) {
                try {
                    JSONArray restsJson = body.getJSONArray("businesses");
                    ArrayList<Restaurant> rests = Restaurant.fromJson(restsJson);
                    adapter.addAll(rests);
                    Log.d("DEBUG", rests.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void onFailure(Throwable arg0) {
                Toast.makeText(RestaurantListActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_list, menu);
		return true;
	}

}
