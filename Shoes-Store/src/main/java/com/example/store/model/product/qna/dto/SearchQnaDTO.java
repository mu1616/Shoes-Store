package com.example.store.model.product.qna.dto;

public class SearchQnaDTO {
	private int searchType=0;
	private String qna_member = "%";
	//1:전부 2:답변대기 3:답변완료
	private int is_answer = 1;
	private int qna_product;
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	public String getQna_member() {
		return qna_member;
	}
	public void setQna_member(String qna_member) {
		this.qna_member = qna_member;
	}
	public int getIs_answer() {
		return is_answer;
	}
	public void setIs_answer(int is_answer) {
		this.is_answer = is_answer;
	}
	
	public int getQna_product() {
		return qna_product;
	}
	public void setQna_product(int qna_product) {
		this.qna_product = qna_product;
	}
	@Override
	public String toString() {
		return "SearchQnaDTO [searchType=" + searchType + ", qna_member=" + qna_member + ", is_answer=" + is_answer
				+ ", qna_product=" + qna_product + "]";
	}
	
	
	
}
