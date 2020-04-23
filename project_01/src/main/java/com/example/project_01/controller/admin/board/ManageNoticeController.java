package com.example.project_01.controller.admin.board;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.project_01.model.notice.dao.NoticeDAO;
import com.example.project_01.model.notice.dto.NoticeDTO;
import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.service.pagination.PageService;

@Controller
public class ManageNoticeController {
	@Autowired
	NoticeDAO noticeDao;
	@Autowired
	PageService pageService;
	
	@RequestMapping("/admin/board/notice/write")
	public String writeNotice(Model model, String notice_idx) {
		List<String> typeList = noticeDao.selectNoticeType();
		model.addAttribute("typeList",typeList);
		if(notice_idx != null) {
			NoticeDTO noticeDto = noticeDao.selectOne(Integer.parseInt(notice_idx));
			model.addAttribute("noticeDto",noticeDto);
		}
		return "admin/admin_writeNotice";
	}
	
	@RequestMapping("/admin/board/notice/writeComplete")
	public String writeComplete(NoticeDTO noticeDto, Principal principal) {
		noticeDto.setNotice_id(principal.getName());
		noticeDao.insertOne(noticeDto);
		return "admin/admin";
	}
	
	@RequestMapping("/admin/board/notice/modify")
	public String modify(NoticeDTO noticeDto, Principal principal) {
		noticeDto.setNotice_id(principal.getName());
		return "admin/admin_modifyComplete";
	}
	
	@RequestMapping("/admin/board/notice/{currentPage}")
	public String noticeList(@PathVariable("currentPage")int currentPage) {
		int totalRecord = noticeDao.countRecord();
		PageDTO pageDto = pageService.calPage(currentPage, 20, totalRecord, 10);
		return "admin/admin_noticeList.html";
	}
}
