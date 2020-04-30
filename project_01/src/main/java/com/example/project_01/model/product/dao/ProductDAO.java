package com.example.project_01.model.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.product.dto.ProductDTO;
import com.example.project_01.model.product.dto.ProductEntity;
import com.example.project_01.model.search.dto.SearchDTO;

@Mapper
public interface ProductDAO {
	public void productRegister(ProductEntity ProductDto);
	public int countProduct(SearchDTO searchDto);
	public List<ProductDTO> selectProduct(int start, int length, SearchDTO searchDto);
	public void deleteProduct(String [] product_idx);
	//prdocut_idx로 상품검색, ProductEntity 반환
	public ProductEntity selectOne(int product_idx);
	public void updateProduct(ProductEntity productEntity, int product_idx);
	//prdocut_idx로 상품검색, ProductDTO 반환
	public ProductDTO selectProductDTO(int product_idx);
	public List<ProductDTO> selectProductByDisplay(int display_md);
	//메인진열 상품추가
	public void addProductMainDisplay(int product_idx, int display_md);
	//메인진열 상품삭제(모든상품)
	public void delProductMainDisplay(int display_md);
	//특정 상품을 메인진열에서 제거
	public void delProductMainDisplayByProduct(int product_idx);
	//판매횟수 업데이트
	public void updateSaleCount(int product_idx, int count);
	//별점 업데이트
	public void updateRating(int product_idx, int product_rating);
	//리뷰 횟수 업데이트
	public void updateReviewCount(int product_idx, int count);
}
