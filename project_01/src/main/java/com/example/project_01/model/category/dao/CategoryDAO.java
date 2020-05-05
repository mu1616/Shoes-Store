package com.example.project_01.model.category.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDAO {
	public String getCategoryName(int category_idx);
	public List<String> selectAll();
}
