package com.yahoo.foodie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yahoo.foodie.models.Restaurant;
import com.yahoo.group12.foodie.R;

public class RestaurantDetailFragment extends Fragment {
	private TextView tvName;
//	private TextView tvPrice;
	private TextView tvReviews;
	private TextView tvAddr;
	private TextView tvType;
	private RatingBar rbRating;
	private Restaurant rest;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_restaurant_detail, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}

	private void setupViews() {
		tvName = (TextView) getActivity().findViewById(R.id.tvName);
//		tvPrice = (TextView) getActivity().findViewById(R.id.tvPrice); // no such field now
		tvReviews = (TextView) getActivity().findViewById(R.id.tvReviews);
		rbRating = (RatingBar) getActivity().findViewById(R.id.rbRating);
		tvAddr = (TextView) getActivity().findViewById(R.id.tvAddr);
		tvType = (TextView) getActivity().findViewById(R.id.tvType);
		populateRestaurantDetail();
	}

	private void populateRestaurantDetail() {
		tvName.setText(rest.getName());
//		tvPrice.setText("$$"); // no such field now
		tvReviews.setText(Integer.toString(rest.getReviewCount()) + " Reviews");
		rbRating.setRating((float) rest.getRating());
		tvAddr.setText(rest.getAddr());
		tvType.setText(rest.getCategories());
		Log.d("DEBUG RestaurantDetailFragment",
				"review_count: " + rest.getReviewCount());
		Log.d("DEBUG RestaurantDetailFragment", "rating: " + rest.getRating());
		Log.d("DEBUG RestaurantDetailFragment", "addr: " + rest.getAddr());
	}

	public void setRestaurant(Restaurant restaurant) {
		this.rest = restaurant;
	}
}
