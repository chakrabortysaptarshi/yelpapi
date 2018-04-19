package com.outpatient.project;

public class Business {
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	private String address;
	
	private int reviewCount;
	
	private int rating;
	
	public Business(String bName, String bAddr, int brevCount, int brating) {
		name = bName;
		address = bAddr;
		reviewCount = brevCount;
		rating = brating;
	}

	@Override
	public String toString() {
		return "[ name : " + name + ", address : " + address + ", reviewCount : "+ reviewCount + ", rating :"+ rating +"]"; 
	}
}
