package com.example.store.service.admin.member.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.store.model.member.dao.MemberDAO;
import com.example.store.model.member.dto.MemberDTO;
import com.example.store.model.member.dto.RoleDTO;

@Service
public class ManageRoleService {
	@Autowired
	MemberDAO memberDao;
	
	//관리자 해임
	@Transactional
	public void depriveAdmin(MemberDTO memberDto) {
		memberDao.updateRole(memberDto.getMem_idx(), 2);
	}
	
	//등급 추가
	@Transactional
	public void addRole(RoleDTO roleDto) {
		memberDao.insertRole(roleDto);
		memberDao.sortRole();	
		memberDao.UpdateAllMemberRole();
	}
	
	//등급 삭제
	@Transactional
	public void deleteRole(String role_name) {
		memberDao.deleteRole(role_name);
		memberDao.sortRole();
		memberDao.UpdateAllMemberRole();
	}
	
	//등급 수정
	@Transactional
	public void modifyRoleInfo(RoleDTO roleDto) {
		memberDao.updateRoleInfo(roleDto);
		memberDao.sortRole();
		memberDao.UpdateAllMemberRole();
	}
}
