package com.example.project_01.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_01.model.pagination.dto.PageDTO;
import com.example.project_01.model.product.dao.ProductDAO;
import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.search.dto.SearchDTO;
@Service
public class ProductService {
	@Autowired
	ProductDAO productDao;
	
	//페이지당 상품개수 = 20
		public PageDTO calPage(int currentPage, int size, SearchDTO searchDto) {
			int countRecord;
			int startPage;
			int endPage;
			int totalPage;
			PageDTO pageDto = new PageDTO();
			pageDto.setCurrentPage(currentPage);
			countRecord = productDao.countProduct(searchDto);
			pageDto.setCountRecord(countRecord);
			totalPage = (int)Math.ceil(countRecord/(double)size);
			pageDto.setTotalPage(totalPage);
			startPage = (currentPage-1)/10*10+1;
			pageDto.setStartPage(startPage);
			endPage = (startPage+9>totalPage)?totalPage:startPage+9;
			pageDto.setEndPage(endPage);
			return pageDto;
		}
		
		public List<ProductDTO> selectProduct(int currentPage, int size, SearchDTO searchDto) {
			int start = (currentPage-1) * size;
			List<ProductDTO> productList = productDao.selectProduct(start, size, searchDto);
			return productList;
		}
}
