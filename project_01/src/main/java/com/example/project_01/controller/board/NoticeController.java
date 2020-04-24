package com.example.project_01.controller.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.notice.dao.NoticeDAO;
import com.example.project_01.model.notice.dto.NoticeDTO;
import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.service.board.NoticeService;
import com.example.project_01.service.pagination.PageService;

@Controller
public class NoticeController {
	@Autowired
	NoticeDAO noticeDao;
	@Autowired
	PageService pageService;
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping("/board/list")
	public String noticeList(Model model) {
		return "board/noticeList";
	}
	
	@RequestMapping("/board/getList")
	public String getList(String notice_type, int currentPage, Model model) {
		if(notice_type.equals("notice")) notice_type = "공지사항";
		int totalRecord = noticeDao.selectCountByType(notice_type);
		PageDTO pageDto = pageService.calPage(currentPage, 15, totalRecord, 10);
		List<NoticeDTO> noticeList = noticeService.selectNotice(notice_type, currentPage, 15);
		model.addAttribute("noticeList",noticeList);
		model.addAttribute("pageDto",pageDto);
		if(notice_type.equals("공지사항"))
			return "/board/noticeTable";
		if(notice_type.equals("faq"))
			return "/board/faqTable";
		return null;
	}
	
	@RequestMapping("/board/notice/contents")
	public String contents(int notice_idx, Model model) {
		NoticeDTO noticeDto = noticeDao.selectOne(notice_idx);
		model.addAttribute("noticeDto",noticeDto);
		return "board/noticeDetail";
	}
}