package com.yahoo.foodie.activities;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yahoo.foodie.R;
import com.yahoo.foodie.fragments.RestaurantDetailFragment;
import com.yahoo.foodie.models.Restaurant;

public class RestaurantDetailActivity extends RootActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private Restaurant restaurant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_detail);

		this.restaurant = (Restaurant) getIntent().getSerializableExtra(
				"restaurant");

		RestaurantDetailFragment restaurantDetailFragment = (RestaurantDetailFragment) getSupportFragmentManager()
				.findFragmentById(R.id.frgmtRestaurantDetail);
		restaurantDetailFragment.setRestaurant(this.restaurant);
		mLocationClient = new LocationClient(this, this, this);
		mapFragment = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		if (mapFragment != null) {
			map = mapFragment.getMap();
			if (map != null) {
				map.setMyLocationEnabled(true);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_detail, menu);
		return true;
	}

	/*
	 * Called when the Activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		if (isGooglePlayServicesAvailable()) {
			mLocationClient.connect();
		}

	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Log.d("DEBUG", "Connected");

		Geocoder coder = new Geocoder(this);
		List<Address> address;

		try {
			Log.d("DEBUG", "getting address object from addr: "
					+ this.restaurant.getAddr());
			address = coder.getFromLocationName(this.restaurant.getAddr(), 5);
			if (address == null) {
				Log.d("DEBUG", "can't get address object from literal addr: "
						+ this.restaurant.getAddr());
				return;
			}
			Address location = address.get(0);
			LatLng latLng = new LatLng(location.getLatitude(),
					location.getLongitude());
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
					latLng, 17);
			// map.animateCamera(cameraUpdate);
			map.addMarker(new MarkerOptions().position(latLng));
			map.setOnMarkerClickListener(new OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(Marker marker) {
					Toast.makeText(getApplicationContext(),
							"pop up friend list fragment and choose friends",
							Toast.LENGTH_LONG).show();
					return true;
				}
			});
			map.moveCamera(cameraUpdate);

		} catch (IOException e) {
			Toast.makeText(this, "location was null!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry. Location services not available to you",
					Toast.LENGTH_LONG).show();
		}
	}

	public void onInvite(MenuItem mi) {
		Intent i = new Intent(this, ContactsListActivity.class);
		startActivity(i);
	}
}
