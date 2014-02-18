package com.yahoo.foodie.clients;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.foodie.fragments.RestaurantListFragment.OnItemSelectedListener;
import com.yahoo.foodie.models.Restaurant;
import com.yahoo.group12.foodie.R;

public class RestaurantsAdapter extends ArrayAdapter<Restaurant> {

	private OnItemSelectedListener onclickListener;

    public RestaurantsAdapter(Context context, List<Restaurant> rests) {
		super(context, 0, rests);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.restaurant_item, null);
		}
		// Get the data item for this position
		final Restaurant rest = getItem(position);

		// Lookup views within item layout
		ImageView ivProfile = (ImageView) convertView
				.findViewById(R.id.ivProfile);
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
		TextView tvReviews = (TextView) convertView
				.findViewById(R.id.tvReviews);
		RatingBar rbRating = (RatingBar) convertView
				.findViewById(R.id.rbRating);
		TextView tvAddr = (TextView) convertView.findViewById(R.id.tvAddr);
		TextView tvType = (TextView) convertView.findViewById(R.id.tvType);

		// Populate the data into the template view using the data object
		ImageLoader.getInstance().displayImage(rest.getImageUrl(), ivProfile);
		tvName.setText(rest.getName());
		tvPrice.setText("$$"); // fake now
		Log.d("DEBUG RestaurantAdapter",
				"review_count: " + rest.getReviewCount());
		Log.d("DEBUG RestaurantAdapter", "rating: " + rest.getRating());
		Log.d("DEBUG RestaurantAdapter", "addr: " + rest.getAddr());
		tvReviews.setText(Integer.toString(rest.getReviewCount()) + " Reviews");
		rbRating.setRating((float) rest.getRating());
		tvAddr.setText(rest.getAddr());
		tvType.setText(rest.getCategories());

//		ivProfile.setTag(rest.getRestaurant());
		ivProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Restaurant restaurant = (Restaurant) v.getTag();	
                if (onclickListener != null) {
                    Log.d("DEBUG", "click profile image for restaurant: " + rest.getName());
                    onclickListener.onProfileImageSelected(rest);
                }       
			}
		});

		// Return the completed view to render on screen
		return convertView;
	}

    public void addClickListener(OnItemSelectedListener listener) {
        this.onclickListener = listener;
    }
}
