package com.yahoo.foodie.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Restaurant implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String phone;
	private String imageUrl;
	private int reviewCount;
	private double rating;
	private String categories;
	private String addr;

	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}
	
	public String getAddr() {
        return this.addr;
    }
	
	public String getCategories() {
        return this.categories;
    }

	public int getReviewCount() {
		return this.reviewCount;
	}

	public double getRating() {
		return this.rating;
	}


	protected static String getCategories(JSONArray categories) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < categories.length(); i++) {
			try {
				sb.append(categories.getJSONArray(i).optString(0));
//				Log.d("DEBUG Restaurant", categories.getJSONArray(i)
//						.optString(0));
				if (i < categories.length() - 1)
					sb.append(", ");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	protected static String getAddr(JSONArray addresses) {
		StringBuilder sb = new StringBuilder();
		try {
			// return this.addr.join(",");
			for (int i = 0; i < addresses.length(); i++) {
				sb.append(addresses.getString(i));
				if (i < addresses.length() - 1)
					sb.append(", ");
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
			b.categories = getCategories(jsonObject.getJSONArray("categories"));
			b.addr = getAddr(jsonObject.getJSONObject("location").getJSONArray(
					"display_address"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return b;
	}

	// Decodes array of business json results into business model objects
	public static ArrayList<Restaurant> fromJson(JSONArray jsonArray) {
		ArrayList<Restaurant> businesses = new ArrayList<Restaurant>(
				jsonArray.length());
		// Process each result in json array, decode and convert to business
		// object
		for (int i = 0; i < jsonArray.length(); i++) {
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
