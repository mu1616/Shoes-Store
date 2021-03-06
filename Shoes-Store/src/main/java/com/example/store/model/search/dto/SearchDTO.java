package com.example.store.model.search.dto;

public class SearchDTO {
	private String product_name = "%";
	private String product_idx = "%";
	private String product_category = "%";
	private String product_brand = "%";
	private String product_isDisplay = "%";
	//0: and, 1: or 
	private int option = 0;
	//정렬 1: 최신순 2:높은가격순 3:낮은가격순  4:인기순
	private int sort = 1;
	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_idx() {
		return product_idx;
	}

	public void setProduct_idx(String product_idx) {
		this.product_idx = product_idx;
	}

	public String getProduct_category() {
		return product_category;
	}

	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}

	public String getProduct_brand() {
		return product_brand;
	}

	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}

	public String getProduct_isDisplay() {
		return product_isDisplay;
	}

	public void setProduct_isDisplay(String product_isDisplay) {
		this.product_isDisplay = product_isDisplay;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}
	
	
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "SearchDTO [product_name=" + product_name + ", product_idx=" + product_idx + ", product_category="
				+ product_category + ", product_brand=" + product_brand + ", product_isDisplay=" + product_isDisplay
				+ ", option=" + option + ", sort=" + sort + "]";
	}

	
	
	

	

	

	
	
	
	

}
