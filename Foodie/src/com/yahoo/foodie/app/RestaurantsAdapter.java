package com.yahoo.foodie.app;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.foodie.models.Restaurant;
import com.yahoo.group12.foodie.R;


public class RestaurantsAdapter extends ArrayAdapter<Restaurant> {
    
    public RestaurantsAdapter(Context context, List<Restaurant> rests) {
        super(context, 0 , rests);
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
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvReviews = (TextView) convertView.findViewById(R.id.tvReviews);
        TextView tvRating = (TextView) convertView.findViewById(R.id.tvRating);
        TextView tvAddr = (TextView) convertView.findViewById(R.id.tvAddr);
        TextView tvType = (TextView) convertView.findViewById(R.id.tvType);

        
        // Populate the data into the template view using the data object
        ImageLoader.getInstance().displayImage(rest.getImageUrl(), ivProfile);
        tvName.setText(rest.getName());
        tvPrice.setText("$$"); //fake now
        Log.d("DEBUG", "review_count: " + rest.getReviewCount());
        Log.d("DEBUG", "rating: " + rest.getRating());
        Log.d("DEBUG", "addr: " + rest.getAddr());
        tvReviews.setText(Integer.toString(rest.getReviewCount()) + " Reviews");
        tvRating.setText(Double.toString(rest.getRating()));
        tvAddr.setText(rest.getAddr());
        tvType.setText("Fusion"); //fake now
        
        ivProfile.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // placeholder, no op now      
            }
        });
        
        // Return the completed view to render on screen
        return convertView;
    }

}
