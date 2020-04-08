package com.example.project_01;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project_01.model.order.dao.OrderDAO;
import com.example.project_01.model.order.dto.OrderDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OrderDaoTest {
	@Autowired
	OrderDAO orderDao;
	@Test
	public void insertOrder() {
		OrderDTO orderDto = new OrderDTO();
		orderDto.setProduct_idx(346);
		orderDto.setSize(250);
		orderDto.setCount(3);
		orderDto.setMem_id("mu1616");
		orderDto.setMem_name("강민수");
		orderDto.setOrder_addr1("addr1");
		orderDto.setOrder_addr2("addr2");
		orderDto.setOrder_phone("01047156017");
		orderDto.setOrder_postcode("33333");
		orderDto.setPay(30000);
		orderDao.insertOrder(orderDto);
	}
}
