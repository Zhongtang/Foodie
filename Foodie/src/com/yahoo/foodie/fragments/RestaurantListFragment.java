package com.yahoo.foodie.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.foodie.R;
import com.yahoo.foodie.activities.RestaurantDetailActivity;
import com.yahoo.foodie.activities.SearchFavoriteActivity;
import com.yahoo.foodie.clients.RestaurantsAdapter;
import com.yahoo.foodie.clients.YelpClient;
import com.yahoo.foodie.clients.YelpClientApp;
import com.yahoo.foodie.models.Restaurant;

public class RestaurantListFragment extends Fragment 
implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{
    private RestaurantsAdapter adapter;
    private LocationClient mLocationClient;
    private Location curLocation;

    private OnItemSelectedListener listener;

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        public void onRestaurantSelected(Restaurant rest);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            this.listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement RestaurantListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ArrayList<Restaurant> rests = new ArrayList<Restaurant>();
        adapter = new RestaurantsAdapter(getActivity().getApplicationContext(),
                rests);       
        // delegate this to adapter
        adapter.addClickListener(listener); 
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_list, container,
                false);
        ListView lvRests = (ListView)v.findViewById(R.id.lvRestaurants);
        lvRests.setAdapter(adapter);
        lvRests.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos,
                    long id) {
                Restaurant rest = (Restaurant)adapter.getItem(pos);
                Intent i = new Intent(getActivity(),
                        RestaurantDetailActivity.class);
                i.putExtra("restaurant", rest);
                startActivity(i); 
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.restaurant_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
        case R.id.miSearch:
            onSearch(item);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void onSearch(MenuItem mi) {
        Intent i = new Intent(getActivity(), SearchFavoriteActivity.class);
        startActivity(i);
    }

    /*
     * Called when the Activity becomes visible.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Connect the client.
        Log.d("DEBUG", "connecting to location service");
        mLocationClient.connect();
    }
    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    public void onStop() {
        // Disconnecting the client invalidates it.
        Log.d("DEBUG", "disconnecting location client");
        mLocationClient.disconnect();
        super.onStop();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLocationClient = new LocationClient(getActivity(), this, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Log.d("DEBUG", "Connection failed");
    }

    @Override
    public void onConnected(Bundle arg0) {
        curLocation = mLocationClient.getLastLocation();
        if (null == curLocation) {
            Log.d("DEBUG", "onConnected but can't get last location");
            //fail over to a default location for debugging purpose
            curLocation = new Location(LocationManager.GPS_PROVIDER);
            curLocation.setLatitude(37.3757);
            curLocation.setLongitude(-122.0295);
        }

        curLocation = new Location(LocationManager.GPS_PROVIDER);
        curLocation.setLatitude(37.3757);
        curLocation.setLongitude(-122.0295);

        Log.d("DEBUG", "current location: " + curLocation.getLatitude() + "," + curLocation.getLongitude());

        YelpClient client = YelpClientApp.getRestClient();
        client.search("food", curLocation, new JsonHttpResponseHandler() {
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
                Log.d("DEBUG", "failed to search Restaurant, " + arg0.getMessage());
            }
        });

    }

    @Override
    public void onDisconnected() {
        Toast.makeText(getActivity(), "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();    
    }
}
