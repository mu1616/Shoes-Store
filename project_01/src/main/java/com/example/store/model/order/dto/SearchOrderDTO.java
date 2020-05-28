package com.example.store.model.order.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchOrderDTO {
	private String searchType="검색분류";
	private String searchWord;
	private String fromDate;
	private String toDate;
	private String order_state = "%";
	
	SearchOrderDTO(){
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String time = (new SimpleDateFormat("yyyy-MM-dd").format(date));
		fromDate = time;
		toDate = time;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	@Override
	public String toString() {
		return "SearchOrderDTO [searchType=" + searchType + ", searchWord=" + searchWord + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", order_state=" + order_state + "]";
	}
	
	
	
}
