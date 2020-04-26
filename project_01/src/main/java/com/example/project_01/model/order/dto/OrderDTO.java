package com.example.project_01.model.order.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class OrderDTO {
	private int order_idx;
	private String mem_id;
	private String mem_name;
	private String order_postcode;
	private String order_addr1;
	private String order_addr2;
	private String order_phone;
	private Date order_date;
	private String order_state;
	private String order_code;
	private String merchant_uid;
	private int product_idx;
	private int size;
	private int count;
	private int pay;
	private String product_image;
	private String product_brand;
	private String product_name;
	private String date;
	
	
	public String getMerchant_uid() {
		return merchant_uid;
	}
	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getOrder_idx() {
		return order_idx;
	}
	public void setOrder_idx(int order_idx) {
		this.order_idx = order_idx;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getOrder_postcode() {
		return order_postcode;
	}
	public void setOrder_postcode(String order_postcode) {
		this.order_postcode = order_postcode;
	}
	public String getOrder_addr1() {
		return order_addr1;
	}
	public void setOrder_addr1(String order_addr1) {
		this.order_addr1 = order_addr1;
	}
	public String getOrder_addr2() {
		return order_addr2;
	}
	public void setOrder_addr2(String order_addr2) {
		this.order_addr2 = order_addr2;
	}
	public String getOrder_phone() {
		return order_phone;
	}
	public void setOrder_phone(String order_phone) {
		this.order_phone = order_phone;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public int getProduct_idx() {
		return product_idx;
	}
	public void setProduct_idx(int product_idx) {
		this.product_idx = product_idx;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPay() {
		return pay;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
	
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
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
	@Override
	public String toString() {
		return "OrderDTO [order_idx=" + order_idx + ", mem_id=" + mem_id + ", mem_name=" + mem_name
				+ ", order_postcode=" + order_postcode + ", order_addr1=" + order_addr1 + ", order_addr2=" + order_addr2
				+ ", order_phone=" + order_phone + ", order_date=" + order_date + ", order_state=" + order_state
				+ ", order_code=" + order_code + ", merchant_uid=" + merchant_uid + ", product_idx=" + product_idx
				+ ", size=" + size + ", count=" + count + ", pay=" + pay + ", product_image=" + product_image
				+ ", product_brand=" + product_brand + ", product_name=" + product_name + ", date=" + date + "]";
	}
	
	
	
	
	
	
	
}
