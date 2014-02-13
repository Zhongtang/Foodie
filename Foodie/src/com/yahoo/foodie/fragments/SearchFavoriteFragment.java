package com.yahoo.foodie.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.yahoo.foodie.models.SearchFilter;
import com.yahoo.foodie.persistence.FoodiePreference;
import com.yahoo.group12.foodie.R;

public class SearchFavoriteFragment extends Fragment {
    FragmentActivity listener;
    private EditText etSearchTerm;
    private EditText etLocation;
    private Spinner spDistance;
    private Spinner spSortBy;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_search_favorite, container, false);
		setupViews(v);
		return v;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (FragmentActivity) activity;
    }

    private void setupViews(View v) {
        etSearchTerm = (EditText)v.findViewById(R.id.etQuery);
        etLocation = (EditText)v.findViewById(R.id.etLocation);
        spDistance = (Spinner)v.findViewById(R.id.spDistance);
        spSortBy = (Spinner)v.findViewById(R.id.spSortBy);
        
        SearchFilter filter = FoodiePreference.get(getActivity().getApplicationContext());
        if (filter != null) {
            Log.d("DEBUG", "sort by: " + filter.getSortBy());
            Log.d("DEBUG", "search term: " + filter.getQueryTerm());
            Log.d("DEBUG", "location: " + filter.getLocation());
        } else {
            Log.d("DEBUG", "filter is null");
        }
        
    }
	
	public void onSavePreference() {
        
        String queryTerm = etSearchTerm.getText().toString();
        String location = etLocation.getText().toString();
        String sortBy = spSortBy.getSelectedItem().toString();
        String distanceStr = spDistance.getSelectedItem().toString();
        float distance; // this may need to be double for precision
        if ("Auto".equals(distanceStr)) {
            distance = 0.5f; //? what does auto means here?
        }
        try {
            distance = Float.parseFloat(distanceStr);
        } catch (NumberFormatException ne) {
            distance = 0.5f;
        }       
        
        SearchFilter filterPref = new SearchFilter.Builder(queryTerm)
                                               .setLocation(location)
                                               .setSortBy(sortBy)
                                               .setDistance(distance)
                                               .build();
        
        new FoodiePreference(getActivity().getApplicationContext(), filterPref).save();
    }

}
