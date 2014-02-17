package com.yahoo.foodie.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.yahoo.foodie.models.SearchFilter;

public class FoodiePreference {
	private SearchFilter searchFilter;
	private Context context;

	public FoodiePreference(Context context, SearchFilter searchFilter) {
		this.searchFilter = searchFilter;
		this.context = context;
	}

	public void save() {
		Gson gson = new Gson();
		String jsonPref = gson.toJson(searchFilter);
		Log.d("DEBUG", "json for filter: " + jsonPref);

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = pref.edit();
		edit.putString("preference", jsonPref);
		edit.commit();
	}

	public static SearchFilter get(Context context) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		String jsonPref = pref.getString("preference", "");

		Gson gson = new Gson();
		SearchFilter filter = gson.fromJson(jsonPref, SearchFilter.class);

		return filter;
	}
}
