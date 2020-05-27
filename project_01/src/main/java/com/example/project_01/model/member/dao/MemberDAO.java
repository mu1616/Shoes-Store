package com.example.project_01.model.member.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.member.dto.RoleDTO;
import com.example.project_01.model.member.dto.SearchMemberDTO;
// MEMBER Table, ROLE Table, EmailAuth Table
@Mapper
public interface MemberDAO {
	//회원아이디에 맞는 레코드 가져오기
	public MemberDTO findById(String mem_id);
	
	//회원가입
	public void join(MemberDTO memberDto);
	
	//특정 조건에 맞는 회원 레코드 가져오기 (SearchMemberDto로 조건 전달)
	public List<MemberDTO> selectMember(int start, int length, SearchMemberDTO searchMemberDto);
	
	//특정 조건에 맞는 회원 수 가져오기 (SearchMemberDto로 조건 전달)
	public int countMember(SearchMemberDTO searchMemberDto);
	
	//회원 탈퇴
	public void deleteOne(int mem_idx);
	
	//회원번호에 맞는 레코드 가져오기
	public MemberDTO findByIdx(int mem_idx);
	
	//등급 테이블에서 모든 등급정보 가격순으로 가져오기
	public List<RoleDTO> selectRole();
	
	//특정 Member의 Role을 지정한 Role로 변경
	public void updateRole(int mem_idx, int mem_role);
	
	//특정 Member의 total(총 구매가격) 변경
	public void updateTotal(String mem_id, int pay);
	
	//등급명에 맞는 등급레코드 가져오기
	public RoleDTO selectRoleByRoleName(String role_name);
	
	//특정 Member의 Role을 total(총 구매가격)에 맞는 Role로 변경
	public void updateMemberRole(String mem_id);
	
	//새로운 등급 생성
	public void insertRole(RoleDTO roleDto);
	
	//가격 순으로 role_idx 정렬  (ADMIN 제외)
	public void sortRole();
	
	//모든 Member의 role을 업데이트시킴 (새로운 등급 생성하거나 수정 이후에)
	public void UpdateAllMemberRole();
	
	//해당 Role 삭제
	public void deleteRole(String role_name);
	
	//Role 레코드 수정
	public void updateRoleInfo(RoleDTO roleDto);
	
	//이메일인증 테이블 조회
	public HashMap<String, String> selectAuthMailInfo(String mem_id);
	
	//이메일 인증 정보 수정
	public void updateAuthMailInfo(String mem_id, String mail, String auth_key);
	
	//이메일 인증 정보 생성
	public void insertAuthMailInfo(String mem_id, String mail, String auth_key);
	
	//회원의 이메일속성 수정
	public void updateMemberMail(String mem_id, String mail);
	
	//회원정보(핸드폰, 주소) 수정
	public void updateMemberInfo(String mem_id, String mem_phone, String mem_postcode, String mem_addr1, String mem_addr2);
	
	//패스워드 변경
	public void updatePassword(String mem_id, String mem_pw);
}
