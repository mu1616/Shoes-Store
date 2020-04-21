package com.example.project_01.service.admin.member.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.member.dto.RoleDTO;

@Service
public class ManageRoleService {
	@Autowired
	MemberDAO memberDao;
	
	@Transactional
	public void depriveAdmin(MemberDTO memberDto) {
		memberDao.updateRole(memberDto.getMem_idx(), 2);
	}
	
	@Transactional
	public void addRole(RoleDTO roleDto) {
		memberDao.insertRole(roleDto);
		memberDao.sortRole();	
		memberDao.UpdateAllMemberRole();
	}
	
	@Transactional
	public void deleteRole(String role_name) {
		memberDao.deleteRole(role_name);
		memberDao.sortRole();
		memberDao.UpdateAllMemberRole();
	}
	
	@Transactional
	public void modifyRoleInfo(RoleDTO roleDto) {
		memberDao.updateRoleInfo(roleDto);
		memberDao.sortRole();
		memberDao.UpdateAllMemberRole();
	}
}
