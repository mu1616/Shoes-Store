package com.example.project_01.service.pagination;

import org.springframework.stereotype.Service;

import com.example.project_01.model.pagination.dto.PageDTO;

@Service
public class PageServiceImpl implements PageService {

	@Override
	public PageDTO calPage(int currentPage, int size, int totalRecord, int block) {
		int startPage;
		int endPage;
		int totalPage;
		PageDTO pageDto = new PageDTO();
		pageDto.setCurrentPage(currentPage);
		pageDto.setSize(size);
		pageDto.setBlock(block);
		pageDto.setCountRecord(totalRecord);
		totalPage = (int)Math.ceil(totalRecord/(double)size);
		pageDto.setTotalPage(totalPage);
		startPage = (currentPage-1)/block*block+1;
		pageDto.setStartPage(startPage);
		endPage = (startPage+(block-1)>totalPage)?totalPage:startPage+(block-1);
		pageDto.setEndPage(endPage);
		return pageDto;
	}

}
