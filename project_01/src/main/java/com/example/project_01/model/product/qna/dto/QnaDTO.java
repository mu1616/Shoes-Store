package com.example.project_01.model.product.qna.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class QnaDTO {
	private int qna_idx;
	private String qna_type;
	private String qna_title;
	private String qna_question;
	private String qna_answer;
	private int qna_secret;
	private String qna_member;
	private int qna_product;
	private Date qna_date;
	public int getQna_idx() {
		return qna_idx;
	}
	public void setQna_idx(int qna_idx) {
		this.qna_idx = qna_idx;
	}
	public String getQna_type() {
		return qna_type;
	}
	public void setQna_type(String qna_type) {
		this.qna_type = qna_type;
	}
	public String getQna_title() {
		return qna_title;
	}
	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}
	public String getQna_question() {
		return qna_question;
	}
	public void setQna_question(String qna_question) {
		this.qna_question = qna_question;
	}
	public String getQna_answer() {
		return qna_answer;
	}
	public void setQna_answer(String qna_answer) {
		this.qna_answer = qna_answer;
	}
	public int getQna_secret() {
		return qna_secret;
	}
	public void setQna_secret(int qna_secret) {
		this.qna_secret = qna_secret;
	}
	public String getQna_member() {
		return qna_member;
	}
	public void setQna_member(String qna_member) {
		this.qna_member = qna_member;
	}
	public int getQna_product() {
		return qna_product;
	}
	public void setQna_product(int qna_product) {
		this.qna_product = qna_product;
	}
	public Date getQna_date() {
		return qna_date;
	}
	public void setQna_date(Date qna_date) {
		this.qna_date = qna_date;
	}
	@Override
	public String toString() {
		return "QnaDTO [qna_idx=" + qna_idx + ", qna_type=" + qna_type + ", qna_title=" + qna_title + ", qna_question="
				+ qna_question + ", qna_answer=" + qna_answer + ", qna_secret=" + qna_secret + ", qna_member="
				+ qna_member + ", qna_product=" + qna_product + ", qna_date=" + qna_date + "]";
	}
	
	
	
	
	
	
}
