package com.example.project_01.model.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.member.dto.RoleDTO;
import com.example.project_01.model.member.dto.SearchMemberDTO;

@Mapper
public interface MemberDAO {
	
	public MemberDTO findById(String mem_id);
	public void join(MemberDTO memberDto);
	public List<MemberDTO> selectMember(int start, int length, SearchMemberDTO searchMemberDto);
	public int countMember(SearchMemberDTO searchMemberDto);
	public void deleteOne(int mem_idx);
	public MemberDTO findByIdx(int mem_idx);
	public List<RoleDTO> selectRole();
	//특정 Member의 Role을 지정한 Role로 변경
	public void updateRole(int mem_idx, int mem_role);
	public void updateTotal(String mem_id, int pay);
	public RoleDTO selectRoleByRoleName(String role_name);
	//특정 Member의 Role을 total(총 구매가격)에 맞는 Role로 변경
	public void updateMemberRole(String mem_id);
}
