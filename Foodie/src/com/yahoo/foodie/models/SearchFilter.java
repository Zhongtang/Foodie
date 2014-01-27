package com.yahoo.foodie.models;

public class SearchFilter {
	private String queryTerm;
	private String location;
	private Boolean[] priceRange;	// Array of size 4
	private Boolean openNow;
	private Boolean hotAndNew;
	private Boolean offeringADeal;
	private Boolean delivery;
	private Float distance;			// 0 meaning Auto
	private String sortBy;
	private Boolean takeOut;
	private Boolean goodForKids;
	private Boolean goodForGroups;
	
	public SearchFilter() {
		queryTerm = "";
		location = "";
		priceRange = new Boolean[4];	//? Are they initialized to false?
		openNow = false;
		hotAndNew = false;
		offeringADeal = false;
		delivery = false;
		distance = 0.0f;
		sortBy = "BestMatch";
		takeOut = false;
		goodForKids = false;
		goodForGroups = false;
	}
	
	public String getQueryTerm() {
		return queryTerm;
	}
	public String getLocation() {
		return location;
	}
	public Boolean[] getPriceRange() {
		return priceRange;
	}
	public Boolean getOpenNow() {
		return openNow;
	}
	public Boolean getHotAndNew() {
		return hotAndNew;
	}
	public Boolean getOfferingADeal() {
		return offeringADeal;
	}
	public Boolean getDelivery() {
		return delivery;
	}
	public Float getDistance() {
		return distance;
	}
	public String getSortBy() {
		return sortBy;
	}
	public Boolean getTakeOut() {
		return takeOut;
	}
	public Boolean getGoodForKids() {
		return goodForKids;
	}
	public Boolean getGoodForGroups() {
		return goodForGroups;
	}
}
