package com.example.project_01.model.search.dto;

public class SearchDTO {
	private String product_name = "%";
	private String product_idx = "%";
	private String product_category = "%";
	private String product_brand = "%";
	private String product_isDisplay = "%";
	//0: and, 1: or 
	private int searchOption = 0;
	
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
	
	

	public int getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(int searchOption) {
		this.searchOption = searchOption;
	}

	@Override
	public String toString() {
		return "SearchDTO [product_name=" + product_name + ", product_idx=" + product_idx + ", product_category="
				+ product_category + ", product_brand=" + product_brand + ", product_isDisplay=" + product_isDisplay
				+ ", searchOption=" + searchOption + "]";
	}

	

	
	
	
	

}
