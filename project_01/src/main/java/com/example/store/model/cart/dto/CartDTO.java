package com.example.store.model.cart.dto;


public class CartDTO {
	//카트 인덱스번호
	private int cart_idx;
	private String cart_member;
	//카트에 담길 상품번호
	private int cart_product;
	//신발의 사이즈
	private int cart_size;
	private int cart_count;
	private String product_image;
	private String product_brand;
	private String product_name;
	private int product_price;
	public int getCart_idx() {
		return cart_idx;
	}
	public void setCart_idx(int cart_idx) {
		this.cart_idx = cart_idx;
	}
	public String getCart_member() {
		return cart_member;
	}
	public void setCart_member(String cart_member) {
		this.cart_member = cart_member;
	}
	public int getCart_product() {
		return cart_product;
	}
	public void setCart_product(int cart_product) {
		this.cart_product = cart_product;
	}
	public int getCart_size() {
		return cart_size;
	}
	public void setCart_size(int cart_size) {
		this.cart_size = cart_size;
	}
	public int getCart_count() {
		return cart_count;
	}
	public void setCart_count(int cart_count) {
		this.cart_count = cart_count;
	}
	
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	public String getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
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
	@Override
	public String toString() {
		return "CartDTO [cart_idx=" + cart_idx + ", cart_member=" + cart_member + ", cart_product=" + cart_product
				+ ", cart_size=" + cart_size + ", cart_count=" + cart_count + ", product_image=" + product_image
				+ ", product_brand=" + product_brand + ", product_name=" + product_name + ", product_price="
				+ product_price + "]";
	}
	
	
	
	
	
}
