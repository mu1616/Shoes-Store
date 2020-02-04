package com.example.project_01.model.product.dto;

public class ProductEntity {
	
	private int product_idx;
	private String product_name;
	private int product_price;
	private int product_brand;
	private int product_category;
	private String product_contents;
	private String product_image;
	private int product_isDisplay;
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
	public int getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(int product_brand) {
		this.product_brand = product_brand;
	}
	public int getProduct_category() {
		return product_category;
	}
	public void setProduct_category(int product_category) {
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
				+ product_isDisplay + "]";
	}


	
	
	
}
