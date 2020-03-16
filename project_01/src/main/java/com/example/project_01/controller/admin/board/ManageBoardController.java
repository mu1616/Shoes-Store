package com.example.project_01.controller.admin.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManageBoardController {
	
	@RequestMapping("/admin/board/qna")
	public String qnaList() {
		
		return "admin_productQna";
	}
}
