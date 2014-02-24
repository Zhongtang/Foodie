package com.yahoo.foodie.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Friends")
public class Friends extends ParseObject {
	public Friends() {
		super();
	}
	public void makeFriends(String userReq, String userAcp) {
		put("userReq", userReq);
		put("userAcp", userAcp);
	}
	public String getFriendAfterQuery(String myUsername) {
		String user1 = getString("userReq");
		String user2 = getString("userAcp");
		return user1.equals(myUsername) ? user2 : user1;
	}
}