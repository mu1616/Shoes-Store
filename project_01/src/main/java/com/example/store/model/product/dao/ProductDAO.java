package com.example.store.model.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.store.model.product.dto.ProductDTO;
import com.example.store.model.product.dto.ProductEntity;
import com.example.store.model.search.dto.SearchDTO;
//상품테이블, 진열테이블
@Mapper
public interface ProductDAO {
	//상품등록
	public void productRegister(ProductEntity ProductDto);
	
	//조건에 맞는 상품 개수 가져오기
	public int countProduct(SearchDTO searchDto);
	
	//조건에 맞는 상품 가져오기
	public List<ProductDTO> selectProduct(int start, int length, SearchDTO searchDto);
	
	//상품 삭제
	public void deleteProduct(String [] product_idx);
	
	//prdocut_idx로 상품검색, ProductEntity 반환
	public ProductEntity selectOne(int product_idx);
	
	//상품정보 수정
	public void updateProduct(ProductEntity productEntity, int product_idx);
	
	//prdocut_idx로 상품검색, ProductDTO 반환
	public ProductDTO selectProductDTO(int product_idx);
	
	//메인진열 상품 가져오기
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
	public void updateRating(int product_idx);
	
	//리뷰 횟수 업데이트
	public void updateReviewCount(int product_idx, int count);
	
	//별점 0점으로 초기화
	public void initRating(int product_idx);
}
