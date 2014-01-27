package com.yahoo.foodie.models;


public class SearchFilter {
	private final String queryTerm;
	private final String location;
	private final boolean[] priceRange; // Array of size 4
	private final boolean openNow;
	private final boolean hotAndNew;
	private final boolean offeringADeal;
	private final boolean delivery;
	private final Float distance; // 0 meaning Auto
	private final String sortBy;
	private final boolean takeOut;
	private final boolean goodForKids;
	private final boolean goodForGroups;
	
	
	/**
	 * utilize builder patter to create SearchFilter class as the class takes a lot parameters.
	 * 
	 * it can chain all setters up before calling build()
	 * usage: SearchFilter filter = new SearchFilter.Builder(String queryTerm)
	 * 									.setOpenNow(true)
	 *                                  .setSortBy("BestMatch")
	 *                                  .build();
	 */
	private SearchFilter(Builder builder) {
		queryTerm = builder.queryTerm;
		location = builder.location;
		priceRange = builder.priceRange;
		openNow = builder.openNow;
		hotAndNew = builder.hotAndNew;
		offeringADeal = builder.offeringADeal;
		delivery = builder.delivery;
		distance = builder.distance;
		sortBy = builder.sortBy;
		takeOut = builder.takeOut;
		goodForKids = builder.goodForKids;
		goodForGroups = builder.goodForGroups;
	}

	public static class Builder {

		private String queryTerm;
		private String location;
		private boolean[] priceRange = new boolean[4];
		private boolean openNow = false;
		private boolean hotAndNew = false;
		private boolean offeringADeal = false;
		private boolean delivery = false;
		private Float distance = 0.0f; // 0 meaning Auto
		private String sortBy = "BestMatch";
		private boolean takeOut = false;
		private boolean goodForKids = false;
		private boolean goodForGroups = false;

		public Builder(String queryTerm) {
			this.queryTerm = queryTerm;
		}

		public Builder setOpenNow(boolean open) {
			this.openNow = open;
			return this;
		}

		public Builder setLocation(String loc) {
			this.location = loc;
			return this;
		}

		public Builder setHotAndNew(boolean hotnew) {
			this.hotAndNew = hotnew;
			return this;
		}

		public Builder setOfferingADeal(boolean deal) {
			this.offeringADeal = deal;
			return this;
		}

		public Builder setDelivery(boolean delivery) {
			this.delivery = delivery;
			return this;
		}

		public Builder setDistance(float distance) {
			this.distance = distance;
			return this;
		}

		public Builder setSortBy(String sortBy) {
			this.sortBy = sortBy;
			return this;
		}

		public Builder setTakeOut(boolean takeout) {
			this.takeOut = takeout;
			return this;
		}

		public Builder setGoodForKids(boolean goodForKids) {
			this.goodForKids = goodForKids;
			return this;
		}

		public Builder setGoodForGroups(boolean goodForGroup) {
			this.goodForGroups = goodForGroup;
			return this;
		}

		public Builder setPriceRange(boolean[] priceRange) {
			if (priceRange != null) {
				System.arraycopy(priceRange, 0, this.priceRange, 0,
						this.priceRange.length);
			}
			return this;
		}

		public SearchFilter build() {
			return new SearchFilter(this);
		}
	}

	public String getQueryTerm() {
		return queryTerm;
	}

	public String getLocation() {
		return location;
	}

	public boolean[] getPriceRange() {
		return priceRange;
	}

	public boolean getOpenNow() {
		return openNow;
	}

	public boolean getHotAndNew() {
		return hotAndNew;
	}

	public boolean getOfferingADeal() {
		return offeringADeal;
	}

	public boolean getDelivery() {
		return delivery;
	}

	public Float getDistance() {
		return distance;
	}

	public String getSortBy() {
		return sortBy;
	}

	public boolean getTakeOut() {
		return takeOut;
	}

	public boolean getGoodForKids() {
		return goodForKids;
	}

	public boolean getGoodForGroups() {
		return goodForGroups;
	}
}
