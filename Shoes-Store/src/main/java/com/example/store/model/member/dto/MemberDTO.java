package com.example.store.model.member.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class MemberDTO {
	private int mem_idx;
	private String mem_name;
	//yyyyMMdd 형식으로 데이터가 입력되어야함
	@DateTimeFormat(pattern = "yyyyMMdd")
	private Date mem_birth;
	private String mem_id;
	private String mem_pw;
	private String mem_phone;
	private String mem_addr1;
	private String mem_addr2;
	private Date mem_regdate;
	private String mem_role;
	private int mem_total;
	private String mem_postcode;
	private String mem_mail;
	private String birth;
	private String regdate;
	
	
	public String getMem_mail() {
		return mem_mail;
	}
	public void setMem_mail(String mem_mail) {
		this.mem_mail = mem_mail;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getMem_postcode() {
		return mem_postcode;
	}
	public void setMem_postcode(String mem_postcode) {
		this.mem_postcode = mem_postcode;
	}
	public int getMem_idx() {
		return mem_idx;
	}
	public void setMem_idx(int mem_idx) {
		this.mem_idx = mem_idx;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public Date getMem_birth() {
		return mem_birth;
	}
	public void setMem_birth(Date mem_birth) {
		this.mem_birth = mem_birth;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_pw() {
		return mem_pw;
	}
	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_addr1() {
		return mem_addr1;
	}
	public void setMem_addr1(String mem_addr1) {
		this.mem_addr1 = mem_addr1;
	}
	public String getMem_addr2() {
		return mem_addr2;
	}
	public void setMem_addr2(String mem_addr2) {
		this.mem_addr2 = mem_addr2;
	}
	public Date getMem_regdate() {
		return mem_regdate;
	}
	public void setMem_regdate(Date mem_regdate) {
		this.mem_regdate = mem_regdate;
	}
	public String getMem_role() {
		return mem_role;
	}
	public void setMem_role(String mem_role) {
		this.mem_role = mem_role;
	}
	
	
	public int getMem_total() {
		return mem_total;
	}
	public void setMem_total(int mem_total) {
		this.mem_total = mem_total;
	}
	@Override
	public String toString() {
		return "MemberDTO [mem_idx=" + mem_idx + ", mem_name=" + mem_name + ", mem_birth=" + mem_birth + ", mem_id="
				+ mem_id + ", mem_pw=" + mem_pw + ", mem_phone=" + mem_phone + ", mem_addr1=" + mem_addr1
				+ ", mem_addr2=" + mem_addr2 + ", mem_regdate=" + mem_regdate + ", mem_role=" + mem_role
				+ ", mem_total=" + mem_total + ", mem_postcode=" + mem_postcode + ", mem_mail=" + mem_mail + ", birth="
				+ birth + ", regdate=" + regdate + "]";
	}
	
	
	
	

	
	
}
