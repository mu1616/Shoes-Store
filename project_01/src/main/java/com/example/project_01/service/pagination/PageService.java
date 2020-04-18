package com.example.project_01.service.pagination;

import com.example.project_01.model.pagination.dto.PageDTO;

public interface PageService {
	//(현재페이지,한페이지에 보여줄 레코드 수, 전체레코드수, 페이지블럭의 수)
	public PageDTO calPage(int currentPage, int size, int totalRecord, int block);
}
