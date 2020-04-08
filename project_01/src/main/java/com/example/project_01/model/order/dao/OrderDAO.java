package com.example.project_01.model.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.project_01.model.order.dto.OrderDTO;

@Mapper
public interface OrderDAO {
	public void insertOrder(OrderDTO orderDto);
}
