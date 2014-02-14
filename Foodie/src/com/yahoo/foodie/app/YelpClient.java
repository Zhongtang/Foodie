package com.yahoo.foodie.app;

import org.scribe.builder.api.Api;
import org.scribe.model.Token;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class YelpClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = YelpApi2.class; // Change this
    public static final String REST_URL = "http://api.yelp.com/v2"; // Change this, base API URL  
    private static final String REST_CONSUMER_KEY = "8Bo_iY7MRX7IfsYZ8U17IA";
    private static final String REST_CONSUMER_SECRET = "4q-sNU4wiOdWGZ-GqgyvUjvxNSE";
    private static final String TOKEN = "e5f1p_bSihbhzqND2VytNHN06d5BDvbh";
    private static final String TOKEN_SECRET = "csu-3Ns91hUFuc1nrVAOsR1mojg";
    private static final String REST_CALLBACK_URL = "oauth://foodieapp";

    public YelpClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
        this.client.setAccessToken(new Token(TOKEN, TOKEN_SECRET));
    }

    public void search(String term, String location, AsyncHttpResponseHandler handler) {
    	// http://api.yelp.com/v2/search?term=food&location=San+Francisco
    	String apiUrl = getApiUrl("search");
        RequestParams params = new RequestParams();
        params.put("term", term);
        params.put("location", location);
        client.get(apiUrl, params, handler);
    }

}