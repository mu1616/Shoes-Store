package com.example.project_01.controller.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.member.dto.SearchMemberDTO;
import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.service.admin.member.ManageMemberService;

@Controller
public class ManageMemberController {
	@Autowired
	MemberDAO memberDao;
	@Autowired
	ManageMemberService memberService;
	
	@RequestMapping("/admin/member/list/{idx}")
	public String memberList(Model model, 
			@PathVariable(value = "idx", required = false) int idx, 
			String searchOption, SearchMemberDTO searchMemberDto, String search) {
		if(searchOption == null || searchOption.equals("검색분류"))
			searchMemberDto.setMem_id("%");
		else if(searchOption.equals("아이디"))
			searchMemberDto.setMem_id(search);
		PageDTO pageDto = memberService.calPage(idx, searchMemberDto);
		System.out.println(searchOption+"\n"+searchMemberDto);
		List<MemberDTO> memberList = memberService.selectMember(pageDto.getCurrentPage(), searchMemberDto);
		model.addAttribute("memberList",memberList);
		model.addAttribute("pageDto",pageDto);
		model.addAttribute("searchOption",searchOption);
		model.addAttribute("searchMemberDto",searchMemberDto);
		
		return "admin_memberlist";
	}
	@ResponseBody
	@RequestMapping("/admin/member/delete")
	public void deleteMember(int mem_idx) {
		memberDao.deleteOne(mem_idx);
	}
	
	@RequestMapping("/admin/member/detail")
	public String memberDetail(int mem_idx, Model model) {
		MemberDTO memberDto = memberDao.findByIdx(mem_idx);
		model.addAttribute("memberDto", memberDto);
		return "popup/memberDetail";
	}
}
