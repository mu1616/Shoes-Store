package com.example.store.model.category.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDAO {
	//특정 카테고리명 가져오기
	public String getCategoryName(int category_idx);
	
	//모든 카테고리명 가져오기
	public List<String> selectAll();
}
