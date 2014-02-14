package com.yahoo.foodie.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Restaurant {
	private String id;
	private String name;
	private String phone;
	private String imageUrl;
    private int reviewCount;
    private double rating;
    private JSONArray addr;
	
	public String getName() {
		return this.name;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	public int getReviewCount() {
        return this.reviewCount;
    }
	
	public double getRating() {
        return this.rating;
    }
	
	public String getAddr() {
	    StringBuilder sb = new StringBuilder();
	    try {
            //return this.addr.join(",");
	        for (int i = 0; i < this.addr.length(); i++) {
	            sb.append(this.addr.getString(i));
	        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	    return sb.toString();
    }
	
	// Decodes business json into business model object
	public static Restaurant fromJson(JSONObject jsonObject) {
		Restaurant b = new Restaurant();
        // Deserialize json into object fields
		try {
			b.id = jsonObject.getString("id");
        	b.name = jsonObject.getString("name");
        	b.phone = jsonObject.getString("display_phone");
        	b.imageUrl = jsonObject.getString("image_url");
        	b.reviewCount = jsonObject.getInt("review_count");
        	b.rating = jsonObject.getDouble("rating");
        	b.addr = jsonObject.getJSONObject("location").getJSONArray("display_address");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
		// Return new object
		return b;
	}
	
	// Decodes array of business json results into business model objects
    public static ArrayList<Restaurant> fromJson(JSONArray jsonArray) {
        ArrayList<Restaurant> businesses = new ArrayList<Restaurant>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject businessJson = null;
            try {
            	businessJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Restaurant business = Restaurant.fromJson(businessJson);
            if (business != null) {
            	businesses.add(business);
            }
        }

        return businesses;
    }
    
    @Override
    public String toString() {
    	return id + " " + name + " " + phone + " " + imageUrl;
    }
}
