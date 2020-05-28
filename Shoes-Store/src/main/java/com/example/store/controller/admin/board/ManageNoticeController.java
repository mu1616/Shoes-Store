package com.example.store.controller.admin.board;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.store.model.notice.dao.NoticeDAO;
import com.example.store.model.notice.dto.NoticeDTO;
import com.example.store.model.pagination.dto.PageDTO;
import com.example.store.service.admin.board.ManageNoticeService;
import com.example.store.util.Paging;

@Controller
public class ManageNoticeController {
	@Autowired
	NoticeDAO noticeDao;
	@Autowired
	Paging pageService;
	@Autowired
	ManageNoticeService noticeService;
	
	//공지사항 작성 폼
	@RequestMapping("/admin/board/notice/write")
	public String writeNotice(Model model, String notice_idx) {
		List<String> typeList = noticeDao.selectNoticeType();
		model.addAttribute("typeList",typeList);
		
		//새 글 작성이 아니라 수정일 경우
		if(notice_idx != null) {
			NoticeDTO noticeDto = noticeDao.selectOne(Integer.parseInt(notice_idx));
			model.addAttribute("noticeDto",noticeDto);
		}
		
		return "admin/admin_writeNotice";
	}
	
	// 공지사항 작성요청 시 처리
	@RequestMapping("/admin/board/notice/writeComplete")
	public String writeComplete(NoticeDTO noticeDto, Principal principal) {
		noticeDto.setNotice_id(principal.getName());
		noticeDao.insertOne(noticeDto);
		return "redirect:/admin/board/notice/1";
	}
	
	//공지사항 수정요청 시 처리
	@RequestMapping("/admin/board/notice/modify")
	public String modify(NoticeDTO noticeDto, Principal principal) {
		noticeDto.setNotice_id(principal.getName());
		noticeDao.updateOne(noticeDto);
		return "admin/admin_modifyComplete";
	}
	
	//공지사항 리스트 페이지
	@RequestMapping("/admin/board/notice/{currentPage}")
	public String noticeList(@PathVariable("currentPage")int currentPage, Model model) {
		int totalRecord = noticeDao.countRecord();
		PageDTO pageDto = pageService.calPage(currentPage, 20, totalRecord, 10);
		List<NoticeDTO> noticeList = noticeService.selectNotice(currentPage, 20);
		
		model.addAttribute("pageDto",pageDto);
		model.addAttribute("noticeList",noticeList);
		
		return "admin/admin_noticeList.html";
	}
	
	//공지사항 삭제요청 처리
	@RequestMapping("/admin/board/notice/delete")
	public String deleteNotice(int notice_idx) {
		noticeDao.deleteOne(notice_idx);
		return "redirect:/admin/board/notice/1";
	}
}
