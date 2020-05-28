package com.example.store.controller.admin.member;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.store.model.member.dao.MemberDAO;
import com.example.store.model.member.dto.MemberDTO;
import com.example.store.model.member.dto.RoleDTO;
import com.example.store.model.member.dto.SearchMemberDTO;
import com.example.store.model.pagination.dto.PageDTO;
import com.example.store.service.admin.member.ManageMemberService;
import com.example.store.util.Paging;

@Controller
public class ManageMemberController {
	@Autowired
	MemberDAO memberDao;
	@Autowired
	ManageMemberService memberService;
	@Autowired
	Paging pageService;
	
	//회원정보 리스트 페이지
	@RequestMapping("/admin/member/list/{idx}")
	public String memberList(Model model, 
			@PathVariable(value = "idx", required = false) int currentPage, 
			String searchOption, SearchMemberDTO searchMemberDto, String search) {
		// 검색조건 필터링
		if(searchOption == null || searchOption.equals("검색분류"))
			searchMemberDto.setMem_id("%");
		else if(searchOption.equals("아이디")) {
			searchMemberDto.setMem_id(search);
			if(search.equals(""))
				searchMemberDto.setMem_id("%");
		}
		
		int totalRecord = memberDao.countMember(searchMemberDto);
		PageDTO pageDto = pageService.calPage(currentPage, 20, totalRecord, 10);
		List<MemberDTO> memberList = memberService.selectMember(currentPage, 20, searchMemberDto);
		
		model.addAttribute("memberList",memberList);
		model.addAttribute("pageDto",pageDto);
		model.addAttribute("searchOption",searchOption);
		searchMemberDto.setMem_id(search);
		model.addAttribute("searchMemberDto",searchMemberDto);
		model.addAttribute("roleList",memberDao.selectRole());
		
		return "admin/admin_memberlist";
	}
	
	//회원 삭제(탈퇴)시 처리
	@ResponseBody
	@RequestMapping("/admin/member/delete")
	public void deleteMember(int mem_idx) {
		memberDao.deleteOne(mem_idx);
	}
	
	//회원 상세정보 페이지
	@ResponseBody
	@RequestMapping("/admin/member/detail")
	public MemberDTO memberDetail(int mem_idx) {
		MemberDTO memberDto = memberDao.findByIdx(mem_idx);
		memberDto.setRegdate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(memberDto.getMem_regdate())));
		memberDto.setBirth((new SimpleDateFormat("yyyy-MM-dd").format(memberDto.getMem_birth())));
		return memberDto;
	}
	

}
