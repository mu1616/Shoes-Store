package com.example.store.model.product.dto;

public class ProductEntity {
	
	private int product_idx;
	private String product_name;
	private int product_price;
	private String product_brand;
	private String product_category;
	private String product_contents;
	private String product_image;
	private int product_isDisplay;
	private int procut_salecount;
	private float product_rating;	
	private int product_reviewcount;
	
	public int getProduct_reviewcount() {
		return product_reviewcount;
	}
	public void setProduct_reviewcount(int product_reviewcount) {
		this.product_reviewcount = product_reviewcount;
	}
	public float getProduct_rating() {
		return product_rating;
	}
	public void setProduct_rating(float product_rating) {
		this.product_rating = product_rating;
	}
	public int getProcut_salecount() {
		return procut_salecount;
	}
	public void setProcut_salecount(int procut_salecount) {
		this.procut_salecount = procut_salecount;
	}
	public int getProduct_isDisplay() {
		return product_isDisplay;
	}
	public int getProduct_idx() {
		return product_idx;
	}
	public void setProduct_idx(int product_idx) {
		this.product_idx = product_idx;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getProduct_price() {
		return product_price;
	}
	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
	public String getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}
	public String getProduct_category() {
		return product_category;
	}
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	public String getProduct_contents() {
		return product_contents;
	}
	public void setProduct_contents(String product_contents) {
		this.product_contents = product_contents;
	}
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	public int getProduct_isdisplay() {
		return product_isDisplay;
	}
	public void setProduct_isDisplay(int product_isDisplay) {
		this.product_isDisplay = product_isDisplay;
	}
	@Override
	public String toString() {
		return "ProductEntity [product_idx=" + product_idx + ", product_name=" + product_name + ", product_price="
				+ product_price + ", product_brand=" + product_brand + ", product_category=" + product_category
				+ ", product_contents=" + product_contents + ", product_image=" + product_image + ", product_isDisplay="
				+ product_isDisplay + ", procut_salecount=" + procut_salecount + ", product_rating=" + product_rating
				+ "]";
	}
	

}
