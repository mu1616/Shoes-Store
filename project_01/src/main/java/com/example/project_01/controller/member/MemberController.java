package com.example.project_01.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;

@Controller
public class MemberController {
	@Autowired
	MemberDAO memberDao;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/join", method=RequestMethod.GET)
	public String join() {
		return "join";
	}
	
	@RequestMapping(value = "/join", method=RequestMethod.POST)
	public String joinOk(@ModelAttribute MemberDTO memberDto) {
		System.out.println(memberDto);
		try {
		memberDao.join(memberDto);
		} catch (DataIntegrityViolationException e) {
			return "error/joinError";
		}
		return "welcome";
	}

	@ResponseBody
	@RequestMapping("/join/idcheck")
	public int idCheck(String mem_id) {
		if (memberDao.idCheck(mem_id) == null) {
			return 0;
		} else {
			return 1;
		}
	}
}