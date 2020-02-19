package com.example.project_01.model.member.dto;

public class SearchMemberDTO {
	private String mem_id = "%";
	private String mem_role = "%";
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_role() {
		return mem_role;
	}
	public void setMem_role(String mem_role) {
		this.mem_role = mem_role;
	}
	@Override
	public String toString() {
		return "SearchMemberDTO [mem_id=" + mem_id + ", mem_role=" + mem_role + "]";
	}
	
	
}
