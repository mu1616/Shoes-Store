package com.example.project_01.service.admin.member.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;

@Service
public class ManageRoleService {
	@Autowired
	MemberDAO memberDao;
	
	@Transactional
	public void depriveAdmin(MemberDTO memberDto) {
		memberDao.updateRole(memberDto.getMem_idx(), 2);
	}
}
