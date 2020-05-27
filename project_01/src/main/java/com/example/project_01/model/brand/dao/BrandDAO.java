package com.example.project_01.model.brand.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandDAO {
	//모든 브랜드명 셀렉트
	public List<String> selectAll();
}
