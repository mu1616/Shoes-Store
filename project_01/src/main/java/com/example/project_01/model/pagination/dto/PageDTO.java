package com.example.project_01.model.pagination.dto;

public class PageDTO {
	private int currentPage;
	private int countRecord;
	private int totalPage;
	private int startPage;
	private int endPage;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCountRecord() {
		return countRecord;
	}
	public void setCountRecord(int countRecord) {
		this.countRecord = countRecord;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	@Override
	public String toString() {
		return "PageDTO [currentPage=" + currentPage + ", countRecord=" + countRecord + ", totalPage=" + totalPage
				+ ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	
	
	
}
