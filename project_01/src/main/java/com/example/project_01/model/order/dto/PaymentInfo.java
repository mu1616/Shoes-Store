package com.example.project_01.model.order.dto;

public class PaymentInfo {
	private String message;
	private String merchant_uid;
	private String buyer_tel;
	private int amount;
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMerchant_uid() {
		return merchant_uid;
	}
	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
	public String getBuyer_tel() {
		return buyer_tel;
	}
	public void setBuyer_tel(String buyer_tel) {
		this.buyer_tel = buyer_tel;
	}
	@Override
	public String toString() {
		return "PaymentInfo [message=" + message + ", merchant_uid=" + merchant_uid + ", buyer_tel=" + buyer_tel
				+ ", amount=" + amount + "]";
	}
	
	
	
}
