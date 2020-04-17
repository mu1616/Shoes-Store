package com.example.project_01.service.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.member.dto.RoleDTO;
import com.example.project_01.model.member.dto.SearchMemberDTO;
import com.example.project_01.model.pagination.dto.PageDTO;

@Service
public class ManageMemberService {
	@Autowired
	MemberDAO memberDao;
	//페이지당 상품개수 = 20
	public PageDTO calPage(int currentPage, SearchMemberDTO searchMemberDto) {
		int countRecord;
		int startPage;
		int endPage;
		int totalPage;
		PageDTO pageDto = new PageDTO();
		pageDto.setCurrentPage(currentPage);
		countRecord = memberDao.countMember(searchMemberDto);
		pageDto.setCountRecord(countRecord);
		totalPage = (int)Math.ceil(countRecord/(double)20);
		pageDto.setTotalPage(totalPage);
		//페이지버튼에서 가장 첫 페이지버튼의 숫자
		startPage = (currentPage-1)/10*10+1;
		pageDto.setStartPage(startPage);
		//페이지버튼에서 가장 마지막 페이지버튼의 숫자
		endPage = (startPage+9>totalPage)?totalPage:startPage+9;
		pageDto.setEndPage(endPage);
		return pageDto;
	}
	
	//페이지당 상품개수 = 20
	public List<MemberDTO> selectMember(int currentPage, SearchMemberDTO searchMemberDto) {
		int start = (currentPage-1) * 20;
		int length = 20;
		List<MemberDTO> memberList = memberDao.selectMember(start, length, searchMemberDto);
		return memberList;
	}
	
	@Transactional
	public void depriveAdmin(MemberDTO memberDto) {
		memberDao.updateRole(memberDto.getMem_idx(), 2);
	}
}
