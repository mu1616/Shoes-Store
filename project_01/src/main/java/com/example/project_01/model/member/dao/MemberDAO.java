package com.example.project_01.model.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.member.dto.MemberDTO;

@Mapper
public interface MemberDAO {
	
	public MemberDTO findById(String mem_id);
	public void join(MemberDTO memberDto);
	
}
