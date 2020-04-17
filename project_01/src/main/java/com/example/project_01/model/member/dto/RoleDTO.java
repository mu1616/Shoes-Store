package com.example.project_01.model.member.dto;

public class RoleDTO {
	int role_idx;
	String role_name;
	int role_price;
	
	public int getRole_idx() {
		return role_idx;
	}
	public void setRole_idx(int role_idx) {
		this.role_idx = role_idx;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
	public int getRole_price() {
		return role_price;
	}
	public void setRole_price(int role_price) {
		this.role_price = role_price;
	}
	@Override
	public String toString() {
		return "RoleDTO [role_idx=" + role_idx + ", role_name=" + role_name + ", role_price=" + role_price + "]";
	}
	
	
}
