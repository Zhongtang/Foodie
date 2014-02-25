package com.yahoo.foodie.models;

import java.util.ArrayList;
import java.util.List;

public class FoodieUser {
	// TODO: make status enumerate
	private String username;
	private boolean status;
	
	public void setUsername(String inUsername) {
		username = inUsername;
	}
	public void setStatus(boolean inStatus) {
		status = inStatus;
	}
	public String getUsername() {
		return username;
	}
	public boolean checkStatus() {
		return status;
	}
	public String getStatusDrawable() {
		return status ? "@drawable/ic_foodie_user_status_on" : "@drawable/ic_foodie_user_status_off";
	}
	
	// TODO: Instead of hardcoding status, augment Friends (and backend) to have it including status info
	public static ArrayList<FoodieUser> fromFriendQueryResult(List<Friends> results, String myUsername) {
		ArrayList<FoodieUser> allMyFriends = new ArrayList<FoodieUser>();
        for(int i = 0; i < results.size(); i++) {
        	FoodieUser oneFriend = new FoodieUser();
        	oneFriend.setUsername(results.get(i).getFriendAfterQuery(myUsername));
        	oneFriend.setStatus(false);
            allMyFriends.add(oneFriend);
        }
        return allMyFriends;
	}

}
