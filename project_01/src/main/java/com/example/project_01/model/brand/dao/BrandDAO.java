package com.example.project_01.model.brand.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandDAO {
	public List<String> selectAll();
}
