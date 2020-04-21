package com.example.project_01.controller.admin.member.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.member.dto.RoleDTO;
import com.example.project_01.service.admin.member.role.ManageRoleService;

@Controller
public class ManageRoleController {
	@Autowired
	MemberDAO memberDao;
	@Autowired
	ManageRoleService roleService;
	
	@RequestMapping("/admin/member/role/show")
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
		roleService.depriveAdmin(memberDto);
	}
	
	@RequestMapping("/admin/member/role/manage")
	public String manageRole(Model model) {
		List<RoleDTO> roleList = memberDao.selectRole();
		model.addAttribute("roleList",roleList);
		return "admin/admin_manageRole";
	}
	
	@RequestMapping("/admin/member/role/add")
	public String addRole(RoleDTO roleDto) {
		roleService.addRole(roleDto);
		return "redirect:/admin/member/role/manage";
	}
	
	@ResponseBody
	@RequestMapping("/admin/member/role/delete")
	public void deleteRole(String role_name) {
		roleService.deleteRole(role_name);
	}
	
	@ResponseBody
	@RequestMapping("/admin/member/role/modify")
	public void modifyRoleInfo(RoleDTO roleDto) {
		roleService.modifyRoleInfo(roleDto);
	}
}
