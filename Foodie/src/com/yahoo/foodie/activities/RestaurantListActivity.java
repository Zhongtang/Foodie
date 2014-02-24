package com.yahoo.foodie.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;
import com.yahoo.foodie.R;
import com.yahoo.foodie.fragments.RestaurantListFragment;
import com.yahoo.foodie.fragments.SearchFavoriteFragment;
import com.yahoo.foodie.models.Friends;
import com.yahoo.foodie.models.Restaurant;
import com.yahoo.foodie.models.SearchFilter;
import com.yahoo.foodie.persistence.FoodiePreference;

public class RestaurantListActivity extends RootActivity implements
		RestaurantListFragment.OnItemSelectedListener {
    private Context self = this;

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
        
        // Stone: Parse.initialize(this, "1RPMooDcVnHYlBowXpHdCwU1qzTCyDc46XuY80AK", "Bx6tXHCtOnVGZiDiKmHoueiIaTgQSgtdzUlIy8YK");
        // Zenith: Parse.initialize(this, "Iu7WYT2uS0CGn6HP1X6Rj2QscU1KyirbAyGyGhPd", "vNum3crFgtTs2dLSJPwydKXpzYqftbpZI44APfKa");
        // Zhongtang: Parse.initialize(this, "8XfMtfEQT44xBf8qS8iucwai3JTfZOmg2EF4Yr4w", "uANkznUoVmJVgzn484HYWHTOrC97bxIR8aPQ1CeR");
        ParseObject.registerSubclass(Friends.class);    // register need to be called before initialize
        //Parse.initialize(this, "8XfMtfEQT44xBf8qS8iucwai3JTfZOmg2EF4Yr4w", "uANkznUoVmJVgzn484HYWHTOrC97bxIR8aPQ1CeR");
        Parse.initialize(this, "1RPMooDcVnHYlBowXpHdCwU1qzTCyDc46XuY80AK", "Bx6tXHCtOnVGZiDiKmHoueiIaTgQSgtdzUlIy8YK");


        // For test:
        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();
        PushService.setDefaultPushCallback(this, SearchFavoriteActivity.class);
        
        // Grant accounts, setup friendships
        // One-time set-up for test. comment out in the second run
        //userSignUp("ztian", "iamztian");
        //userSignUp("zenith", "iamzenith");
        //userSignUp("stone", "iamstone");
        //makeFriends("ztian", "zenith");
        //makeFriends("ztian", "stone");
        //makeFriends("stone", "zenith");
        
        // user session log in
        userLogIn("stone", "iamstone");
	}

	@Override
	public void onRestaurantSelected(Restaurant rest) {
		Intent i = new Intent(getApplicationContext(),
				RestaurantDetailActivity.class);
		i.putExtra("restaurant", rest);
		startActivity(i);
	}

	   private void userSignUp(String username, String password) {
	        // TODO: check syntax
	        // TODO: add phone# so that it's possible to sync with local contact list
	        ParseUser user = new ParseUser();
	        user.setUsername(username);
	        user.setPassword(password);
	        user.signUpInBackground(new SignUpCallback() {
	            public void done(ParseException e) {
	                if (e == null) {
	                    Toast.makeText(self, "Successfully Signed Up!", Toast.LENGTH_SHORT).show();
	                } else {
	                    Toast.makeText(self, "Failed to Sign Up!", Toast.LENGTH_SHORT).show();
	                }
	            }
	        });
	    }
	    
	    private void userLogIn(final String username, String password) {
	        ParseUser.logInInBackground(username, password, new LogInCallback() {
	            public void done(ParseUser user, ParseException e) {
	                if (e == null) {
	                    Toast.makeText(self, "YOU ARE " + user.getUsername(), Toast.LENGTH_SHORT).show();
	                    // Calling pushSetup, getMyFriends and pushToUsers here to test their functionality
	                    pushSetup(ParseUser.getCurrentUser().getUsername());
	                    getMyFriends(ParseUser.getCurrentUser().getUsername());
	                    ArrayList<String> targets = new ArrayList<String>();
	                    targets.add(ParseUser.getCurrentUser().getUsername());
	                    pushToUsers(targets, "LET'S HAVE LUNCH");
	                } else {
	                    Toast.makeText(self, "Failed to Log in!", Toast.LENGTH_SHORT).show();
	                }
	            }
	        });
	    }
	    
	    private void makeFriends(String userReq, String userAcp) {
	        Friends friends = new Friends();
	        friends.makeFriends(userReq, userAcp);
	        friends.saveInBackground();
	    }
	    
	    // Given a string username, fetch all his friends username
	    public void getMyFriends(final String myUsername) {
	        ParseQuery<Friends> queryUserReq = ParseQuery.getQuery(Friends.class);
	        queryUserReq.whereEqualTo("userReq", myUsername);
	        
	        ParseQuery<Friends> queryUserAcp = ParseQuery.getQuery(Friends.class);
	        queryUserAcp.whereEqualTo("userAcp", myUsername);
	        
	        List<ParseQuery<Friends>> queries = new ArrayList<ParseQuery<Friends>>();
	        queries.add(queryUserReq);
	        queries.add(queryUserAcp);
	        
	        ParseQuery<Friends> mainQuery = ParseQuery.or(queries);
	        mainQuery.findInBackground(new FindCallback<Friends>() {
	          public void done(List<Friends> results, ParseException e) {
	                if (e == null) {
	                    // Access the array of results here
	                    String allMyFriends = "";
	                    for(int i = 0; i < results.size(); i++) {
	                        allMyFriends += (" " + results.get(i).getFriendAfterQuery(myUsername));
	                    }
	                    Toast.makeText(self, "Your Friends: " + allMyFriends, Toast.LENGTH_SHORT).show();
	                } else {
	                    Log.d("item", "Error: " + e.getMessage());
	                }
	          }
	        });
	    }
	    
	    // Setup push for a given user
	    private void pushSetup(String username) {
	        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
	        installation.put("username", username);
	        installation.saveInBackground();
	    }
	    
	    
	    // Given an array of usernames, send push notification to all these users
	    public void pushToUsers(ArrayList<String> targets, String message) {
	        List<ParseQuery<ParseInstallation>> queries = new ArrayList<ParseQuery<ParseInstallation>>();
	        for(int i = 0; i < targets.size(); i++) {
	            ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();;
	            query.whereEqualTo("username", targets.get(i));
	            queries.add(query);
	        }
	        
	        ParseQuery<ParseInstallation> mainQuery = ParseQuery.or(queries);
	        ParsePush push = new ParsePush();
	        push.setQuery(mainQuery);
	        push.setMessage(message);
	        push.sendInBackground();

	    }
}
