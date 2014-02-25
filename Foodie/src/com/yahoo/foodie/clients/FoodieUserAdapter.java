package com.yahoo.foodie.clients;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yahoo.foodie.models.FoodieUser;
import com.yahoo.foodie.R;

public class FoodieUserAdapter extends ArrayAdapter<FoodieUser> {
	
	public FoodieUserAdapter(Context context, List<FoodieUser> users) {
		super(context, 0, users);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_foodie_user, null);
        }

        FoodieUser user = getItem(position);
        
        ImageView ivStatus = (ImageView) view.findViewById(R.id.ivFoodieUserStatus);
        int imageResource = getContext().getResources().getIdentifier(user.getStatusDrawable(), null, getContext().getPackageName());
        ivStatus.setImageDrawable(getContext().getResources().getDrawable(imageResource));

        TextView tvUsername = (TextView) view.findViewById(R.id.tvFoodieUserUsername);
        tvUsername.setText(user.getUsername());

        return view;
	}
}
