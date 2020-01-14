package com.example.project_01.controller.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.service.member.MemberService;

@Controller
public class MemberController {
	@Autowired
	MemberDAO memberDao;
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	MemberService memberService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginOk(@ModelAttribute MemberDTO memberDto, Model model, HttpSession session) {
		MemberDTO memberDto_select = memberService.login(memberDto);
		if (memberDto_select == null) {
			model.addAttribute("loginFail", "로그인에 실패하였습니다.");
			return "/login";
		}
		System.out.println(memberDto_select);
		session.setAttribute("memberDto", memberDto_select);
		return "home";

	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinOk(@ModelAttribute MemberDTO memberDto) {
		memberDto.setMem_pw(encoder.encode(memberDto.getMem_pw()));
		// System.out.println(memberDto);
		// 데이터 무결성 예외 처리
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
	/*
	 * @RequestMapping("/welcome") public String welcome() { return "welcome"; }
	 */
}