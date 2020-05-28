package com.example.project_01.util;

import org.springframework.stereotype.Service;

import com.example.project_01.model.pagination.dto.PageDTO;

@Service
public class Paging {

	// (현재페이지,한페이지에 보여줄 레코드 수, 전체레코드수, 페이지블럭의 수)
	public PageDTO calPage(int currentPage, int size, int totalRecord, int block) {
		int startPage;
		int endPage;
		int totalPage;
		PageDTO pageDto = new PageDTO();

		totalPage = (int) Math.ceil(totalRecord / (double) size);
		startPage = (currentPage - 1) / block * block + 1;
		endPage = (startPage + (block - 1) > totalPage) ? totalPage : startPage + (block - 1);

		pageDto.setCurrentPage(currentPage);
		pageDto.setSize(size);
		pageDto.setBlock(block);
		pageDto.setCountRecord(totalRecord);
		pageDto.setTotalPage(totalPage);
		pageDto.setStartPage(startPage);
		pageDto.setEndPage(endPage);

		return pageDto;
	}

}
