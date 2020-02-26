package com.example.project_01.model.category.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDAO {
	public String getCategoryName(int category_idx);
}
