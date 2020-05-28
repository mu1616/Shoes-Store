package com.example.store.service.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.store.model.member.dao.MemberDAO;
import com.example.store.model.member.dto.MemberDTO;
import com.example.store.model.member.dto.RoleDTO;
import com.example.store.model.member.dto.SearchMemberDTO;
import com.example.store.model.pagination.dto.PageDTO;

@Service
public class ManageMemberService {
	@Autowired
	MemberDAO memberDao;
	
	//페이지당 상품개수 = 20
	public List<MemberDTO> selectMember(int currentPage, int size, SearchMemberDTO searchMemberDto) {
		int start = (currentPage-1) * size;
		List<MemberDTO> memberList = memberDao.selectMember(start, size, searchMemberDto);
		return memberList;
	}
	

}
