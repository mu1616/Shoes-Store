package com.example.project_01.model.product.dto;

public class ProductDTO {
	
	int product_idx;
	String product_name;
	int product_price;
	int product_brand;
	int product_stock;
	int product_category;
	String product_contents;
	String product_image;
	int product_isDisplay;
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
	public int getProduct_stock() {
		return product_stock;
	}
	public void setProduct_stock(int product_stock) {
		this.product_stock = product_stock;
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
		return "ProductDTO [product_idx=" + product_idx + ", product_name=" + product_name + ", product_price="
				+ product_price + ", product_brand=" + product_brand + ", product_stock=" + product_stock
				+ ", product_category=" + product_category + ", product_contents=" + product_contents
				+ ", product_image=" + product_image + ", product_isDisplay=" + product_isDisplay
				+  "]";
	}
	
	
	
}
