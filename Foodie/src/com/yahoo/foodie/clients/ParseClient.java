package com.yahoo.foodie.clients;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

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
import com.yahoo.foodie.activities.RestaurantListActivity;
import com.yahoo.foodie.models.Friends;

public class ParseClient {
    private static ParseClient parseClient = null;
    
    public static ParseClient getInstance(Context context) {
        if (null == parseClient) {
            parseClient = new ParseClient(context);
        }
        return parseClient;
    }
    
    private ParseClient(Context context) {
        // Stone: Parse.initialize(this, "1RPMooDcVnHYlBowXpHdCwU1qzTCyDc46XuY80AK", "Bx6tXHCtOnVGZiDiKmHoueiIaTgQSgtdzUlIy8YK");
        // Zenith: Parse.initialize(this, "Iu7WYT2uS0CGn6HP1X6Rj2QscU1KyirbAyGyGhPd", "vNum3crFgtTs2dLSJPwydKXpzYqftbpZI44APfKa");
        // Zhongtang: Parse.initialize(this, "8XfMtfEQT44xBf8qS8iucwai3JTfZOmg2EF4Yr4w", "uANkznUoVmJVgzn484HYWHTOrC97bxIR8aPQ1CeR");
            
        ParseObject.registerSubclass(Friends.class);    // register need to be called before initialize
        Parse.initialize(context, "8XfMtfEQT44xBf8qS8iucwai3JTfZOmg2EF4Yr4w", "uANkznUoVmJVgzn484HYWHTOrC97bxIR8aPQ1CeR");
        PushService.setDefaultPushCallback(context, RestaurantListActivity.class);    
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
                    Log.d("DEBUG", "Successfully Signed Up!");
                } else {                 
                    Log.d("DEBUG", "Failed to Sign Up!");
                }
            }
        });
    }
    
    public void userLogIn(final String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                  Log.d("DEBUG", "YOU ARE " + user.getUsername());
                    // Calling pushSetup, getMyFriends and pushToUsers here to test their functionality
                    pushSetup(ParseUser.getCurrentUser().getUsername());
                    getMyFriends(ParseUser.getCurrentUser().getUsername());
                    ArrayList<String> targets = new ArrayList<String>();
                    targets.add(ParseUser.getCurrentUser().getUsername());
                    //pushToUsers(targets, "LET'S HAVE LUNCH");
                } else {
                    Log.d("DEBUG", "Failed to Log in! " + user.getUsername());
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
                  Log.d("DEBUG", "Your Friends: " + allMyFriends);
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
          }
        });
    }
    
    // Static method, overloading the previous one
    // Given a string username, fetch all his friends username, and do something in the callback
    public static void getMyFriends(final String myUsername, FindCallback<Friends> callback) {
        ParseQuery<Friends> queryUserReq = ParseQuery.getQuery(Friends.class);
        queryUserReq.whereEqualTo("userReq", myUsername);
        
        ParseQuery<Friends> queryUserAcp = ParseQuery.getQuery(Friends.class);
        queryUserAcp.whereEqualTo("userAcp", myUsername);
        
        List<ParseQuery<Friends>> queries = new ArrayList<ParseQuery<Friends>>();
        queries.add(queryUserReq);
        queries.add(queryUserAcp);
        
        ParseQuery<Friends> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(callback);
    }
    
    // Setup push for a given user
    private void pushSetup(String username) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", username);
        installation.saveInBackground();
    }
    
    
    // Given an array of usernames, send push notification to all these users
    public static void pushToUsers(ArrayList<String> targets, String message) {
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
