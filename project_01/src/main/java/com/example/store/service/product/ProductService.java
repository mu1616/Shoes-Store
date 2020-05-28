package com.example.store.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.store.model.pagination.dto.PageDTO;
import com.example.store.model.product.dao.ProductDAO;
import com.example.store.model.product.dto.ProductDTO;
import com.example.store.model.search.dto.SearchDTO;

@Service
public class ProductService {
	@Autowired
	ProductDAO productDao;

	public List<ProductDTO> selectProduct(int currentPage, int size, SearchDTO searchDto) {
		int start = (currentPage - 1) * size;
		List<ProductDTO> productList = productDao.selectProduct(start, size, searchDto);
		return productList;
	}
}
