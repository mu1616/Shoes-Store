package com.example.project_01.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/join")
	public String join() {
		return "join";
	}
}