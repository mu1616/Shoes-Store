package com.example.project_01.controller.admin.member;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.member.dto.RoleDTO;
import com.example.project_01.model.member.dto.SearchMemberDTO;
import com.example.project_01.model.order.dto.OrderDTO;
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
		else if(searchOption.equals("아이디")) {
			searchMemberDto.setMem_id(search);
			if(search.equals(""))
				searchMemberDto.setMem_id("%");
		}
		PageDTO pageDto = memberService.calPage(idx, searchMemberDto);
		List<MemberDTO> memberList = memberService.selectMember(pageDto.getCurrentPage(), searchMemberDto);
		model.addAttribute("memberList",memberList);
		model.addAttribute("pageDto",pageDto);
		model.addAttribute("searchOption",searchOption);
		searchMemberDto.setMem_id(search);
		model.addAttribute("searchMemberDto",searchMemberDto);
		model.addAttribute("roleList",memberDao.selectRole());
		return "admin/admin_memberlist";
	}
	@ResponseBody
	@RequestMapping("/admin/member/delete")
	public void deleteMember(int mem_idx) {
		memberDao.deleteOne(mem_idx);
	}
	
	@ResponseBody
	@RequestMapping("/admin/member/detail")
	public MemberDTO memberDetail(int mem_idx) {
		MemberDTO memberDto = memberDao.findByIdx(mem_idx);
		memberDto.setRegdate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(memberDto.getMem_regdate())));
		memberDto.setBirth((new SimpleDateFormat("yyyy-MM-dd").format(memberDto.getMem_birth())));
		return memberDto;
	}
	
	@RequestMapping("/admin/member/role")
	public String memberRole(int mem_idx, Model model) {
		MemberDTO memberDto = memberDao.findByIdx(mem_idx);
		List<RoleDTO> roleList = memberDao.selectRole();
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("roleList",roleList);
		return "popup/memberRole";
	}
	
	//관리자 임명
	@ResponseBody
	@RequestMapping("/admin/member/role/getAdmin")
	public void getAdmin(int mem_idx) {
		memberDao.updateRole(mem_idx, 1);
	}
	
	@ResponseBody
	@RequestMapping("/admin/member/role/depriveAdmin")
	public void depriveAdmin(int mem_idx) {
		MemberDTO memberDto = memberDao.findByIdx(mem_idx);
		memberService.depriveAdmin(memberDto);
	}
}
