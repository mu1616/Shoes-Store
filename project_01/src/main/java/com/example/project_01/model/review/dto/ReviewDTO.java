package com.example.project_01.model.review.dto;

import java.util.Date;

public class ReviewDTO {
	private int review_product;
	private Date review_date;
	private String review_contents;
	private int review_rating;
	private String review_member;
	private String review_ordercode;
	
	
	public String getReview_ordercode() {
		return review_ordercode;
	}
	public void setReview_ordercode(String review_ordercode) {
		this.review_ordercode = review_ordercode;
	}
	public int getReview_product() {
		return review_product;
	}
	public void setReview_product(int review_product) {
		this.review_product = review_product;
	}
	public Date getReview_date() {
		return review_date;
	}
	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}
	public String getReview_contents() {
		return review_contents;
	}
	public void setReview_contents(String review_contents) {
		this.review_contents = review_contents;
	}
	public int getReview_rating() {
		return review_rating;
	}
	public void setReview_rating(int review_rating) {
		this.review_rating = review_rating;
	}
	public String getReview_member() {
		return review_member;
	}
	public void setReview_member(String review_member) {
		this.review_member = review_member;
	}
	@Override
	public String toString() {
		return "ReviewDTO [review_product=" + review_product + ", review_date=" + review_date + ", review_contents="
				+ review_contents + ", review_rating=" + review_rating + ", review_member=" + review_member
				+ ", review_ordercode=" + review_ordercode + "]";
	}
	
	
	
}
