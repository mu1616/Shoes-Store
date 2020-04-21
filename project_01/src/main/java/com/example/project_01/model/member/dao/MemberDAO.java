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
	//특정 Member의 total(총 구매가격) 변경
	public void updateTotal(String mem_id, int pay);
	public RoleDTO selectRoleByRoleName(String role_name);
	//특정 Member의 Role을 total(총 구매가격)에 맞는 Role로 변경
	public void updateMemberRole(String mem_id);
	public void insertRole(RoleDTO roleDto);
	//가격 순으로 role_idx 정렬  (ADMIN 제외)
	public void sortRole();
	//모든 Member의 role을 업데이트시킴 
	public void UpdateAllMemberRole();
	//해당 Role 삭제
	public void deleteRole(String role_name);
	//Role 레코드 수정
	public void updateRoleInfo(RoleDTO roleDto);
}
